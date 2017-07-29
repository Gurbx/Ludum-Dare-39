package com.gurbx.ld39.utils.projectile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.gurbx.ld39.utils.GameInterface;

public class Projectile implements GameInterface {
	protected float x, y, dx, dy;
	private float targetX, targetY;
	protected float impact;
	private float width, height, radians;
	private float lifeTime;
	protected boolean shouldRemove;
	private Sprite sprite;
	protected int damage;
	protected ProjectileType type;
	
	public Projectile(float x, float y, float targetX, float targetY, float speed, TextureRegion texture, int damage, float impact, ProjectileType type) {
		this.type = type;
		this.sprite = new Sprite(texture);
		this.x = x;
		this.y = y;
		this.sprite.setPosition(x, y);
		this.width = texture.getRegionWidth();
		this.height = texture.getRegionHeight();
		this.sprite.setPosition(x, y);
		lifeTime = 6f;
		this.damage = damage;
		sprite.setScale(1.5f);
		this.impact = impact;
		radians = (float) Math.atan2(targetY - y, targetX - x);
		
		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;
	}

	@Override
	public void update(float delta) {
		lifeTime-=delta;
		if (lifeTime <= 0) shouldRemove = true;
		
		x += dx * delta;
		y += dy * delta;
		
		sprite.setRotation((float) Math.toDegrees(radians) + 90);
		sprite.setPosition(x, y);
	}
	
	protected boolean hitsTarget(float eX, float eY) {
		int hitRange = 30;
		if (x-15 < eX && x-15 + hitRange > eX &&
				eY < y-15 + hitRange && eY > y-15) {
			return true;
		}
		return false;
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public void dispose() {
	}
	
	public boolean shouldRemove() {
		return shouldRemove;
	}
	
}