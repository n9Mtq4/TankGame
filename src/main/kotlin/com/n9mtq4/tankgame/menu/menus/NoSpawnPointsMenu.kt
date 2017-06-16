package com.n9mtq4.tankgame.menu.menus

import com.n9mtq4.tankgame.GAME_SCALE
import com.n9mtq4.tankgame.getHeight
import com.n9mtq4.tankgame.menu.Menu
import com.n9mtq4.tankgame.menu.MenuManager
import java.awt.Font
import java.awt.Graphics
import java.awt.event.KeyEvent

/**
 * Created by will on 6/4/17 at 12:46 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class NoSpawnPointsMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	companion object {
		private const val FONT_SPACING = 10
		private const val FONT_X_OFFSET = 20
		private const val FONT_Y_OFFSET = 30
		private val TEXT_FONT = Font(Font.SANS_SERIF, Font.BOLD, 15 * GAME_SCALE)
	}
	
	val text = listOf(
			"There is at least one spawn point missing!",
			"In the level creator:",
			"Push 1 to set the spawn for team 1",
			"Push 2  to set the spawn for team 2",
			"Push ENTER to go back to level creator"
	)
	
	override fun draw(g: Graphics) {
		
		g.clearAll()
		
		g.font = TEXT_FONT
		
		val height = text.map { g.font.getHeight(it, g.frc) }.sorted().last()
		
		text.forEachIndexed { index, t ->
			g.drawString(t, FONT_X_OFFSET, (height + FONT_SPACING) * (index + 1) + FONT_Y_OFFSET)
		}
		
	}
	
	override fun keyPressed(e: KeyEvent?) {
		
		super.keyPressed(e) // ESC goes back
		// also enter goes back
		when(e?.keyCode) {
			KeyEvent.VK_ENTER -> menuManager.popMenu(this)
		}
		
	}
	
}
