package com.n9mtq4.tankgame.entities

import com.n9mtq4.tankgame.*
import com.n9mtq4.tankgame.menu.menus.GameMenu
import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.geom.GeneralPath
import java.awt.geom.Path2D


/**
 * Created by will on 5/25/17 at 10:00 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class Tank(x: Double, y: Double, var angle: Double, val color: Color, val keyControls: BooleanArray, game: GameMenu) : Entity(x, y, game) {
	
	companion object {
		const val COOLDOWN_VALUE = 60 / GAME_SPEED // keep cooldown set to 60 seconds no matter what tick speed we are running at
		const val TANK_SCALE = GAME_SCALE * 2.0
		val TANK_POLYGON = Polygon(intArrayOf(-6, -3, -3, -1, -1, 1, 1, 3, 3, 6, 6, 3, 3, -3, -3, -6),
				intArrayOf(-4, -4, -2, -2, -6, -6, -2, -2, -4, -4, 4, 4, 2, 2, 4, 4), 16)
	}
	
	val originalAngle = angle
	
	var score = 0
	var cooldown = 0
	
	val bounds = TANK_POLYGON.bounds
	val transform = AffineTransform()
	
	val projectiles = ArrayList<Projectile>()
	
	val hasWon: Boolean
		get() = score >= WINNING_SCORE
	
	fun fire() {
		
		// check to see if they can shoot
		if (cooldown > 0) return
		
		// set cooldown
		cooldown = COOLDOWN_VALUE.toInt()
		
		// fire projectile
		// TODO: do proper trig to get bullets to fire in the center of the gun
		val projectile = Projectile(x, y - 1 * GAME_SCALE, PROJECTILE_SPEED, angle, game, this)
		projectiles.add(projectile)
		
	}
	
	override fun tick() {
		
		checkKeyboard()
		
		projectiles.removeAll(projectiles.filter { it.toDestroy })
		projectiles.forEach { it.tick() }
		
		// process fire cooldown
		if (cooldown > 0) cooldown--
		
	}
	
	override fun draw(g: Graphics) {
		
		g as Graphics2D
		
		g.color = color
		
		val rotated = getTransformedShape()
		g.fill(rotated)
		
		// draw projectiles
		g.color = PROJECTILE_COLOR
		projectiles.forEach { it.draw(g) }
		
	}
	
	fun checkKeyboard() {
		
		if (keyControls[0]) {
			// forward
			moveAlongForwardVector(TANK_FORWARD_SPEED)
		}
		if (keyControls[1]) {
			// backward
			moveAlongForwardVector(-TANK_BACKWARD_SPEED)
		}
		if (keyControls[2]) {
			// turn left
			angle -= TANK_TURN_SPEED
		}
		if (keyControls[3]) {
			// turn right
			angle += TANK_TURN_SPEED
		}
		if (keyControls[4]) {
			// fire
			fire()
		}
		
	}
	
	fun moveAlongForwardVector(value: Double) {
		
		// get delta values
		val yd = value * Math.sin(Math.toRadians(angle))
		val xd = value * Math.cos(Math.toRadians(angle))
		
		val nx = x + xd
		val ny = y + yd
		
		moveX(nx)
		moveY(ny)
		
		game.level?.getTileAtTankCoords(nx, ny)?.onEnter(this)
		
	}
	
	private fun moveX(nx: Double) {
		
		if (nx in 0..GAME_WIDTH) {
			game.level?.getTileAtTankCoords(nx, y)?.takeUnless { it.isSolid(this) }?.let { x = nx }
		}
		
	}
	
	private fun moveY(ny: Double) {
		
		if (ny in 0..GAME_HEIGHT - SCORE_OFFSET) {
			game.level?.getTileAtTankCoords(x, ny)?.takeUnless { it.isSolid(this) }?.let { y = ny }
		}
		
	}
	
	fun gotHit(tank: Tank) {
		
	}
	
	fun hit(tank: Tank) {
		
	}
	
	fun reset() {
		score = 0
		cooldown = 0
		angle = originalAngle
		for (i in 0..keyControls.size - 1) keyControls[i] = false
		projectiles.clear()
	}
	
	fun getTransformedShape(): Shape {
		
		transform.apply {
			setToIdentity()
			scale(TANK_SCALE, TANK_SCALE)
			translate(getRenderX(x) / TANK_SCALE, getRenderY(y) / TANK_SCALE)
			rotate(Math.toRadians(angle + 90), bounds.getX() + bounds.width / 2, bounds.getY() + bounds.height / 2)
		}
		
		// scale and rotate
		val path = if (TANK_POLYGON is Path2D) TANK_POLYGON as Path2D else GeneralPath(TANK_POLYGON)
		return path.createTransformedShape(transform)
		
	}
	
}
