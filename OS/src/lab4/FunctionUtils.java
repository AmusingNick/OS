package lab4;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;


public class FunctionUtils {
	static JTextArea jta ;
	ArrayList<FCB> alFCB = new ArrayList<>();
	MKDEL mkdel = new MKDEL();
	FunctionUtils(JTextArea jta){
		this.jta = jta;
		mkdel.init(this.jta);
	}
	static public void MD(String file,String doc){
		File f = new File(doc,file);
		f.mkdir();
	}
	static public void CD2(String doc){
		String cadoc = doc.replaceAll("\\\\", "@");
		String[] oldDoc = cadoc.split("@");
		String[] newDoc = new String[oldDoc.length-1];
		for(int i=0;i<oldDoc.length-1;i++)
			newDoc[i] = oldDoc[i]+"\\";
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<newDoc.length;i++)
			sb.append(newDoc[i]);
		sb.append(">");
		jta.append(sb.toString());
	}
	static public void CD(String file,String doc){
		String a = doc.split(">")[0];
		a += file;
		a += "\\";
		a += ">";
		jta.append(a);
	}
	static public void RD(String file,String doc){
		File f = new File(doc,file);
		if(f.list().length == 0)
			f.delete();
		else
			jta.append("不是空文件，不能删除");
	}
	static public void DIRXing(String doc,String tong){
		//String copyString = tong.split("*")[1];
		
		File f = new File(doc);
		File[] fs = f.listFiles();
		for(int i=0;i<fs.length;i++){
			/*Pattern p = Pattern.compile("\b");
			Matcher m = p.matcher(fs[i].getName());
			System.out.println("m.matches():"+m.matches());*/
			
			if(fs[i].getName().contains("j")){//  名字符合通配符要求
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(fs[i].lastModified());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
				String sdfTime = sdf.format(c.getTime());
				jta.append(sdfTime+"\t"+"<DIR>"+"\t"+fs[i].getName()+"\n");
			}
		}
	}
	static public void DIRWen(String doc,String tong){
		System.out.println("tong:"+tong);
		System.out.println("通配：问号");
	}
	static public void DIR(String doc){
		File f = new File(doc);
		File[] fs = f.listFiles();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(f.lastModified());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		String sdfTime = sdf.format(c.getTime());
		jta.append(sdfTime+"\t"+"<DIR>"+"\t"+"."+"\n");
		jta.append(sdfTime+"\t"+"<DIR>"+"\t"+".."+"\n");
		long get[] = writeFs(fs,jta);
		long directoryCount = get[0];
		long directorySize = get[1];
		long fileCount =get[2];
		long fileSize = get[3];
		jta.append("\t"+(fileCount)+"个文件,共"+(fileSize)+"个字节"+"\n");
		jta.append("\t"+(directoryCount)+"个目录,共"+(directorySize)+"个字节");
	}
	private static long[] writeFs(File[] fs, JTextArea jta2) {
		int directoryCount =0;
		long directorySize = 0;
		int fileCount =0;
		long fileSize = 0;
		Calendar c = Calendar.getInstance();
		for(int i=0;i<fs.length;i++){
			c.setTimeInMillis(fs[i].lastModified());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
			String sdfTime = sdf.format(c.getTime());
			if(fs[i].isDirectory()){
				directorySize += fs[i].getTotalSpace();
				directoryCount++;
				jta.append(sdfTime+"\t"+"<DIR>"+"\t"+fs[i].getName()+"\n");
			}
			else{
				fileSize += fs[i].getTotalSpace();
				fileCount++;
				jta.append(sdfTime+"\t"+"\t"+fs[i].getName()+"\n");
			}
		}
		long a[] = new long[]{directoryCount,directorySize,
				fileCount,fileSize};
		return a;
	}
	static public void TREE(String doc){
		ForTree.printfAllFile(doc,jta);
	}
	public void MK(String file,String doc,String SizeOfType){
		File f = new File(doc,file);
		try {
			f.createNewFile();
			mkdel.distribution(Integer.parseInt(SizeOfType));
			mkdel.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void DEL(String file,String doc){
		File f = new File(doc,file);
		f.delete();
		mkdel.recycle();
		mkdel.show();
	}
}
