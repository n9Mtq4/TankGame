package com.n9mtq4.tankgame.menu

/**
 * Created by will on 5/29/17 at 8:48 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class MenuOption(var optionName: String, var description: String = "", val callback: () -> Unit = {})
