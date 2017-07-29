package com.gurbx.ld39.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.player.Player;
import com.gurbx.ld39.utils.Constants;
import com.gurbx.ld39.utils.GameInterface;

public class UI implements GameInterface {
	private final Application app;
	private Player player;
	private Sprite hp;
	private Sprite bar;
	
	public UI(TextureAtlas atlas, Application app, Player player) {
		this.app = app;
		this.player = player;
		hp = new Sprite(atlas.findRegion("heart"));
		hp.setPosition(Constants.UI_VIRTUAL_WIDTH - 80, Constants.UI_VIRTUAL_HEIGHT - 20);
		bar = new Sprite(atlas.findRegion("bar"));
		bar.setPosition(Constants.UI_VIRTUAL_WIDTH - 60, Constants.UI_VIRTUAL_HEIGHT - 20);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch batch) {
		bar.draw(batch);
		hp.draw(batch);
	}
	
	public void renderBars() {
		app.shapeRenderer.begin(ShapeType.Filled);
		app.shapeRenderer.setColor(Color.RED);
		app.shapeRenderer.rect(bar.getX()+3, bar.getY(), bar.getWidth()*player.getHealth()/player.getMaxHealth() -6, bar.getHeight());
		app.shapeRenderer.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
