package com.n9mtq4.tankgame.menu

import com.n9mtq4.tankgame.GAME_HEIGHT
import com.n9mtq4.tankgame.GAME_SCALE
import com.n9mtq4.tankgame.GAME_WIDTH
import com.n9mtq4.tankgame.getHeight
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyEvent

/**
 * Created by will on 5/29/17 at 7:54 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class MainMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	companion object {
		const val GAME_TITLE = "Will's Tank Game"
	}
	
	override fun draw(g: Graphics) {
		super.draw(g)
		
		g.clearRect(0, 0, GAME_WIDTH * GAME_SCALE, GAME_HEIGHT * GAME_SCALE)
		g as Graphics2D
		val frc = g.fontRenderContext
		
		g.font = TITLE_FONT
		val x = calcCenter(GAME_TITLE, g.font, frc)
		g.drawString(GAME_TITLE, x, g.font.getHeight(GAME_TITLE, frc) + 20)
		
	}
	
	override fun keyPressed(e: KeyEvent?) {
		when (e?.keyCode) {
			KeyEvent.VK_ENTER -> menuManager.startGame()
		}
	}
	
}
