package mts.teta.resizer;

import mts.teta.resizer.cli.ConsoleAttributes;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(	name = "crop-resize",
						mixinStandardHelpOptions = true,
						version = "crop-resize 0.0.1",
						description = "...")
public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {
	public static void main(String... args) {
		int exitCode = runConsole(args);
		System.out.println("Is this Stuff");
		System.exit(exitCode);
	}

	public int setInputFile(File file)
	{
		return (1);
	};
	public int setOutputFile(File file)	{
		return (1);
	};
	public int setResizeWidth(Integer reducedPreviewWidth)	{
		return (1);
	};
	public int setResizeHeight(Integer reducedPreviewHeight)	{
		return (1);

	};
	public int setQuality(int quality)	{
		return (1);
	};

	protected static int runConsole(String[] args) {
		return new CommandLine(new ResizerApp()).execute(args);
	}

	@Override
	public Integer call() throws Exception {/*
		ImageProcessor imageProcessor = new ImageProcessor();
		imageProcessor.processImage(ImageIO.read(inputFile), this);*/
		return 0;
	}
}
