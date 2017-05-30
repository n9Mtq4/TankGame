package com.n9mtq4.tankgame

import com.n9mtq4.tankgame.menu.MenuManager
import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Font.BOLD


/**
 * Created by will on 5/25/17 at 9:48 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class GameClass : Canvas(), Runnable {
	
	companion object {
		const val SCORE_MARGIN_TOP = 20
	}
	
	var running = false
	var fps = 0
	lateinit var thread: Thread
	
	var menuManager = MenuManager(this)
	
	init {
		
		preferredSize = Dimension(GAME_WIDTH * GAME_SCALE, GAME_HEIGHT * GAME_SCALE)
		
		addKeyListener(menuManager)
		addMouseListener(menuManager)
		addMouseMotionListener(menuManager)
		
	}
	
	fun start() {
		if (running) return
		this.running = true
		thread = Thread(this)
		thread.start()
	}
	
	fun stop() {
		if (!running) return
		this.running = false
		thread.join()
	}
	
	fun render() {
		
		val bs = this.bufferStrategy
		if (bs == null) {
			createBufferStrategy(3)
			return
		}
		val g = bs.drawGraphics
		
		menuManager.draw(g)
		
		if (DEBUG) {
			g.color = Color.RED
			g.font = Font("Verdana", BOLD, 24)
			g.drawString("$fps fps", 0, 20)
			g.font = Font("Verdana", Font.BOLD, 12)
		}
		
		g.dispose()
		bs.show()
		
	}
	
	fun tick() {
		
		menuManager.tick()
		
	}
	
	override fun run() {
		
		var frames = 0
		var unprocessedSeconds = 0.0
		var previousTime = System.nanoTime()
		val clockSpeed = 1 / GAME_SPEED
		var tickCount = 0
		var ticked = false
		
		requestFocus()
		requestFocusInWindow()
		tick()
		while (running) {
			
//			game loop
			val currentTime = System.nanoTime()
			val passedTime = currentTime - previousTime
			previousTime = currentTime
			unprocessedSeconds += passedTime / 1000000000.0
			
			while (unprocessedSeconds > clockSpeed) {
				
				tick()
				unprocessedSeconds -= clockSpeed
				ticked = true
				tickCount++
				if (tickCount % GAME_SPEED.toInt() == 0) {
					
					println(tickCount.toString() + " ups, " + frames + " fps")
					previousTime += 1000
					fps = frames
					frames = 0
					tickCount = 0
					
				}
				
			}
			
			if (ticked) {
				
				render()
				frames++
				ticked = false
				
			}
			
			if (!FPS_CAP) {
				render()
				frames++
			}
			
		}
		
	}
	
}
