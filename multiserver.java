import java.io.*;
import java.net.*;
import java.util.Random;
public class multiserver {
  public static void main(String[] args) throws Exception {
    ServerSocket m_ServerSocket = new ServerSocket(8888);
    int id = -1;
	ClientServiceThread  [] cliThread=new ClientServiceThread[50];
    while (true) {
      Socket clientSocket = m_ServerSocket.accept();
	  
      cliThread[++id]=new ClientServiceThread(clientSocket, id);
      cliThread[id].start();
    }
  }
}

class ClientServiceThread extends Thread {
  Socket clientSocket;
  int clientID = 0;
  boolean running = true;

  ClientServiceThread(Socket s, int i) {
    clientSocket = s;
    clientID = i;
  }
 boolean isInteger(String value) {
   try {
       Integer.parseInt(value);
       return true;
   }catch (NumberFormatException e) {
       return false;
   }
}
  public void run() {
    System.out.println("Accepted Client : ID - " + clientID + " : Address - "
        + clientSocket.getInetAddress().getHostName());
    try {
      BufferedReader   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      PrintWriter   out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	  BufferedReader kin=new BufferedReader(new InputStreamReader(System.in));
      while (running) {
        String clientCommand = in.readLine();
        System.out.println("Client "+clientID+" Says :" + clientCommand);
		if(isInteger(clientCommand)&&Integer.parseInt(clientCommand)>0)
		{
		//System.out.println("yes");
		Random ran=new Random();
		int input=Integer.parseInt(clientCommand);

		int output=ran.nextInt(input)+1;
		System.out.println("I(server) Says :" +output);
		
		out.println(Integer.toString(output));
		//System.out.print("I(server) Says :");
		//out.println(kin.readLine());
		}
		else{
			out.println("error");
		}
        if (clientCommand.equalsIgnoreCase("quit")) {
          running = false;
          System.out.print("Stopping client thread for client : " + clientID);
        } else {
          //out.println(clientCommand);
          out.flush();
        }
      
	 }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}