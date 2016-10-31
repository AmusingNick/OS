package lab2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Mains extends JFrame{
	/*
	 *north: 输入部分*/
	JLabel pageLength = new JLabel("PageLength:");
	JTextField jtfPageLength = new JTextField("6",10);	
	JLabel memoryLength = new JLabel("MemoryLength:");
	JTextField jtfMemoryLength = new JTextField("3",10);
	JLabel pageSize = new JLabel("pageSize(k):");
	JTextField jtfPageSize = new JTextField("1",10);
	JButton sure = new JButton("确定");
	JPanel north = new JPanel();
	
	JLabel address = new JLabel("Address:");
	JTextField jtfAddress = new JTextField(10);
	JButton sureAddress = new JButton("选定地址");	
	/*
	 * center: 居中显示JTable部分
	 * */
	JPanel center = new JPanel();
	JPanel pageTable = new JPanel();
	JPanel RAM_FIFO = new JPanel();
	JPanel RAM_LRU = new JPanel();
	JPanel RAM_OPT = new JPanel();
	/*
	 * south: 显示各项信息部分
	 * */
	/*逻辑地址,
	物理地址,
	页面长度,
	页面大小,
	访问次数,
	命中次数,
	缺页率,
	逻辑地址对应物理块号*/
	//逻辑地址
	JLabel logical = new JLabel("Logical:");
	JLabel setLogical = new JLabel("------"); 
	//对应块号
	JLabel count = new JLabel("Count:");
	JLabel setCount = new JLabel("------");
	//物理地址
	JLabel physical = new JLabel("Physical:");
	JLabel setPhysical = new JLabel("------");
	//页面大小
	JLabel pagesize = new JLabel("PageSize:");
	JLabel setPagesize = new JLabel("------");
	//访问次数
	public int countVisit = 0;
	JLabel visit = new JLabel("Visit:");
	JLabel setVisit = new JLabel("------");
	//命中次数
	public int countAbsent = 0;
	JLabel absent = new JLabel("Absent:");
	JLabel setAbsent = new JLabel("------");
	//缺页率
	JLabel rate = new JLabel("Rate:");
	JLabel setRate = new JLabel("------");
	JPanel south = new JPanel();
	//OPT的储存链表
	ArrayList<String> alOPT = new ArrayList<>();
	public void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		north.setLayout(new FlowLayout());
		north.add(pageLength);
		north.add(jtfPageLength);
		north.add(memoryLength);
		north.add(jtfMemoryLength);
		north.add(pageSize);
		north.add(jtfPageSize);
		north.add(sure);
		//本次点击是进行初始化过程
		sure.addMouseListener(new Ml(this));
		this.add(north,BorderLayout.NORTH);
	
		center.setLayout(new GridLayout(1,4,20,5));
		center.add(pageTable);
		center.add(RAM_FIFO);
		center.add(RAM_LRU);
		center.add(RAM_OPT);
		this.add(center,BorderLayout.CENTER);
		
		south.setLayout(new GridLayout(7,2));
		south.add(logical);
		south.add(setLogical);
		south.add(count);
		south.add(setCount);
		south.add(physical);
		south.add(setPhysical);
		south.add(pagesize);
		south.add(setPagesize);
		south.add(visit);
		south.add(setVisit);
		south.add(absent);
		south.add(setAbsent);
		south.add(rate);
		south.add(setRate);
		this.add(south,BorderLayout.SOUTH);
		this.pack();
	}
	public static void main(String[] args){
		Mains frame = new Mains();
		frame.init();
		//下面为全屏幕代码
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
        gd.setFullScreenWindow(frame);
	}
}
