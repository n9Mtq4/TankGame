package com.n9mtq4.tankgame.menu.menus

import com.n9mtq4.tankgame.GAME_HEIGHT
import com.n9mtq4.tankgame.GAME_SCALE
import com.n9mtq4.tankgame.getHeight
import com.n9mtq4.tankgame.menu.Menu
import com.n9mtq4.tankgame.menu.MenuManager
import com.n9mtq4.tankgame.menu.MenuOption
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*
import java.awt.event.MouseEvent

/**
 * Created by will on 5/29/17 at 7:54 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class MainMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	companion object {
		const val GAME_TITLE = "Will's Tank Game"
		const val FONT_SPACING = 5 * GAME_SCALE
		const val CHOICE_YOFFSET = 150 * GAME_SCALE
	}
	
	var updateOptionBounds = true
	
	var selectedIndex = 0
	
	val options = listOf(
			MenuOption("Play Game", "Starts the game") { menuManager.startGame() },
			MenuOption("Select Level", "Select a specific level") { menuManager.pushMenu(LevelSelectMenu(menuManager)) },
			MenuOption("Create Level", "Create your own level") { menuManager.pushMenu(CreateLevelMenu(menuManager)) },
			MenuOption("Controls", "Shows the list of controls") { menuManager.pushMenu(ControlsMenu(menuManager)) },
			MenuOption("Exit Game", "Closes the game") { System.exit(0) }
	)
	
	override fun draw(g: Graphics) {
		super.draw(g)
		
		g.clearAll()
		
		// title
		g.font = TITLE_FONT
		val x = calcCenter(GAME_TITLE, g.font, g.frc)
		g.drawString(GAME_TITLE, x, g.font.getHeight(GAME_TITLE, g.frc) + 20)
		
		// options
		options.forEachIndexed { index, option ->
			
			val text = option.optionName
			
			g.font = if (selectedIndex == index) SELECTED_OPTION_FONT else OPTION_FONT
			
			val fontCenterX = calcCenter(text, g.font, g.frc)
			val fontHeight = g.font.getHeight(text, g.frc)
			val fontYPos = (fontHeight + FONT_SPACING) * index + CHOICE_YOFFSET
			
			if (updateOptionBounds) option.calcRenderBounds(fontCenterX, fontYPos - fontHeight, g.frc, g.font)
			
			g.drawString(text, fontCenterX, fontYPos)
			
		}
		
		if (updateOptionBounds) updateOptionBounds = false
		
		// show description
		g.font = OPTION_FONT
		val selectedOption = options[selectedIndex]
		val selectedOptionText = selectedOption.description
		g.drawString(selectedOptionText, 0, GAME_HEIGHT * GAME_SCALE - FONT_SPACING)
		
	}
	
	override fun keyPressed(e: KeyEvent?) {
		// no super, since shouldn't be able to pop the main menu
		when (e?.keyCode) {
			VK_DOWN, VK_S -> {
				selectedIndex++
				if (selectedIndex <= -1) selectedIndex = options.size - 1
				selectedIndex %= options.size
			}
			VK_UP, VK_W -> {
				selectedIndex--
				if (selectedIndex <= -1) selectedIndex = options.size - 1
				selectedIndex %= options.size
			}
			VK_ENTER, VK_E -> {
				options[selectedIndex].callback()
			}
		}
	}
	
	private fun menuSelectFromClick() = options[selectedIndex].callback()
	
	private fun setSelectedOptionFromMouse(x: Int, y: Int) {
		
		options.map { it.renderBounds }.filterNotNull().forEachIndexed { index, bounds ->
			if (bounds.contains(x, y)) selectedIndex = index
		}
		
	}
	
	override fun mouseClicked(e: MouseEvent?) {
		
		super.mouseClicked(e)
		
		e?.let { menuSelectFromClick() }
		
	}
	
	override fun mouseMoved(e: MouseEvent?) {
		
		super.mouseMoved(e)
		
		e?.let {
			setSelectedOptionFromMouse(it.x, it.y)
		}
		
	}
	
}
