package com.n9mtq4.tankgame.level

import com.n9mtq4.kotlin.extlib.ignoreAndNull
import com.n9mtq4.tankgame.BARRIER_COLOR
import com.n9mtq4.tankgame.GAME_HEIGHT
import com.n9mtq4.tankgame.GAME_SCALE
import com.n9mtq4.tankgame.GAME_WIDTH
import com.n9mtq4.tankgame.SCORE_OFFSET
import com.n9mtq4.tankgame.menu.menus.GameMenu
import java.awt.Graphics

/**
 * Created by will on 5/27/17 at 8:53 AM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class Level(val width: Int, val height: Int, val game: GameMenu) {
	
	val tiles: Array<Tile> = Array(width * height) { i -> Tile.OpenTile(i / width, i % width, game) }
	
	val tileWidth = GAME_WIDTH * GAME_SCALE / width
	val tileHeight = (GAME_HEIGHT - SCORE_OFFSET) * GAME_SCALE / height
	
	var firstTime = true
	
	fun tick() {
		
		if (firstTime) {
			firstTime = false
			tiles.forEach { it.init() }
		}
		
	}
	
	fun draw(g: Graphics) {
		
		g.color = BARRIER_COLOR
		tiles.filter { it is Tile.ClosedTile }.forEach {
			g.fillRect(it.x * tileWidth, it.y * tileHeight + SCORE_OFFSET * GAME_SCALE, tileWidth, tileHeight)
		}
		
	}
	
	/**
	 * Gets the tile at the desired coords
	 * */
	fun getTileAtTankCoords(x: Double, y: Double): Tile? {
		
		// TODO: uses getRenderX and getRenderY without calling it!
		val tileX = x / tileWidth * GAME_SCALE
		val tileY = y / tileHeight * GAME_SCALE
		
		return ignoreAndNull { tiles[width * tileY.toInt() + tileX.toInt()] }
		
	}
	
	operator fun get(x: Int, y: Int) = tiles[width * y + x]
	operator fun get(index: Int) = tiles[index]
	operator fun set(x: Int, y: Int, value: Tile) { tiles[width * y + x] = value }
	operator fun set(index: Int, value: Tile) { tiles[index] = value }
	
	fun aGetRenderX(x: Double) = x / tileWidth
	fun aGetRenderY(y: Double) = (y - SCORE_OFFSET * GAME_SCALE) / tileHeight
	
}
