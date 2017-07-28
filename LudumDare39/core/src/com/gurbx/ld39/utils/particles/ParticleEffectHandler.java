package com.gurbx.ld39.utils.particles;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Array;

public class ParticleEffectHandler {
	private TextureAtlas generalAtlas;
	private static HashMap<ParticleEffectType, ParticleEffectPool> pools;
	private static HashMap<ParticleEffectType, Array<PooledEffect>> activeEffectsMap;
	
	public ParticleEffectHandler(TextureAtlas atlas) {
		this.generalAtlas = atlas;
		pools = new HashMap<ParticleEffectType, ParticleEffectPool>();
		activeEffectsMap = new HashMap<ParticleEffectType, Array<PooledEffect>>();
		
		//Init effects
		for (ParticleEffectType effectType : ParticleEffectType.values()) {
			ParticleEffect particleEffect = new ParticleEffect();
			particleEffect.load(Gdx.files.internal(effectType.getPath()), atlas);
			
			ParticleEffectPool pool = new ParticleEffectPool(particleEffect, (int) (effectType.getPoolSIze()*0.5f), effectType.getPoolSIze());
			pools.put(effectType, pool);
			
			Array<PooledEffect> activeEffects = new Array<PooledEffect>();
			activeEffectsMap.put(effectType, activeEffects);
		}
	}
	
	public static void addParticleEffect(ParticleEffectType type, float x, float y) {
		PooledEffect effect = pools.get(type).obtain();
		if (effect != null) {
			activeEffectsMap.get(type).add(effect);
			effect.setPosition(x, y);
		}
	}
	
	public static void addParticleEffect(ParticleEffectType type, float x, float y, int duration) {
		PooledEffect effect = pools.get(type).obtain();
		if (effect != null) {
			activeEffectsMap.get(type).add(effect);
			effect.setDuration(duration);
			effect.setPosition(x, y);
		}
	}
	
	public void render(SpriteBatch batch, float delta) {
		for (ParticleEffectType key : ParticleEffectType.values()) {
			if (key.getBehindTowers()) continue;
			Array<PooledEffect> effects = activeEffectsMap.get(key);
			for (int i = 0; i <effects.size; i++) {
				PooledEffect effect = effects.get(i);
				if (effect.isComplete()) {
					pools.get(key).free(effect);
					effects.removeIndex(i);
				} else {
					effect.draw(batch, delta);
				}
			}
		}
	}
	
	public void renderBehindTowers(SpriteBatch batch, float delta) {
		for (ParticleEffectType key : ParticleEffectType.values()) {
			if (!key.getBehindTowers()) continue;
			Array<PooledEffect> effects = activeEffectsMap.get(key);
			for (int i = 0; i <effects.size; i++) {
				PooledEffect effect = effects.get(i);
				if (effect.isComplete()) {
					pools.get(key).free(effect);
					effects.removeIndex(i);
				} else {
					effect.draw(batch, delta);
				}
			}
		}
	}
	
	public void dispose() {
		for (ParticleEffectType key : ParticleEffectType.values()) {
			pools.get(key).clear();
			for (int i = 0; i < activeEffectsMap.get(key).size; i++) {
				activeEffectsMap.get(key).get(i).dispose();
			}
		}
	}

}
