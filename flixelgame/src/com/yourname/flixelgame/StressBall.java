package com.yourname.flixelgame;

import org.flixel.*;

public class StressBall extends FlxSprite
{
	// Prepare player sprite sheet
	static public String ImgPlayer = "assets/stressballer/data/pack:spaceman";
	//static public String ImgPlayer = "assets/stressballer/pack:player";
	// Prepare player sounds
	static public String SndHurt = "stressballer/data/hurt.mp3";
	static public String SndJump = "stressballer/data/jump.mp3";
	static public String SndBounce = "stressballer/data/bounce.mp3";
	static public String SndLand = "stressballer/data/land.mp3";
	
	// Declare corresponding FlxSound objects
	private FlxSound sfxJump;
	private FlxSound sfxLand;
	private FlxSound sfxHurt;
	//private FlxSound sfxBounce;
	
	public int target;
	public int moving;
	
	public float rollPower; // Potential horizontal energy
	public float jumpPower; // Potential vertical energy
	public float jumpAngle; // Jumping angle
	
	public float maxHealth;
	public FlxEmitter gibs;

	public int bombs;
	public int maxBombs;
	
	public boolean pressed;

	public boolean inAir; // Not implemented yet
	
	public boolean updated;

	public boolean launchpad;
	public boolean firedFlare;
	
	public boolean halfSpeed;
	
	public FlxPoint playerPoint;
	public FlxPoint mousePoint;
	
	public float rJumpAngle;
	
	public Double tempDouble;
	
	public StressBall(int x,int y)
	{
		super(x,y); // Super - Starting coordinates
		loadGraphic(ImgPlayer,true,true,6,7); // Loads prepared sprite sheet, is animated, is reversable
		
		// Specify Animations
		addAnimation("idle", new int[]{0});
		addAnimation("roll", new int[]{1, 2, 3, 0}, 12);
		addAnimation("charge_jump", new int[]{4});
		addAnimation("idle_up", new int[]{5});
		addAnimation("run_up", new int[]{6, 7, 8, 5}, 12);
		addAnimation("jump_up", new int[]{9});
		addAnimation("jump_down", new int[]{10});
			
		// Load prepared sounds into FlxSound objects
		sfxHurt = new FlxSound().loadEmbedded(SndHurt, false, false, FlxSound.SFX);
		//sfxBounce = new FlxSound().loadEmbedded(SndBounce, false, false, FlxSound.SFX);
		sfxJump = new FlxSound().loadEmbedded(SndJump, false, false, FlxSound.SFX);
		sfxLand = new FlxSound().loadEmbedded(SndLand, false, false, FlxSound.SFX);
		
		pressed = false;
		halfSpeed = false;
		jumpPower = 50;
		
		playerPoint = new FlxPoint();
		mousePoint = new FlxPoint();
		maxVelocity.x = 200;
		
		this.elasticity = .3f;
	}
	
	@Override
	public void destroy()
	{
		super.destroy();
		gibs = null;
		
	}

	@Override
	public void update()
	{
		//MOVEMENT
		//Keyboard Movement
		acceleration.x = 0;
		if(FlxG.keys.LEFT)
		{
			setFacing(LEFT);
			acceleration.x -= drag.x;
		}
		else if(FlxG.keys.RIGHT)
		{
			setFacing(RIGHT);
			acceleration.x += drag.x;
		}
		if(FlxG.keys.justPressed("X"))
		{	
			jump(inAir);
		}
		
		if(FlxG.mouse.justPressed())
		{
			pressed = true;
		}
		if(pressed)
		{
			//drawLine();
			halfSpeed = true;
			jumpPower = jumpPower + 12.5f;
			setAimAngle();
			maxVelocity.y = 2000;
			maxVelocity.x = 2000;
			
			if(FlxG.mouse.justReleased())
			{
				drag.x = 40;
				mouseJump();
				halfSpeed = false;
				pressed = false;
				//maxVelocity.y = 120;
				//maxVelocity.x = 80;
				jumpPower = 50;
			}
		}
		
		//bounding box tweaks
		width = 6;
		height = 7;
		offset.x = 1;
		offset.y = 1;
			
		// Basic player physics
		//if(!halfSpeed)
		//{
			int rollSpeed = 80;
			//drag.x = rollSpeed*3;
			acceleration.y = 420;
			//jumpPower = 200;
			//maxVelocity.x = rollSpeed;
			//maxVelocity.y = 120;
		//}
		//else
		//{
		//	int rollSpeed = 40;
		//	drag.x = rollSpeed*4;
		//	acceleration.y = 210;
		//}
				
		// InAir Setter
		if(justTouched(FLOOR))
		{
			inAir = false;
			drag.x = rollSpeed*3;
			//maxVelocity.x = 200;
		}
		
		// Make a little noise if you just touched the floor
		if(justTouched(FLOOR) && (velocity.y > 50))
			sfxLand.play(true);
		
		//ANIMATION
				if(velocity.y != 0)
					play("jump_up");
				else if(velocity.x == 0)
					play("idle");
				else
					play("roll");
	}
	
	private void drawLine()
	{
		
	}
	
	private void mouseJump()
	{
		tempDouble = (jumpAngle * (Math.PI / 180));
		rJumpAngle = tempDouble.floatValue();
		tempDouble = (Math.cos(rJumpAngle) * jumpPower);
		velocity.x = tempDouble.floatValue();
		tempDouble = Math.sin(rJumpAngle) * jumpPower;
		velocity.y = tempDouble.floatValue();
		sfxJump.play(true);		
	}
	
	private void setAimAngle()
	{
		playerPoint.x = x + (width/2);
		playerPoint.y = y + (height/2);
		mousePoint.x = FlxG.mouse.x;
		mousePoint.y = FlxG.mouse.y;
		jumpAngle = FlxU.getAngle(playerPoint, mousePoint) - 90;
	}
	
	private void jump(boolean doubleJump)
	{
		if(((int) velocity.y == 0) || doubleJump)
		{
			inAir = true;
			float tempPower = 200;
			velocity.y = -tempPower;
			sfxJump.play(true);			
		}
	}
	
	@Override
	public void hurt(float Damage)
	{
		Damage = 0;
		if(getFlickering())
			return;
		sfxHurt.play(true);
		flicker(1.3f);
		if(FlxG.score > 1000) FlxG.score -= 1000;
		if(velocity.x > 0)
			velocity.x = -maxVelocity.x;
		else
			velocity.x = maxVelocity.x;
		super.hurt(Damage);
	}
}