package com.gurbx.ld39.utils.sound;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.audio.Sound;
import com.gurbx.ld39.Application;

public class SoundHandler {
	private static HashMap<Sounds, Sound> sounds;
	private static ArrayList<TimedSound> timedSounds;
	
	public SoundHandler(Application app) {
		sounds = new HashMap<Sounds, Sound>();
		timedSounds = new ArrayList<TimedSound>();
		
		
		//Init sounds
		for (Sounds soundType : Sounds.values()) {
			Sound sound = app.assets.get(soundType.getPath(), Sound.class);
			sounds.put(soundType, sound);
		}
	}
	
	public static void playSound(Sounds sound) {
		if (!Application.SOUND_ON) return;
		sounds.get(sound).play(sound.getVolume());
	}
	
	public static void playSound(Sounds sound, float duration)  {
		if (!Application.SOUND_ON) return;
		timedSounds.add(new TimedSound(sounds.get(sound), duration, sound.getVolume()));
		
	}
	
	public void update(float delta) {
		for (int i = 0; i < timedSounds.size(); i++) {
			timedSounds.get(i).update(delta);
			if (timedSounds.get(i).finishedPlaying()) {
				timedSounds.remove(i);
			}
 		}
	}
	
	
	public void dispose() {
		for (Sounds key : sounds.keySet()) {
			sounds.get(key).dispose();
		}
	}

}