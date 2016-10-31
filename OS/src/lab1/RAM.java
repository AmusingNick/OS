package lab1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class RAM {
	//�ɱ�������ڴ�
	static final int size = 400;   //�ڴ湲�ƴ�С
	static final int beginAddress = 100;  //�ڴ���ʼ��ַ
	//key�����С,value������ʼ��ַ
	Map<Integer,Integer> free = new TreeMap<>(); 
	//���б�:��ʼ״̬:400,100
	Map<Integer,Integer> busy = new TreeMap<>(); 
	//ռ�ñ�:��ʼ״̬:0,0
	//������������Ӧ�㷨����Ƭ��СΪ2Kb,���������н��̵Ŀռ䡣���Բ鿴������ռ�Ŀռ��ϵͳ���пռ䡣
	RAM(){
		free.put(400, 100);
	}
	public boolean BF(PCB p){
		/*
		 * ����PCB��������BF�㷨���ŵ���Ӧ�ڴ���
		 * */
		System.out.println("��ӽ���"+p.name+"���ڴ�");
		for(Integer m:free.keySet()){
			if(m >= p.PCBsize){
				System.out.println("���������С��װ����");
				setAllP(p);//����p��Ϣ
				updateBusy(p);
				busy.put(p.size,p.benginAddress); //ռ�ñ���� 
				updateFree(p,m);//���б����:��ռ�õ��޳�
				break;
			}
			System.out.println("�������������");
		}
		lookFreeBusy();
		return false;
	}
	private void recycleFirst(PCB p){
		System.out.println("���յ�һ��");
		if(free.get(nextPSize(p.benginAddress)) != null){
			if(free.get(nextPSize(p.benginAddress))
					==p.size+p.benginAddress){
				//�����ǿյ�
				free.put(p.size+nextPSize(p.benginAddress)
						,p.benginAddress);
				free.remove(valueGetKey(free, p.benginAddress+p.size));
			}else
				free.put(p.size,p.benginAddress);
		}else
			free.put(p.size,p.benginAddress);
		lookFreeBusy();
		System.out.println("--------------------------");
	}
	private void recycleLast(PCB p){
		System.out.println("�������һ��");
		int put1=0,put2=0;
		int remove=0;
		for(Integer i:free.values()){
			if(i+valueGetKey(free, i)==p.benginAddress){
				put1 = p.size+valueGetKey(free, i);
				put2 = i;
				remove = valueGetKey(free, i);
			}
		}
		if(put1!=0&&put2!=0){
			free.put(put1, put2);
			free.remove(remove);
			lookFreeBusy();
			System.out.println("--------------------------");
			return ;
		}
		free.put(p.size,p.benginAddress);
		lookFreeBusy();
		System.out.println("--------------------------");
	}
	public void recycle(PCB p){
		//1.���ͷ�w2,���ͷ�w1,����ֱ߽�����~
		//2.���ͷ�w3,���ͷ�w4,����ֱ߽�����
		System.out.println("������"+p.name+"���ڴ����Ƴ�");
		//��p��ռ�ÿռ�,����free��
		busy.remove(valueGetKey(busy,p.benginAddress));
		if(p.benginAddress == beginAddress){
			//������յ��ǵ�һ��
			recycleFirst(p);
			return;
		}
		if(p.benginAddress +p.size ==beginAddress+size){
			//������յ������һ��
			recycleLast(p);
			return ;
		}	
		free.put(p.size, p.benginAddress);//�൱����ռ��ռ
		int put1=0 ,put2=0;
		int remove = 0;
		for(Integer i:free.values()){
			for(Integer j:busy.values()){
				if(i + valueGetKey(free,i)==p.benginAddress
						&&p.benginAddress+p.size==j){
					System.out.println("�Ͽ���ռ");
					put1 = p.size+valueGetKey(free,i);
					put2 = i;
					remove = i;
					break;
				}
				if(j+valueGetKey(busy,j)==p.benginAddress
						&&i==p.benginAddress+p.size){
					System.out.println("�¿���ռ");
					put1 = p.size+valueGetKey(free,i);
					put2 = p.benginAddress;
					remove = i;
					break;
				}
			}
		}
		if(put1!=0 && put2!=0){
			System.out.println("�ϲ�����");
			free.remove(valueGetKey(free,p.benginAddress));
			free.remove(valueGetKey(free,remove));
			free.put(put1, put2);
		}
		int remove1=0,remove2=0;
		for(Integer i:free.values()){
			if(i + valueGetKey(free,i)==p.benginAddress){
				System.out.println("�Ͽ��¿�");
				free.remove(valueGetKey(free,p.benginAddress));
				remove1 = i;
				remove2 = free.get(nextPSize(i));
				put1 = valueGetKey(free,i) +p.size
						+ nextPSize(i);
				put2 = i;
				free.remove(valueGetKey(free,remove1));
				free.remove(valueGetKey(free,remove2));
				free.put(put1, put2);
				break;
			}
		}
		lookFreeBusy();
		System.out.println("--------------------------");
	}
	public void updateFree(PCB p,int m){
		//m�Ǵ�����з�����С
		free.remove(valueGetKey(free, p.benginAddress));
		Integer putSize = m-p.size;
		if(putSize != 0)
			free.put(putSize, p.benginAddress+p.size);
	}
	public void updateBusy(PCB p){
		/*busy.put(p.size,p.benginAddress); 
		int put1 = 0,put2 =0;
		for(Integer i:free.values()){
			if(i == p.benginAddress){
				put1 = valueGetKey(busy, i);
				put2 = i;
			}
		}
		if(put2!=0){
			
		}*/
	}
	public void setAllP(PCB p){
		p.size = p.PCBsize;
		int address = 0;
		for(Integer i:free.values())
			//���ܼ�����,Ӧ���Ǽӵ�����ǰ~
			if(valueGetKey(free, i) >= p.size)
				p.benginAddress = i;
	}
	public void lookFreeBusy(){
		//���ռ�ñ�,���б�
		System.out.println("ռ�ñ�(��С,��ʼ��ַ):");
		System.out.println(busy);
		//���б�
		System.out.println("���б�(��С,��ʼ��ַ):");
		System.out.println(free);
	}
	private static int valueGetKey(Map map,int value) {
	    Set set = map.entrySet();//�½�һ�������ظ��ļ���
	    Iterator it = set.iterator();//��������
	    int s = 0;
	    while(it.hasNext()) {
	      Map.Entry entry = (Map.Entry)it.next();//�ҵ�����key-value�Լ���
	      if(entry.getValue().equals(value)) {//ͨ���ж��Ƿ��и�valueֵ
	        s = (int)entry.getKey();//ȡ��keyֵ
	      }
	    }
	    return s;
	 }
	private int nextPSize(int value){
		int key = valueGetKey(free, value);
		Set<Integer> s = free.keySet();
		int nextP = 0;
		for(Integer i:s){
			if(i>key){
				nextP = i;
				break;
			}
		}
		return nextP;
	}
}
