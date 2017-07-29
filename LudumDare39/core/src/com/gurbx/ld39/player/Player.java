package com.gurbx.ld39.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld39.utils.GameInterface;
import com.gurbx.ld39.world.World;

public class Player implements GameInterface {
	private final float MAX_SPEED = 10f;
	private final float ACCELERATION = 100f;
	private final float DEACCELERATION = 20f;
	private float xModifier;
	private float yModifier;
	
	private Vector2 position;
	private float width, height;
	private World world;
	
	private Animation currentAnimation;
	private Animation stand, jump, run;
	private float elapsedTime;
	private boolean flipX;
	
	public Player(World world, TextureAtlas generalAtlas) {
		this.world = world;
		initAnimations(generalAtlas);
		position = new Vector2(world.getGroundWidth()*0.5f, world.getGroundHeight() + height*0.5f);
		flipX = false;
	}

	private void initAnimations(TextureAtlas atlas) {
		elapsedTime = 0;
		//STAND ANIMATION
		TextureRegion[] standFrames = new TextureRegion[4];
	    for (int i = 0; i < standFrames.length; i++) {
	    	standFrames[i] = atlas.findRegion("playerStand" + (i+1));
	    }
	    stand = new Animation(1/8f, standFrames);  
		this.width = standFrames[0].getRegionWidth();
		this.height = standFrames[0].getRegionHeight();
	    //RUN
		TextureRegion[] runFrames = new TextureRegion[8];
	    for (int i = 0; i < runFrames.length; i++) {
	    	runFrames[i] = atlas.findRegion("playerRun" + (i+1));
	    }
	    run = new Animation(1/12f, runFrames);  
	    //SLIDE
//		TextureRegion[] slideFrames = new TextureRegion[2];
//	    for (int i = 0; i < slideFrames.length; i++) {
//	    	slideFrames[i] = atlas.findRegion("playerSlide" + (i+1));
//	    }
//	    slide = new Animation(1/12f, slideFrames);  
	    //JUMP
		TextureRegion[] jumpFrames = new TextureRegion[2];
	    for (int i = 0; i < jumpFrames.length; i++) {
	    	jumpFrames[i] = atlas.findRegion("playerJump" + (i+1));
	    }
	    jump = new Animation(1/12f, jumpFrames);
	    //LAND
//		TextureRegion[] landFrames = new TextureRegion[3];
//	    for (int i = 0; i < landFrames.length; i++) {
//	    	landFrames[i] = atlas.findRegion("playerLand" + (i+1));
//	    }
//		this.width = landFrames[0].getRegionWidth();
//		this.height = landFrames[0].getRegionHeight();
//	    land = new Animation(1/12f, landFrames);  
	    
	    currentAnimation = stand;
	}

	@Override
	public void update(float delta) {
		elapsedTime += delta;
		handleMovement(delta);
		handleGravity(delta);
		handleAnimations();
		
	}

	private void handleAnimations() {
		if (!world.isStoppedByGround(position.x, position.y, width, height)) {
			currentAnimation = jump;
		} else {
			currentAnimation = stand;
			if (Math.abs(xModifier) > 1) {
				currentAnimation = run;
			}
		}
		
	}

	private void handleGravity(float delta) {
		if (!world.isStoppedByGround(position.x, position.y, width, height)) {
			yModifier += world.getGravity() * delta;
		} else {
			yModifier = 0;
			position.y = world.getGroundHeight() + height*0.5f;
			//JUMP
			if (Gdx.input.isKeyPressed(Keys.UP)) {
				yModifier = - 10f;
			}
		}
		position.y -= yModifier;
		
	}

	private void handleMovement(float delta) {
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			flipX = true;
			xModifier -= delta * ACCELERATION;
			if (xModifier < -MAX_SPEED) xModifier = -MAX_SPEED;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			flipX = false;
			xModifier += delta * ACCELERATION;
			if (xModifier > MAX_SPEED) xModifier = MAX_SPEED;
		}
		
		position.x += xModifier;
		if (xModifier > 0) {
			xModifier -= DEACCELERATION * delta;
		}
		if (xModifier < 0) {
			xModifier += DEACCELERATION * delta;
		}
		if (xModifier < 1f && xModifier > -1f) {
			xModifier = 0;
		}
		
	}

	@Override
	public void render(SpriteBatch batch) {

//		batch.draw(tex, position.x - width*0.5f, position.y-height*0.5f);
//		batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), position.x - width*0.5f, position.y-height*0.5f);
		float animationWidth = width;
		if (flipX) animationWidth = -width;
//		batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), (position.x - width*0.5f), (position.y-height*0.5f),
//				animationWidth*0.5f, height*0.5f, animationWidth, height, 1, 1, 0);
		
		batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), position.x - animationWidth*0.5f, position.y-height*0.5f,
				animationWidth, height);
	}

	@Override
	public void dispose() {
		
	}

	public Vector2 getPosition() {
		return position;
	}
	

}
