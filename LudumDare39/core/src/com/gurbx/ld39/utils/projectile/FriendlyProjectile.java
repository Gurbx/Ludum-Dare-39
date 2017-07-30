package com.gurbx.ld39.utils.projectile;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gurbx.ld39.enemies.Enemy;
import com.gurbx.ld39.utils.particles.ParticleEffectHandler;
import com.gurbx.ld39.utils.particles.ParticleEffectType;

public class FriendlyProjectile extends Projectile {
	private ArrayList<Enemy> enemies;
	
	public FriendlyProjectile(float x, float y, float targetX, float targetY, float speed, 
			TextureRegion texture, ArrayList<Enemy> enemies, int damage, float impact, ProjectileType type) {
		super(x, y, targetX, targetY, speed, texture, damage, impact, type);
		this.enemies = enemies;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		checkCollision();
	}

	private void checkCollision() {
		for (int i = 0; i < enemies.size(); i++) {
			if (hitsTarget(enemies.get(i).getPosition().x, enemies.get(i).getPosition().y)) {
				switch (type) {
				case PLAYER_ATTACK:
					ParticleEffectHandler.addParticleEffect(ParticleEffectType.HIT, x, y);
					break;
				default:
					break;
				}
				enemies.get(i).damage(damage, impact*0.5f + (float) (Math.random()*impact*0.5f), x, y);
				shouldRemove = true;
			}
		}
	}
	
}
