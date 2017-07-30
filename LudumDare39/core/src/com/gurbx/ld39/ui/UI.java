package com.gurbx.ld39.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.player.Player;
import com.gurbx.ld39.utils.Constants;
import com.gurbx.ld39.utils.GameInterface;
import com.gurbx.ld39.world.World;

public class UI implements GameInterface {
	private final Application app;
	private Player player;
	private World world;
	private Sprite hp, energy;
	private Sprite playerHPBar, playerEnergyBar, generatorLeftBar, generatorRightBar;
	private Sprite timeWarp;
	private float timeModifier;
	private float barWidth;
	
	public UI(TextureAtlas atlas, Application app, Player player, World world) {
		this.app = app;
		this.player = player;
		this.world = world;
		energy = new Sprite(atlas.findRegion("powerIcon"));
		energy.setPosition(Constants.UI_VIRTUAL_WIDTH - 130, Constants.UI_VIRTUAL_HEIGHT - 36);
		energy.setScale(0.75f);
		hp = new Sprite(atlas.findRegion("heart"));
		hp.setScale(0.75f);
		hp.setPosition(Constants.UI_VIRTUAL_WIDTH - 130, Constants.UI_VIRTUAL_HEIGHT - 20);
		timeWarp = new Sprite(atlas.findRegion("timeWarp"));
		timeWarp.setPosition(0, 0);
		initBars(atlas);
	}

	private void initBars(TextureAtlas atlas) {
		TextureRegion barTex = atlas.findRegion("bar");
		barWidth = barTex.getRegionWidth();
		playerHPBar = new Sprite(barTex);
		playerHPBar.setPosition(Constants.UI_VIRTUAL_WIDTH - barWidth-10, Constants.UI_VIRTUAL_HEIGHT - 20);
		playerEnergyBar = new Sprite(barTex);
		playerEnergyBar.setPosition(Constants.UI_VIRTUAL_WIDTH - barWidth-10, Constants.UI_VIRTUAL_HEIGHT - 36);
		generatorLeftBar = new Sprite(barTex);
		generatorLeftBar.setPosition(10, Constants.UI_VIRTUAL_HEIGHT - 20);
		generatorRightBar = new Sprite(barTex);
		generatorRightBar.setPosition(10, Constants.UI_VIRTUAL_HEIGHT - 36);
		
	}

	@Override
	public void update(float delta) {
		timeWarp.setAlpha(1 - timeModifier);
	}

	@Override
	public void render(SpriteBatch batch) {
		timeWarp.draw(batch);
		playerHPBar.draw(batch);
		playerEnergyBar.draw(batch);
		generatorLeftBar.draw(batch);
		generatorRightBar.draw(batch);
		hp.draw(batch);
		energy.draw(batch);
		
		//Render timer
		app.font1.draw(batch, "Survive: " + world.getCountdownTimer(), Constants.UI_VIRTUAL_WIDTH*0.5f - 30 , Constants.UI_VIRTUAL_HEIGHT - 10);
	}
	
	public void renderBars() {
		app.shapeRenderer.begin(ShapeType.Filled);
		//Player ENERGY
		app.shapeRenderer.setColor(Color.YELLOW);
		app.shapeRenderer.rect(playerEnergyBar.getX()+3, playerEnergyBar.getY(), playerEnergyBar.getWidth()*player.getEnergy()/player.getMaxEnergy() -6, playerEnergyBar.getHeight());
		//Player HP
		app.shapeRenderer.setColor(0.8f, 0f, 0f, 1f);
		app.shapeRenderer.rect(playerHPBar.getX()+3, playerHPBar.getY(), playerHPBar.getWidth()*player.getHealth()/player.getMaxHealth() -6, playerHPBar.getHeight());
		
		//Generator left
		app.shapeRenderer.rect(generatorLeftBar.getX()+3, generatorLeftBar.getY(), generatorLeftBar.getWidth()*world.getLeftGenerator().getHealth()/world.getLeftGenerator().getMaxHealth() -6, generatorLeftBar.getHeight());
		app.shapeRenderer.rect(generatorRightBar.getX()+3, generatorRightBar.getY(), generatorRightBar.getWidth()*world.getRightGenerator().getHealth()/world.getRightGenerator().getMaxHealth() -6, generatorRightBar.getHeight());
		app.shapeRenderer.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void setTimeModifier(float timeModifier) {
		this.timeModifier = timeModifier;
		
	}

}
