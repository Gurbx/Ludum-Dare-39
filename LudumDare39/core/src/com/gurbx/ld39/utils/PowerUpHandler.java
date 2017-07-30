package com.gurbx.ld39.utils;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld39.player.Player;

public class PowerUpHandler implements GameInterface {
	private static Player player;
	private static TextureAtlas atlas;
	private static ArrayList<PowerUp> powerUps;
	
	public PowerUpHandler(Player player, TextureAtlas atlas) {
		this.atlas = atlas;
		this.player = player;
		powerUps = new ArrayList<>();
	}

	@Override
	public void update(float delta) {
		for (int i = 0; i < powerUps.size(); i++) {
			powerUps.get(i).update(delta);
			if (powerUps.get(i).shouldRemove()) {
				powerUps.get(i).dispose();
				player.addPower();
				powerUps.remove(i);
			}
		}
		
	}

	@Override
	public void render(SpriteBatch batch) {
		for (int i = 0; i < powerUps.size(); i++) {
			powerUps.get(i).render(batch);
		}
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public static void addPowerUp(Vector2 position) {
		powerUps.add(new PowerUp(position, atlas, player));
		
	}

}
