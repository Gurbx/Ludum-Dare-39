package com.gurbx.ld39.utils.sound;

import com.badlogic.gdx.audio.Sound;

public class TimedSound {
	private Sound sound;
	private float duration;
	private boolean finished;
	private long soundID;
	
	public TimedSound(Sound sound, float duration, float volume) {
		this.sound = sound;
		this.duration = duration;
		this.finished = false;
		this.soundID = this.sound.loop(volume);
	
	}
	
	public void update(float delta) {
		duration -= delta;
		if (duration <= 0) {
			sound.stop(soundID);
			finished = true;
		}
	}
	
	public boolean finishedPlaying() {
		return finished;
	}
}
