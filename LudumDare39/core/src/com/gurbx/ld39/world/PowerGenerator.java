package com.gurbx.ld39.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gurbx.ld39.utils.GameInterface;
import com.gurbx.ld39.utils.particles.ParticleEffectHandler;
import com.gurbx.ld39.utils.particles.ParticleEffectType;

public class PowerGenerator implements GameInterface {
	private float x, y;
	private final int MAX_HP = 100;
	private float health;
	private Sprite sprite;
	
	public PowerGenerator(float x, float y, TextureRegion texture) {
		this.x = x;
		this.y = y;
		this.sprite = new Sprite(texture);
		this.sprite.setPosition(x-texture.getRegionWidth()*0.5f, y-texture.getRegionHeight()*0.5f);
		health = MAX_HP;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public float getY() {
		return y;
	}
	
	public float getX() {
		return x;
	}

	public void damage(float damage) {
		health -= damage;
		ParticleEffectHandler.addParticleEffect(ParticleEffectType.HIT, x, y);
		
	}

	public float getHealth() {
		return health;
	}
	
	public float getMaxHealth() {
		return MAX_HP;
	}

}
