package com.n9mtq4.tankgame

import java.awt.Font
import java.awt.font.FontRenderContext

/**
 * Created by will on 5/28/17 at 10:42 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
fun Font.getWidth(str: String, fontRenderContext: FontRenderContext) = 
		createGlyphVector(fontRenderContext, str).visualBounds.width.toInt()
fun Font.getHeight(str: String, fontRenderContext: FontRenderContext) =
		createGlyphVector(fontRenderContext, str).visualBounds.height.toInt()
