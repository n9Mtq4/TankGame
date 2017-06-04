package com.n9mtq4.tankgame.menu.menus

import com.n9mtq4.tankgame.menu.Menu
import com.n9mtq4.tankgame.menu.MenuManager
import java.awt.Color
import java.awt.Graphics

/**
 * Created by will on 5/29/17 at 8:53 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class LevelSelectMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	override fun draw(g: Graphics) {
		
		super.draw(g)
		
		g.color = Color.BLACK
		g.drawString("Level select placeholder", 20, 40)
		
		g.drawString("Push ESC to go back", 20, 60)
		
	}
	
}
