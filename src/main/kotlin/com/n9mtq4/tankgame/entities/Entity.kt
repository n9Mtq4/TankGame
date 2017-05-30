package com.n9mtq4.tankgame.entities

import com.n9mtq4.tankgame.GAME_SCALE
import com.n9mtq4.tankgame.SCORE_OFFSET
import com.n9mtq4.tankgame.menu.menus.GameMenu
import java.awt.Graphics

/**
 * Created by will on 5/27/17 at 10:13 AM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
open class Entity(var x: Double, var y: Double, val game: GameMenu) {
	
	open fun tick() {
		
	}
	
	open fun draw(g: Graphics) {
		
	}
	
	fun getRenderX(x: Double) = x * GAME_SCALE
	
	fun getRenderY(y: Double) = (y + SCORE_OFFSET) * GAME_SCALE
	
}
