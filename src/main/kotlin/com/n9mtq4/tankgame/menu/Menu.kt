package com.n9mtq4.tankgame.menu

import com.n9mtq4.tankgame.GAME_SCALE
import com.n9mtq4.tankgame.GAME_WIDTH
import java.awt.Font
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.font.FontRenderContext



/**
 * Created by will on 5/29/17 at 7:01 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
open class Menu(val menuManager: MenuManager) : KeyListener, MouseListener {
	
	companion object {
		val TITLE_FONT = Font(Font.SANS_SERIF, Font.BOLD, 50)
		val OPTION_FONT = Font(Font.SANS_SERIF, Font.PLAIN, 24)
		val SELECTED_OPTION_FONT = Font(Font.SANS_SERIF, Font.BOLD, 24)
	}
	
	open fun draw(g: Graphics) {
		
	}
	
	fun push() {
		menuManager.pushMenu(this)
	}
	
	fun pop() {
		menuManager.popMenu(this)
	}
	
	protected fun calcCenter(text: String, font: Font, fontRenderContext: FontRenderContext): Int {
		
		val width = font.createGlyphVector(fontRenderContext, text).visualBounds.width.toInt()
		val centerPos = GAME_WIDTH * GAME_SCALE
		
		return (centerPos - width) / 2
		
	}
	
	override fun keyTyped(e: KeyEvent?) {}
	override fun keyPressed(e: KeyEvent?) {}
	override fun keyReleased(e: KeyEvent?) {}
	
	override fun mouseReleased(e: MouseEvent?) {}
	override fun mouseEntered(e: MouseEvent?) {}
	override fun mouseClicked(e: MouseEvent?) {}
	override fun mouseExited(e: MouseEvent?) {}
	override fun mousePressed(e: MouseEvent?) {}
	
}
