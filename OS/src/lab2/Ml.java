package lab2;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Ml extends MouseAdapter{
	Mains jf;
	Font f = new Font("Dialog",Font.BOLD,50);
	Ml(Mains jf){
		this.jf = jf;
	}
	@Override
	public void mouseClicked(MouseEvent e){
		//在这里面建立要改的按钮
		JButton[] jbPageTableFlag = new JButton[Integer.parseInt(jf.jtfPageLength.getText())];
		JButton[] jbPageTablePhycsics = new JButton[Integer.parseInt(jf.jtfPageLength.getText())];;
		JButton[] jbFIFO = new JButton[Integer.parseInt(jf.jtfMemoryLength.getText())];
		JButton[] jbLRU = new JButton[Integer.parseInt(jf.jtfMemoryLength.getText())];
		JButton[] jbOPT = new JButton[Integer.parseInt(jf.jtfMemoryLength.getText())];
		int[] BitMapKong = getBitMapKong();
		JLabel[] jlMemoryFIFO = new JLabel[BitMapKong.length];
		JLabel[] jlMemoryLRU = new JLabel[BitMapKong.length];
		JLabel[] jlMemoryOPT = new JLabel[BitMapKong.length];
		//下面是对请求页表的建立
		updateJPage(jbPageTableFlag,jbPageTablePhycsics);
		//下面是对FIFO,LRU,OPT三个内存块的建立
		updateJRAM(jbFIFO,jbLRU,jbOPT,jlMemoryFIFO,jlMemoryLRU,jlMemoryOPT,BitMapKong);
		updateJF();
		
		//把按钮都传过去.然后各项实体数据在MlAddress中建立
		jf.sureAddress.addMouseListener(
				new MlAddress(this,jbPageTableFlag,jbPageTablePhycsics
						,jbFIFO,jbLRU,jbOPT,jlMemoryFIFO,jlMemoryLRU,jlMemoryOPT));
	}
	private void updateJF(){
		jf.north.removeAll();
		jf.north.add(jf.address);
		jf.north.add(jf.jtfAddress);
		jf.north.add(jf.sureAddress);
		jf.repaint();
		jf.north.validate();
		jf.validate();
	}
	private void updateJPage(JButton[] flag,JButton[] physics){
		int pLength = Integer.parseInt(jf.jtfPageLength.getText());
		jf.pageTable.setLayout(new GridLayout(pLength+1,3));
		JLabel pageNumber = new JLabel("PageNumber");
		JLabel physicsNumber = new JLabel("PhysicsNumber");
		JLabel pageFlag = new JLabel("PageFlag");
		jf.pageTable.add(pageNumber);
		jf.pageTable.add(physicsNumber);
		jf.pageTable.add(pageFlag);
		int w = 0,v = 0;
		for(int i=0;i<3*pLength;i++){
			if(i%3==0){
				JLabel jlPageTable = new JLabel(""+(i+1)/3+"",
						JLabel.CENTER);
				jlPageTable.setFont(f);
				jf.pageTable.add(jlPageTable);
			}else if(i%3==2){
				//状态位
				flag[w] = new JButton("0");
				flag[w].setFont(f);
				flag[w].setEnabled(false);
				jf.pageTable.add(flag[w++]);
			}
			else{
				//物理块
				physics[v] = new JButton();
				physics[v].setFont(f);
				physics[v].setEnabled(false);
				jf.pageTable.add(physics[v++]);
			}
		}
	}
	private void updateJRAM(JButton[] jbFIFO,JButton[] jbLRU,JButton[] jbOPT,
			JLabel[] jlMemoryFIFO,JLabel[] jlMemoryLRU,JLabel[] jlMemoryOPT,int[] BitMapKong){
		//根据位视图发内存(初始)
		for(int i=0;i<BitMapKong.length;i++){
			jlMemoryFIFO[i] = new JLabel(""+(BitMapKong[i])+"",JLabel.CENTER);
			jlMemoryLRU[i] = new JLabel(""+(BitMapKong[i])+"",JLabel.CENTER);
			jlMemoryOPT[i] = new JLabel(""+(BitMapKong[i])+"",JLabel.CENTER);
		}
		int m = 0;
		int n = 0,v = 0, t = 0;
		int rLength = Integer.parseInt(jf.jtfMemoryLength.getText());
		jf.RAM_FIFO.setLayout(new GridLayout(rLength+1,2));
		jf.RAM_LRU.setLayout(new GridLayout(rLength+1,2));
		jf.RAM_OPT.setLayout(new GridLayout(rLength+1,2));
		JLabel jl_FIFO_Location = new JLabel("FIFO_Location");
		JLabel jl_FIFO = new JLabel("FIFO_Memory");
		jf.RAM_FIFO.add(jl_FIFO_Location);
		jf.RAM_FIFO.add(jl_FIFO);
		for(int i=0;i<2*rLength;i++){
			if(i%2==0){
				jlMemoryFIFO[m].setFont(f);
				jf.RAM_FIFO.add(jlMemoryFIFO[m++]);
			}else{
				jbFIFO[n] = new JButton();
				jbFIFO[n].setFont(f);
				jbFIFO[n].setEnabled(false);
				jf.RAM_FIFO.add(jbFIFO[n++]);
			}
		}
		m = 0;
		
		JLabel jl_LRU_Location = new JLabel("LRU_Location");
		JLabel jl_LRU = new JLabel("LRU_Memory");
		jf.RAM_LRU.add(jl_LRU_Location);
		jf.RAM_LRU.add(jl_LRU);
		for(int i=0;i<2*rLength;i++){
			if(i%2==0){
				jlMemoryLRU[m].setFont(f);
				jf.RAM_LRU.add(jlMemoryLRU[m++]);
			}else{
				jbLRU[v] = new JButton();
				jbLRU[v].setFont(f);
				jbLRU[v].setEnabled(false);
				jf.RAM_LRU.add(jbLRU[v++]);
			}
		}
		m = 0;
		
		JLabel jl_OPT_Location = new JLabel("OPT_Location");
		JLabel jl_OPT = new JLabel("OPT_Memory");
		jf.RAM_OPT.add(jl_OPT_Location);
		jf.RAM_OPT.add(jl_OPT);
		for(int i=0;i<2*rLength;i++){
			if(i%2==0){
				jlMemoryOPT[m].setFont(f);
				jf.RAM_OPT.add(jlMemoryOPT[m++]);
			}else{
				jbOPT[t] = new JButton();
				jbOPT[t].setFont(f);
				jbOPT[t].setEnabled(false);
				jf.RAM_OPT.add(jbOPT[t++]);
			}
		}
	}
	private int[] getBitMapKong(){
		BitMap bmm = new BitMap();
		int sizeMemory = Integer
				.valueOf(jf.jtfMemoryLength.getText());
		int[] test = bmm.getSequence(sizeMemory);
		for(int i=0;i<test.length;i++)
			System.out.print(test[i]+"\t");
		return test;
	}
}
