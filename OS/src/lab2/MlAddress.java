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
	JButton[] jbPageTablePhycsics;  //����ҳ��
	JButton[] jbFIFO,jbLRU,jbOPT;
	JLabel[] jlMemoryFIFO,jlMemoryLRU,jlMemoryOPT;//�ڴ��
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
			//�����16���Ƶ�ַ
			String hexStringAddress = ml.jf.jtfAddress.getText();
			String tenStringAddress = Integer.valueOf(hexStringAddress,16).toString();
			//ת���ɵ�10��������
			Integer tenIntAddress = Integer.parseInt(tenStringAddress);
			int forPageNumber = tenIntAddress/1024;
			int forPageNumberAddress = tenIntAddress%1024;
			changeJfCenter(forPageNumber);//ҳ����ڴ�
			changeJfSouth(forPageNumber,forPageNumberAddress);//�޸�����һ�Ѽ���Ķ���
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
				//OPT����,ѡ��һҳ����
				System.out.println("OPT����,ѡ��һҳ����");
				int max = ml.jf.alOPT.indexOf(jbOPT[0].getText());
				int index = 0;
				//fristTime����OPTҳ�浱ǰ������ҳ�ĺ����һ�γ���
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
		int flagFull = 0;  //����Ƿ�����ڴ���ڴ������
		//�жϷ����ڴ���Ƿ�����
		for(int i=0;i<jbOPT.length;i++)
			if(!jbOPT[i].getText().equals(""))
				flagFull++;
		if(flagFull == jbOPT.length)
			return true;
		else 
			return false;
	}
	private void changeJfCenter(int forPageNumber){
		//���߼���
		boolean isFull = isFull();
		boolean isAtRAM = isAtRAM(forPageNumber);
		int intPageLength = Integer.parseInt(ml.jf.jtfPageLength.getText());
		if(forPageNumber <= intPageLength-1){
			//ҳ��>=ҳ����
			ml.jf.alOPT.add(String.valueOf(forPageNumber));
			if(isAtRAM){
				ml.jf.countAbsent++;
				System.out.println("���ڴ���,ֱ���γ������ַ");
				if(isFull)
					changeLRUExchangeSame(forPageNumber);
				return ;
			}else{
				if(!isFull){
					//�ڴ治��
					changePageTable(forPageNumber);
					changeFIFO(forPageNumber);
					changeLRU(forPageNumber);
				}
				else{
					//�ڴ���:ѡ��һҳ����
					System.out.println("�ڴ���:ѡ��һҳ����");
					int goPage = Integer.valueOf(jbFIFO[Integer.parseInt(ml.jf.jtfMemoryLength.getText())-1].getText());
					changeFIFOExchange(forPageNumber);
					changeLRUExchange(forPageNumber);
					changePageTableExchange(forPageNumber,goPage);
				}
			}
		}else
			ml.jf.jtfAddress.setText("������߼���ַ����ҳ��Χ");
		//changeOPT();
	}
	private boolean isFull(){
		int flagFull = 0;  //����Ƿ�����ڴ���ڴ������
		//�жϷ����ڴ���Ƿ�����
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
		//ʵ���Ǽ��״̬λ,������ֱ�Ӽ���������ڴ��
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
		//������µ�ѹû
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
		//����ϵ�ѹ����
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
		//����ϵ�ѹ����
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
		//����ϵ�ѹ����
		jbLRU[0].setText(String.valueOf(forPageNumber));
	}
	private void changeJfSouth(int forPageNumber,int forPageNumberAddress){
		//�߼���ַ
		ml.jf.setLogical.setText(ml.jf.jtfAddress.getText());
		//��Ӧ��ż����ڵ�ַ
		ml.jf.setCount.setText((forPageNumber)+"   "+(forPageNumberAddress));
		//�����ַ
		String physical = String.valueOf((1024*forPageNumber*Integer.parseInt(ml.jf.jtfPageSize.getText())
				+ forPageNumberAddress));
		ml.jf.setPhysical.setText(physical);
		//ҳ���С
		ml.jf.setPagesize.setText((ml.jf.jtfPageSize.getText())+"k");
		//���ʴ���
		String exVisit = (String.valueOf(ml.jf.countVisit));
		ml.jf.setVisit.setText(exVisit);
		//���д���
		String exAbsent = (String.valueOf(ml.jf.countAbsent));
		ml.jf.setAbsent.setText(exAbsent);
		//ȱҳ��
		NumberFormat numberformat = NumberFormat.getInstance();
		numberformat.setMaximumFractionDigits(2);
		float fAbsent = Integer.valueOf(exAbsent);
		float fVisit = Integer.valueOf(exVisit);
		String rate = numberformat.format((1-(fAbsent/fVisit))*100);
		ml.jf.setRate.setText(rate+"%");
	}
}
