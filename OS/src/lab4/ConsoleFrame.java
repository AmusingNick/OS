package lab4;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsoleFrame {
	JFrame jf = new JFrame("ģ�������н���");
	JTextArea jta = new JTextArea(20,50);
	void init(){
		jta.setBackground(Color.black);
		jta.setForeground(Color.white);
		jta.setFont(new Font(Font.DIALOG,Font.BOLD,20));
		jta.setText("ģ��Microsoft Windows �����н���\n"
				+ "(c) 2015 Hongyu Wu����������Ȩ����\n"
				+ "\n"
				+ "֧������:\n"
				+ "1.MD -��Ŀ¼��\n"
				+ "2.CD -Ŀ¼�� cd..��cd.\n"
				+ "3.RD -��Ŀ¼��\n"
				+ "4.DIR(�г���ǰĿ¼������Ŀ¼��)(ͨ���*,?)\n"
				+ "5.TREE(�ļ����νṹ��ʾ)\n"
				+ "6.MK -�ļ��� -��С���ֽڣ�\n"
				+ "7.DEL -�ļ���\n\n"
				+ "C:\\Users\\hongyu\\Desktop\\>");
		jta.addKeyListener(new ConsoleKeyEnter(jta));
		jf.add(new JScrollPane(jta));
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
	}
	public static void main(String[] args) {
		new ConsoleFrame().init();
	}
}
