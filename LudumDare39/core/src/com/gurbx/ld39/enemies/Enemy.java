package com.gurbx.ld39.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld39.player.Player;
import com.gurbx.ld39.utils.GameInterface;
import com.gurbx.ld39.utils.particles.ParticleEffectHandler;
import com.gurbx.ld39.utils.particles.ParticleEffectType;
import com.gurbx.ld39.world.World;

public class Enemy implements GameInterface {
	private final float SPEED = 100f;
	private final float FRICTION = 500f;
	private Player player;
	private Vector2 position;
	private float health = 10;
	
	private World world;
	private boolean shouldRemove;
	
	private float yModifier;
	private float elapsedTime;
	private Animation run, stand;
	private Animation currentAnimation;
	private float width, height;
	private boolean flipX;
	
	private float dx, dy;
	
	public Enemy(TextureAtlas atlas, Vector2 position, World world, Player player) {
		this.world = world;
		this.player = player;
		initAnimations(atlas);
		shouldRemove = false;
		this.position = position;
		this.flipX = false;
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
	    
	    currentAnimation = run;
		
	}


	@Override
	public void update(float delta) {
		elapsedTime += delta;
		handleMovement(delta);
		handleGravity(delta);
		//Falling down
		if (position.y <= 0) shouldRemove = true;
		
	}
	
	private void handleMovement(float delta) {
		moveTowardsPlayer(delta);
		if (Math.abs(dx) > 0) {
			if (dx > 0) dx -= FRICTION * delta;
			if (dx < 0) dx += FRICTION * delta;
		}
		position.x += dx * delta;
		
	}


	private void moveTowardsPlayer(float delta) {
		if (player.getPosition().x < position.x) {
			position.x -= SPEED * delta;
			flipX = true;
		}
		
		if (player.getPosition().x > position.x) {
			position.x += SPEED * delta;
			flipX = false;
		} 
		
	}


	private void handleGravity(float delta) {
		if (!world.isStoppedByGround(position.x, position.y, width, height)) {
			yModifier += world.getGravity() * delta;
		} else {
			yModifier = 0;
			position.y = world.getGroundHeight() + height*0.5f;
		}
		position.y -= yModifier;
	}
	
	
	@Override
	public void render(SpriteBatch batch) {
		float animationWidth = width;
		if (flipX) animationWidth = -width;
//		batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), (position.x - width*0.5f), (position.y-height*0.5f),
//				animationWidth*0.5f, height*0.5f, animationWidth, height, 1, 1, 0);
		
		batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), position.x - animationWidth*0.5f, position.y-height*0.5f,
				animationWidth, height);
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


	public boolean shouldRemove() {
		return shouldRemove;
	}


	public boolean overlaps(float x, float y, float range) {
		if (x < position.x + width && x + range > position.x &&
				y < position.y + height && y + range > position.y) {
			return true;
		}
		return false;
	}


	public void damage(int damage, float impact, float x, float y) {
		health -= damage;
		if (health < 0) shouldRemove = true;
		ParticleEffectHandler.addParticleEffect(ParticleEffectType.BLOOD1, position.x, position.y);
		
		//Handle impact
		float radians = (float) Math.atan2(position.y - y, position.x - x);
		dx = MathUtils.cos(radians) * impact;
//		dy = MathUtils.sin(radians) * impact;
		
		yModifier = - 0.01f*impact;
		position.y += 1f;
		position.y -= yModifier;
		
	}
	
	

}
