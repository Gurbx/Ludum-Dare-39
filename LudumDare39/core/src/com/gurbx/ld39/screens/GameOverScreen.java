package com.gurbx.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.utils.Constants;

public class GameOverScreen extends GameScreen {
	
	private TextureAtlas atlas;
	private Stage stage;
	private Label gameOverLabel;
	private TextButton retryButton;

	public GameOverScreen(Application app) {
		super(app);
	}

	@Override
	public void show() {
		stage = new Stage(app.uiViewport);
		atlas = app.assets.get("img/generalPack.atlas", TextureAtlas.class);
		LabelStyle style = new LabelStyle(app.font1, Color.WHITE);
		gameOverLabel = new Label("Game Over!", style);
		gameOverLabel.setPosition(Constants.UI_VIRTUAL_WIDTH*0.5f - gameOverLabel.getWidth()*0.5f, Constants.UI_VIRTUAL_HEIGHT*0.5f + 20);
		stage.addActor(gameOverLabel);
		initButtons();
		Gdx.input.setInputProcessor(stage);
	}
	
	private void initButtons() {
        Skin skin = new Skin(atlas);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = app.font1;
        style.up = skin.getDrawable("button");
        style.down = skin.getDrawable("buttonPressed");
        style.over = skin.getDrawable("buttonHover");
        
        retryButton = new TextButton("RETRY", style);
        retryButton.setPosition(Constants.UI_VIRTUAL_WIDTH*0.5f-49, Constants.UI_VIRTUAL_HEIGHT*0.5f - 40);
        retryButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	app.setScreen(app.playScreen);
            };
        });
        
        stage.addActor(retryButton);
		
	}
	
	public void update(float delta) {
		stage.act();
		
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0.3f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		app.batch.begin();
//		app.font1.draw(app.batch, "GAME OVER!", 100, 100);
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
		if (stage!=null) stage.dispose();
		
	}

}
