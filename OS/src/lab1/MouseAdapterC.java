package lab1;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MouseAdapterC extends MouseAdapter implements Runnable{
	AllProgress ap ;
	JPanel jp;
	RAM r;
	public MouseAdapterC(AllProgress ap,JPanel jp,RAM r) {
		this.ap = ap;
		this.jp = jp;
		this.r = r;
	}
	public MouseAdapterC() {}
	@Override	
	public void mouseClicked(MouseEvent e){
		if(e.getSource() instanceof JButton){
			JButton j = (JButton)e.getSource();
			String clickedSelect = j.getText();
			switch(clickedSelect){
				case "��������":
					create();
					break;
				case "��ֹ����":
					stop();
					break;
				case "���ѽ���":
					wake();break;
				case "��������":
					block();
					break;
			}
		}
	}
	public void write(){
		//ִ��
		JTextArea jtaNow = (JTextArea)jp.getComponent(1);
		if(ap.p != null && !ap.p.name.equals(""))
			jtaNow.setText(ap.p.name+"-"+ap.p.PCBsize);
		else 
			jtaNow.setText("");
		//����
		JTextArea jtaAlready = (JTextArea)jp.getComponent(3);
		StringBuffer alQ = new StringBuffer();
		for(PCB aaa:ap.alreadyNow){
			alQ.append(aaa.name+"-"+aaa.PCBsize);
			alQ.append("-->");
		}
		jtaAlready.setText(alQ.toString());
		//����
		JTextArea jtaBlock = (JTextArea)jp.getComponent(5);
		StringBuffer alQ1 = new StringBuffer();
		for(PCB bbb:ap.blockQueue){
			alQ1.append(bbb.name+"-"+"-"+bbb.PCBsize);
			alQ1.append("-->");
		}
		jtaBlock.setText(alQ1.toString());
	}
	private void create(){
		JOptionPane jop = new JOptionPane();
		String progress = jop.showInputDialog(null, "������?");
		String size = jop.showInputDialog(null,"�ռ��С?");
		//���뵽����������
		if(progress != null && Integer.parseInt(size)>0){
			PCB addP = new PCB();
			addP.name = progress;
			addP.PCBsize = Integer.parseInt(size);
			ap.alreadyNow.add(addP);
			//��ӽ��̵��ڴ�
			r.BF(addP);
			//���CPU����,�ͽ���ִ��̬
			//if(ap.p == null)
				if(ap.p.name.equals(""))
					ap.p = ap.alreadyNow.poll();
			//��ap�и��������ݱ��� д��������
			write();
		}
	}
	private void stop(){
		r.recycle(ap.p);
		if(ap.alreadyNow.peek() != null)
			ap.p = ap.alreadyNow.poll();
		else 
			ap.p = new PCB();
		write();
	}
	private void block(){
		if(!ap.p.name.equals("")){
			ap.blockQueue.add(ap.p);
			ap.p = new PCB();
			ap.p = ap.alreadyNow.poll();
			write();
		}
	}
	private void wake(){
		String[] blockQueue = new String[20];
		int i = 0;
		for(PCB block : ap.blockQueue)
			blockQueue[i++] = block.name;
		Object selectedValue = JOptionPane.showInputDialog(null, null, "", 1, null,  blockQueue, null);
		PCB wakeP = null;
		for(PCB block : ap.blockQueue)
			if(block.name.equals(selectedValue.toString()))
				wakeP = block;
		if(selectedValue!=null){
			if(ap.p==null){
				ap.alreadyNow.add(wakeP);
				ap.p = ap.alreadyNow.poll();
				ap.blockQueue.remove(wakeP);
			}else{
				ap.alreadyNow.add(wakeP);
				ap.blockQueue.remove(wakeP);
			} 
		}
		write();
	}
	@Override
	public void run() {
		while(true){
			changeTimeA();
			try {
				Thread.sleep(ap.timeA);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void changeTimeA(){
		/*
		 * ʱ��Ƭ����:�ھ������������ѡ��һ��PCB,����Ϊ����ִ��PCB
		 * ��������
		 * �����Ǿ������в�Ϊ��
		 * */
		if(ap.alreadyNow.peek() != null){
			int randomSize = ap.alreadyNow.size();
			Random rd = new Random();
			int i = rd.nextInt(randomSize);
			ArrayList al = new ArrayList();
			al.addAll(ap.alreadyNow);
			PCB willNow= (PCB)al.get(i);  //�õ���Ҫ��ִ�е�PCB
			ap.alreadyNow.remove(willNow);
			if(ap.p != null){
				ap.alreadyNow.add(ap.p);
				ap.p = willNow;
				write();
			}
		}
	}
}
