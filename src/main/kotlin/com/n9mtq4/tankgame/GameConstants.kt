package com.n9mtq4.tankgame

import java.awt.Color

/**
 * Created by will on 5/25/17 at 9:52 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
const val FPS_CAP = true
const val GAME_SPEED = 60.0
const val GAME_WIDTH = 400
const val GAME_HEIGHT = 500
const val SCORE_OFFSET = 100
const val GAME_SCALE = 2
const val DEBUG = true

const val LEVEL_WIDTH = 50
const val LEVEL_HEIGHT = 50
const val WINNING_SCORE = 5

val SCORE_BACKGROUND_COLOR: Color = Color.CYAN
val BACKGROUND_COLOR: Color = Color.WHITE
val BARRIER_COLOR: Color = Color.BLACK
val TEAM_ONE_COLOR: Color = Color.RED
val TEAM_TWO_COLOR: Color = Color.BLUE
val TEAM_ONE_NAME = "Red"
val TEAM_TWO_NAME = "Blue"

val PROJECTILE_COLOR: Color = Color.BLACK

const val TANK_FORWARD_SPEED = 1.2
const val TANK_BACKWARD_SPEED = TANK_FORWARD_SPEED
const val TANK_TURN_SPEED = 2.0
const val PROJECTILE_SPEED = 3.0
