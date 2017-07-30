package com.gurbx.ld39.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld39.enemies.Enemy;
import com.gurbx.ld39.enemies.EnemyHandler;
import com.gurbx.ld39.utils.GameInterface;
import com.gurbx.ld39.utils.particles.ParticleEffectHandler;
import com.gurbx.ld39.utils.particles.ParticleEffectType;
import com.gurbx.ld39.utils.projectile.FriendlyProjectile;
import com.gurbx.ld39.utils.projectile.ProjectileType;
import com.gurbx.ld39.world.World;

public class Player implements GameInterface {
	private float health;
	private final int MAX_HEALTH = 100;
	private float energy;
	private final int MAX_ENERGY = 100;
	private final float ENERGY_REGEN = 20f;
	private final float ENERGT_REG_ACC = 1f;
	private float energtAcc;
	private final float ENERGY_TIMER_TIME = 1f;
	private float energyTimer;
	
	
	private final float MAX_SPEED = 5f;
	private final float ACCELERATION = 100f;
	private final float DEACCELERATION = 20f;
	private final float FRICTION = 500f;
	private float xModifier;
	private float yModifier;
	private boolean moving;
	
	
	private boolean dead;
	private Vector2 position;
	private float width, height;
	private World world;
	private EnemyHandler enemyHandler;
	
	private Animation currentAnimation;
	private Animation stand, jump, run, roll;
	private Sprite gun;
	private Sprite head;
	private float gunWidth, gunHeight;
	private float headWidth, headHeight;
	private float elapsedTime;
	private boolean flipX;
	private boolean rolling;
	private boolean jumping;
	private boolean justHit;
	private final float ROLL_TIME = 1/18f*7f;
	private float rollingTimer;
	
	private float dx;
	
	private PlayerProjectileHandler projectileHandler;
	private TextureRegion projectileTex, projectileHeavyTex;
	
	public Player(World world, TextureAtlas generalAtlas) {
		health = MAX_HEALTH;
		energy = MAX_ENERGY;
		this.world = world;
		position = new Vector2(world.getGroundWidth()*0.5f, world.getGroundHeight() + height*0.5f);
		initAnimations(generalAtlas);
		flipX = false;
		projectileTex = generalAtlas.findRegion("particle1");
		projectileHeavyTex = generalAtlas.findRegion("particle99");
		justHit = false;
		energtAcc = 1;
		dead = false;
		moving = false;
	}
	
	public void setEnemyHandler(EnemyHandler enemyHandler, PlayerProjectileHandler playerProjectileHandler) {
		this.enemyHandler = enemyHandler;
		this.projectileHandler = playerProjectileHandler;
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
	    //ROLL
		TextureRegion[] rollFrames = new TextureRegion[7];
	    for (int i = 0; i < rollFrames.length; i++) {
	    	rollFrames[i] = atlas.findRegion("playerRoll" + (i+1));
	    }
	    roll = new Animation(1/18f, rollFrames);  
	    //JUMP
		TextureRegion[] jumpFrames = new TextureRegion[2];
	    for (int i = 0; i < jumpFrames.length; i++) {
	    	jumpFrames[i] = atlas.findRegion("playerJump" + (i+1));
	    }
	    jump = new Animation(1/6f, jumpFrames);
	    //LAND
//		TextureRegion[] landFrames = new TextureRegion[3];
//	    for (int i = 0; i < landFrames.length; i++) {
//	    	landFrames[i] = atlas.findRegion("playerLand" + (i+1));
//	    }
//		this.width = landFrames[0].getRegionWidth();
//		this.height = landFrames[0].getRegionHeight();
//	    land = new Animation(1/12f, landFrames);  
	    currentAnimation = stand;
	    
	    //Init gun sprite
	    TextureRegion gunTex = atlas.findRegion("playerGun");
	    gunWidth = gunTex.getRegionWidth();
	    gunHeight = gunTex.getRegionHeight();
	    gun = new Sprite(gunTex);
	    gun.setPosition(position.x - gunWidth*0.5f, position.y -gunHeight*0.5f);
	    //Init head
	    TextureRegion headTex = atlas.findRegion("playerHead");
	    headWidth = headTex.getRegionWidth();
	    headHeight = headTex.getRegionHeight() + 2;
	    head = new Sprite(headTex);
	    head.setPosition(position.x - headWidth*0.5f, position.y -headHeight*0.5f);
	}

	@Override
	public void update(float delta) {
		elapsedTime += delta;
		handleEnergy(delta);
		handleMovement(delta);
		handleGravity(delta);
		handleAnimations();
		handleAttack(delta);
		//If out of bounds
		if (position.y <= -600) {
			dead = true;
			health = 0;
		}
	}

	private void handleEnergy(float delta) {
		if (energy < 0) energy = 0;
		energyTimer -= delta;
		if (energyTimer <= 0) {
			if (energy < MAX_ENERGY) energy += ENERGY_REGEN * delta;
		}


	}

	private void handleAttack(float delta) {
//		if (Gdx.input.isKeyJustPressed(Keys.D)) {
//			ParticleEffectHandler.addParticleEffect(ParticleEffectType.HIT, position.x, position.y);
//			enemyHandler.attack(position.x, position.y, 32, 1, 400);
//		}
//		
//		if (Gdx.input.isKeyJustPressed(Keys.S)) {
//			if (!useEnergy(3)) return;
//			
//			float modifier = 5;
//			if (flipX) modifier = -5;
//			projectileHandler.addProjectile(new FriendlyProjectile(position.x + modifier, position.y, position.x + modifier*2,  position.y, 
//					700, projectileTex, enemyHandler.getEnemies(), 3, 200, ProjectileType.PLAYER_ATTACK));
//			ParticleEffectHandler.addParticleEffect(ParticleEffectType.HIT, position.x + modifier, position.y);
//		}
	}
	
	public void shoot(float mouseX, float mouseY) {
		if (rolling) return;
		if (!useEnergy(3)) return;
		float modifier = 5;
		if (flipX) modifier = -5;
		projectileHandler.addProjectile(new FriendlyProjectile(position.x + modifier, position.y, mouseX,  mouseY, 
				700, projectileTex, enemyHandler.getEnemies(), 3, 200, ProjectileType.PLAYER_ATTACK));
		ParticleEffectHandler.addParticleEffect(ParticleEffectType.HIT, position.x + modifier, position.y);
	}
	
	public void shootHeavy(float mouseX, float mouseY) {
		if (rolling) return;
		if (!useEnergy(10)) return;
		float modifier = 5;
		if (flipX) modifier = -5;
		projectileHandler.addProjectile(new FriendlyProjectile(position.x + modifier, position.y, mouseX,  mouseY, 
				300, projectileHeavyTex, enemyHandler.getEnemies(), 6, 500, ProjectileType.PLAYER_ATTACK));
		ParticleEffectHandler.addParticleEffect(ParticleEffectType.SPAWN, position.x + modifier, position.y);
	}

	private boolean useEnergy(int enrg) {
		if (this.energy < enrg) return false;
		this.energy -= enrg;
		energyTimer = ENERGY_TIMER_TIME;
		return true;
	}

	private void handleAnimations() {
	    gun.setPosition(position.x - gunWidth*0.5f, position.y -gunHeight*0.5f);
	    head.setPosition(position.x - headWidth*0.5f, position.y -headHeight*0.5f);
	    
		if (!world.isStoppedByGround(position.x, position.y, width, height)) {
			currentAnimation = jump;
		} else {
			currentAnimation = stand;
			if (Math.abs(xModifier) > 1) {
				currentAnimation = run;
			}
		}
		
		if (rolling) currentAnimation = roll;
		
	}

	private void handleGravity(float delta) {
		if (!world.isStoppedByGround(position.x, position.y, width, height)) {
			yModifier += world.getGravity() * delta;
		} else {
			jumping = false;
			justHit = false;
			yModifier = 0;
			position.y = world.getGroundHeight() + height*0.5f;
			dx = 0;
			//JUMP
			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				if (rolling) return;
				yModifier = - 400f;
				jumping = true;
			}
		}
		position.y -= yModifier * delta;
	}

	private void handleMovement(float delta) {
		//Handle impact
		position.x += dx * delta;
//		if (justHit) return;
		if (Math.abs(dx) > 0 && !jumping) {
			if (dx > 0) dx -= FRICTION * delta;
			if (dx < 0) dx += FRICTION * delta;
		}
		
		rollingTimer-=delta;
		if (rollingTimer <= 0) rolling = false;
		
		moving = false;
		if (Gdx.input.isKeyPressed(Keys.A)  && !justHit) {
			moving = true;
			flipX = true;
			if (!rolling) xModifier -= delta * ACCELERATION;
			if (xModifier < -MAX_SPEED && !rolling) xModifier = -MAX_SPEED;
		}
		if (Gdx.input.isKeyPressed(Keys.D) && !justHit) {
			moving = true;
			flipX = false;
			if (!rolling) xModifier += delta * ACCELERATION;
			if (xModifier > MAX_SPEED && !rolling) xModifier = MAX_SPEED;
		}
		
		//Deacceleration
		position.x += xModifier;
		if (xModifier > 0 && !jumping) {
			xModifier -= DEACCELERATION * delta;
			if (rolling) xModifier -= DEACCELERATION * delta;
			moving = true;
		}
		if (xModifier < 0  && !jumping) {
			xModifier += DEACCELERATION * delta;
			if (rolling) xModifier += DEACCELERATION * delta;
			moving = true;
		}
		if (xModifier < 1f && xModifier > -1f) {
			xModifier = 0;
			rolling = false;
		}
		
		//Roll
		if (Gdx.input.isKeyJustPressed(Keys.S)  && !justHit) {
			if (rolling) return;
			elapsedTime = 0;
			rolling = true;
			ParticleEffectHandler.addParticleEffect(ParticleEffectType.CLOUD, position.x, position.y);
			rollingTimer = ROLL_TIME;
			
			float rollBoost = 15;
			if (jumping) {
				yModifier = - 400f;
				rollBoost = 0;
			}
			
			if (!flipX) {
				xModifier += rollBoost;
			}
			if (flipX) {
				xModifier -= rollBoost;
			}
		}
		
	}
	
	public void setGunRotation(float rotation) {
		gun.setRotation(rotation);
		
		if (Math.abs(rotation) > 90) {
			gun.setFlip(true, false);
			head.setFlip(true, false);
			gun.setRotation(rotation-180);
			flipX = true;
		} else {
			flipX = false;
			gun.setFlip(false, false);
			head.setFlip(false, false);
//			gun.setRotation(rotation);
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
		if (!rolling) gun.draw(batch);
		if ((moving || jumping) && !rolling) head.draw(batch);
	}

	@Override
	public void dispose() {
		
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public float getHealth() {
		return health;
	}

	public float getMaxHealth() {
		return MAX_HEALTH;
	}
	
	public float getEnergy() {
		return energy;
	}
	
	public float getMaxEnergy() {
		return MAX_ENERGY;
	}

	public void damage(float damage, float impact, float x, float y) {
		if (rolling) return;
		ParticleEffectHandler.addParticleEffect(ParticleEffectType.BLOOD1, position.x, position.y);
		if (!jumping) ParticleEffectHandler.addParticleEffect(ParticleEffectType.BLOOD_GROUND, position.x, position.y-height*0.5f);
		health -= damage;
		if (health <= 0) {
			dead = true;
			health = 0;
		}
		
		//Handle impact
		float radians = (float) Math.atan2(position.y - y, position.x - x);
		dx = MathUtils.cos(radians) * impact;
//		dy = MathUtils.sin(radians) * impact;

		justHit = true;
		jumping = true;
		yModifier = - impact;
		position.y += 1f;
		
	}
	
	public boolean isDead() {
		return dead;
	}


}
