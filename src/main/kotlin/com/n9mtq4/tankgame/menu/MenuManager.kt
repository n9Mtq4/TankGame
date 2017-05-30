package com.n9mtq4.tankgame.menu

import com.n9mtq4.tankgame.GameClass
import com.n9mtq4.tankgame.level.Level
import com.n9mtq4.tankgame.level.loadLevel
import com.n9mtq4.tankgame.menu.menus.GameMenu
import com.n9mtq4.tankgame.menu.menus.MainMenu
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.lang.IllegalStateException
import java.util.Stack

/**
 * Created by will on 5/29/17 at 7:02 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class MenuManager(val gameClass: GameClass) : KeyListener, MouseListener, MouseMotionListener {
	
	private val menuStack: Stack<Menu> = Stack()
	
	var gameMenu = GameMenu(this)
	
	init {
		menuStack.push(MainMenu(this))
	}
	
	fun draw(g: Graphics) {
		menuStack.peek().draw(g)
	}
	
	fun tick() {
		menuStack.peek().tick()
	}
	
	fun pushMenu(menu: Menu) {
		menuStack.push(menu)
		menu.onPush()
	}
	
	@Throws(IllegalStateException::class)
	fun popMenu(menu: Menu) {
		if (menuStack.peek() != menu) throw IllegalStateException("Can't pop a menu that isn't yourself!")
		if (menuStack.size == 1) throw IllegalStateException("Can't pop the last menu!")
		menuStack.pop().onPop()
	}
	
	fun startGame(level: Level = loadLevel(1, gameMenu) ?: Level(50, 50, gameMenu), reset: Boolean = true) { // TODO: make level size a variable?
		
		if (reset) gameMenu.reset(level)
		pushMenu(gameMenu)
		
	}
	
	override fun keyTyped(e: KeyEvent?) = menuStack.peek().keyTyped(e)
	override fun keyPressed(e: KeyEvent?) = menuStack.peek().keyPressed(e)
	override fun keyReleased(e: KeyEvent?) = menuStack.peek().keyReleased(e)
	
	override fun mouseReleased(e: MouseEvent?) = menuStack.peek().mouseReleased(e)
	override fun mouseEntered(e: MouseEvent?) = menuStack.peek().mouseEntered(e)
	override fun mouseClicked(e: MouseEvent?) = menuStack.peek().mouseClicked(e)
	override fun mouseExited(e: MouseEvent?) = menuStack.peek().mouseExited(e)
	override fun mousePressed(e: MouseEvent?) = menuStack.peek().mousePressed(e)
	
	override fun mouseMoved(e: MouseEvent?) = menuStack.peek().mouseMoved(e)
	override fun mouseDragged(e: MouseEvent?) = menuStack.peek().mouseDragged(e)
	
}
