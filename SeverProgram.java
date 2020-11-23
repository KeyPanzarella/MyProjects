

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;




public class SeverProgram {
	public static void main(String[] args) throws IOException
	{
		
		System.out.println("Awaiting connection from client");
		ServerSocket server = new ServerSocket(4504);
		Socket client = server.accept();
		
		System.out.println("Accepted new connection");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);

		boolean continLoop = true;
		while(continLoop)
		{
			String line = "";
			String dataToTransmit;
			Process p;
			BufferedReader consInput;
			
			try
			{
				line = in.readLine();
				//System.out.println(line);
				int selection_Server = Integer.parseInt(line);
				switch (selection_Server) {
				case 1:
					//Host current Date and Time
					System.out.println("Sending current Date and Time");
					
					p = Runtime.getRuntime().exec("date");
					consInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while( (dataToTransmit = consInput.readLine()) != null)
						out.println(dataToTransmit);
					
					out.println("finishedTransmission");
					
					break;
				case 2:
					//Host uptime
					System.out.println("Sending uptime");

					p = Runtime.getRuntime().exec("uptime");
					consInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while( (dataToTransmit = consInput.readLine()) != null)
						out.println(dataToTransmit);
					
					out.println("finishedTransmission");

					break;
				case 3:
					//Host memory use
					System.out.println("Sending memory use");

					p = Runtime.getRuntime().exec("free");
					consInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while( (dataToTransmit = consInput.readLine()) != null)
						out.println(dataToTransmit);
					
					out.println("finishedTransmission");
					
					break;
				case 4:
					//Host Netstat
					System.out.println("Sending Netstat");
					
					p = Runtime.getRuntime().exec("netstat");
					consInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while( (dataToTransmit = consInput.readLine()) != null)
						out.println(dataToTransmit);
					
					out.println("finishedTransmission");
					
					break;
				case 5:
					//Host current users
					System.out.println("Sending current users");

					p = Runtime.getRuntime().exec("users");
					consInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while( (dataToTransmit = consInput.readLine()) != null)
						out.println(dataToTransmit);
					
					out.println("finishedTransmission");
					
					break;
				case 6:
					//Host running processes
					System.out.println("Sending running processes");

					p = Runtime.getRuntime().exec("ps");
					consInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while( (dataToTransmit = consInput.readLine()) != null)
						out.println(dataToTransmit);
					
					out.println("finishedTransmission");
					
					break;
				/*case 7:
					System.out.println("disconnecting");
					out.println("disconnect");
					break;*/
				}
			}
			catch (IOException e)
			{
				System.out.println("Read Failed");
				System.exit(0);
			}
			
			if(line.equals("7"))
			{
				System.out.println("Disconnect request received");
				//System.exit(0);
				continLoop = false;
			}
		}
		
		server.close();
		client.close();
		System.out.println();
		System.out.println("Disconnected and Exiting");
	}
}
