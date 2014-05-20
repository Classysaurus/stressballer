package com.yourname.flixelgame;

import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.FlxSprite;
import org.flixel.FlxButton;
import org.flixel.FlxG;

import org.flixel.event.IFlxButton;

public class MenuState extends FlxState
{
	public FlxButton playButton;
	public FlxText title;
	public float buttonX;
	public float buttonY;
	
	public FlxSprite player = new FlxSprite(0, 0, "assets/ball.png");
	
	@Override
	public void create()
	{
		title = new FlxText(FlxG.width/2 - 28, 64, 200, "Stress Baller");
		buttonX = FlxG.width/2 - 35;
		buttonY = FlxG.height/2 - 9;
		//FlxG.setBgColor(0xcf131c1b);
	}
	
	@Override
	public void destroy()
	{
		super.destroy();
		playButton = null;
		title = null;
	}
	
	@Override
	public void update()
	{
		super.update();
		
		add(title);
		add(player);
		playButton = new FlxButton(buttonX,buttonY,"Click or Hit X",new IFlxButton()
									{@Override public void callback(){onPlay();}});
		playButton.update();
		add(playButton);
		if(FlxG.keys.X)
		{
			onPlay();
		};
	}
	
	protected void onPlay()
	{
		playButton.exists = false;
		FlxG.switchState(new PlayState());
		//FlxG.play(SndHit2, 1f, false, false);
	}
}