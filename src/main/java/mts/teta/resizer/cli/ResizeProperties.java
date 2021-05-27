package mts.teta.resizer.cli;

public class ResizeProperties {
	private int width;
	private int height;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public ResizeProperties(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public boolean isValid()	{
		return (getHeight() > 0 && getWidth() > 0);
	}
}
