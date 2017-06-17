package com.n9mtq4.tankgame.menu.menus

import com.n9mtq4.tankgame.GAME_HEIGHT
import com.n9mtq4.tankgame.GAME_SCALE
import com.n9mtq4.tankgame.GAME_WIDTH
import com.n9mtq4.tankgame.level.levelExists
import com.n9mtq4.tankgame.level.levelThumbNail
import com.n9mtq4.tankgame.menu.Menu
import com.n9mtq4.tankgame.menu.MenuManager
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage

/**
 * Created by will on 5/29/17 at 8:53 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class LevelSelectMenu(menuManager: MenuManager) : Menu(menuManager) {
	
	companion object {
		const val NUM_ROWS = 4
		const val NUM_COLS = 4
		const val THUMBNAIL_PADDING_X = 20 * GAME_SCALE
		const val THUMBNAIL_PADDING_Y = 20 * GAME_SCALE
		const val TOP_MARGIN_Y = 50 * GAME_SCALE
		const val BOTTOM_MARGIN_Y = 50 * GAME_SCALE
		val LEVEL_NAME_FONT = Font(Font.SANS_SERIF, Font.PLAIN, 16 * GAME_SCALE)
		val BORDER_COLOR: Color = Color.GREEN
	}
	
	var maxLevelNum: Int = 0
	var levelThumbnails: List<BufferedImage> = emptyList()
	var selectedLevel = 0
	
	var mx = 0
	var my = 0
	
	override fun onPush() {
		
		super.onPush()
		
		// update the number of levels
		updateLevelData()
		
	}
	
	override fun draw(g: Graphics) {
		
		super.draw(g)
		
		// top search / controls
		
		g.font = LEVEL_NAME_FONT
		
		g.color = Color.BLACK
		g.drawString("Push ESC to go back", 20, 40)
		
		g.drawString("Currently $maxLevelNum levels exist", 20, 80)
		
		// level thumbnails
		
		val thumbnailWidth = (GAME_WIDTH * GAME_SCALE - (THUMBNAIL_PADDING_X * NUM_COLS)) / NUM_COLS
		val thumbnailHeight = (GAME_HEIGHT * GAME_SCALE - (THUMBNAIL_PADDING_Y * NUM_ROWS) - TOP_MARGIN_Y - BOTTOM_MARGIN_Y) / NUM_ROWS
		
		val xOffset = ((GAME_WIDTH * GAME_SCALE) - (thumbnailWidth * NUM_COLS)) / NUM_COLS / GAME_SCALE / 2 // half on left, half on right
		val yOffset = ((GAME_HEIGHT * GAME_SCALE) - (thumbnailHeight * NUM_ROWS)) / NUM_ROWS / GAME_SCALE / 2 + TOP_MARGIN_Y
		
		for (y in 0..NUM_ROWS - 1) {
			for (x in 0..NUM_COLS - 1) {
				// TODO: better upscale for level thumbnails
				val index = x + y * NUM_ROWS
				if (index < levelThumbnails.size) {
					val dx = x * (thumbnailWidth + THUMBNAIL_PADDING_X) + xOffset
					val dy = y * (thumbnailHeight + THUMBNAIL_PADDING_Y) + yOffset
					if (index == selectedLevel) {
						val borderX = dx - THUMBNAIL_PADDING_X / 4 // two for reason of border, two for 1/2 of padding for centering
						val borderY = dy - THUMBNAIL_PADDING_Y / 4
						val borderWidth =  thumbnailWidth + THUMBNAIL_PADDING_X / 2 // two for half of thumbnail padding, which is for two levels
						val borderHeight = thumbnailHeight + THUMBNAIL_PADDING_Y / 2
						g.color = BORDER_COLOR
						g.fillRect(borderX, borderY, borderWidth, borderHeight)
					}
					g.drawImage(levelThumbnails[index], dx, dy, thumbnailWidth, thumbnailHeight, null)
				}
			}
		}
		
		// bottom pages
		
		val numPages = levelThumbnails.size / (NUM_ROWS * NUM_COLS)
		
	}
	
	private fun updateLevelData() {
		// update level numbers
		maxLevelNum = 0
		(1..Integer.MAX_VALUE).takeWhile { levelExists(it) }.forEach { maxLevelNum++ }
		// load in the level images
		levelThumbnails = (1..maxLevelNum).map { levelThumbNail(it) }
	}
	
	private fun fireMouseClicked() {
		
	}
	
	override fun keyPressed(e: KeyEvent?) {
		
		super.keyPressed(e) // esc to pop
		
		// TODO: a search thing
		
	}
	
	override fun mouseClicked(e: MouseEvent?) {
		
		super.mouseClicked(e)
		
		fireMouseClicked()
		
	}
	
	override fun mouseMoved(e: MouseEvent?) {
		
		super.mouseMoved(e)
		
		e?.let { 
			this.mx = it.x
			this.my = it.y
		}
		
	}
	
}
