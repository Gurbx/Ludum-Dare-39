package com.gurbx.ld39.utils.timewarp;

import java.util.ArrayList;


public class TimeWarp {
	private final float MIN_TIME = 0.1f;
	private float timeModifier;
	private ArrayList<TimeWarpInstance> timeWarps;
	
	public TimeWarp() {
		timeWarps = new ArrayList<TimeWarpInstance>();
		reset();
	}

	private void reset() {
		timeWarps.clear();
		timeModifier = 1.0f;
	}
	
	public void update(float delta) {		
		timeModifier = getTimeModifier(delta);
	}
	
	private float getTimeModifier(float delta) {
		float lowestModifier = 1.0f;
		for (int i = 0; i < timeWarps.size(); i++) {
			timeWarps.get(i).update(delta);
			if (timeWarps.get(i).shouldRemove()) {
				timeWarps.remove(i);
			} else {
				if (Math.abs(timeWarps.get(i).getTimeModifier()) < lowestModifier) {
					lowestModifier = timeWarps.get(i).getTimeModifier();
				}
			}
		}
		return lowestModifier;
	}

	public void warpTime(float totalDuration, float fadeInDuration, float fadeOutDuration, float percent) {
		if (percent > 1) return; // Cant handle speed up yet.
		timeWarps.add(new TimeWarpInstance(totalDuration, fadeInDuration, fadeOutDuration, percent, MIN_TIME));
	}
	
	
	public float getTimeModifer() { return timeModifier; }
}

