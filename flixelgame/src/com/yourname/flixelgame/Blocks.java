package com.yourname.flixelgame;

import org.flixel.*;

public class Blocks extends FlxTileblock
{
	public Blocks(float X, float Y, int Width, int Height)
	{
		super(X,Y,Width,Height);
	}
	
	public void moveBlocks(float increment)
	{
		this.y = this.y + increment;
	}
}