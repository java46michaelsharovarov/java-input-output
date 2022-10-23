package telran.util;

import java.time.Instant;
import java.time.ZoneId;

public class Logger {
	private Level level = Level.INFO;
	private Handler handler;
	private String name;

	public Logger(Handler handler, String name) {
		this.handler = handler;
		this.name = name;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void error(String message) {
		publish(Level.ERROR, message);
	}
	
	public void warn(String message) {
		if(level.compareTo(Level.ERROR) < 0) {
			publish(Level.WARN, message);
		}
	}
	
	public void info(String message) {
		if(level.compareTo(Level.WARN) < 0) {
			publish(Level.INFO, message);
		}
	}
	
	public void debug(String message) {
		if(level.compareTo(Level.DEBUG) <= 0) {
			publish(Level.DEBUG, message);
		}
	}
	
	public void trace(String message) {
		if(level == Level.TRACE) {
			publish(Level.TRACE, message);
		}
	}

	private void publish(Level level, String message) {
		String zoneId = ZoneId.systemDefault().getId();
		handler.publish(new LoggerRecord(Instant.now(), zoneId, level, name, message));
	}

}
