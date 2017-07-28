package com.gurbx.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.utils.sound.Sounds;

public class LoadingScreen extends GameScreen {

	public LoadingScreen(Application app) {
		super(app);
	}

	@Override
	public void show() {
		loadGeneralAssets();
//		loadSounds();
		
	}

	private void loadGeneralAssets() {
		app.assets.load("img/generalPack.atlas", TextureAtlas.class);
		
	}
	
	private void loadSounds() {
		for (Sounds soundType : Sounds.values()) {
			 app.assets.load(soundType.getPath(), Sound.class);
		}
		
	}

	private void update(float delta) {
		app.assets.update();
		if (app.assets.getProgress() >= 1) {
			app.setScreen(app.playScreen);
		}
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0.5f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		app.batch.begin();
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
		
	}

}
