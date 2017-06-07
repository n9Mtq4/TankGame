package com.n9mtq4.tankgame.menu.menus

import com.n9mtq4.tankgame.menu.Menu
import com.n9mtq4.tankgame.menu.MenuManager
import java.awt.Color
import java.awt.Graphics

/**
 * Created by will on 6/6/17 at 8:52 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class TieMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	override fun draw(g: Graphics) {
		
		super.draw(g) // clear screen
		
		g.color = Color.BLACK
		g.font = WinMenu.WINMENU_FONT
		g.drawString("There is a tie!", 10, 50)
		
	}
	
}
