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
	private Sprite sprite, damaged1, damaged2, damaged3;
	private boolean burning;
	
	public PowerGenerator(float x, float y, TextureRegion texture, TextureRegion textureDamaged1,
			TextureRegion textureDamaged2, TextureRegion textureDamaged3) {
		this.x = x;
		this.y = y;
		this.sprite = new Sprite(texture);
		this.sprite.setPosition(x-texture.getRegionWidth()*0.5f, y-texture.getRegionHeight()*0.5f);
		
		this.damaged1 = new Sprite(textureDamaged1);
		this.damaged1.setPosition(x-textureDamaged1.getRegionWidth()*0.5f, y-textureDamaged1.getRegionHeight()*0.5f);
		this.damaged2 = new Sprite(textureDamaged2);
		this.damaged2.setPosition(x-textureDamaged2.getRegionWidth()*0.5f, y-textureDamaged2.getRegionHeight()*0.5f);
		this.damaged3 = new Sprite(textureDamaged3);
		this.damaged3.setPosition(x-textureDamaged3.getRegionWidth()*0.5f, y-textureDamaged3.getRegionHeight()*0.5f);
		
		
		health = MAX_HP;
		burning = false;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch batch) {
		if (health > MAX_HP*0.75f) {
			sprite.draw(batch);
		} else if (health > MAX_HP * 0.5f){
			damaged1.draw(batch);
		} else if (health > MAX_HP * 0.25){
			damaged2.draw(batch);
			if (!burning) {
				burning =true;
				ParticleEffectHandler.addParticleEffect(ParticleEffectType.FIRE, x, y, 999999);
			}
		} else {
			damaged3.draw(batch);
		}
		
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
