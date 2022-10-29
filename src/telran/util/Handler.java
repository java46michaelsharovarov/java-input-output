package telran.util;

public interface Handler {
	public void publish(LoggerRecord loggerRecord);
	public default void close() {};
}
