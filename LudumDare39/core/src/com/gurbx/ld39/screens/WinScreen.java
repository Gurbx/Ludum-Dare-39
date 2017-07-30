package com.gurbx.ld39.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.utils.Constants;

public class WinScreen extends GameScreen {
	private TextureAtlas atlas;
	private Stage stage;
	private Label introText, introText2, introText3, introText4;
	private float a;
	
	private float timer;
	private boolean text1, text2, text3;

	public WinScreen(Application app) {
		super(app);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		stage = new Stage(app.uiViewport);
		atlas = app.assets.get("img/generalPack.atlas", TextureAtlas.class);
		LabelStyle style = new LabelStyle(app.font2, Color.WHITE);
		introText = new Label("CONGRATULATIONS!", style);
		introText.setPosition(Constants.UI_VIRTUAL_WIDTH*0.5f - introText.getWidth()*0.5f, Constants.UI_VIRTUAL_HEIGHT*0.5f + 20);
		introText.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2))));
		
		introText2 = new Label("THE UNDEAD HORDE WAS DEFEATED", style);
		introText2.setPosition(Constants.UI_VIRTUAL_WIDTH*0.5f - introText2.getWidth()*0.5f, Constants.UI_VIRTUAL_HEIGHT*0.5f + 20);
		introText2.addAction(sequence(alpha(0)));
		
		introText3 = new Label("UNFORTUNATELY YOUR CITY STILL RAN OUT OF POWER...", style);
		introText3.setPosition(Constants.UI_VIRTUAL_WIDTH*0.5f - introText3.getWidth()*0.5f, Constants.UI_VIRTUAL_HEIGHT*0.5f + 20);
		introText3.addAction(sequence(alpha(0)));
		
		introText4 = new Label("THANK YOU FOR PLAYING!", style);
		introText4.setPosition(Constants.UI_VIRTUAL_WIDTH*0.5f - introText4.getWidth()*0.5f, Constants.UI_VIRTUAL_HEIGHT*0.5f + 20);
		introText4.addAction(sequence(alpha(0)));
		
		stage.addActor(introText);
		stage.addActor(introText2);
		stage.addActor(introText3);
		stage.addActor(introText4);
//		initButtons();
		
		text1 = false;
		text2 = false;
		text3 = false;
		Gdx.input.setInputProcessor(stage);
		
		timer = 15;
		a = 1;
		
	}
	
	private void update(float delta) {
		stage.act();
		timer -= delta;
		if (timer < 12 && text1 == false) {
			text1 = true;
			introText.addAction(parallel(fadeOut(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2)));
			introText2.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2))));
		}
		
		if (timer < 9 && text2 == false) {
			text2 = true;
			introText2.addAction(parallel(fadeOut(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2)));
			introText3.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2))));
		}
		if (timer < 5 && text3 == false) {
			text3 = true;
			introText3.addAction(parallel(fadeOut(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2)));
			introText4.addAction(sequence(alpha(0), parallel(fadeIn(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2))));
		}
		if (timer <= 1) {
			a -= delta;
			introText4.addAction(parallel(fadeOut(0.5f), moveBy(0, -20, 0.5f, Interpolation.pow2)));
		}
		
		if (timer <= 0) {
			app.setScreen(app.menuScreen);
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
		if (stage != null)stage.dispose();
		
	}

}
