package lab5;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CinInit {
	JFrame jf = new JFrame();
	static JTextField[] jtfComeMoment ;
	static JTextField[] jtfServiceTime ;
	static String[] proName = new String[]{"A","B","C","D","E","F","G","H","I","J"};
	void init(){
		String proNumberString = new JOptionPane().showInputDialog(null, 
				"初始进入进程数");
		try{
			int proNumber = Integer.parseInt(proNumberString);
			jtfComeMoment = new JTextField[proNumber];
			jtfServiceTime = new JTextField[proNumber];
			jf.setVisible(true);
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setLayout(new GridLayout(4,proNumber+1));
			jf.add(new JLabel());
			for(int i=0;i<proNumber;i++)
				jf.add(new JLabel(proName[i]));
			jf.add(new JLabel("进入时刻")); 
			for(int i=0;i<proNumber;i++){
				JTextField jtf = new JTextField();
				jtfComeMoment[i] = jtf;
				jf.add(jtf);
			}
			jf.add(new JLabel("服务时间"));
			for(int i=0;i<proNumber;i++){
				JTextField jtf = new JTextField();
				jtfServiceTime[i] = jtf;
				jf.add(jtf);
			}
			for(int i=0;i<proNumber;i++)
				jf.add(new JLabel());
			JButton jbSure = new JButton("确认写入文件");
			jf.add(jbSure);
			jf.pack();
			
			///
			jbSure.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					File f = new File(
							"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out.txt");
					if(f.exists())
						f.delete();
					//写入文件
					new RandomAccessFileTest().writeBegin(
							proName,proNumber, jtfComeMoment, jtfServiceTime);
					File f_FCFS = new File(
							"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out_FCFS.txt");
					File f_SJF = new File(
							"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out_SJF.txt");
					File f_RR1 = new File(
							"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out_RR1.txt");
					File f_RR2 = new File(
							"C:\\Users\\hongyu\\Desktop\\操作系统实验流程图\\lab5\\Test\\out_RR2.txt");
					if(f_FCFS.exists() && f_SJF.exists() && f_RR1.exists()
							&&f_RR2 .exists()){
						f_FCFS.delete();
						f_SJF.delete();
						f_RR1.delete();
						f_RR2.delete();
					}
					//写表头:out文件写出来3个
					new RandomAccessFileTest().writeCopy();
					//读出文件后，进行数据修改
					new RandomAccessFileTest().writeAppend();
				}
			});
		}catch (NumberFormatException e){
			new JOptionPane().showMessageDialog(null, 
					"输入不合法，请重新输入");
		}
	}
	public static void main(String[] args) {
		new CinInit().init();
	}
}	
