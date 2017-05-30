package com.n9mtq4.tankgame.menu

import com.n9mtq4.tankgame.getHeight
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*

/**
 * Created by will on 5/29/17 at 7:54 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class MainMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	companion object {
		const val GAME_TITLE = "Will's Tank Game"
		const val FONT_SPACING = 10
		const val CHOICE_YOFFSET = 300
	}
	
	var selectedOption = 0
	
	val options = listOf(
			MenuOption("Play Game", "Starts the game") { menuManager.startGame() },
			MenuOption("Select Level", "Select a specific level") { menuManager.pushMenu(CreateLevelMenu(menuManager)) },
			MenuOption("Create Level", "Create your own level") { menuManager.pushMenu(CreateLevelMenu(menuManager)) },
			MenuOption("Controls", "Shows the list of controls") { menuManager.pushMenu(ControlsMenu(menuManager)) }
	)
	
	override fun draw(g: Graphics) {
		super.draw(g)
		
		g.clearAll()
		g as Graphics2D
		val frc = g.fontRenderContext
		
		// title
		g.font = TITLE_FONT
		val x = calcCenter(GAME_TITLE, g.font, frc)
		g.drawString(GAME_TITLE, x, g.font.getHeight(GAME_TITLE, frc) + 20)
		
		// options
		options.forEachIndexed { index, option ->
			
			val text = option.optionName
			
			if (selectedOption == index) g.setFont(SELECTED_OPTION_FONT)
			else g.setFont(OPTION_FONT)
			
			g.drawString(text, calcCenter(text, g.font, frc), (g.font.getHeight(text, frc) + FONT_SPACING) * index + CHOICE_YOFFSET)
			
		}
		
	}
	
	override fun keyPressed(e: KeyEvent?) {
		// no super, since shouldn't be able to pop the main menu
		when (e?.keyCode) {
			VK_DOWN, VK_S -> {
				selectedOption++
				if (selectedOption <= -1) selectedOption = options.size - 1
				selectedOption %= options.size
			}
			VK_UP, VK_W -> {
				selectedOption--
				if (selectedOption <= -1) selectedOption = options.size - 1
				selectedOption %= options.size
			}
			VK_ENTER, VK_E -> {
				options[selectedOption].callback()
			}
		}
	}
	
}
