package lab1;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Jf extends JFrame{	
	//就栽到Map上了
	//Map中的Set值不能有重复的,有重复的,就自动替换了
	//测试用例改为q1-20,q2-50,q3-80
	private JTextArea jtf = new JTextArea();
	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	//上面的
	private JLabel jl1 = new JLabel("选择操作");
	private JButton creation = new JButton("创建进程");
	private JButton Termination = new JButton("终止进程");
	private JButton wakeUp = new JButton("唤醒进程");
	private JButton block = new JButton("阻塞进程");
	//下面的
	private AllProgress ap = new AllProgress();
	private RAM r = new RAM();
	//点击的所有操作,都是更改ap的操作,显示就是遍历ap中值的操作.
	private JLabel jl2 = new JLabel("当前执行进程");
	public JTextArea now = new JTextArea();
	private JLabel jl3 = new JLabel("就绪队列");
	public JTextArea alreadyQueue = new JTextArea();
	private JLabel jl4 = new JLabel("阻塞队列");
	public JTextArea blockQueue = new JTextArea();
	//监听器
	private MouseAdapter mouseC = new MouseAdapterC(ap,jp2,r);
	Jf(){
		this.setSize(1000,500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1));
		LayoutJp1();
		LayoutJp2();
		//时间片线程
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
