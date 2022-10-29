package telran.net;
import java.io.*;
import java.net.*;
public class ServerStringAppl {
	
	public static int PORT=3000;
	
	public static void main(String[] args) throws Exception{
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Server is listening to connections on port " + PORT);
			while(true) {
				Socket socket = serverSocket.accept();
				runProtocol(socket);
			}
		}
	}
	
	private static void runProtocol(Socket socket){
		try {
			BufferedReader input = 
					new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream output = new PrintStream(socket.getOutputStream());
			while(true) {
				String request = input.readLine();
				if (request == null) {
					break;
				}
				String response = getResponse(request);
				output.println(response);		
			}
		} catch (IOException e) {
			System.out.println("client has closed connection improperly");
		}		
	}
	
	private static String getResponse(String request) {
		String[] tokens = request.split("#");
		String response = "";
		if (tokens.length != 2) {
			response = "Wrong request: should be <type>#<string";
		} else if (tokens[0].equals("reverse")) {
			response = new StringBuilder(tokens[1]).reverse().toString();
		} else if (tokens[0].equals("length")) {
			response = "" + tokens[1].length();
		} else {
			response = "Wrong request type: should be either 'reverse' or 'length'";
		}
		return response;
	}

}