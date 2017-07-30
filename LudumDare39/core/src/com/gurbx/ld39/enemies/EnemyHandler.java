package com.gurbx.ld39.enemies;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld39.player.Player;
import com.gurbx.ld39.utils.GameInterface;
import com.gurbx.ld39.utils.particles.ParticleEffectHandler;
import com.gurbx.ld39.utils.particles.ParticleEffectType;
import com.gurbx.ld39.world.World;

public class EnemyHandler implements GameInterface {
	private ArrayList<Enemy> enemies;
	private TextureAtlas atlas;
	private World world;
	private Player player;
	
	private final float SPAWN_TIME = 3f;
	private float spawnTimer;
	private float spawnX, spawnWidth;
	private float spawnY;
	
	private Random random;
	
	public EnemyHandler(TextureAtlas atlas, World world, Player player) {
		this.atlas = atlas;
		this.world = world;
		this.player = player;
		enemies = new ArrayList<>();
		random = new Random();
		
		spawnX = world.getGroundX();
		spawnWidth = world.getGroundWidth();
		spawnY = 500;
		
//		enemies.add(new Enemy(atlas, new Vector2(400, 600), world, player));
	}
	
	private void handleEnemySpawn(float delta) {
		spawnTimer -= delta;
		if (spawnTimer <= 0) {
			spawnEnemy();
			spawnTimer = SPAWN_TIME;
			if (world.getCountdownTimer() < 80) spawnTimer = SPAWN_TIME * 0.5f;
		}
	}
	
	private void spawnEnemy() {
		float x = spawnX + random.nextFloat()*spawnWidth;
		enemies.add(new Enemy(atlas, new Vector2(x, spawnY), world, player));
		ParticleEffectHandler.addParticleEffect(ParticleEffectType.SPAWN, x, spawnY);
	}

	@Override
	public void update(float delta) {
		handleEnemySpawn(delta);
		
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update(delta);
			if (enemies.get(i).shouldRemove()){
				enemies.get(i).dispose();
				enemies.remove(i);
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(batch);
		}
		
	}

	@Override
	public void dispose() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).dispose();
		}
	}

	public void attack(float x, float y, float range, int damage, float impact) {
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).overlaps(x,y,range)) {
				float imp =  (float) (Math.random()*impact);
				enemies.get(i).damage(damage, imp, x,y);
			}
		}
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

}
