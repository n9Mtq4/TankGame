package com.n9mtq4.tankgame.menu.menus

import com.n9mtq4.tankgame.menu.Menu
import com.n9mtq4.tankgame.menu.MenuManager
import java.awt.Color
import java.awt.Font
import java.awt.Graphics

/**
 * Created by will on 6/6/17 at 8:32 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class WinMenu(val teamName: String, val teamColor: Color, menuManager: MenuManager) : Menu(menuManager) {
	
	companion object {
		internal val WINMENU_FONT = Font(Font.SANS_SERIF, Font.BOLD, 32)
	}
	
	override fun draw(g: Graphics) {
		
		super.draw(g) // clear screen
		
		g.color = teamColor
		g.font = WINMENU_FONT
		g.drawString("Team $teamName wins!", 10, 50)
		
	}
	
}
