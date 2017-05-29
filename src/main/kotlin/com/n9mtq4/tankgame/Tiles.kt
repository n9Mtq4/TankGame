package com.n9mtq4.tankgame

/**
 * Created by will on 5/27/17 at 8:55 AM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
sealed class Tile(val x: Int, val y: Int, val game: GameClass) {
	
	open fun init() {
		
	}
	
	open fun onEnter(entity: Entity) {
		
	}
	
	open fun isSolid(entity: Entity): Boolean {
		return false
	}
	
	class OpenTile(x: Int, y: Int, game: GameClass) : Tile(x, y, game) {
		
		
		
	}
	
	class ClosedTile(x: Int, y: Int, game: GameClass) : Tile(x, y, game) {
		
		override fun onEnter(entity: Entity) {
			(entity as? Projectile)?.destroy()
		}
		
		override fun isSolid(entity: Entity): Boolean {
			return true
		}
		
	}
	
	class SpawnTeam1Tile(x: Int, y: Int, game: GameClass) : Tile(x, y, game) {
		
		override fun init() {
			
			game.tanks[0].x = x.toDouble() * (game.level?.tileWidth ?: 1) / GAME_SCALE
			game.tanks[0].y = y.toDouble() * (game.level?.tileHeight ?: 1) / GAME_SCALE
			
		}
		
	}
	
	class SpawnTeam2Tile(x: Int, y: Int, game: GameClass) : Tile(x, y, game) {
		
		override fun init() {
			
			game.tanks[1].x = x.toDouble() * (game.level?.tileWidth ?: 1) / GAME_SCALE
			game.tanks[1].y = y.toDouble() * (game.level?.tileHeight ?: 1) / GAME_SCALE
			
		}
		
	}
	
}
