package core;

public class EntityAnimator {
	private float frameX = 0;
	private float frameY = 0;
	private int maxXFrame = 4;
	private int maxYFrame = 4;
	private float cycleSpeed = 0.002f;

	public int getFrameX() {
		return (int) frameX;
	}

	public void setFrameX(float frameX) {
		this.frameX = frameX;
	}

	public int getFrameY() {
		return (int) frameY;
	}

	public void setFrameY(float frameY) {
		this.frameY = frameY;
	}

	public int getMaxXFrame() {
		return maxXFrame;
	}

	public void setMaxXFrame(int maxXFrame) {
		this.maxXFrame = maxXFrame;
	}

	public int getMaxYFrame() {
		return maxYFrame;
	}

	public void setMaxYFrame(int maxYFrame) {
		this.maxYFrame = maxYFrame;
	}

	public void cycle() {
		frameX += cycleSpeed;
		if (frameX > maxXFrame) {
			frameX = 0;
		}
	}
}
