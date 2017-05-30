package com.n9mtq4.tankgame.menu

import com.n9mtq4.tankgame.getHeight
import java.awt.Graphics

/**
 * Created by will on 5/29/17 at 8:54 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class ControlsMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	companion object {
		const val FONT_SPACING = 10
		const val FONT_X_OFFSET = 40
		const val FONT_Y_OFFSET = 30
	}
	
	val text = listOf("This is just a placeholder", "ESC to go back", "Team #1:", "WASD to move", "E to shoot", "Team #2:", "ARROWS to move", "?/ to shoot")
	
	override fun draw(g: Graphics) {
		
		g.clearAll()
		
		g.font = SELECTED_OPTION_FONT
		
		val height = text.map { g.font.getHeight(it, g.frc) }.sorted().first()
		
		text.forEachIndexed { index, t ->
			g.drawString(t, FONT_X_OFFSET, (height + FONT_SPACING) * (index + 1) + FONT_Y_OFFSET)
		}
		
	}
	
}
