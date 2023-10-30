package telran.employee;

import telran.employee.service.Company;
import telran.employee.service.CopmanyImplemention;
import telran.net.ApplProtocol;
import telran.net.TcpServer;

public class CompanyServerAppl {
	private static final int PORT = 5000;
	private static final String DEFAULT_FILE_NAME = "employees";
	
	public static void main(String[] args) throws Exception{
		String fileName =  args.length > 0 ? args[0] : DEFAULT_FILE_NAME;
		Company company = new CopmanyImplemention();
		ApplProtocol protocol = new CompanyProtocol(company);
		TcpServer tcpServer = new TcpServer (PORT, protocol);
		tcpServer.run();
	}
	
}
