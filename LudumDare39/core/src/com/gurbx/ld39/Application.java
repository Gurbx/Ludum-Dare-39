package com.gurbx.ld39;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gurbx.ld39.screens.GameOverScreen;
import com.gurbx.ld39.screens.IntroScreen;
import com.gurbx.ld39.screens.LoadingScreen;
import com.gurbx.ld39.screens.MenuScreen;
import com.gurbx.ld39.screens.PlayScreen;
import com.gurbx.ld39.utils.Constants;

public class Application extends Game {
	public final static boolean SOUND_ON = true;
	public AssetManager assets;
	public SpriteBatch batch;
	public Viewport viewport, uiViewport;
	public OrthographicCamera camera, uiCamera;
	public ShapeRenderer shapeRenderer;
	//Screens
	public LoadingScreen loadingScreen;
	public PlayScreen playScreen;
	public MenuScreen menuScreen;
	public IntroScreen introScreen;
	public GameOverScreen gameOverScreen;
	
	public BitmapFont font1;
	
	@Override
	public void create () {
		initGeneral();
		initFonts();
		initScreens();
	}
	
	private void initGeneral() {
		assets = new AssetManager();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		uiCamera = new OrthographicCamera();
		viewport = new StretchViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
		uiViewport = new StretchViewport(Constants.UI_VIRTUAL_WIDTH, Constants.UI_VIRTUAL_HEIGHT, uiCamera);
		viewport.apply();
		uiViewport.apply();
		camera.position.set(Constants.VIRTUAL_WIDTH/2, Constants.VIRTUAL_HEIGHT/2, 0);
		uiCamera.position.set(Constants.UI_VIRTUAL_WIDTH/2, Constants.UI_VIRTUAL_HEIGHT/2, 0);
		camera.update();
		uiCamera.update();
		shapeRenderer = new ShapeRenderer();
		
		font1 = new BitmapFont();
	}
	
	private void initFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/notomono-regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 14;
		parameter.borderWidth = 1f;
		parameter.borderColor = Color.DARK_GRAY;
		font1 = generator.generateFont(parameter); 
		parameter.size = 11;
//		font2 = generator.generateFont(parameter);
		generator.dispose(); 
	}
	
	private void initScreens() {
		loadingScreen = new LoadingScreen(this);
		playScreen = new PlayScreen(this);
		gameOverScreen = new GameOverScreen(this);
		menuScreen = new MenuScreen(this);
		introScreen = new IntroScreen(this);
		setScreen(loadingScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
		uiViewport.update(width, height);
		camera.update();
		uiCamera.update();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
		batch.dispose();
		loadingScreen.dispose();
		playScreen.dispose();
		shapeRenderer.dispose();
		font1.dispose();
		menuScreen.dispose();
		introScreen.dispose();
		gameOverScreen.dispose();
	}
}
