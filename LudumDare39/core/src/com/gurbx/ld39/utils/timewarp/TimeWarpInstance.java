package com.gurbx.ld39.utils.timewarp;

public class TimeWarpInstance {
	private float timeModifier, targetTime, targetTimeDifference;
	private float timer, fadeInDuration, fadeOutDuration, timerFull;
	private float minTime;
	private boolean shouldRemove;
	
	public TimeWarpInstance(float totalDuration, float fadeInDuration, float fadeOutDuration, float percent, float minTime) {
		timeModifier = 1.0f;
		targetTime = timeModifier;
		timer = 0;
		fadeInDuration = 0;
		shouldRemove = false;
		this.targetTime = percent;
		this.timer = totalDuration;
		this.timerFull = totalDuration;
		this.fadeInDuration = fadeInDuration;
		this.fadeOutDuration = fadeOutDuration;
		this.targetTimeDifference = targetTime - 1;
		this.minTime = minTime;
	}

	public void update(float delta) {
		//Transition to time warp
		if (timer >= timerFull - fadeInDuration) {
			timeModifier += targetTimeDifference/fadeInDuration * delta;
		}
		//Transition out of time warp
		if (timer <= fadeOutDuration) {
			timeModifier -= targetTimeDifference/fadeOutDuration * delta;
		}
		if (timeModifier <= minTime) timeModifier = minTime;
		
		//Handle timer
		timer -= delta;
		if (timer <= 0) {
			shouldRemove = true;
		}
	}
	
	public boolean shouldRemove() { return shouldRemove; }
	public float getTimeModifier() { return timeModifier; }

}