package com.gurbx.ld39.utils.sound;

public enum Sounds {
	LASER ("sound/laser2.wav", 1f),
	LASER2 ("sound/laser4.wav", 0.5f),
	JUMP ("sound/jump.wav", 1f),
	ROLL ("sound/roll.wav", 1f),
	EXPLOSION1 ("sound/explosion1.wav", 0.6f),
	PICKUP ("sound/pickup.wav", 1f),
	SELECT ("sound/select.wav", 1f),
	SLOW ("sound/slow.wav", 1f),
	ATTACK ("sound/attack.wav", 0.24f),
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
