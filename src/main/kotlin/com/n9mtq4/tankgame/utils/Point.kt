package com.n9mtq4.tankgame.utils

/**
 * Created by will on 6/4/17 at 12:17 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
data class Point2i(var x: Int, var y: Int)

fun Point2i.setInvalid() {
	this.x = POINT2I_INVALID.x
	this.y = POINT2I_INVALID.y
}

val POINT2I_INVALID = Point2i(-1, -1)
