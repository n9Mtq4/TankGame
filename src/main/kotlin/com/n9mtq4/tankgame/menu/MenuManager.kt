package com.n9mtq4.tankgame.menu

import com.n9mtq4.tankgame.GameClass
import com.n9mtq4.tankgame.Level
import com.n9mtq4.tankgame.loadLevel
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.lang.IllegalStateException
import java.util.Stack

/**
 * Created by will on 5/29/17 at 7:02 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class MenuManager(val gameClass: GameClass) : KeyListener, MouseListener {
	
	private val menuStack: Stack<Menu> = Stack()
	var inGame = false
	
	init {
		menuStack.push(MainMenu(this))
	}
	
	fun draw(g: Graphics) {
		menuStack.peek().draw(g)
	}
	
	fun pushMenu(menu: Menu) {
		menuStack.push(menu)
	}
	
	@Throws(IllegalStateException::class)
	fun popMenu(menu: Menu) {
		if (menuStack.peek() != menu) throw IllegalStateException("Can't pop a menu that isn't yourself!")
		if (menuStack.size == 1) throw IllegalStateException("Can't pop the last menu!")
		menuStack.pop()
	}
	
	fun startGame(level: Level = loadLevel(1, gameClass) ?: Level(50, 50, gameClass)) { // TODO: make level size a variable?
		
		gameClass.reset(level)
		
//		gameClass.level = level
		
		inGame = true
		
	}
	
	override fun keyTyped(e: KeyEvent?) = menuStack.peek().keyTyped(e)
	override fun keyPressed(e: KeyEvent?) = menuStack.peek().keyPressed(e)
	override fun keyReleased(e: KeyEvent?) = menuStack.peek().keyReleased(e)
	
	override fun mouseReleased(e: MouseEvent?) = menuStack.peek().mouseReleased(e)
	override fun mouseEntered(e: MouseEvent?) = menuStack.peek().mouseEntered(e)
	override fun mouseClicked(e: MouseEvent?) = menuStack.peek().mouseClicked(e)
	override fun mouseExited(e: MouseEvent?) = menuStack.peek().mouseExited(e)
	override fun mousePressed(e: MouseEvent?) = menuStack.peek().mousePressed(e)
	
}
