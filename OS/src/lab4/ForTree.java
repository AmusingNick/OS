package lab4;

import java.io.File;

import javax.swing.JTextArea;

public class ForTree {
	static int count = 0;
	static void printfAllFile(String URL,JTextArea jta){
		File f = new File(URL);
		String fileList[] = f.list();
		jta.append("\n");
		for(int i=0;i<count;i++)
			jta.append("\t");
		if(count != 0) //不是根的时候
			jta.append("└─ "+f.getName());
		else
			jta.append(f.getName());
		count ++ ;
		if(fileList != null){
			for(int i=0;i<fileList.length; i++){
				//将f接到fileList的每一个底下
				printfAllFile(URL+fileList[i]+"\\",jta);
				count -- ;
			}
		}
		else
			return;
	}
}
