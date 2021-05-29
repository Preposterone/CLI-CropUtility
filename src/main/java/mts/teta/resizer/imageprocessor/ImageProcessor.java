package mts.teta.resizer.imageprocessor;

import mts.teta.resizer.ResizerApp;

/*	Resize and quality	*/
import net.coobird.thumbnailator.Thumbnails;
/*	Crop and blur	*/
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;

import static java.lang.System.exit;
import static marvinplugins.MarvinPluginCollection.crop;
import static marvinplugins.MarvinPluginCollection.gaussianBlur;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageProcessor extends ResizerFlags {
	public void processImage(BufferedImage read, ResizerApp resizerApp) {
		setFlags(readConfig(resizerApp));
		try {
			performOperations(read, resizerApp);
		} catch (IOException e) {
			System.out.println(e.toString());
			exit(1);
		}
	}

	void performOperations(BufferedImage read, ResizerApp config) throws IOException {
		BufferedImage output = null;

		if (doResize(this.getFlags())) {	//if required - we have resized the image and saved in buffer
				output = Thumbnails.of(read)
							.forceSize(config.getResizeWidth(), config.getResizeHeight())
							.asBufferedImage();
		}
		//if buffer is not null, we write it to disk, else, we copy input with specified quality (100 by default)
		if (doQuality(this.getFlags()))	{	//always true
			Thumbnails.of(output == null ? read : output)
					.scale(1.0, 1.0)
					.outputQuality(config.getQuality() / 100.0F)
					.toFile(config.getOutputFileFull());
		}
		if (doCrop(this.getFlags()))	{
			MarvinImage marvinIn = MarvinImageIO.loadImage(config.getOutputFileFull().getAbsolutePath());
			MarvinImage marvinOut = new MarvinImage();
			crop(marvinIn, marvinOut, config.getCropX(), config.getCropY(), config.getCropWidth(),
					config.getCropHeight());
			MarvinImageIO.saveImage(marvinOut, config.getOutputFileFull().getAbsolutePath());
		}
		if (doBlur(this.getFlags()))	{
			MarvinImage marvinIn = MarvinImageIO.loadImage(config.getOutputFileFull().getAbsolutePath());
			MarvinImage marvinOut = new MarvinImage();
			gaussianBlur(marvinIn, marvinOut, config.getBlurRadius());
			MarvinImageIO.saveImage(marvinOut, config.getOutputFileFull().getAbsolutePath());
		}
	}
}
