package telran.io;
import java.io.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Application for copying files based on FileInputStream and FileOutputStream
 * files may be very big (several Gbytes ) 
 * args[0] - source file path
 * args[1] - destination file path
 * args[2] - if exists "overwritten" then destination may be overwritten otherwise may not be
 * Output one of the following:
 * Files have been copied (<amount of bytes> <time of copying>)
 * Source file doesn't exist
 * Destination can not be overwritten
 *
 */
public class CopyFilesInputOutputStreams {
private static final Object OVERWRITTEN = "overwritten";
private static final int BUFFER_SIZE = 1000 * 1024 ;
private static final String NO_DIRECTORY_DESTINATION = "Destination %s has non-existed directory in the path\n";
private static final String WRONG_NUMBER_ARGUMENTS = "too few arguments\nusage:"
		+ " first argument is a source path;"
					+ "\nsecond argument is a destination path;"
					+ "\nthird argument is 'overwritten'  ";
private static final String UNKNOWN_ERROR = "Unknown error ";
private static final String SUCCESS_MESSAGE = "From %s to %s\n copied %d for %d milliseconds \n";
private static final String SOURCE_NOT_EXISTS = "Source %s does not exist\n";
private static final String DESTINATION_NO_OVERWRITTEN = "Destination %s can not be overwritten";
static File[] files;
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println(WRONG_NUMBER_ARGUMENTS);
		} else {
			try {
				files = getSrcDest(args);
				copyFiles();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		

	}
	private static void copyFiles() throws Exception{
		
		try (InputStream input = new FileInputStream(files[0]);
				OutputStream output = new FileOutputStream(files[1]);){
			copyStreams(input, output);
		} catch (FileNotFoundException e) {
			throw new Exception(String.format(NO_DIRECTORY_DESTINATION,
					files[1].getName()));
			
		} 
		
	}
	private static void copyStreams(InputStream input, OutputStream output) throws Exception{
		long totalLength = 0;
		int currentLength = 0;
		byte buffer[] = new byte[BUFFER_SIZE];
		Instant start = Instant.now();
		try {
			while((currentLength = input.read(buffer)) > 0) {
				totalLength += currentLength;
				output.write(buffer, 0, currentLength);
			}
		} catch (Exception e) {
			throw new Exception(UNKNOWN_ERROR + e.getMessage());
		}
		System.out.printf(SUCCESS_MESSAGE, files[0].getCanonicalPath(),
				files[1].getCanonicalPath(), totalLength, ChronoUnit.MILLIS.between(start, Instant.now()));
		
	}
	private static File[] getSrcDest(String[] args) throws Exception{
		File[] files = new File[2]; //files[0] - source; files[1] - destination
		files[0] = new File( args[0] );
		if (!files[0].exists()) {
			throw new Exception(String.format(SOURCE_NOT_EXISTS, args[0]));
		}
		files[1] = new File( args[1] );
		if (files[1].exists() && (args.length < 3 || !args[2].equals(OVERWRITTEN))) {
			throw new Exception(String.format(DESTINATION_NO_OVERWRITTEN, files[1].getAbsolutePath()));
		}
		return files;
	}

}
