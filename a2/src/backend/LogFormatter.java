package backend;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * The subclass of formatter to format the log file
 * @author Vugar Mammadli
 *
 */
public class LogFormatter extends Formatter {
	@Override
	public String format(LogRecord record) {
		return record.getMessage() + "\r\n";
	}
}
