package com.n9mtq4.tankgame

import java.awt.Graphics

/**
 * Created by will on 5/27/17 at 10:13 AM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
open class Entity(var x: Double, var y: Double, val game: GameClass) {
	
	open fun tick() {
		
	}
	
	open fun draw(g: Graphics) {
		
	}
	
	fun getRenderX(x: Double) = x * GAME_SCALE
	
	fun getRenderY(y: Double) = (y +  SCORE_OFFSET) * GAME_SCALE
	
}
