import java.io.*;
  
import java.net.*;
  
  
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class multiclientimg{
  
    public static void main(String[] args)throws Exception {
	
        //使用本地文件系統接受網絡數據並存為新文件
     String filename="C:/Users/Dannie/Desktop/clientimg/newfile1.jpg";
        int port=8888;
 
  
        // 通過Socket連接文件服務器
        System.out.println("準備連接server端");
        Socket server=null;
        try {
         server=new Socket(InetAddress.getLocalHost(),port);
        } catch (java.io.IOException e) {
          System.out.println("與Server連線失敗，可能是Server尚未開啟");
          System.out.println("錯誤訊息IOException :" + e.toString());
          System.out.println("\n關閉程式");
          return;
        }
        System.out.println("連線成功");
  
        //創建網絡接受流接受服務器文件數據 
        System.out.println("準備接收檔案");
        InputStream netIn=server.getInputStream();
  
     System.out.println("接收檔案的新檔名為:"+filename);
        File file=new File(filename);//生成的文件名
        file.createNewFile();
         
        InputStream in=new DataInputStream(new BufferedInputStream(netIn));
        RandomAccessFile raf=new RandomAccessFile(file,"rw");
		
		BufferedReader   insize = new BufferedReader(new InputStreamReader(server.getInputStream()));
		String sss=insize.readLine();
		int ii=Integer.parseInt(sss);
		System.out.println("開始接收檔案大小"+sss);
	
		
		
        //創建緩衝區緩衝網絡數據
        System.out.println("開始接收檔案"+ii);
        byte[] buf=new byte[ ii];
		
        int num=in.read(buf);         
  
        while(num!=(-1)){//是否讀完所有數據
  
              raf.write(buf,0,num);//將數據寫往文件
  
              raf.skipBytes(num);//順序寫文件字節
  
              num=in.read(buf);//繼續從網絡中讀取文件
  
        }
        System.out.println("接收檔案完成");
		
        in.close();
  
        raf.close();
		Test s = new Test();
	s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Process process=Runtime.getRuntime().exec("C:/Users/Dannie/Desktop/clientimg/newfile1.jpg"); 
		//Process p = Runtime.getRuntime().exec("rundll32 SHELL64.DLL,ShellExec_RunDLL " +"C:\\Users\\Dannie\\Desktop\\clientimg\\newfile1.jpg");
		//Runtime.getRuntime().exec("cmd.exe /C \\""); 
    }
public static class Test extends JFrame {

public Test() {
setBak(); //調用背景方法
Container c = getContentPane(); //獲取JFrame面板
JPanel jp = new JPanel(); //創建個JPanel
jp.setOpaque(false); //把JPanel設置為透明 這樣就不會遮住後面的背景 這樣你就能在JPanel隨意加元件了
c.add(jp);
setSize(540, 450);
setVisible(true);
}
public void setBak(){
((JPanel)this.getContentPane()).setOpaque(false);
ImageIcon img = new ImageIcon("C:\\Users\\Dannie\\Desktop\\clientimg\\newfile1.jpg");
JLabel background = new JLabel(img);this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
}



}
}

