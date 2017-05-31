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
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.event.MouseEvent

/**
 * Created by will on 5/29/17 at 8:54 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class CreateLevelMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	companion object {
		const val SCORE_PLACEHOLDER = "SCORE"
	}
	
	private var mousePressed = false
	private var barrier = true
	
	private var brushSize = 1
	
	private var level = Level(LEVEL_WIDTH, LEVEL_HEIGHT, menuManager.gameMenu)
	
	override fun draw(g: Graphics) {
		
		super.draw(g)
		
		// have to draw the currently created level
		level.draw(g)
		
		// score card
		g.color = SCORE_BACKGROUND_COLOR
		g.fillRect(0, 0, GAME_WIDTH * GAME_SCALE, SCORE_OFFSET * GAME_SCALE)
		
		g.color = Color.BLACK
		g.font = Font("Verdana", Font.BOLD, 200)
		
		val height1 = g.font.getHeight(SCORE_PLACEHOLDER, g.frc)
		g.drawString(SCORE_PLACEHOLDER, calcCenter(SCORE_PLACEHOLDER, g.font, g.frc), height1 + GameClass.SCORE_MARGIN_TOP)
		
	}
	
	override fun tick() {
		
		
		
	}
	
	fun checkBarrierStatus(x: Int, y: Int) = ignore {
		
		val levelX = level.aGetRenderX(x.toDouble()).toInt()
		val levelY = level.aGetRenderY(y.toDouble()).toInt()
		
		val tile = level[levelX, levelY]
		barrier = tile is Tile.OpenTile
		
	}
	
	fun toggleLevelBarrierWindowPos(x: Int, y: Int) = ignore {
		
		val levelX = level.aGetRenderX(x.toDouble()).toInt()
		val levelY = level.aGetRenderY(y.toDouble()).toInt()
		
		if (barrier) level[levelX, levelY] = Tile.ClosedTile(levelX, levelY, menuManager.gameMenu)
		else level[levelX, levelY] = Tile.OpenTile(levelX, levelY, menuManager.gameMenu)
		
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
	}
	
	override fun mouseDragged(e: MouseEvent?) {
		e?.let { toggleLevelBarrierWindowPos(it.x, it.y) }
	}
	
}
