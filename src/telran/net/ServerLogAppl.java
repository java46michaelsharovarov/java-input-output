package telran.net;
import java.io.*;
import java.net.*;
import java.util.stream.Collectors;

import telran.util.Level;

public class ServerLogAppl {
	
	private static final String LOGGER = "logger.log";
	public static int PORT=3000;
	
	public static void main(String[] args) throws Exception{
		try (ServerSocket serverSocket = new ServerSocket(PORT);
				PrintStream printStream = new PrintStream(LOGGER);) {
			System.out.println("Server is listening to connections on port " + PORT);
			while(true) {
				Socket socket = serverSocket.accept();
				runProtocol(socket, printStream);
			}
		}
	}
	
	private static void runProtocol(Socket socket, PrintStream printStream){
		try {
			BufferedReader input = 
					new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream output = new PrintStream(socket.getOutputStream());
			while(true) {
				String request = input.readLine();
				if (request == null) {
					break;
				}
				String response = getResponse(request, printStream);
				output.println(response);		
			}
		} catch (IOException e) {
			System.out.println("client has closed connection improperly");
		}		
	}
	
	private static String getResponse(String request, PrintStream printStream) {
		String[] tokens = request.split("#");
		String response = "";
		if (tokens.length != 2) {
			response = "Wrong request: should be <type>#<string>";
		} else if (tokens[0].equals("log")) {			
			printStream.println(tokens[1]);
			response = "OK";
		} else if (tokens[0].equals("counter")) {
			response = getCount(tokens[1]);
		} else {
			response = "Wrong request type: should be either 'log' or 'counter'";
		}
		return response;
	}

	private static String getCount(String level) {
		try (BufferedReader reader = new BufferedReader(new FileReader(LOGGER))) {			
			return reader.lines()
			.map(s -> Level.valueOf(s.split(" ")[1]))
			.filter(s -> s.equals(Level.valueOf(level.toUpperCase())))
			.collect(Collectors.counting())
			.toString();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
}