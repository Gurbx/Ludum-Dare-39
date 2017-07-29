package com.gurbx.ld39.utils;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.gurbx.ld39.player.Player;

public class Input implements InputProcessor {
	private Player player;
	
	public Input(Player player) {
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
		// TODO Auto-generated method stub
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

}
