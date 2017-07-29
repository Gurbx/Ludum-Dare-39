package com.gurbx.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.player.Player;
import com.gurbx.ld39.utils.Input;
import com.gurbx.ld39.utils.particles.ParticleEffectHandler;
import com.gurbx.ld39.utils.sound.SoundHandler;
import com.gurbx.ld39.world.World;

public class PlayScreen extends GameScreen {
	private TextureAtlas generalAtlas;
	private ParticleEffectHandler particleHandler;
//	private SoundHandler sound;
	private World world;
	private Player player;
	private Input input;

	public PlayScreen(Application app) {
		super(app);
	}

	@Override
	public void show() {
//		sound = new SoundHandler(app);
		generalAtlas = app.assets.get("img/generalPack.atlas", TextureAtlas.class);
//		particleHandler = new ParticleEffectHandler(generalAtlas)
		world = new World(app);
		player = new Player(world, generalAtlas);
		app.camera.position.set(player.getPosition(), 0);
		input = new Input(player);
		
		Gdx.input.setInputProcessor(input);
		
	}
	
	private void update(float delta) {
//		sound.update(delta);
		player.update(delta);
		world.update(delta);
		handleCamera(delta);
	}

	private void handleCamera(float delta) {
		float lerp = 5f;
		Vector3 position = app.camera.position;
		position.x += (player.getPosition().x  - position.x) * lerp * delta;
		position.y += (player.getPosition().y + 50  - position.y) * lerp * delta;
		
		app.camera.update();
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0.5f, 0.7f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.renderMap();
		
		app.batch.setProjectionMatrix(app.camera.combined);
		app.batch.begin();
		player.render(app.batch);
//		particleHandler.render(app.batch, delta);
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
//		particleHandler.dispose();
//		sound.dispose();
		generalAtlas.dispose();
		world.dispose();
		player.dispose();
		
	}

}
