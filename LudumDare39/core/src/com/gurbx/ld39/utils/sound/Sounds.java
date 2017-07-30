package com.gurbx.ld39.utils.sound;

public enum Sounds {
	LASER ("sound/laser2.wav", 1f),
	JUMP ("sound/jump.wav", 1f),
	ROLL ("sound/Roll.wav", 1f),
	EXPLOSION1 ("sound/explosion1.wav", 0.6f),
	HIT ("sound/hit1.wav", 1f),
	HIT2 ("sound/hit2.wav", 1f);
	
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
