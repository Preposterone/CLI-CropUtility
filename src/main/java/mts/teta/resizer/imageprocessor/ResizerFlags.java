package mts.teta.resizer.imageprocessor;

import mts.teta.resizer.ResizerApp;
public class ResizerFlags {
	private static final int FLAG_RESIZE	 = 0b0001;
	private static final int FLAG_QUALITY	 = 0b0010;
	private static final int FLAG_CROP		 = 0b0100;
	private static final int FLAG_BLUR		 = 0b1000;
	private int flags = 0;

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	int readConfig(ResizerApp config) {
		int ret = 0b0000;

		if (config.isResizeExpected())
			ret = ret | FLAG_RESIZE;
//		if (config.isQualityExpected())
		ret = ret | FLAG_QUALITY;	//always true
		if (config.isCropExpected())
			ret = ret | FLAG_CROP;
		if (config.isBlurExpected())
			ret = ret | FLAG_BLUR;
		return (ret);
	}

	public boolean doResize(int flags)	{
		return ((flags & FLAG_RESIZE) > 0);
	}
	public boolean doQuality(int flags)	{
		return ((flags & FLAG_QUALITY) > 0);
	}
	public boolean doCrop(int flags)	{
		return ((flags & FLAG_CROP) > 0);
	}
	public boolean doBlur(int flags)	{
		return ((flags & FLAG_BLUR) > 0);
	}
}
