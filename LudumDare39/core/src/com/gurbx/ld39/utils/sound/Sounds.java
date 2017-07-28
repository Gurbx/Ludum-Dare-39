package com.gurbx.ld39.utils.sound;

public enum Sounds {
	BUTTON_CLICK ("sound/buttonClick.wav", 1f);
	
	private String path;
	private float volume;

	private Sounds(String path, float volume) {
		this.path = path;
		this.volume = volume;
	}

	public String getPath() {
		return path;
	}
	
	public float getVolume() {
		return volume;
	}
}
