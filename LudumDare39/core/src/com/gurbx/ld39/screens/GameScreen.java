package com.gurbx.ld39.screens;

import com.badlogic.gdx.Screen;
import com.gurbx.ld39.Application;

public abstract class GameScreen implements Screen {
	protected final Application app;
	
	public GameScreen(Application app) {
		this.app = app;
	}

}
