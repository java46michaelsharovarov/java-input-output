package telran.util;

public interface Handler extends AutoCloseable {
	public void publish(LoggerRecord loggerRecord);
	public default void close() {};
}
