package mts.teta.resizer;

import mts.teta.resizer.cli.ConsoleAttributes;
import mts.teta.resizer.imageprocessor.BadAttributesException;
import mts.teta.resizer.imageprocessor.ImageProcessor;

import picocli.CommandLine;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Optional;
import java.util.concurrent.Callable;

@CommandLine.Command(
		name = "resizer", version = "resizer 1.0",
		headerHeading = "Version: resizer 1.0 https://github.com/Preposterone/mts_teta_backend2_ccrr%n",
		sortOptions = false, separator = " ",
		synopsisHeading = "Available formats: jpeg png%nUsage: convert input-file [options ...] output-file%n",
		customSynopsis = {"Options Settings:"}
)
public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {
	public static void main(String... args) {
		int exitCode = runConsole(args);
		System.exit(exitCode);
	}

	protected static int runConsole(String[] args) {
		var colorScheme = new CommandLine.Help.ColorScheme.Builder()
				.ansi(CommandLine.Help.Ansi.ON)
				.build();
		return (
				new CommandLine(new ResizerApp())
						.setColorScheme(colorScheme)
						.setUsageHelpLongOptionsMaxWidth(40)
						.execute(args)
		);
	}

	@Override
	public Integer call() throws Exception {
		var imageProcessor = new ImageProcessor();
		ConfigVerifier.VerifyConfig(this);
		imageProcessor.processImage(ImageIO.read(getInputFile()), this);
		return 0;
	}

	private static class ConfigVerifier	{
		private static final int QUALITY_MAX = 100;
		private static final int QUALITY_MIN = 1;

		public static void VerifyConfig(ResizerApp config) throws BadAttributesException {
			/*	Throw BadAttributesException if input file doesn't have valid extension	*/
			if (config.getInputFile().exists() && !isImageExtValid(config.getInputFile().getName()))	{
				throw new BadAttributesException(INV_EXTENSION);
			}
			/*	Throw BadAttributesException if specified paramateres don't fit the boundaries	*/
			if (config.getQuality() > QUALITY_MAX || config.getQuality() < QUALITY_MIN
					|| config.getBlurRadius() < 0
					|| (config.isResizeExpected() && (config.getResizeHeight() <= 0 || config.getResizeWidth() <= 0))
					|| (config.isCropExpected() && (config.getCropX() < 0 || config.getCropY() < 0 || config.getCropHeight() <= 0 || config.getCropWidth() <= 0))) {
				throw new BadAttributesException(INV_QUALITY);
			}
			/*	Verify specified format, can be only 'jpg', 'jpeg' or 'png'	*/
			if (!config.getFormat().equalsIgnoreCase("png") && !config.getFormat().equalsIgnoreCase("jpg") && !config.getFormat().equalsIgnoreCase("jpeg"))	{
				throw new BadAttributesException(INV_FORMAT);
			}
			/*	If outputFile has valid format specified in its name, override format 	*/
			if (isImageExtValid(config.getOutputFile()))	{
				config.setFormat(config.getOutputFile().substring(config.getOutputFile().lastIndexOf(".") + 1));
				config.setOutputFileFull(new File(config.getOutputFile()));
			}	else	{	/*	If not - concat outputFile and format	*/
				config.setOutputFileFull(new File(config.getOutputFile() + "." + config.getFormat()));
			}
		}

		/*	Checks if fileName has extension {jpeg, jpg, png}	*/
		static boolean isImageExtValid(String fileName)	{
			var ext = Optional.ofNullable(fileName)
					.filter(f -> f.contains("."))
					.map(f -> f.substring(fileName.lastIndexOf(".") + 1));
			return (ext.isPresent() && (ext.get().equalsIgnoreCase("jpg") || ext.get().equalsIgnoreCase("jpeg") || ext.get().equalsIgnoreCase("png")));
		}

	}
}
