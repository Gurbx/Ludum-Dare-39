package com.gurbx.ld39.utils.particles;

public enum ParticleEffectType {
	BLOOD1("img/blood1.p", 40, false),
	BLOOD2("img/blood2.p", 40, false),
	BLOOD_GROUND("img/bloodGround.p", 100, true),
	CLOUD("img/cloud.p", 20, false),
	FLARE("img/flare.p", 20, false),
	SPAWN("img/spawn.p", 20, false),
	FIRE("img/fire.p", 20, false),
	PICKUP("img/pickup.p", 10, false),
	HIT("img/hi1t.p", 40, false);
	
	private String path;
	private int poolSIze;
	private boolean behindTowers;


	private ParticleEffectType(String path, int poolSIze, boolean behindTowers) {
		this.path = path;
		this.poolSIze = poolSIze;
		this.behindTowers = behindTowers;
	}
	
	public String getPath() {
		return path;
	}


	public int getPoolSIze() {
		return poolSIze;
	}
	
	
	public boolean getBehindTowers() {
		return behindTowers;
	}

}
