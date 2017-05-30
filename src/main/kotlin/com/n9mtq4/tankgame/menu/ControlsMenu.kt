package com.n9mtq4.tankgame.menu

import java.awt.Graphics
import java.awt.event.KeyEvent

/**
 * Created by will on 5/29/17 at 8:54 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class ControlsMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	override fun draw(g: Graphics) {
		
		g.clearAll()
		
		g.font = SELECTED_OPTION_FONT
		
		g.drawString("This is just a place holder for now", 100, 80)
		
		g.drawString("WASD to move team 1", 100, 100)
		g.drawString("E to shoot team 1", 100, 120)
		g.drawString("ARROWS to move team 2", 100, 140)
		g.drawString("?/ to shoot team 2", 100, 160)
		g.drawString("Push ESC to go back", 100, 180)
		
	}
	
	override fun keyTyped(e: KeyEvent?) {
		super.keyTyped(e)
	}
	
}
