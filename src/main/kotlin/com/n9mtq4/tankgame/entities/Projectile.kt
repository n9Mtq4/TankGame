package com.n9mtq4.tankgame.entities

import com.n9mtq4.kotlin.extlib.ignore
import com.n9mtq4.tankgame.GAME_HEIGHT
import com.n9mtq4.tankgame.GAME_SCALE
import com.n9mtq4.tankgame.GAME_WIDTH
import com.n9mtq4.tankgame.SCORE_OFFSET
import com.n9mtq4.tankgame.menu.menus.GameMenu
import java.awt.Graphics

/**
 * Created by will on 5/27/17 at 10:16 AM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class Projectile(x: Double, y: Double, var velocity: Double, var angle: Double, game: GameMenu, val tank: Tank) : Entity(x, y, game) {
	
	companion object {
		const val PROJECTILE_SIZE = 5
	}
	
	val yd = velocity * Math.sin(Math.toRadians(angle))
	val xd = velocity * Math.cos(Math.toRadians(angle))
	
	var toDestroy = false
	
	fun destroy() {
		toDestroy = true
	}
	
	override fun tick() {
		
		val nx = x + xd
		val ny = y + yd
		
		game.tanks.filter { it != tank }.forEach { 
			if (it.getTransformedShape().contains(getRx(x), getRy(y))) {
				tank.score++
				it.gotHit(tank)
				tank.hit(it)
				this.destroy()
			}
		}
		
		// get tile that it will enter
		ignore { game.level?.getTileAtTankCoords(nx, ny)?.onEnter(this) }
		
		if (nx !in 0..GAME_WIDTH || ny !in 0..GAME_HEIGHT - SCORE_OFFSET) destroy()
		
		x = nx
		y = ny
		
	}
	
	override fun draw(g: Graphics) {
		
		g.fillRect(getRx(x).toInt(), getRy(y).toInt(), getRWidth(), getRHeight())
		
	}
	
	private fun getRx(x: Double) = (getRenderX(x) * GAME_SCALE / 2)
	private fun getRy(y: Double) = (getRenderY(y) - PROJECTILE_SIZE * GAME_SCALE / 2)
	private fun getRWidth() = 5 * GAME_SCALE
	private fun getRHeight() = 5 * GAME_SCALE
	
}
