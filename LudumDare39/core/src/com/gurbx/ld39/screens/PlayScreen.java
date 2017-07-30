package com.gurbx.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.enemies.EnemyHandler;
import com.gurbx.ld39.player.Player;
import com.gurbx.ld39.player.PlayerProjectileHandler;
import com.gurbx.ld39.ui.UI;
import com.gurbx.ld39.utils.Input;
import com.gurbx.ld39.utils.particles.ParticleEffectHandler;
import com.gurbx.ld39.utils.sound.SoundHandler;
import com.gurbx.ld39.utils.timewarp.TimeWarp;
import com.gurbx.ld39.world.World;

public class PlayScreen extends GameScreen {
	private TextureAtlas generalAtlas;
	private ParticleEffectHandler particleHandler;
//	private SoundHandler sound;
	private World world;
	private Player player;
	private Input input;
	private EnemyHandler enemyHandler;
	private UI ui;
	private PlayerProjectileHandler playerProjectileHandler;
	
	private TimeWarp timeWarp;
	private float timeModifier;

	public PlayScreen(Application app) {
		super(app);
	}

	@Override
	public void show() {
		timeWarp = new TimeWarp();
//		sound = new SoundHandler(app);
		generalAtlas = app.assets.get("img/generalPack.atlas", TextureAtlas.class);
		particleHandler = new ParticleEffectHandler(generalAtlas);
		world = new World(app, generalAtlas);
		player = new Player(world, generalAtlas);
		app.camera.position.set(player.getPosition(), 0);
		input = new Input(player);
		enemyHandler = new EnemyHandler(generalAtlas, world, player);
		playerProjectileHandler = new PlayerProjectileHandler(enemyHandler.getEnemies());
		player.setEnemyHandler(enemyHandler, playerProjectileHandler);
		ui = new UI(generalAtlas, app, player, world);
		
		Gdx.input.setInputProcessor(input);
	}
	
	private void update(float delta) {
		timeWarp.update(delta);
		timeModifier = timeWarp.getTimeModifer();
		ui.setTimeModifier(timeModifier);
//		sound.update(delta);
		player.update(delta);
		playerProjectileHandler.update(delta * timeModifier);
		world.update(delta*timeModifier);
		handleCamera(delta);
		enemyHandler.update(delta*timeModifier);
		ui.update(delta);
		
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			timeWarp.warpTime(5.5f, 1f, 1f, 0.1f);
		}
	}

	private void handleCamera(float delta) {
		float lerp = 10f;
		Vector3 position = app.camera.position;
		position.x += (player.getPosition().x  - position.x) * lerp * delta;
		position.y += (player.getPosition().y + 30  - position.y) * lerp * delta;
		
		app.camera.update();
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0.4f, 0.7f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.renderMap();
		
		app.batch.setProjectionMatrix(app.camera.combined);
		app.batch.begin();
		world.render(app.batch);
		particleHandler.renderBehindTowers(app.batch, delta * timeModifier);
		enemyHandler.render(app.batch);
		player.render(app.batch);
		playerProjectileHandler.render(app.batch);
		particleHandler.render(app.batch, delta*timeModifier);
		app.batch.end();
		
		app.shapeRenderer.setProjectionMatrix(app.uiCamera.combined);
		ui.renderBars();
		
		app.batch.setProjectionMatrix(app.uiCamera.combined);
		app.batch.begin();
		ui.render(app.batch);
		app.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		particleHandler.dispose();
//		sound.dispose();
		generalAtlas.dispose();
		world.dispose();
		player.dispose();
		enemyHandler.dispose();
		ui.dispose();
		playerProjectileHandler.dispose();
		
	}

}
