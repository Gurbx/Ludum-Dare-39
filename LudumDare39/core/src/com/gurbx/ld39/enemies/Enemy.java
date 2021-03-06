package com.gurbx.ld39.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld39.player.Player;
import com.gurbx.ld39.utils.GameInterface;
import com.gurbx.ld39.utils.PowerUpHandler;
import com.gurbx.ld39.utils.particles.ParticleEffectHandler;
import com.gurbx.ld39.utils.particles.ParticleEffectType;
import com.gurbx.ld39.utils.sound.SoundHandler;
import com.gurbx.ld39.utils.sound.Sounds;
import com.gurbx.ld39.world.PowerGenerator;
import com.gurbx.ld39.world.World;

public class Enemy implements GameInterface {
	private final float SPEED = 100f;
	private final float FRICTION = 500f;
	private Player player;
	private PowerGenerator targetGenerator;
	private Vector2 position;
	private float health = 10;
	private float attackCooldown = 1f;
	private float attackTimer;
	private float damage = 1;
	
	private World world;
	private boolean shouldRemove;
	
	private float yModifier;
	private float elapsedTime;
	private Animation run, stand, hit;
	private Animation currentAnimation;
	private float width, height;
	private boolean flipX;
	private boolean jumping;
	
	private float dx, dy;
	
	public Enemy(TextureAtlas atlas, Vector2 position, World world, Player player) {
		this.world = world;
		this.player = player;
		initAnimations(atlas);
		shouldRemove = false;
		this.position = position;
		this.flipX = false;
		jumping = true;
		targetGenerator = world.getClosestPowerGenerator(position.x, position.y);
	}
	
	
	private void initAnimations(TextureAtlas atlas) {
		elapsedTime = (float) (Math.random()*3f);
		//STAND ANIMATION
		TextureRegion[] standFrames = new TextureRegion[4];
	    for (int i = 0; i < standFrames.length; i++) {
	    	standFrames[i] = atlas.findRegion("playerStand" + (i+1));
	    }
	    stand = new Animation(1/8f, standFrames);  
		this.width = standFrames[0].getRegionWidth();
		this.height = standFrames[0].getRegionHeight();
	    //RUN
		TextureRegion[] runFrames = new TextureRegion[4];
	    for (int i = 0; i < runFrames.length; i++) {
	    	runFrames[i] = atlas.findRegion("zombieRun" + (i+1));
	    }
	    run = new Animation(1/8f, runFrames);
	    
		TextureRegion[] hitFrames = new TextureRegion[2];
	    for (int i = 0; i < hitFrames.length; i++) {
	    	hitFrames[i] = atlas.findRegion("zombieHit" + (i+1));
	    }
	    hit = new Animation(1/6f, hitFrames);  
	    
	    currentAnimation = run;
		
	}


	@Override
	public void update(float delta) {
		elapsedTime += delta;
		handleMovement(delta);
		handleGravity(delta);
		handleAnimations(delta);
		//Falling down
		if (position.y <= 0) shouldRemove = true;
		
	}
	
	private void handleAnimations(float delta) {
		if (jumping) {
			currentAnimation = hit;
		} else {
			currentAnimation = run;
		}
		
	}


	private void handleMovement(float delta) {
		position.x += dx * delta;
		if (jumping) return;
		if (getDistanceFromPlayer() < getDistanceFromTargetGenerator()) {
			moveTowardsPlayer(delta);
		} else {
			moveTowardsGenerator(delta);
		}
		if (Math.abs(dx) > 0) {
			if (dx > 0) dx -= FRICTION * delta;
			if (dx < 0) dx += FRICTION * delta;
		}
		
	}

	private void moveTowardsGenerator(float delta) {
		if (targetGenerator.getX() < position.x) {
			position.x -= SPEED * delta;
			flipX = true;
		}
		
		if (targetGenerator.getX() > position.x) {
			position.x += SPEED * delta;
			flipX = false;
		}
		//Handle attack
		attackTimer-=delta;
		if (attackTimer <= 0) {
			//Check if close enough to attack
			if (overlaps(targetGenerator.getX(), targetGenerator.getY(), width + 10)) {
				attackTimer = attackCooldown;
				targetGenerator.damage(damage);
				SoundHandler.playSound(Sounds.ATTACK);
			}
		}
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
		//Handle attack
		attackTimer-=delta;
		if (attackTimer <= 0) {
			//Check if close enough to attack
			if (overlaps(player.getPosition().x, player.getPosition().y, width + 10)) {
				attackTimer = attackCooldown;
				SoundHandler.playSound(Sounds.ATTACK);
				player.damage(damage, 200, position.x, position.y);
			}
		}
		
	}


	private void handleGravity(float delta) {
		if (!world.isStoppedByGround(position.x, position.y, width, height)) {
			yModifier += world.getGravity() * delta;
		} else {
			jumping = false;
			yModifier = 0;
			position.y = world.getGroundHeight() + height*0.5f;
		}
		position.y -= yModifier*delta;
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
		if (health < 0) {
			shouldRemove = true;
			ParticleEffectHandler.addParticleEffect(ParticleEffectType.BLOOD2, position.x, position.y);
			SoundHandler.playSound(Sounds.HIT);
			SoundHandler.playSound(Sounds.EXPLOSION1);
			int timeDrop = (int) (Math.random()*15);
			if (timeDrop <= 2) {
				PowerUpHandler.addPowerUp(position);
			}
		} else {
			ParticleEffectHandler.addParticleEffect(ParticleEffectType.BLOOD1, position.x, position.y);
			SoundHandler.playSound(Sounds.HIT);
		}
		if (!jumping) ParticleEffectHandler.addParticleEffect(ParticleEffectType.BLOOD_GROUND, position.x, position.y-height*0.5f);
		
		//Handle impact
		float radians = (float) Math.atan2(position.y - y, position.x - x);
		dx = MathUtils.cos(radians) * impact;
//		dy = MathUtils.sin(radians) * impact;
		
		jumping = true;
		yModifier = - impact;
		position.y += 1f;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	private float getDistanceFromPlayer() {
		float distance = (float) Math.sqrt((position.x-player.getPosition().x)*(position.x-player.getPosition().x) 
				+ (position.y- player.getPosition().y)*(position.y-player.getPosition().y));
		return distance;
	}
	
	private float getDistanceFromTargetGenerator() {
		float distance = (float) Math.sqrt((position.x-targetGenerator.getX())*(position.x-targetGenerator.getX()) 
				+ (position.y- targetGenerator.getY())*(position.y-targetGenerator.getY()));
		return distance;
	}
	
	

}
