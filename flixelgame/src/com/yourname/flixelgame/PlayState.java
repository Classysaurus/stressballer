package com.yourname.flixelgame;

import org.flixel.FlxCamera;
import org.flixel.FlxGroup;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.FlxG;
import org.flixel.FlxTileblock;

public class PlayState extends FlxState
{
	
	protected String ImgTech = "assets/stressballer/data/pack:tech_tiles";
	
	protected StressBall player;
	protected FlxGroup blocks;
	protected FlxGroup objects;
	
	protected FlxText playerCoord;
	protected FlxText cameraCoord;
	protected FlxText dAngleText;
	protected FlxText rAngleText;
	protected FlxText jumpPower;
	
	@Override
	public void create()
	{
		//add(new FlxText(0, 0, 200, "Hello World"));
		player = new StressBall(10,12000-80);
		
		FlxG.worldBounds.height = 20000;
		FlxG.camera.scroll.y = 12000;
		playerCoord = new FlxText(0,0,100,String.valueOf(player.y));
		cameraCoord = new FlxText(0,10,100,String.valueOf(FlxG.camera.scroll.y));
		dAngleText = new FlxText(0,20,100,String.valueOf(player.jumpAngle));
		rAngleText = new FlxText(0,30,100,String.valueOf(player.rJumpAngle));
		jumpPower = new FlxText(0,40,100,String.valueOf(player.jumpPower));
		
		add(playerCoord);
		add(cameraCoord);
		add(dAngleText);
		add(rAngleText);
		add(jumpPower);
		
		objects = new FlxGroup();
		blocks = new FlxGroup();
		
		objects.add(player);
		
		generateLevel();
		
		add(blocks);		
		add(player);
		
		FlxG.camera.setBounds(0,0,240,12000,false);
		
	}
	
	@Override
	public void update()
	{
		super.update();
		// Moves blocks down
		//blockOffset();
		playerCoord.x = player.x -10;
		playerCoord.y = player.y -20;
		cameraCoord.x = player.x -10;
		cameraCoord.y = player.y -10;
		dAngleText.x = player.x - 10;
		dAngleText.y = player.y - 40;
		rAngleText.x = player.x - 10;
		rAngleText.y = player.y - 30;
		jumpPower.x = player.x - 10;
		jumpPower.y = player.y - 50;
		playerCoord.setText(String.valueOf(player.y));
		cameraCoord.setText(String.valueOf(FlxG.camera.scroll.y));
		rAngleText.setText("rAngle: " + String.valueOf(player.rJumpAngle));
		dAngleText.setText("dAngle: " + String.valueOf(player.jumpAngle));
		jumpPower.setText("jPower: " + String.valueOf(player.jumpPower));
				
		FlxG.collide(blocks,objects);
		
		//blocks.setAll("y", y+1);
		
		if(player.y-FlxG.camera.scroll.y < 160)
		{
			FlxG.camera.scroll.y = player.y - 160;
		}
		else
		{
			FlxG.camera.scroll.y = FlxG.camera.scroll.y - 0.2f;
		}
		
	}
	
	protected void blockOffset()
	{
		int memberCount = blocks.length;
		for(int i = 0; i < memberCount; i++)
		{
			((Blocks)blocks.members.get(i)).moveBlocks(1);
		}
	}
	
	@Override
	public void destroy()
	{
		super.destroy();

		blocks = null;
		player = null;
		
		objects = null;
	}
	
	protected void generateLevel()
	{
		Blocks b;
		
		b = new Blocks(0,12000 - 10,640,16);
		b.loadTiles(ImgTech);
		blocks.add(b);
		
		int tiers = 240;
		int tierHeight = 12000;
		while(tiers > 0)
		{
			tierHeight = tierHeight - 40;
			b = new Blocks(randomWithRange(0,FlxG.width/2), tierHeight, randomWithRange(10, FlxG.width/2), 8);
			tiers--;
			b.loadTiles(ImgTech);
			blocks.add(b);
		}
	}
	
	protected int randomWithRange(int min, int max)
	{
		   int range = (max - min) + 1;     
		   return (int)(Math.random() * range) + min;
	}
}