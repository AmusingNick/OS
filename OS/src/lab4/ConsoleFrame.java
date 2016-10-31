package lab4;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsoleFrame {
	JFrame jf = new JFrame("模拟命令行界面");
	JTextArea jta = new JTextArea(20,50);
	void init(){
		jta.setBackground(Color.black);
		jta.setForeground(Color.white);
		jta.setFont(new Font(Font.DIALOG,Font.BOLD,20));
		jta.setText("模拟Microsoft Windows 命令行界面\n"
				+ "(c) 2015 Hongyu Wu。保留所有权利。\n"
				+ "\n"
				+ "支持命令:\n"
				+ "1.MD -子目录名\n"
				+ "2.CD -目录名 cd..和cd.\n"
				+ "3.RD -子目录名\n"
				+ "4.DIR(列出当前目录的所有目录项)(通配符*,?)\n"
				+ "5.TREE(文件树形结构显示)\n"
				+ "6.MK -文件名 -大小（字节）\n"
				+ "7.DEL -文件名\n\n"
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
