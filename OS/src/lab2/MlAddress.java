package lab2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MlAddress extends MouseAdapter{
	Ml ml;
	JButton[] jbPageTableFlag;
	JButton[] jbPageTablePhycsics;  //请求页表
	JButton[] jbFIFO,jbLRU,jbOPT;
	JLabel[] jlMemoryFIFO,jlMemoryLRU,jlMemoryOPT;//内存块
	public MlAddress(Ml ml, JButton[] jbPageTableFlag,
			JButton[] jbPageTablePhycsics, JButton[] jbFIFO, JButton[] jbLRU,
			JButton[] jbOPT, JLabel[] jlMemoryFIFO, JLabel[] jlMemoryLRU,
			JLabel[] jlMemoryOPT) {
		super();
		this.ml = ml;
		this.jbPageTableFlag = jbPageTableFlag;
		this.jbPageTablePhycsics = jbPageTablePhycsics;
		this.jbFIFO = jbFIFO;
		this.jbLRU = jbLRU;
		this.jbOPT = jbOPT;
		this.jlMemoryFIFO = jlMemoryFIFO;
		this.jlMemoryLRU = jlMemoryLRU;
		this.jlMemoryOPT = jlMemoryOPT;
	}
	public void mouseClicked(MouseEvent e){
		if(ml.jf.jtfAddress.getText().equals("-1")){
			changeOPT();
		}else{
			ml.jf.countVisit++;
			//输入的16进制地址
			String hexStringAddress = ml.jf.jtfAddress.getText();
			String tenStringAddress = Integer.valueOf(hexStringAddress,16).toString();
			//转换成的10进制数字
			Integer tenIntAddress = Integer.parseInt(tenStringAddress);
			int forPageNumber = tenIntAddress/1024;
			int forPageNumberAddress = tenIntAddress%1024;
			changeJfCenter(forPageNumber);//页表和内存
			changeJfSouth(forPageNumber,forPageNumberAddress);//修改下面一堆计算的东西
		}
	}
	private void changeOPT(){
		if(ml.jf.alOPT.size()>0){
			if(!isFullOPT()){
				int forPageNumber = Integer.parseInt(ml.jf.alOPT.get(0));
				boolean isAtRAMOPT = isAtRAMOPT(forPageNumber);
				if(!isAtRAMOPT)
					changeOPTReal(forPageNumber);
				ml.jf.alOPT.remove(0);	
			}else{
				//OPT满了,选择一页换出
				System.out.println("OPT满了,选择一页换出");
				int max = ml.jf.alOPT.indexOf(jbOPT[0].getText());
				int index = 0;
				//fristTime储存OPT页面当前的所有页的后序第一次出现
				for(int i=1;i<jbOPT.length;i++)
					if(max<ml.jf.alOPT.indexOf(jbOPT[0].getText())){
						max = ml.jf.alOPT.indexOf(jbOPT[0].getText());
						index = i;
					}
				int forPageNumber = Integer.parseInt(ml.jf.alOPT.get(0));
				boolean isAtRAMOPT = isAtRAMOPT(forPageNumber);
				if(!isAtRAMOPT)
					jbOPT[index].setText(String.valueOf(forPageNumber));
				ml.jf.alOPT.remove(0);	
			}
		}
	}
	private boolean isFullOPT(){
		int flagFull = 0;  //标记是否分配内存的内存块已满
		//判断分配内存块是否满了
		for(int i=0;i<jbOPT.length;i++)
			if(!jbOPT[i].getText().equals(""))
				flagFull++;
		if(flagFull == jbOPT.length)
			return true;
		else 
			return false;
	}
	private void changeJfCenter(int forPageNumber){
		//主逻辑层
		boolean isFull = isFull();
		boolean isAtRAM = isAtRAM(forPageNumber);
		int intPageLength = Integer.parseInt(ml.jf.jtfPageLength.getText());
		if(forPageNumber <= intPageLength-1){
			//页号>=页表长度
			ml.jf.alOPT.add(String.valueOf(forPageNumber));
			if(isAtRAM){
				ml.jf.countAbsent++;
				System.out.println("在内存中,直接形成物理地址");
				if(isFull)
					changeLRUExchangeSame(forPageNumber);
				return ;
			}else{
				if(!isFull){
					//内存不满
					changePageTable(forPageNumber);
					changeFIFO(forPageNumber);
					changeLRU(forPageNumber);
				}
				else{
					//内存满:选择一页换出
					System.out.println("内存满:选择一页换出");
					int goPage = Integer.valueOf(jbFIFO[Integer.parseInt(ml.jf.jtfMemoryLength.getText())-1].getText());
					changeFIFOExchange(forPageNumber);
					changeLRUExchange(forPageNumber);
					changePageTableExchange(forPageNumber,goPage);
				}
			}
		}else
			ml.jf.jtfAddress.setText("输入的逻辑地址超出页表范围");
		//changeOPT();
	}
	private boolean isFull(){
		int flagFull = 0;  //标记是否分配内存的内存块已满
		//判断分配内存块是否满了
		for(int i=0;i<jbFIFO.length;i++)
			if(!jbFIFO[i].getText().equals(""))
				flagFull++;
		if(flagFull == jbFIFO.length)
			return true;
		else 
			return false;
	}
	private boolean isAtRAMOPT(int forPageNumber){
		for(int i=jbOPT.length-1;i>=0;i--)
			if(jbOPT[i].getText().equals(String.valueOf(forPageNumber)))
				return true;
		return false;
	}
	private boolean isAtRAM(int forPageNumber){
		//实际是检查状态位,本程序直接检索分配的内存块
		for(int i=jbFIFO.length-1;i>=0;i--)
			if(jbFIFO[i].getText().equals(String.valueOf(forPageNumber)))
				return true;
		return false;
	}
	
	
	
	private void changePageTable(int forPageNumber){
		jbPageTableFlag[forPageNumber].setText("1");
		for(int i=0;i<jbFIFO.length;i++)
			if(jbFIFO[i].getText().equals(""))
				jbPageTablePhycsics[forPageNumber].setText(jlMemoryFIFO[i].getText());
	}
	private void changeFIFO(int forPageNumber){
		for(int i=jbFIFO.length-1;i>=0;i--)
			if(jbFIFO[i].getText().equals("")){
				jbFIFO[i].setText(String.valueOf(forPageNumber));
				break;
			}
	}
	private void changeOPTReal(int forPageNumber){
		for(int i=jbOPT.length-1;i>=0;i--)
			if(jbOPT[i].getText().equals("")){
				jbOPT[i].setText(String.valueOf(forPageNumber));
				break;
			}
	}
	private void changePageTableExchange(int forPageNumber,int goPage){
		jbPageTableFlag[goPage].setText("0");
		jbPageTablePhycsics[goPage].setText("");
		jbPageTableFlag[forPageNumber].setText("1");
		jbPageTablePhycsics[forPageNumber].setText(jlMemoryFIFO[0].getText());
		UpdatePhyics();
	}
	private void changeFIFOExchange(int forPageNumber){
		//将最底下的压没
		String temp;
		String temp1=jbFIFO[0].getText();
		for(int i=1;i<jbFIFO.length;i = i+2){
			temp = jbFIFO[i].getText();
			jbFIFO[i].setText(temp1);
			if(i+1<jbFIFO.length){
				temp1 = jbFIFO[i+1].getText();
				jbFIFO[i+1].setText(temp);
			}
		}
		//将最顶上的压上来
		jbFIFO[0].setText(String.valueOf(forPageNumber));
	}
	private void changeLRUExchange(int forPageNumber){
		String temp;
		String temp1=jbLRU[0].getText();
		for(int i=1;i<jbLRU.length;i = i+2){
			temp = jbLRU[i].getText();
			jbLRU[i].setText(temp1);
			if(i+1<jbLRU.length){
				temp1 = jbLRU[i+1].getText();
				jbLRU[i+1].setText(temp);
			}
		}
		//将最顶上的压上来
		jbLRU[0].setText(String.valueOf(forPageNumber));
	}
	private void UpdatePhyics(){
		for(int i=0;i<jbFIFO.length;i++){
			int pageNumber = Integer.parseInt(jbFIFO[i].getText());
			jbPageTablePhycsics[pageNumber].setText(jlMemoryFIFO[i].getText()); 
		}
	}
	private void changeLRU(int forPageNumber){
		for(int i=jbLRU.length-1;i>=0;i--)
			if(jbLRU[i].getText().equals("")){
				jbLRU[i].setText(String.valueOf(forPageNumber));
				break;
			}
	}
	private void changeLRUExchangeSame(int forPageNumber){
		int w=0;
		for(int i=0;i<jbLRU.length;i++)
			if(Integer.valueOf(jbLRU[i].getText()) == forPageNumber)
				w = i;
		
		
		String temp;
		String temp1=jbLRU[0].getText();
		for(int i=1;i<=w;i = i+2){
			temp = jbLRU[i].getText();
			jbLRU[i].setText(temp1);
			if(i+1<=w){
				temp1 = jbLRU[i+1].getText();
				jbLRU[i+1].setText(temp);
			}
		}
		//将最顶上的压上来
		jbLRU[0].setText(String.valueOf(forPageNumber));
	}
	private void changeJfSouth(int forPageNumber,int forPageNumberAddress){
		//逻辑地址
		ml.jf.setLogical.setText(ml.jf.jtfAddress.getText());
		//对应块号及块内地址
		ml.jf.setCount.setText((forPageNumber)+"   "+(forPageNumberAddress));
		//物理地址
		String physical = String.valueOf((1024*forPageNumber*Integer.parseInt(ml.jf.jtfPageSize.getText())
				+ forPageNumberAddress));
		ml.jf.setPhysical.setText(physical);
		//页面大小
		ml.jf.setPagesize.setText((ml.jf.jtfPageSize.getText())+"k");
		//访问次数
		String exVisit = (String.valueOf(ml.jf.countVisit));
		ml.jf.setVisit.setText(exVisit);
		//命中次数
		String exAbsent = (String.valueOf(ml.jf.countAbsent));
		ml.jf.setAbsent.setText(exAbsent);
		//缺页率
		NumberFormat numberformat = NumberFormat.getInstance();
		numberformat.setMaximumFractionDigits(2);
		float fAbsent = Integer.valueOf(exAbsent);
		float fVisit = Integer.valueOf(exVisit);
		String rate = numberformat.format((1-(fAbsent/fVisit))*100);
		ml.jf.setRate.setText(rate+"%");
	}
}
