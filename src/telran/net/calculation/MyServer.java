package telran.net.calculation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import telran.view.InputOutput;

public class MyServer {
	static final int PORT = 1000;
	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server is listnening on port " + PORT);
		while(true) {
			Socket socket = serverSocket.accept();
			runProtocol(socket);
		}

	}
	private static void runProtocol(Socket socket)  {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintStream writer = new PrintStream(socket.getOutputStream())) {
			boolean running = true;
			while(running) {
				String request = reader.readLine();
				if (request == null) {
					System.out.println("client closed connection");
					running = false;
				} else {
					String response = getResponse(request);
					writer.println(response);
				}
			}
			
		} catch(Exception e) {
			System.out.println("Abnormal socket closing");
		}
		
	}
	private static String getResponse(String request) {
		
		 String response = null;
		    String[] tokens = request.split("#");

		    if (tokens.length != 3) {
		        response = "request must be in format <type>#<number1>#<number2>";
		    } else {
		        try {
		            String type = tokens[0];
		            double number1 = Double.parseDouble(tokens[1]);
		            double number2 = Double.parseDouble(tokens[2]);
		            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		            LocalDate date = LocalDate.parse(tokens[1], formatter);
		            int daysToSubtract = Integer.parseInt(tokens[2]);
		            LocalDate date1 = LocalDate.parse(tokens[1]);
                    LocalDate date2 = LocalDate.parse(tokens[2]);

		            switch (type) {
		                case "Add":
		                    response = String.valueOf(number1 + number2);
		                    break;
		                case "Subtract":
		                    response = String.valueOf(number1 - number2);
		                    break;
		                case "Multiply":
		                    response = String.valueOf(number1 * number2);
		                    break;
		                case "Divide":
		                    if (number2 != 0) {
		                        response = String.valueOf(number1 / number2);
		                    } else {
		                        response = "Division by zero is not allowed";
		                    }break;
		                case "After":
			                    LocalDate resultDate = date.plusDays(daysToSubtract);
			                    response = resultDate.toString();
			                    break;

		                case "Before":
		                    LocalDate resultDate2 = date.minusDays(daysToSubtract);
		                    response = resultDate2.toString();
		                    break;

		                case "Between":
		                    long daysBetween = ChronoUnit.DAYS.between(date1, date2);
		                    response = String.valueOf(daysBetween);
		                    break;
		                  
		                default:
		                    response = "Wrong type";
		            }
		        } catch (NumberFormatException e) {
		            response = "Invalid numbers in the request";
		        }
		    }

		    return response;
		}
}
