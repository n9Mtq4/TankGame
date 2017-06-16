package com.n9mtq4.tankgame.menu.menus

import com.n9mtq4.tankgame.*
import com.n9mtq4.tankgame.entities.Tank
import com.n9mtq4.tankgame.level.Level
import com.n9mtq4.tankgame.level.loadLevel
import com.n9mtq4.tankgame.menu.Menu
import com.n9mtq4.tankgame.menu.MenuManager
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyEvent

/**
 * Created by will on 5/30/17 at 7:41 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class GameMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	companion object {
		val SCORE_FONT = Font("Verdana", Font.BOLD, 100 * GAME_SCALE)
	}
	
	var level = loadLevel(1, this)
	
	val tank1Controls = booleanArrayOf(false, false, false, false, false)
	val tank2Controls = booleanArrayOf(false, false, false, false, false)
	private var tank1 = Tank(100.0, 100.0, 0.0, TEAM_ONE_COLOR, tank1Controls, this)
	private var tank2 = Tank(120.0, 120.0, 180.0, TEAM_TWO_COLOR, tank2Controls, this)
	
	var tanks = listOf<Tank>(tank1, tank2)
	
	fun reset(level: Level) {
		tanks.forEach { it.reset() }
		this.level = level
		level.reset()
	}
	
	override fun draw(g: Graphics) {
		
		super.draw(g)
		
		g as Graphics2D // smart casting
		
		val fontRenderContext = g.fontRenderContext
		
		// background
		g.color = BACKGROUND_COLOR
		g.fillRect(0, 0, GAME_WIDTH * GAME_SCALE, GAME_HEIGHT * GAME_SCALE)
		
		// tanks
		tanks.forEach { it.draw(g) }
		
		// level
		level?.draw(g)
		
		// score card
		g.color = SCORE_BACKGROUND_COLOR
		g.fillRect(0, 0, GAME_WIDTH * GAME_SCALE, SCORE_OFFSET * GAME_SCALE)
		
		g.font = SCORE_FONT
		
		val spacing = GAME_WIDTH * GAME_SCALE / 3
		
		g.color = TEAM_ONE_COLOR
		val team1Score = tank1.score.toString()
		val width1 = g.font.getWidth(team1Score, fontRenderContext)
		val height1 = g.font.getHeight(team1Score, fontRenderContext)
		g.drawString(tank1.score.toString(), spacing - (width1 / 2), height1 + GameClass.SCORE_MARGIN_TOP)
		
		g.color = TEAM_TWO_COLOR
		val team2Score = tank2.score.toString()
		val width2 = g.font.getWidth(team2Score, fontRenderContext)
		val height2 = g.font.getHeight(team2Score, fontRenderContext)
		g.drawString(tank2.score.toString(), spacing * 2 - (width2 / 2), height2 + GameClass.SCORE_MARGIN_TOP)
		
	}
	
	override fun tick() {
		
		level?.tick()
		tanks.forEach { it.tick() }
		
		// check for win condition
		level?.takeUnless { it.inLevelCreator }?.let {
			if (tank1.hasWon && tank2.hasWon) {
				menuManager.popMenu(this)
				menuManager.pushMenu(TieMenu(menuManager))
			}else if (tank1.hasWon) {
				menuManager.popMenu(this)
				menuManager.pushMenu(WinMenu(TEAM_ONE_NAME, TEAM_ONE_COLOR, menuManager))
			}else if (tank2.hasWon) {
				menuManager.popMenu(this)
				menuManager.pushMenu(WinMenu(TEAM_TWO_NAME, TEAM_TWO_COLOR, menuManager))
			}
		}
		
	}
	
	override fun keyPressed(e: KeyEvent?) {
		super.keyPressed(e) // go to main with esc
		when (e?.keyCode) {
			KeyEvent.VK_W -> tank1Controls[0] = true
			KeyEvent.VK_S -> tank1Controls[1] = true
			KeyEvent.VK_A -> tank1Controls[2] = true
			KeyEvent.VK_D -> tank1Controls[3] = true
			KeyEvent.VK_E -> tank1Controls[4] = true
			KeyEvent.VK_UP -> tank2Controls[0] = true
			KeyEvent.VK_DOWN -> tank2Controls[1] = true
			KeyEvent.VK_LEFT -> tank2Controls[2] = true
			KeyEvent.VK_RIGHT -> tank2Controls[3] = true
			KeyEvent.VK_SLASH -> tank2Controls[4] = true
		}
	}
	
	override fun keyReleased(e: KeyEvent?) {
		when (e?.keyCode) {
			KeyEvent.VK_W -> tank1Controls[0] = false
			KeyEvent.VK_S -> tank1Controls[1] = false
			KeyEvent.VK_A -> tank1Controls[2] = false
			KeyEvent.VK_D -> tank1Controls[3] = false
			KeyEvent.VK_E -> tank1Controls[4] = false
			KeyEvent.VK_UP -> tank2Controls[0] = false
			KeyEvent.VK_DOWN -> tank2Controls[1] = false
			KeyEvent.VK_LEFT -> tank2Controls[2] = false
			KeyEvent.VK_RIGHT -> tank2Controls[3] = false
			KeyEvent.VK_SLASH -> tank2Controls[4] = false
		}
	}
	
}
