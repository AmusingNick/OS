package test;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class FCB{
	File f ;
	FCB fcb ;
}

public class Test1 {
	ArrayList<FCB> alFcb = new ArrayList<FCB>();
	int count = 0;
	void printfAllFile(String URL){
		File f = new File(URL);
		String fileList[] = f.list();
		for(int i=0;i<count;i++)
			System.out.print("\t");
		if(count != 0) //不是根的时候
			System.out.println("└─ "+f.getName());
		else
			System.out.println(f.getName());
		count ++ ;
		if(fileList != null){
			for(int i=0;i<fileList.length; i++){
				//将f接到fileList的每一个底下
				printfAllFile(URL+fileList[i]+"\\");
				count -- ;
			}
		}
		else
			return;
	}
	public static void main(String[] args) {
		new Test1().printfAllFile("C:\\Users\\hongyu\\Desktop\\");
	}
}

