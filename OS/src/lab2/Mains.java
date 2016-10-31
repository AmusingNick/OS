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
	 *north: ���벿��*/
	JLabel pageLength = new JLabel("PageLength:");
	JTextField jtfPageLength = new JTextField("6",10);	
	JLabel memoryLength = new JLabel("MemoryLength:");
	JTextField jtfMemoryLength = new JTextField("3",10);
	JLabel pageSize = new JLabel("pageSize(k):");
	JTextField jtfPageSize = new JTextField("1",10);
	JButton sure = new JButton("ȷ��");
	JPanel north = new JPanel();
	
	JLabel address = new JLabel("Address:");
	JTextField jtfAddress = new JTextField(10);
	JButton sureAddress = new JButton("ѡ����ַ");	
	/*
	 * center: ������ʾJTable����
	 * */
	JPanel center = new JPanel();
	JPanel pageTable = new JPanel();
	JPanel RAM_FIFO = new JPanel();
	JPanel RAM_LRU = new JPanel();
	JPanel RAM_OPT = new JPanel();
	/*
	 * south: ��ʾ������Ϣ����
	 * */
	/*�߼���ַ,
	�����ַ,
	ҳ�泤��,
	ҳ���С,
	���ʴ���,
	���д���,
	ȱҳ��,
	�߼���ַ��Ӧ������*/
	//�߼���ַ
	JLabel logical = new JLabel("Logical:");
	JLabel setLogical = new JLabel("------"); 
	//��Ӧ���
	JLabel count = new JLabel("Count:");
	JLabel setCount = new JLabel("------");
	//�����ַ
	JLabel physical = new JLabel("Physical:");
	JLabel setPhysical = new JLabel("------");
	//ҳ���С
	JLabel pagesize = new JLabel("PageSize:");
	JLabel setPagesize = new JLabel("------");
	//���ʴ���
	public int countVisit = 0;
	JLabel visit = new JLabel("Visit:");
	JLabel setVisit = new JLabel("------");
	//���д���
	public int countAbsent = 0;
	JLabel absent = new JLabel("Absent:");
	JLabel setAbsent = new JLabel("------");
	//ȱҳ��
	JLabel rate = new JLabel("Rate:");
	JLabel setRate = new JLabel("------");
	JPanel south = new JPanel();
	//OPT�Ĵ�������
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
		//���ε���ǽ��г�ʼ������
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
		//����Ϊȫ��Ļ����
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
        gd.setFullScreenWindow(frame);
	}
}
