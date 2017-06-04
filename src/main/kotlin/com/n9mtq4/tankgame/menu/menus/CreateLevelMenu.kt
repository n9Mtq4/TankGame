package com.n9mtq4.tankgame.menu.menus

import com.n9mtq4.kotlin.extlib.ignore
import com.n9mtq4.tankgame.GAME_SCALE
import com.n9mtq4.tankgame.GAME_WIDTH
import com.n9mtq4.tankgame.GameClass
import com.n9mtq4.tankgame.LEVEL_HEIGHT
import com.n9mtq4.tankgame.LEVEL_WIDTH
import com.n9mtq4.tankgame.SCORE_BACKGROUND_COLOR
import com.n9mtq4.tankgame.SCORE_OFFSET
import com.n9mtq4.tankgame.getHeight
import com.n9mtq4.tankgame.level.Level
import com.n9mtq4.tankgame.level.Tile
import com.n9mtq4.tankgame.menu.Menu
import com.n9mtq4.tankgame.menu.MenuManager
import com.n9mtq4.tankgame.utils.POINT2I_INVALID
import com.n9mtq4.tankgame.utils.Point2i
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent

/**
 * Created by will on 5/29/17 at 8:54 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class CreateLevelMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	companion object {
		val CONTROL_FONT = Font("Verdana", Font.BOLD, 10 * GAME_SCALE)
		val CONTROL_FONT_SPACING = 2 * GAME_SCALE
	}
	
	private var mousePressed = false
	private var barrier = true
	private var mx = 0
	private var my = 0
	
	private var brushRadius = 0
	private var lastDragPoint = POINT2I_INVALID
	
	private var level = Level(LEVEL_WIDTH, LEVEL_HEIGHT, menuManager.gameMenu)
	
	override fun draw(g: Graphics) {
		
		super.draw(g)
		
		// have to draw the currently created level
		level.draw(g)
		level.drawSpawnLocations(g)
		
		// draw brush
		g.color = Color.DARK_GRAY
		val bwidth = brushRadius * level.tileWidth * GAME_SCALE + (level.tileWidth / 2)
		val bheight = brushRadius * level.tileHeight * GAME_SCALE + (level.tileHeight / 2)
		g.drawRect(mx - bwidth / 2, my - bheight / 2, bwidth, bheight)
		
		// score card
		g.color = SCORE_BACKGROUND_COLOR
		g.fillRect(0, 0, GAME_WIDTH * GAME_SCALE, SCORE_OFFSET * GAME_SCALE)
		
		g.color = Color.BLACK
		g.font = CONTROL_FONT
		
		val height1 = g.font.getHeight("ABCDEFG", g.frc)
		g.drawString("Brush Radius: $brushRadius", 0, height1 + GameClass.SCORE_MARGIN_TOP)
		g.drawString("[ to decrease size, ] to increase size", 0, 2 * (height1 + CONTROL_FONT_SPACING) + GameClass.SCORE_MARGIN_TOP)
		g.drawString("Push 1 to set spawn for team 1", 0, 3 * (height1 + CONTROL_FONT_SPACING) + GameClass.SCORE_MARGIN_TOP)
		g.drawString("Push 2 to set spawn for team 2", 0, 4 * (height1 + CONTROL_FONT_SPACING) + GameClass.SCORE_MARGIN_TOP)
		g.drawString("Push ENTER to place", 0, 5 * (height1 + CONTROL_FONT_SPACING) + GameClass.SCORE_MARGIN_TOP)
		
	}
	
	override fun tick() {
		
		
		
	}
	
	private fun checkBarrierStatus(x: Int, y: Int) = ignore {
		
		val levelX = level.aGetRenderX(x.toDouble()).toInt()
		val levelY = level.aGetRenderY(y.toDouble()).toInt()
		
		val tile = level[levelX, levelY]
		barrier = tile is Tile.OpenTile
		
	}
	
	private fun onDrag(x: Int, y: Int) {
		
		if (lastDragPoint != POINT2I_INVALID) {
			
			// ok, since the drag doesn't update fast enough, lets do a linear raycast to the new point from the old one
			
			// TODO: this raycasting has an issue with raycasting vertically; it skips some
			
			// get the angle
			val angle = Math.atan2(y - lastDragPoint.y.toDouble(), x - lastDragPoint.x.toDouble())
			// get the deltas to add on each iteration
			val dx = 1 * Math.cos(angle)
			val dy = 1 * Math.sin(angle)
			
			// start at last point
			var cx = lastDragPoint.x.toDouble()
			var cy = lastDragPoint.y.toDouble()
			
			// go until (cx, cy) is within range of (x, y)
			// can't use equal cause rounding issues
			while (Math.abs(cx - x) !in 0..1 && Math.abs(cy - y) !in 0..1) {
				
				// update (cx, cy)
				toggleLevelBarrierWindowPos(cx.toInt(), cy.toInt()) // TODO: should use rounding
				
				// increment (cx, cy)
				cx += dx
				cy += dy
				
			}
			
		}
		
		// do the one the brush is currently on
		toggleLevelBarrierWindowPos(x, y)
		// this is now the last drag point
		lastDragPoint = Point2i(x, y)
		
	}
	
	private fun toggleLevelBarrierWindowPos(x: Int, y: Int) = ignore {
		
		getAllPointsInBrushFromMouse(x, y).forEach { (levelX, levelY) -> 
			if (barrier) level[levelX, levelY] = Tile.ClosedTile(levelX, levelY, menuManager.gameMenu)
			else level[levelX, levelY] = Tile.OpenTile(levelX, levelY, menuManager.gameMenu)
		}
		
	}
	
	private fun getAllPointsInBrushFromMouse(x: Int, y: Int): List<Point2i> {
		
		val pList = mutableListOf<Point2i>()
		
		// get reference point
		val levelX = level.aGetRenderX(x.toDouble()).toInt()
		val levelY = level.aGetRenderY(y.toDouble()).toInt()
		
		// add a square with this level points
		for (by in -brushRadius..brushRadius) (-brushRadius..brushRadius).mapTo(pList) { bx -> Point2i(bx + levelX, by + levelY) }
		
		return pList.filter { it.x in 0..level.width - 1 && it.y in 0..level.height }
		
	}
	
	// TODO: messy code
	private fun setSpawn(team: Int) {
		
		// search for a already set spawn
		
		val currentLocation = level.getSpawnLocationForTeam(team)
		
		currentLocation.takeUnless { it == POINT2I_INVALID }?.run { 
			level[x, y] = Tile.OpenTile(x, y, menuManager.gameMenu)
		}
		
		val levelX = level.aGetRenderX(mx.toDouble()).toInt()
		val levelY = level.aGetRenderY(my.toDouble()).toInt()
		
		level[levelX, levelY] = if (team == 1) Tile.SpawnTeam1Tile(levelX, levelY, menuManager.gameMenu) else Tile.SpawnTeam2Tile(levelX, levelY, menuManager.gameMenu)
		
	}
	
	private fun attemptGameStart() {
		
		// make sure there are two spawn points first
		
		val sp1 = level.getSpawnLocationForTeam(1)
		val sp2 = level.getSpawnLocationForTeam(2)
		if (sp1 != POINT2I_INVALID && sp2 != POINT2I_INVALID) {
			// there are two spawn points, play the game
			menuManager.startGame(level)
		}else {
			// there are not two spawn points, tell them to add some
			menuManager.pushMenu(NoSpawnPointsMenu(menuManager))
		}
		
	}
	
	private fun decBrush() {
		if (brushRadius > 0) brushRadius--
	}
	
	private fun incBrush() {
		if (brushRadius < 10) brushRadius++
	}
	
	override fun mousePressed(e: MouseEvent?) {
		mousePressed = true
		e?.let {
			checkBarrierStatus(it.x, it.y)
			toggleLevelBarrierWindowPos(it.x, it.y)
		}
	}
	
	override fun mouseReleased(e: MouseEvent?) {
		mousePressed = false
		lastDragPoint = POINT2I_INVALID
	}
	
	override fun mouseMoved(e: MouseEvent?) {
		e?.let {
			this.mx = it.x
			this.my = it.y
		}
	}
	
	override fun mouseDragged(e: MouseEvent?) {
		e?.let { onDrag(it.x, it.y) }
		e?.let {
			this.mx = it.x
			this.my = it.y
		}
	}
	
	override fun keyPressed(e: KeyEvent?) {
		super.keyPressed(e)
		when (e?.keyCode) {
			KeyEvent.VK_OPEN_BRACKET -> decBrush()
			KeyEvent.VK_CLOSE_BRACKET -> incBrush()
			KeyEvent.VK_1 -> setSpawn(1)
			KeyEvent.VK_2 -> setSpawn(2)
			KeyEvent.VK_ENTER -> attemptGameStart()
		}
	}
	
}
