package cheese.model;

import deserted.model.GameSession;

public class TimedItem {

	private boolean timerStarted;
	private double startTime;

	public double getDuration() {
		return 24*60;
	}
	
	public void onTick() {
		
	}
	
	public void update(float deltaTime) {
		if(timerStarted) {
			double currentTime = GameSession.getInstance().getTimeSurvived();
			if(currentTime - startTime >= getDuration()) {
				startTime = currentTime;
				onTick();
			}
		}
	}
	
	public void startTiming() {
		startTime = GameSession.getInstance().getTimeSurvived();
		timerStarted = true;
	}
	
	public void stopTiming() {
		timerStarted = false;
	}
}
