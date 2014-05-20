package com.yourname.flixelgame;

import org.flixel.FlxGame;

public class FlixelGame extends FlxGame
{
	public FlixelGame()
	{
		super(240, 400, MenuState.class, 2, 50, 50, true);
	}
}
