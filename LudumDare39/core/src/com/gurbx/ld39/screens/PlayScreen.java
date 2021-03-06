package com.gurbx.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.enemies.EnemyHandler;
import com.gurbx.ld39.player.Player;
import com.gurbx.ld39.player.PlayerProjectileHandler;
import com.gurbx.ld39.ui.UI;
import com.gurbx.ld39.utils.Constants;
import com.gurbx.ld39.utils.Input;
import com.gurbx.ld39.utils.PowerUpHandler;
import com.gurbx.ld39.utils.particles.ParticleEffectHandler;
import com.gurbx.ld39.utils.particles.ParticleEffectType;
import com.gurbx.ld39.utils.sound.SoundHandler;
import com.gurbx.ld39.utils.sound.Sounds;
import com.gurbx.ld39.utils.timewarp.TimeWarp;
import com.gurbx.ld39.world.World;

public class PlayScreen extends GameScreen {
	private TextureAtlas generalAtlas;
	private ParticleEffectHandler particleHandler;
	private SoundHandler sound;
	private World world;
	private Player player;
	private Input input;
	private EnemyHandler enemyHandler;
	private UI ui;
	private PlayerProjectileHandler playerProjectileHandler;
	private PowerUpHandler powerUpHandler;
	
	private TimeWarp timeWarp;
	private float timeModifier;

	public PlayScreen(Application app) {
		super(app);
	}

	@Override
	public void show() {
		timeWarp = new TimeWarp();
		sound = new SoundHandler(app);
		generalAtlas = app.assets.get("img/generalPack.atlas", TextureAtlas.class);
		particleHandler = new ParticleEffectHandler(generalAtlas);
		world = new World(app, generalAtlas);
		player = new Player(world, generalAtlas);
		app.camera.position.set(player.getPosition(), 0);
		input = new Input(player, app);
		enemyHandler = new EnemyHandler(generalAtlas, world, player);
		playerProjectileHandler = new PlayerProjectileHandler(enemyHandler.getEnemies());
		player.setEnemyHandler(enemyHandler, playerProjectileHandler);
		ui = new UI(generalAtlas, app, player, world);
		powerUpHandler = new PowerUpHandler(player, generalAtlas);
		
		Pixmap pm = new Pixmap(Gdx.files.internal("cursorImage.png"));
		Gdx.input.setCursorImage(pm, 0, 0);
		pm.dispose();
		
		Gdx.input.setInputProcessor(input);
	}
	
	private void update(float delta) {
		timeWarp.update(delta);
		timeModifier = timeWarp.getTimeModifer();
		ui.setTimeModifier(timeModifier);
//		sound.update(delta);
		input.update(delta);
		player.update(delta);
		if (player.isDead()) {
			sound.muteSounds();
			app.setScreen(app.gameOverScreen);
		}
		playerProjectileHandler.update(delta * timeModifier);
		powerUpHandler.update(delta);
		world.update(delta*timeModifier);
		handleCamera(delta);
		enemyHandler.update(delta*timeModifier);
		ui.update(delta);
		
		
		if (Gdx.input.isKeyJustPressed(Keys.E)) {
			if (player.getTimePower() >= 1) {
				player.timeWarpUsed();
				SoundHandler.playSound(Sounds.SLOW);
				ParticleEffectHandler.addParticleEffect(ParticleEffectType.PICKUP, player.getPosition().x, player.getPosition().y);
				timeWarp.warpTime(5.5f, 1f, 1f, 0.1f);
			}
		}
	}

	private void handleCamera(float delta) {
		float lerp = 10f;
		Vector3 position = app.camera.position;
		position.x += (player.getPosition().x  - position.x) * lerp * delta;
		position.y += (player.getPosition().y + 30  - position.y) * lerp * delta;
		
		if (position.y < 0 + Constants.VIRTUAL_HEIGHT*0.5f) position.y = Constants.VIRTUAL_HEIGHT*0.5f;
		
		app.camera.update();
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0.4f, 0.7f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		app.batch.setProjectionMatrix(app.uiCamera.combined);
		app.batch.begin();
		ui.renderBG(app.batch);
		app.batch.end();
		
		world.renderMap();
		
		app.batch.setProjectionMatrix(app.camera.combined);
		app.batch.begin();
		world.render(app.batch);
		particleHandler.renderBehindTowers(app.batch, delta * timeModifier);
		enemyHandler.render(app.batch);
		player.render(app.batch);
		powerUpHandler.render(app.batch);
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
		sound.dispose();
		generalAtlas.dispose();
		world.dispose();
		player.dispose();
		enemyHandler.dispose();
		ui.dispose();
		playerProjectileHandler.dispose();
		powerUpHandler.dispose();
		
	}

}
