import java.io.*;
import java.net.*;
import java.util.Random;
public class multiserverv {
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
public long getlist(File f){//遞迴求取目錄檔個數
long size = 0;
File flist[] = f.listFiles();
size=flist.length;
for (int i = 0; i < flist.length; i++) {
if (flist[i].isDirectory()) {
size = size + getlist(flist[i]);
size--;
}
}
return size;

}



  public void run() {
    System.out.println("Accepted Client : ID - " + clientID + " : Address - "
        + clientSocket.getInetAddress().getHostName());
		
		File ff=new File("C:/Users/Dannie/Desktop/serverimg");
		int filenum=new Long(getlist(ff)).intValue();
		System.out.println("檔案個數:"+filenum);
        String filename ="C:/Users/Dannie/Desktop/serverimg/kobe5.mp4";//文件名,要傳輸的文件
        //int port=8888;
		
		/*Random ran=new Random();
        int output=ran.nextInt(filenum)+1;
		String outputstream=Integer.toString(output);
		System.out.println("kobe "+outputstream);
		String filename=filenameo+outputstream+".jpg";*/
        File file=new File(filename); 
        System.out.println("準備傳送"+filename+"檔案");

		try{
			
		FileInputStream fff=new FileInputStream(file);
		int filesize=fff.available();
		System.out.println("準備傳送"+filename+"檔案大小"+filesize);
		}catch(Exception e){}
		//int filesize=new Long(getFileSizes(file)).intValue();
		
		
        //檢查是否有此檔案
        FileInputStream fos=null;
        try {
            fos=new FileInputStream(file);
        } catch (java.io.IOException e) {
         System.out.println("找不到檔案"+filename);
         System.out.println("錯誤訊息IOException :" + e.toString());
         System.out.println("\n關閉程式");
      return;
       }
        //創建Server 接受客戶請求
        //默認port=8080
        //System.out.println("等待 client連線");
        //ServerSocket ss=new ServerSocket(port);
  
        //Socket client=ss.accept();
        //System.out.println("Client 連線進來");
         
        //數據封裝
        try{
			FileInputStream fff2=new FileInputStream(file);
			int filesize2=fff2.available();
			System.out.println("準備傳送"+filename+"檔案大小"+filesize2);
			System.out.println("準備發送檔案");
			OutputStream netOut=clientSocket.getOutputStream();
  
			OutputStream temp=new DataOutputStream(new BufferedOutputStream(netOut));
			System.out.println("發送檔案大小"+filesize2);
			PrintWriter   out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			out.println(filesize2);
			out.flush();
			//創建文件讀取緩衝區
			System.out.println("開始傳送檔案");
			byte[] buf=new byte[filesize2];
  
			int num=fos.read(buf);
  
			while(num!=(-1)){//是否讀完文件
				temp.write(buf,0,num);//把文件數據寫出網絡緩衝區 
				temp.flush();//刷新緩衝區把數據寫往客戶端
				num=fos.read(buf);//繼續從文件中讀取數據
			}
			System.out.println("傳送檔案完成");
			fos.close(); 
			temp.close();
		}catch(Exception e){}
		
  }
}