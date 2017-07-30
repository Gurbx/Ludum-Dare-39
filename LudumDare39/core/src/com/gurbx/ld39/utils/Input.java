package com.gurbx.ld39.utils;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.gurbx.ld39.Application;
import com.gurbx.ld39.player.Player;

public class Input implements InputProcessor {
	private Player player;
	private final Application app;
	
	public Input(Player player, Application app) {
		this.app = app;
		this.player = player;
	}

	@Override
	public boolean keyDown(int keycode) {
//		if (keycode == Keys.LEFT) {
//			return true;
//		}
//		if (keycode == Keys.RIGHT) {
//			return true;
//		}
		return false;
	}
	
	public void update(float delta) {
		float radians = (float) Math.atan2(getMousePosInGameWorld().y - player.getPosition().y, getMousePosInGameWorld().x - player.getPosition().x);
		player.setGunRotation((float) Math.toDegrees(radians));
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.UP) {
//			player.jump();
			return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == 0) {
			player.shoot(getMousePosInGameWorld().x, getMousePosInGameWorld().y);
		}
		if (button == 1) {
			player.shootHeavy(getMousePosInGameWorld().x, getMousePosInGameWorld().y);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private Vector3 getMousePosInGameWorld() {
		 return app.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
	}

}
