package com.n9mtq4.tankgame.menu.menus

import com.n9mtq4.tankgame.menu.Menu
import com.n9mtq4.tankgame.menu.MenuManager
import java.awt.Graphics
import java.awt.event.MouseEvent

/**
 * Created by will on 5/29/17 at 8:54 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class CreateLevelMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	private var mousePressed = false
	private var barrier = true
	
	override fun draw(g: Graphics) {
		
		super.draw(g)
		
		// have to draw the currently created level
		
		
		
	}
	
	override fun tick() {
		
		
		
	}
	
	override fun mouseReleased(e: MouseEvent?) {
		mousePressed = false
	}
	
	override fun mousePressed(e: MouseEvent?) {
		mousePressed = true
	}
	
}
