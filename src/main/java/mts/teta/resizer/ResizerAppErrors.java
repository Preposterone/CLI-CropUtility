package mts.teta.resizer;

public interface ResizerAppErrors {
	/*	======== ARGUMENT PARSING FATAL ERRORS ========	*/
	String RESIZE_NO_PARAM = "Missing one or multiple arguments for resize option!";
	String CROP_NO_PARAM = "Missing one or multiple arguments for crop option!";

	/*	======== ARGUMENT VALIDATION FATAL ERRORS ========	*/
	String INV_EXTENSION = "Invalid inputFile extension. Can be only 'jpeg' or 'png'!";
	String INV_FORMAT = "Invalid format specified. Can be only 'jpeg' or 'png'!";

	/*	======== ARGUMENT VALIDATION WARNINGS ========	*/
	String WARN_QUALITY_TOO_HIGH = "WARNING! Specified quality is too HIGH! Setting it to 100.";
	String WARN_QUALITY_TOO_LOW = "WARNING! Specified quality is too LOW! Setting it to 1.";
}
