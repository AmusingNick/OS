package lab1;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Jf extends JFrame{	
	//���Ե�Map����
	//Map�е�Setֵ�������ظ���,���ظ���,���Զ��滻��
	//����������Ϊq1-20,q2-50,q3-80
	private JTextArea jtf = new JTextArea();
	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	//�����
	private JLabel jl1 = new JLabel("ѡ�����");
	private JButton creation = new JButton("��������");
	private JButton Termination = new JButton("��ֹ����");
	private JButton wakeUp = new JButton("���ѽ���");
	private JButton block = new JButton("��������");
	//�����
	private AllProgress ap = new AllProgress();
	private RAM r = new RAM();
	//��������в���,���Ǹ���ap�Ĳ���,��ʾ���Ǳ���ap��ֵ�Ĳ���.
	private JLabel jl2 = new JLabel("��ǰִ�н���");
	public JTextArea now = new JTextArea();
	private JLabel jl3 = new JLabel("��������");
	public JTextArea alreadyQueue = new JTextArea();
	private JLabel jl4 = new JLabel("��������");
	public JTextArea blockQueue = new JTextArea();
	//������
	private MouseAdapter mouseC = new MouseAdapterC(ap,jp2,r);
	Jf(){
		this.setSize(1000,500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1));
		LayoutJp1();
		LayoutJp2();
		//ʱ��Ƭ�߳�
		MouseAdapterC mac = new MouseAdapterC(ap,jp2,r);
		new Thread(mac).start();
	}
	public void LayoutJp1(){
		jp1.setLayout(new GridLayout(1,5));
		jp1.add(jl1);
		creation.addMouseListener(mouseC);
		jp1.add(creation);
		Termination.addMouseListener(mouseC);
		jp1.add(Termination);
		wakeUp.addMouseListener(mouseC);
		jp1.add(wakeUp);
		block.addMouseListener(mouseC);
		jp1.add(block);
		this.add(jp1);
	}
	public void LayoutJp2(){
		now.setEditable(false);
		alreadyQueue.setEditable(false);
		blockQueue.setEditable(false);
		jp2.setLayout(new GridLayout(3,2));
		jp2.add(jl2);
		jp2.add(now);
		jp2.add(jl3);
		jp2.add(alreadyQueue);
		jp2.add(jl4);
		jp2.add(blockQueue);
		this.add(jp2);
	}
	public static void main(String[] args) {
		new Jf();
	}
}
