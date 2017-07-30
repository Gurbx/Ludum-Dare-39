package com.gurbx.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.utils.Constants;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class IntroScreen extends GameScreen {
	private TextureAtlas atlas;
	private Stage stage;
	private Label introText, introText2, introText3;
	private float a;
	
	private float timer;
	private boolean text1, text2;
	
	private Sprite bg;

	public IntroScreen(Application app) {
		super(app);
	}

	@Override
	public void show() {
		stage = new Stage(app.uiViewport);
		atlas = app.assets.get("img/generalPack.atlas", TextureAtlas.class);
		LabelStyle style = new LabelStyle(app.font2, Color.WHITE);
		introText = new Label("YOUR VILLAGE IS UNDER ATTACK!", style);
		introText.setPosition(Constants.UI_VIRTUAL_WIDTH*0.5f - introText.getWidth()*0.5f, Constants.UI_VIRTUAL_HEIGHT*0.5f + 20);
		introText.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2))));
		
		introText2 = new Label("POWER HUNGRY UNDEAD ARE AFTER YOUR CITY'S POWER GENERATORS", style);
		introText2.setPosition(Constants.UI_VIRTUAL_WIDTH*0.5f - introText2.getWidth()*0.5f, Constants.UI_VIRTUAL_HEIGHT*0.5f + 20);
		introText2.addAction(sequence(alpha(0)));
		
		introText3 = new Label("YOU'RE THE ONLY ONE STANDING IN THEIR WAY", style);
		introText3.setPosition(Constants.UI_VIRTUAL_WIDTH*0.5f - introText3.getWidth()*0.5f, Constants.UI_VIRTUAL_HEIGHT*0.5f + 20);
		introText3.addAction(sequence(alpha(0)));
		
		stage.addActor(introText);
		stage.addActor(introText2);
		stage.addActor(introText3);
//		initButtons();
		
		bg = new Sprite(atlas.findRegion("bg"));
		bg.setPosition(0, 0);
		
		text1 = false;
		text2 = false;
		Gdx.input.setInputProcessor(stage);
		
		timer = 12;
		a = 1;
		
	}
	
	private void update(float delta) {
		stage.act();
		timer -= delta;
		if (timer < 9 && text1 == false) {
			text1 = true;
			introText.addAction(parallel(fadeOut(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2)));
			introText2.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2))));
		}
		
		if (timer < 5 && text2 == false) {
			text2 = true;
			introText2.addAction(parallel(fadeOut(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2)));
			introText3.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2))));
		}
		if (timer <= 1) {
			a -= delta;
			introText3.addAction(parallel(fadeOut(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2)));
		}
		
		if (timer <= 0) {
			app.setScreen(app.playScreen);
		}
		
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(1-a, 1-a, 1-a, 1-a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		
		app.batch.setProjectionMatrix(app.uiCamera.combined);
		app.batch.begin();
//		bg.draw(app.batch);
		app.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
