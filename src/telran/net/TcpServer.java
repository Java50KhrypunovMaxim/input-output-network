package telran.net;

public class TcpServer implements Runnable {
	private int port;
	private ApplProtocol protocol;
	
	public TcpServer(int port, ApplProtocol protocol) {
		
		this.port = port;
		this.protocol = protocol;
	}

	@Override
	public void run() {
	System.out.println("Server is listening on port " + port);

	}

}
