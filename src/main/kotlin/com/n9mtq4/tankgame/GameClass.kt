package com.n9mtq4.tankgame

import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Font.BOLD
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import java.awt.event.KeyListener


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
	
	var keyboardListener = KeyBoardListener()
	var level = loadLevel(1, this)
	
	private var tank1 = Tank(100.0, 100.0, 0.0, TEAM_ONE_COLOR, keyboardListener.tank1, this)
	private var tank2 = Tank(120.0, 120.0, 180.0, TEAM_TWO_COLOR, keyboardListener.tank2, this)
	
	var tanks = listOf<Tank>(tank1, tank2)
	
	init {
		
		preferredSize = Dimension(GAME_WIDTH * GAME_SCALE, GAME_HEIGHT * GAME_SCALE)
		
		addKeyListener(keyboardListener)
		
	}
	
	fun start() {
		this.running = true
		thread = Thread(this)
		thread.start()
	}
	
	fun stop() {
		this.running = false
		thread.join()
	}
	
	fun gameRender(g: Graphics) {
		
		g as Graphics2D // smart casting
		
		val fontRenderContext = g.fontRenderContext
		
		// background
		g.color = BACKGROUND_COLOR
		g.fillRect(0, 0, GAME_WIDTH * GAME_SCALE, GAME_HEIGHT * GAME_SCALE)
		
		// tanks
		tanks.forEach { it.draw(g) }
		
		// level
		level?.draw(g)
		
		// score card
		g.color = SCORE_BACKGROUND_COLOR
		g.fillRect(0, 0, GAME_WIDTH * GAME_SCALE, SCORE_OFFSET * GAME_SCALE)
		
		g.font = Font("Verdana", Font.BOLD, 200)
		
		val spacing = GAME_WIDTH * GAME_SCALE / 3
		
		g.color = TEAM_ONE_COLOR
		val team1Score = tank1.score.toString()
		val width1 = g.font.getWidth(team1Score, fontRenderContext)
		val height1 = g.font.getHeight(team1Score, fontRenderContext)
		g.drawString(tank1.score.toString(), spacing - (width1 / 2), height1 + SCORE_MARGIN_TOP)
		
		g.color = TEAM_TWO_COLOR
		val team2Score = tank2.score.toString()
		val width2 = g.font.getWidth(team2Score, fontRenderContext)
		val height2 = g.font.getHeight(team2Score, fontRenderContext)
		g.drawString(tank2.score.toString(), spacing * 2 - (width2 / 2), height2 + SCORE_MARGIN_TOP)
		
	}
	
	fun render() {
		
		val bs = this.bufferStrategy
		if (bs == null) {
			createBufferStrategy(3)
			return
		}
		val g = bs.drawGraphics
		
		gameRender(g)
		
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
		
		level?.tick()
		tanks.forEach { it.tick() }
		
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
	
	class KeyBoardListener : KeyListener {
		
		val tank1 = booleanArrayOf(false, false, false, false, false)
		val tank2 = booleanArrayOf(false, false, false, false, false)
		
		override fun keyTyped(e: KeyEvent?) {}
		
		override fun keyPressed(e: KeyEvent?) {
			
			when (e?.keyCode) {
				KeyEvent.VK_W -> tank1[0] = true
				KeyEvent.VK_S -> tank1[1] = true
				KeyEvent.VK_A -> tank1[2] = true
				KeyEvent.VK_D -> tank1[3] = true
				KeyEvent.VK_E -> tank1[4] = true
				KeyEvent.VK_UP -> tank2[0] = true
				KeyEvent.VK_DOWN -> tank2[1] = true
				KeyEvent.VK_LEFT -> tank2[2] = true
				KeyEvent.VK_RIGHT -> tank2[3] = true
				KeyEvent.VK_SLASH -> tank2[4] = true
			}
			
		}
		
		override fun keyReleased(e: KeyEvent?) {
			
			when (e?.keyCode) {
				KeyEvent.VK_W -> tank1[0] = false
				KeyEvent.VK_S -> tank1[1] = false
				KeyEvent.VK_A -> tank1[2] = false
				KeyEvent.VK_D -> tank1[3] = false
				KeyEvent.VK_E -> tank1[4] = false
				KeyEvent.VK_UP -> tank2[0] = false
				KeyEvent.VK_DOWN -> tank2[1] = false
				KeyEvent.VK_LEFT -> tank2[2] = false
				KeyEvent.VK_RIGHT -> tank2[3] = false
				KeyEvent.VK_SLASH -> tank2[4] = false
			}
			
		}
		
	}
	
}
