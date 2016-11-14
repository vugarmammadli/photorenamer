package backend;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * The subclass of formatter to format the log file
 * 
 * Source:
 * http://stackoverflow.com/questions/9304784/is-there-any-way-to-remove-the-
 * information-line-from-java-util-logging-logger-ou
 * 
 * @author Vugar Mammadli
 *
 */
public class LogFormatter extends Formatter {
	@Override
	public String format(LogRecord record) {
		return record.getMessage() + "\r\n";
	}
}