package com.gurbx.ld39.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld39.utils.GameInterface;
import com.gurbx.ld39.world.World;

public class Player implements GameInterface {
	private Vector2 position;
	private float width, height;
	private World world;
	
	private Animation currentAnimation;
	private Animation stand;
	private float elapsedTime;
	
	public Player(World world, TextureAtlas generalAtlas) {
		initAnimations(generalAtlas);
		
		position = new Vector2(world.getGroundWidth()*0.5f, world.getGroundHeight() + height*0.5f);
	}

	private void initAnimations(TextureAtlas atlas) {
		elapsedTime = 0;
		//STAND ANIMATION
		TextureRegion[] standFrames = new TextureRegion[4];
	    for (int i = 0; i < standFrames.length; i++) {
	    	standFrames[i] = atlas.findRegion("playerStand" + (i+1));
	    }
		this.width = standFrames[0].getRegionWidth();
		this.height = standFrames[0].getRegionHeight();
	    stand = new Animation(1/6f, standFrames);  
	    currentAnimation = stand;
	}

	@Override
	public void update(float delta) {
		elapsedTime += delta;
		
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), position.x - width*0.5f, position.y-height*0.5f);
	}

	@Override
	public void dispose() {
		
	}

	public Vector2 getPosition() {
		return position;
	}

}
