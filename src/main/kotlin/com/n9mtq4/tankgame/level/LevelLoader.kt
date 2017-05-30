package com.n9mtq4.tankgame.level

import com.n9mtq4.kotlin.extlib.ignoreAndNull
import com.n9mtq4.tankgame.menu.menus.GameMenu
import javax.imageio.ImageIO

/**
 * Created by will on 5/25/17 at 10:19 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */

fun loadLevel(num: Int, game: GameMenu) = ignoreAndNull<Level> { 
	
	val path = toPath(num)
	
	val inStream = object {}.javaClass.getResourceAsStream(path)
	
	val image = ImageIO.read(inStream)
	
	val height = image.height
	val width = image.width
	
	val level = Level(width, height, game)
	
	for (y in 0..height - 1) {
		for (x in 0..width - 1) {
			
			val pixel = image.getRGB(x, y)
			
			level.tiles[width * y + x] = when(pixel) {
				0xffffffff.toInt() -> Tile.OpenTile(x, y, game)
				0xff000000.toInt() -> Tile.ClosedTile(x, y, game)
				0xffff0000.toInt() -> Tile.SpawnTeam1Tile(x, y, game)
				0xff0000ff.toInt() -> Tile.SpawnTeam2Tile(x, y, game)
				else -> Tile.OpenTile(x, y, game)
			}
			
		}
	}
	
	return level
	
}

fun toPath(num: Int) = "/assets/levels/level$num.png"
