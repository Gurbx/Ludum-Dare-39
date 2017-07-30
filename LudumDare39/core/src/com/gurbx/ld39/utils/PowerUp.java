package com.gurbx.ld39.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld39.player.Player;
import com.gurbx.ld39.utils.particles.ParticleEffectHandler;
import com.gurbx.ld39.utils.particles.ParticleEffectType;
import com.gurbx.ld39.utils.sound.SoundHandler;
import com.gurbx.ld39.utils.sound.Sounds;

public class PowerUp implements GameInterface {
	private Vector2 position;
	private float elapsedTime;
	private Animation animation;
	private float width, height;
	private boolean shouldRemove;
	private Player player;
	
	public PowerUp(Vector2 position, TextureAtlas atlas, Player player) {
		this.position = position;
		initAnimation(atlas);
		shouldRemove = false;
		this.player = player;
	}

	private void initAnimation(TextureAtlas atlas) {
		elapsedTime = 0;
		//STAND ANIMATION
		TextureRegion[] standFrames = new TextureRegion[4];
	    for (int i = 0; i < standFrames.length; i++) {
	    	standFrames[i] = atlas.findRegion("timePower" + (i+1));
	    }
	    animation = new Animation(1/4f, standFrames);  
		this.width = standFrames[0].getRegionWidth();
		this.height = standFrames[0].getRegionHeight();
	}
	

	@Override
	public void update(float delta) {
		elapsedTime += delta;
		if (overlaps(player.getPosition().x, player.getPosition().y, 32)) {
			shouldRemove = true;
			SoundHandler.playSound(Sounds.PICKUP);
			ParticleEffectHandler.addParticleEffect(ParticleEffectType.PICKUP, position.x, position.y);
		}
		
	}
	
	public boolean overlaps(float x, float y, float range) {
		if (x < position.x + width && x + range > position.x &&
				y < position.y + height && y + range > position.y) {
			return true;
		}
		return false;
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(animation.getKeyFrame(elapsedTime, true), position.x - width*0.5f, position.y-height*0.5f,
				width, height);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public boolean shouldRemove() {
		return shouldRemove;
	} 

}
