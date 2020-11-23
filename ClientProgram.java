

import java.util.Scanner;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.PrintWriter;


public class ClientProgram
{
	public static void main(String[] args) throws IOException
	{
		String hostname = "";
        if(args.length != 0)
                hostname = args[0];
        if(hostname.equals(""))
        {
                System.out.println("Error: Server hostname must be entered in argument");
                System.exit(0);
        }
        //System.out.println(hostname);
		
		
        File file = new File("saved_data.txt");
        file.createNewFile();
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println("\n\n\n\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        pw.println("One tab: # of connections");
        pw.println("Two tab: Date and Time (ms)");
        pw.println("Three tab: Netstat (ms)");
        
        
        int clients = Integer.parseInt(args[1]);
        pw.println("\tNumber of Clients: " + clients);
        pw.flush();
        
        
		Scanner sc = new Scanner(System.in);
		Socket socket;
		
		
		int selection;
		
		try {
			socket = new Socket(hostname, 4504);
			System.out.println("Created Socket");
			client_Thread[] clientArr = new client_Thread[clients];
			
			for(int i=0; i<clients; i++)
			{
				clientArr[i] = new client_Thread(socket);
			}
			
			boolean continLoop = true;
			while(continLoop)
			{
				DisplayMenu();
				selection = sc.nextInt();
				for(int i=0; i<clients; i++)
				{
					clientArr[i].run(selection, pw);
					if(selection == 7)
					{
						System.out.println("Terminating Program");
						continLoop = false;
					}
				}
			}
			
			
		} catch (UnknownHostException e) {
		     System.out.println("Unknown host: " + hostname);
		     System.exit(0);
		} catch (IOException e) {
			System.out.println("No I/O");
		     System.exit(0);
		} finally {
			sc.close();
		}
	}
	
	public static void DisplayMenu()
	{
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------------------------------");
		System.out.println("\tMenu:");
		System.out.println("\t1:\tHost current Date and Time");
		System.out.println("\t2:\tHost uptime");
		System.out.println("\t3:\tHost memory use");
		System.out.println("\t4:\tHost Netstat");
		System.out.println("\t5:\tHost current users");
		System.out.println("\t6:\tHost running processes");
		System.out.println("\t7:\tExit");
		System.out.println();
		System.out.print("Enter Selection: ");
	}
}



class client_Thread extends Thread
{
	private Socket socket;
	
	public client_Thread(Socket socket)
	{
		this.socket = socket;
	}
	
	public void run(int c, PrintWriter pw_file)
	{
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		} catch (IOException e) {
			System.out.println("ERROR: creating printwriter and bufferedreader");
			System.exit(1);
		}
		
		
		int selection;
		
			selection = c;
			System.out.println();
			String line;
			long startTime, endTime;
			
			switch (selection) {
			case 1:
				//Host current Date and Time
				startTime = System.currentTimeMillis();
				out.println("1");
				try{
					while( !(line = in.readLine()).equals("finishedTransmission"))
					{
						System.out.println(line);
					}

				} catch (IOException e) {
					System.out.println("Read Failed");
				}
				endTime = System.currentTimeMillis();
				if(pw_file != null)
				{
					pw_file.println("\t\t" + (endTime-startTime));
					pw_file.flush();
				}
				
				break;
			case 2:
				//Host uptime
				out.println("2");
				try{
					while( !(line = in.readLine()).equals("finishedTransmission"))
					{
						System.out.println(line);
					}

				} catch (IOException e) {
					System.out.println("Read Failed");
				}
				break;
			case 3:
				//Host memory use
				out.println("3");
				try{
					while( !(line = in.readLine()).equals("finishedTransmission"))
					{
						System.out.println(line);
					}

				} catch (IOException e) {
					System.out.println("Read Failed");
				}
				break;
			case 4:
				//Host Netstat
				startTime = System.currentTimeMillis();
				out.println("4");
				try{
					while( !(line = in.readLine()).equals("finishedTransmission"))
					{
						System.out.println(line);
					}
						
				} catch (IOException e) {
					System.out.println("Read Failed");
				}
				endTime = System.currentTimeMillis();
				if(pw_file != null)
				{
					pw_file.println("\t\t\t" + (endTime-startTime));
					pw_file.flush();
				}
				break;
			case 5:
				//Host current users
				out.println("5");
				try{
					while( !(line = in.readLine()).equals("finishedTransmission"))
					{
						System.out.println(line);
					}

				} catch (IOException e) {
					System.out.println("Read Failed");
				}
				break;
			case 6:
				//Host running processes
				out.println("6");
				try{
					while( !(line = in.readLine()).equals("finishedTransmission"))
					{
						System.out.println(line);
					}

				} catch (IOException e) {
					System.out.println("Read Failed");
				}
				break;
			case 7:
				//Exit and disconnect
				out.println("7");
				break;
			default:
				System.out.println("Invalid command, try again");
				break;
			}
			
			if(selection == 7)
			{
				System.out.println("Exiting client thread");
				//continLoop = false;
			}
		
	}
}




