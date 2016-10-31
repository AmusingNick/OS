package lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RandomAccessFileTest {
	public void writeBegin(String[] proName ,int proNumber,JTextField[] jtfComeMoment
			,JTextField[] jtfServiceTime){
		//默认写在桌面的out.txt下
		//返回写的东西
		try(
				RandomAccessFile raf = new RandomAccessFile(
						"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out.txt","rw")){
			StringBuffer sbHeader = new StringBuffer();
			sbHeader.append("\t\t");
			for(int i=0;i<proNumber;i++)
				sbHeader.append(proName[i]+"\t");
			raf.write(sbHeader.toString().getBytes());
			
			raf.seek(raf.length());
			raf.write("\r\n进入时刻\t".getBytes());
			raf.seek(raf.length());
			for(int i=0;i<proNumber;i++){
				String acomeMoment =  jtfComeMoment[i].getText()+"\t";
				raf.write(acomeMoment.getBytes());
				raf.seek(raf.length());
			}
			
			raf.write("\r\n服务时间\t".getBytes());
			raf.seek(raf.length());
			for(int i=0;i<proNumber;i++){
				String aServiceTime = jtfServiceTime[i].getText()+"\t";
				raf.write(aServiceTime.getBytes());
				raf.seek(raf.length());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public void writeAppend(){
		Object[] obj2 = ReadOut();
		int[] ComeMomment = (int[])obj2[0];
		int[] ServiceTime = (int[])obj2[1];
		try {
			writeOut_FCFS(ComeMomment,ServiceTime);
			writeOut_SJF(ComeMomment,ServiceTime);
			writeOut_RR1(ComeMomment,ServiceTime);
			writeOut_RR2(ComeMomment,ServiceTime);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void writeCopy(){
		try {
			RandomAccessFile raf_out = new RandomAccessFile(
					"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out.txt","rw");
			RandomAccessFile raf_out_FCFS = new RandomAccessFile(
					"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out_FCFS.txt","rw");
			RandomAccessFile raf_out_SJF = new RandomAccessFile(
					"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out_SJF.txt","rw");
			RandomAccessFile raf_out_RR1 = new RandomAccessFile(
					"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out_RR1.txt","rw");
			RandomAccessFile raf_out_RR2 = new RandomAccessFile(
					"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out_RR2.txt","rw");
			String line ;
			while((line = raf_out.readLine()) != null){
				raf_out_FCFS.writeBytes(line+"\r\n");
				raf_out_SJF.writeBytes(line+"\r\n");
				raf_out_RR1.writeBytes(line+"\r\n");
				raf_out_RR2.writeBytes(line+"\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Object[] ReadOut() {
		//返回读出来的两行：到达时刻和服务时间
		try(
				RandomAccessFile raf = new RandomAccessFile(
						"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out.txt","r")){
			byte[] bbuf = new byte[1024];
			int hasRead = 0;
			String line_ComeMomment ;
			String line_ServiceTime ; 
			raf.readLine();
			line_ComeMomment = raf.readLine();
			line_ServiceTime = raf.readLine();
			String[] ssComeMomment = line_ComeMomment.split("\t");
			String[] ssServiceTime = line_ServiceTime.split("\t");
			int[] ComeMomment = new int[ssComeMomment.length-1];
			int[] ServiceTime = new int[ssServiceTime.length-1];
			
			for(int i=0;i<ComeMomment.length;i++){
				ComeMomment[i] = Integer.parseInt(ssComeMomment[i+1]);
				ServiceTime[i] = Integer.parseInt(ssServiceTime[i+1]);
			}
			return new Object[]{ComeMomment,ServiceTime};
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	private void writeOut_FCFS(int[] ComeMomment,int []ServiceTime) throws FileNotFoundException {
		RandomAccessFile raf_out_FCFS = new RandomAccessFile(
				"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out_FCFS.txt","rw");
		Object[] objgetAll = ProcessSchedule.FCFS(ComeMomment, ServiceTime);
		writeFile(raf_out_FCFS,objgetAll);
	}
	
	private void writeOut_SJF(int[] ComeMomment,int []ServiceTime) throws FileNotFoundException {
		RandomAccessFile raf_out_SJF = new RandomAccessFile(
				"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out_SJF.txt","rw");
		Object[] objgetAll = ProcessSchedule.SJF(ComeMomment, ServiceTime);
		writeFile(raf_out_SJF,objgetAll);
	}
	
	private void writeOut_RR1(int[] ComeMomment,int []ServiceTime) throws FileNotFoundException {
		RandomAccessFile raf_out_RR1 = new RandomAccessFile(
				"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out_RR1.txt","rw");
		Object[] objgetAll = ProcessSchedule.RR1(ComeMomment, ServiceTime);
		writeFile(raf_out_RR1,objgetAll);
	}
	private void writeOut_RR2(int[] ComeMomment,int []ServiceTime) throws FileNotFoundException {
		RandomAccessFile raf_out_RR2 = new RandomAccessFile(
				"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out_RR2.txt","rw");
		Object[] objgetAll = ProcessSchedule.RR2(ComeMomment, ServiceTime);
		writeFile(raf_out_RR2,objgetAll);
	}
	private void writeFile(RandomAccessFile raf_out_FCFS, Object[] objgetAll) {
		int[] c = (int[])objgetAll[0];
		int[] d = (int[])objgetAll[1];
		int[] e = (int[])objgetAll[2];
		double[] f = (double[])objgetAll[3];
		int avgE =(int)objgetAll[4];
		double avgF =(double)objgetAll[5];
		try {
			raf_out_FCFS.seek(raf_out_FCFS.length());
			raf_out_FCFS.write("开始时间\t".getBytes());
			for(int i=0;i<c.length;i++){
				raf_out_FCFS.write(String.valueOf(c[i]).getBytes());
				raf_out_FCFS.write("\t".getBytes());
			}
			raf_out_FCFS.write("\r\n完结时间\t".getBytes());
			for(int i=0;i<c.length;i++){
				raf_out_FCFS.write(String.valueOf(d[i]).getBytes());
				raf_out_FCFS.write("\t".getBytes());
			}
			raf_out_FCFS.write("\r\nW\t\t".getBytes());
			for(int i=0;i<c.length;i++){
				raf_out_FCFS.write(String.valueOf(e[i]).getBytes());
				raf_out_FCFS.write("\t".getBytes());
			}
			raf_out_FCFS.write("\r\nT\t\t".getBytes());
			for(int i=0;i<c.length;i++){
				raf_out_FCFS.write(String.format("%.2f", f[i]).getBytes());
				raf_out_FCFS.write("\t".getBytes());
			}
			raf_out_FCFS.write("\r\n平均周转时间:".getBytes());
			raf_out_FCFS.write(String.valueOf(avgE).getBytes());
			raf_out_FCFS.write("\r\n平均加权周转时间:".getBytes());
			raf_out_FCFS.write(String.format("%.2f", avgF).getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
