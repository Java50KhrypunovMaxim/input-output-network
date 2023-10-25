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

			            switch (type) {
			                case "Add":
			                    response = String.valueOf(Double.parseDouble(tokens[1]) + Double.parseDouble(tokens[2]));
			                    break;
			                case "Subtract":
			                    response = String.valueOf(Double.parseDouble(tokens[1]) - Double.parseDouble(tokens[2]));
			                    break;
			                case "Multiply":
			                    response = String.valueOf(Double.parseDouble(tokens[1]) * Double.parseDouble(tokens[2]));
			                    break;
			                case "Divide":
			                    if (Double.parseDouble(tokens[2]) != 0) {
			                        response = String.valueOf(Double.parseDouble(tokens[1]) / Double.parseDouble(tokens[2]));
			                    } else {
			                        response = "Division by zero is not allowed";
			                    }break;
			                case "After":
			                	LocalDate date = LocalDate.parse(tokens[1]);
			                    int daysToSubtract = Integer.parseInt(tokens[2]);
			                    LocalDate resultDate = date.plusDays(daysToSubtract);
			                    response = resultDate.toString();
			                    break;

			                case "Before":
			                    LocalDate date2 = LocalDate.parse(tokens[1]);
			                    int daysToSubtract2 = Integer.parseInt(tokens[2]);
			                    LocalDate resultDate2 = date2.minusDays(daysToSubtract2);
			                    response = resultDate2.toString();
			                    break;

			                case "Between":
			                    LocalDate date11 = LocalDate.parse(tokens[1]);
			                    LocalDate date22 = LocalDate.parse(tokens[2]);
			                    long daysBetween = ChronoUnit.DAYS.between(date11, date22);
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
