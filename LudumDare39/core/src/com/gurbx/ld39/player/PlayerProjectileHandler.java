package com.gurbx.ld39.player;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gurbx.ld39.enemies.Enemy;
import com.gurbx.ld39.utils.GameInterface;
import com.gurbx.ld39.utils.projectile.FriendlyProjectile;

public class PlayerProjectileHandler implements GameInterface {
	private TextureAtlas atlas;
	private static ArrayList<FriendlyProjectile> projectiles;
	private ArrayList<Enemy> enemies;
	
	
	public PlayerProjectileHandler(ArrayList<Enemy> enemies) {
		projectiles = new ArrayList<FriendlyProjectile>();
		this.enemies = enemies;
	}

	@Override
	public void update(float delta) {
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update(delta);
			if (projectiles.get(i).shouldRemove()) {
				projectiles.remove(i);
			}
		}
		
	}

	@Override
	public void render(SpriteBatch batch) {
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(batch);
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public static void addProjectile(FriendlyProjectile projectile) {
		projectiles.add(projectile);
	}
}
