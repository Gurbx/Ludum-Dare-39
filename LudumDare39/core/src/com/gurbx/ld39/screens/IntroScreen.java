package com.gurbx.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
	private Label introText, introText2;
	private float a = 0.3f;
	
	private float timer = 6;
	private boolean text1;

	public IntroScreen(Application app) {
		super(app);
	}

	@Override
	public void show() {
		stage = new Stage(app.uiViewport);
		atlas = app.assets.get("img/generalPack.atlas", TextureAtlas.class);
		LabelStyle style = new LabelStyle(app.font1, Color.WHITE);
		introText = new Label("YOUR VILLAGE IS UNDER ATTACK!", style);
		introText.setPosition(Constants.UI_VIRTUAL_WIDTH*0.5f - introText.getWidth()*0.5f, Constants.UI_VIRTUAL_HEIGHT*0.5f + 20);
		introText.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2))));
		
		introText2 = new Label("DON'T LET THEM DESTROY YOUR POWER GENERATORS", style);
		introText2.setPosition(Constants.UI_VIRTUAL_WIDTH*0.5f - introText2.getWidth()*0.5f, Constants.UI_VIRTUAL_HEIGHT*0.5f + 20);
		introText2.addAction(sequence(alpha(0)));
		
		stage.addActor(introText);
		stage.addActor(introText2);
//		initButtons();
		
		text1 = false;
		Gdx.input.setInputProcessor(stage);
		
	}
	
	private void update(float delta) {
		stage.act();
		timer -= delta;
		if (timer < 3 && text1 == false) {
			text1 = true;
			introText.addAction(parallel(fadeOut(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2)));
			introText2.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2))));
		}
		if (timer <= 0.3f) {
			a -= delta;
			introText2.addAction(parallel(fadeOut(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2)));
		}
		
		if (timer <= 0) {
			app.setScreen(app.playScreen);
		}
		
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(a, a, a, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		
		app.batch.setProjectionMatrix(app.uiCamera.combined);
		app.batch.begin();
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
