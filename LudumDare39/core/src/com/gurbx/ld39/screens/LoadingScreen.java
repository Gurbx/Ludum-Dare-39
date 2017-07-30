package com.gurbx.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.utils.sound.Sounds;

public class LoadingScreen extends GameScreen {

	public LoadingScreen(Application app) {
		super(app);
	}

	@Override
	public void show() {
		loadGeneralAssets();
		loadSounds();
		
	}

	private void loadGeneralAssets() {
		app.assets.load("img/generalPack.atlas", TextureAtlas.class);
		
		//Load tiled map
		app.assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		app.assets.load("maps/worldMap.tmx", TiledMap.class);
		
	}
	
	private void loadSounds() {
		for (Sounds soundType : Sounds.values()) {
			 app.assets.load(soundType.getPath(), Sound.class);
		}
		
	}

	private void update(float delta) {
		app.assets.update();
		if (app.assets.getProgress() >= 1) {
			app.setScreen(app.menuScreen);
		}
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		app.batch.setProjectionMatrix(app.uiCamera.combined);
		app.batch.begin();
		app.font1.draw(app.batch, "Loading...", 5, 15);
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
