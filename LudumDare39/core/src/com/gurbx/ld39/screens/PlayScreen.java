package com.gurbx.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.utils.particles.ParticleEffectHandler;
import com.gurbx.ld39.utils.sound.SoundHandler;
import com.gurbx.ld39.world.World;

public class PlayScreen extends GameScreen {
	private TextureAtlas generalAtlas;
	private ParticleEffectHandler particleHandler;
//	private SoundHandler sound;
	private World world;

	public PlayScreen(Application app) {
		super(app);
	}

	@Override
	public void show() {
//		sound = new SoundHandler(app);
		generalAtlas = app.assets.get("img/generalPack.atlas", TextureAtlas.class);
//		particleHandler = new ParticleEffectHandler(generalAtlas)
		world = new World(app);
		
	}
	
	private void update(float delta) {
//		sound.update(delta);
		world.update(delta);
		
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.renderMap();
		
		app.batch.setProjectionMatrix(app.camera.combined);
		app.batch.begin();
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
		
	}

}
