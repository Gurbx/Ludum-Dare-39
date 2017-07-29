package com.gurbx.ld39.enemies;

public enum EnemyType {
	
	BASIC ("basic", 10f, 1f, 1);
	
	private String path;
	private float health;
	private float damage;
	private int stabilty;
	
	
	private EnemyType(String path, float health, float damage, int stabilty) {
		this.path = path;
		this.health = health;
		this.damage = damage;
		this.stabilty = stabilty;
	}


	public String getPath() {
		return path;
	}


	public float getHealth() {
		return health;
	}


	public float getDamage() {
		return damage;
	}


	public int getStabilty() {
		return stabilty;
	}
	
	
	
	

}
