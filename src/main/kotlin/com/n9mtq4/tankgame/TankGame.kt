package com.n9mtq4.tankgame

import javax.swing.JFrame

/**
 * Created by will on 5/25/17 at 9:47 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
fun main(args: Array<String>) {
	
	Game()
	
}

class Game {
	
	private val frame: JFrame
	private val gameClass: GameClass
	
	init {
		
		frame = JFrame("Game")
		frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
		
		gameClass = GameClass()
		
		frame.add(gameClass)
		
		frame.isResizable = false
		frame.isVisible = true
		frame.pack()
		
		gameClass.start()
		
	}
	
}
