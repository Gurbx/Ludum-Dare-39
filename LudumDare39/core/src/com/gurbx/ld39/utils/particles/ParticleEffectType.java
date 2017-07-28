package com.gurbx.ld39.utils.particles;

public enum ParticleEffectType {
	BLOOD_SPLATTER("img/bloodSplatter.p", 100, true);
	
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
