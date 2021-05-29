package mts.teta.resizer.cli;

import mts.teta.resizer.ResizerAppErrors;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.Stack;

interface Cli_Constants {
	String CLI_RESIZE_LABEL = "width height";
	String CLI_RESIZE_DESC = "resize the image";

	String CLI_QUALITY_LABEL = "value";
	String CLI_QUALITY_DESC = "JPEG/PNG compression level";

	String CLI_CROP_LABEL = "width height x y";
	String CLI_CROP_DESC = "@|magenta cut|@ out one rectangular area of the image";

	String CLI_BLUR_LABEL = "{radius}";
	String CLI_BLUR_DESC = "reduce image noise detail levels";

	String CLI_FORMAT_LABEL = "\"outputFormat\"";
	String CLI_FORMAT_DESC = "the image @|magenta format type|@";
}

public class ConsoleAttributes implements Cli_Constants, ResizerAppErrors {
	/*Params*/
	@Parameters(index = "0", arity = "1", hidden = true)
	private File inputFile;

	@Parameters(arity = "1", hidden = true)
	private String outputFile;

	private File outputFileFull;

	/*Logic for resize*/
	@Option(names = "--resize", paramLabel = CLI_RESIZE_LABEL, description = CLI_RESIZE_DESC,
			parameterConsumer = ResizeConverter.class)
	private ResizeProperties doResize = null;

	public Integer getResizeWidth() {
		return (doResize.getWidth());
	}

	public Integer getResizeHeight() {
		return (doResize.getHeight());
	}

	public void setResizeWidth(Integer width) {
		if (doResize == null)
			doResize = new ResizeProperties(width, 0);
		else
			doResize.setWidth(width);
	}

	public void setResizeHeight(Integer height) {
		if (doResize == null)
			doResize = new ResizeProperties(0, height);
		else
			doResize.setHeight(height);
	}

	static class ResizeConverter implements CommandLine.IParameterConsumer {
		public void consumeParameters(Stack<String> args,
									  CommandLine.Model.ArgSpec argSpec,
									  CommandLine.Model.CommandSpec commandSpec) {
			if (args.size() < 2) {
				throw new CommandLine.ParameterException(commandSpec.commandLine(), RESIZE_NO_PARAM);
			}
			int width = Integer.parseInt(args.pop());
			int height = Integer.parseInt(args.pop());
			argSpec.setValue(new ResizeProperties(width, height));
		}
	}

	@Option(names = "--quality", paramLabel = CLI_QUALITY_LABEL, description = CLI_QUALITY_DESC)
	private int quality = 100;

	/*Logic for crop*/
	@Option(names = "--crop", paramLabel = CLI_CROP_LABEL, description = CLI_CROP_DESC,
			parameterConsumer = CropConverter.class)
	private final CropProperties doCrop = null;

	public Integer getCropWidth() {
		return (doCrop.getWidth());
	}

	public Integer getCropHeight() {
		return (doCrop.getHeight());
	}

	public Integer getCropX() {
		return (doCrop.getX());
	}

	public Integer getCropY() {
		return (doCrop.getY());
	}

	static class CropConverter implements CommandLine.IParameterConsumer {
		public void consumeParameters(Stack<String> args,
									  CommandLine.Model.ArgSpec argSpec,
									  CommandLine.Model.CommandSpec commandSpec) {
			if (args.size() < 4) {
				throw new CommandLine.ParameterException(commandSpec.commandLine(), CROP_NO_PARAM);
			}
			int width = Integer.parseInt(args.pop());
			int height = Integer.parseInt(args.pop());
			int x = Integer.parseInt(args.pop());
			int y = Integer.parseInt(args.pop());
			argSpec.setValue(new CropProperties(x, y, width, height));
		}
	}

	@Option(names = "--blur", arity = "1", paramLabel = CLI_BLUR_LABEL, description = CLI_BLUR_DESC)
	private int blurRadius;

	@Option(names = "--format", arity = "1", paramLabel = CLI_FORMAT_LABEL, description = CLI_FORMAT_DESC)
	private String format = "jpeg";

	/*	getters - setters	*/
	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public File getOutputFileFull() {
		return outputFileFull;
	}

	public void setOutputFileFull(File outputFileFull) {
		this.outputFileFull = outputFileFull;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public Integer getBlurRadius() {
		return blurRadius;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File file) {
		this.outputFile = file.toString();
	}
	/*	Argument validation	*/
	public boolean isResizeExpected() {
		return (doResize != null);
	}

	public boolean isCropExpected() {
		return (doCrop != null);
	}

	public boolean isBlurExpected() {
		return (getBlurRadius() > 0);
	}
}
