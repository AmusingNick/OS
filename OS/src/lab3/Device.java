package lab3;

import java.util.ArrayDeque;
import java.util.Scanner;

class SDT{
	//ϵͳ�豸��SDT ����,����,��DCT
	String name;
	String type;
	DCT dct;
}
class DCT{
	//�豸���Ʊ�DCT ����,����,״̬(æ������),����,��COCT
	String name;
	String type;
	boolean isBusy = false;
	ArrayDeque<PCB> ad = new ArrayDeque<>();
	COCT coct;
}
class COCT{
	//���������Ʊ�COCT ����,״̬,����,��CHCT
	String name;
	boolean isBusy = false;
	ArrayDeque<PCB> ad = new ArrayDeque<>();
	CHCT chct;
}
class CHCT{
	//ͨ�����Ʊ�CHCT ����,״̬,����
	String name;
	boolean isBusy = false;
	ArrayDeque<PCB> ad = new ArrayDeque<>();
}
//�����PCBָ�����������io��PCB
class PCB {
	String name;  //������
	String IOName; //�����io������
	boolean isWaiting;  //�ý�������״̬
	String whereAd;  //�ý��̱�����I/O�ĵȴ������е�����(û�о�����)
}


public class Device {
	CHCT ch1 ;
	CHCT ch2 ;
	CHCT[] chcts = new CHCT[2];
	COCT co1 ; 
	COCT co2 ; 
	COCT co3 ; 
	COCT[] cocts = new COCT[10];
	int numberCOCT = 3;
	DCT d1 ;
	DCT d2 ;
	DCT d3 ;
	DCT d4 ;
	DCT[] dcts = new DCT[10];
	int numberDCT = 4;
	SDT K;
	SDT M;
	SDT T;
	SDT P;
	SDT[] sdts = new SDT[10];
	int numberSDT = 4;
	
	PCB[] allPCBs = new PCB[30];
	int sizePCB=0;
	Scanner sc = new Scanner(System.in);
	//��ʼ��
	void init(){
		//�豸�ĳ�ʼ��
		ch1 = new CHCT();
		ch1.name = "ch1";
		ch1.isBusy = false;
		ch2 = new CHCT();
		ch2.name = "ch2";
		ch2.isBusy = false;
		chcts[0] = ch1;
		chcts[1] = ch2;
		
		co1 = new COCT();
		co1.name = "co1";
		co1.isBusy = false;
		co1.chct = ch1; 
		co2 = new COCT();
		co2.name = "co2";
		co2.isBusy = false;
		co2.chct = ch2;
		co3 = new COCT();
		co3.name = "co3";
		co3.isBusy = false;
		co3.chct = ch2;
		cocts[0] = co1;
		cocts[1] = co2;
		cocts[2] = co3;
		
		d1 = new DCT();
		d1.name = "d1";
		d1.type = "K";
		d1.isBusy = false;
		d1.coct = co1;
		d2 = new DCT();
		d2.name = "d2";
		d2.type = "M";
		d2.isBusy = false;
		d2.coct = co1;
		d3 = new DCT();
		d3.name = "d3";
		d3.type = "T";
		d3.isBusy = false;
		d3.coct = co2;
		d4 = new DCT();
		d4.name = "d4";
		d4.type = "P";
		d4.isBusy = false;
		d4.coct = co3;
		dcts[0] = d1;
		dcts[1] = d2;
		dcts[2] = d3;
		dcts[3] = d4;
		
		//����
		K = new SDT();
		K.name = "K";
		K.type = "I";
		K.dct = d1;
		//���
		M = new SDT();
		M.name = "M";
		M.type = "I";
		M.dct = d2;
		//��ʾ��
		T = new SDT();
		T.name = "T";
		T.type = "O";
		T.dct = d3;
		//��ӡ��
		P = new SDT();
		P.name = "P";
		P.type = "O";
		P.dct = d4;
		sdts[0] = K;
		sdts[1] = M;
		sdts[2] = T;
		sdts[3] = P;
	}
	//�˵�
	void cat(){
		System.out.println("1.�豸����");
		System.out.println("2.��������");
		System.out.println("3.����豸");
		System.out.println("4.ɾ���豸");
		System.out.println("5.�����豸������");
		System.out.println("0.�˳�");
	}
	void whileCat(){
		init();  //�豸��ʼ��
		cat();   //�˵���ʼ��
		boolean flag = true;
		boolean flag1 = true;
		boolean flag2 = true;
		String cin = sc.next();
		while(flag){
			switch(cin){
			case "1": 
					while(flag1){
						deviceDistribution();
						System.out.println("��S��������,�����˳�");
						String s = sc.next();
						if(!s.equals("S"))
							flag1 = false;
					}
					break;
			case "2": 
					while(flag2){
						recycle();
						System.out.println("��R��������,�����˳�");
						String s = sc.next();
						if(!s.equals("R"))
							flag2 = false;
					}
					break;
			case "3": addDevice(); break;
			case "4": deleteDevice(); break;
			case "5": showIndependence(); break;
			case "0": flag = false;
			}
			if(flag){
				cat();
				cin = sc.next();
				flag1 = true;
				flag2 = true;
			}
		}
	}
	
	
	void deviceDistribution(){
		System.out.println("�������豸����ָ��(S-������-����IO):");
		String s = sc.next();
		String PCBName = s.split("-")[1];
		String IO = s.split("-")[2];
		PCB p = new PCB();
		p.name = PCBName;
		p.IOName = IO;
		SDT findSDT = new SDT();
		for(int i=0;i<numberSDT;i++)
			if(sdts[i].name.equals(p.IOName))
				findSDT = sdts[i];
		if(findSDT.name.equals(""))
			//�ж��Ƿ�������豸
			System.out.println("û�и��豸");
		else{
			if(findSDT.dct.isBusy){
				System.out.println("���豸��DCT״̬λΪ1,��æ");
				System.out.println("��֮����DCT�ĵȴ�������");
				p.isWaiting = true;
				p.whereAd = "DCT";
				allPCBs[sizePCB++] = p;
				findSDT.dct.ad.offer(p);
			}
			else{
				System.out.println("DCT״̬λ��1");
				findSDT.dct.isBusy = true;
				if(findSDT.dct.coct.isBusy){
					System.out.println("���豸��COCT״̬λΪ1,��æ");
					System.out.println("��֮����COCT�ĵȴ�������");
					p.isWaiting = true;
					p.whereAd = "CO";
					allPCBs[sizePCB++] = p;
					findSDT.dct.coct.ad.offer(p);
				}
				else{
					System.out.println("COCT״̬λ��1");
					findSDT.dct.coct.isBusy = true;
					if(findSDT.dct.coct.chct.isBusy){
						System.out.println("���豸��CHCT״̬λΪ1,��æ");
						System.out.println("��֮����CHCT�ĵȴ�������");
						p.isWaiting = true;
						p.whereAd = "CH";
						allPCBs[sizePCB++] = p;
						findSDT.dct.coct.chct.ad.offer(p);
					}else{
						System.out.println("CHCT״̬λ��1");
						findSDT.dct.coct.chct.isBusy = true;
						p.isWaiting = false;
						p.whereAd = "";
						allPCBs[sizePCB++] = p;
						System.out.println("�ý��̷��䵽��IO�豸��������Ϊִ�н���");
					}
				}
			}
		}
		showWaiting();
	}
	void recycle(){
		System.out.println("���������ָ��(R-������-�ͷ��豸):");
		String s = sc.next();
		String PCBName = s.split("-")[1];
		String IO = s.split("-")[2];
		SDT findSDT = new SDT();
		PCB recycleP = null;
		PCB tempP = null;
		for(int i=0;i<numberSDT;i++)
			if(sdts[i].name.equals(IO))
				findSDT = sdts[i];
		if(findSDT.name.equals(""))
			//�ж��Ƿ�������豸
			System.out.println("û�и��豸");
		else{
			for(int i=0;i<sizePCB;i++)
				if(allPCBs[i].name.equals(PCBName)){
					recycleP = allPCBs[i];
				}
			if(recycleP == null){
				System.out.println("�޸ý��̼�¼");
			}
			else{
				if(recycleP.whereAd.equals("DCT")){
					findSDT.dct.ad.remove(recycleP);
					if(findSDT.dct.ad.isEmpty())
						findSDT.dct.isBusy = false;
					System.out.println("�ý�����DCT��Wainting������,�ͷųɹ�");
				}
				if(recycleP.whereAd.equals("CO")){
					findSDT.dct.coct.ad.remove(recycleP);
					if(findSDT.dct.coct.ad.isEmpty()){
						for(int i=0;i<numberDCT;i++){
							if(!dcts[i].ad.isEmpty() && dcts[i].coct.chct
									.name.equals(findSDT.dct.coct.name)){
								tempP = dcts[i].ad.poll();
								tempP.whereAd = "CO";
								findSDT.dct.coct.ad.offer(tempP);
								break;
							}
						}
						if(tempP == null){
							findSDT.dct.coct.isBusy = false;
							findSDT.dct.isBusy = false;
						}
					}
					System.out.println("�ý�����COCT��Wainting������,�ͷųɹ�");
				}
				if(recycleP.whereAd.equals("CH")||recycleP.whereAd.equals("")){
					if(findSDT.dct.coct.chct.ad.contains(recycleP)){
						//System.out.println("�������p");
						findSDT.dct.coct.chct.ad.remove(recycleP);
						if(!findSDT.dct.coct.chct.ad.isEmpty())
							findSDT.dct.coct.chct.ad.poll();
						if(!findSDT.dct.coct.chct.isBusy){
							if(findSDT.dct.coct.chct.ad.isEmpty()){
								COCT coctTemp = null;
								for(int i=0;i<numberCOCT;i++){
									if(!cocts[i].ad.isEmpty() && cocts[i].chct
											.name.equals(findSDT.dct.coct.chct.name)){
										tempP = cocts[i].ad.poll();
										tempP.whereAd = "CH";
										findSDT.dct.coct.chct.ad.offer(tempP);
										coctTemp = cocts[i];
										break;
									}
								}
								if(coctTemp == null){
									//COCT������û����,������DCT
									DCT dctTemp = null;
									for(int i=0;i<numberDCT;i++){
										if(!dcts[i].ad.isEmpty() && dcts[i].coct.chct
												.name.equals(findSDT.dct.coct.chct.name)){
											tempP = dcts[i].ad.poll();
											tempP.whereAd = "CH";
											findSDT.dct.coct.chct.ad.offer(tempP);
											dctTemp = dcts[i];
											break;
										}
									}
									if(dctTemp == null){
										findSDT.dct.coct.chct.isBusy = false;
										findSDT.dct.coct.isBusy = false;
										findSDT.dct.isBusy = false;
									}
								}
								if(coctTemp != null){
									if(coctTemp.ad.isEmpty()){
										for(int i=0;i<numberDCT;i++){
											if(!dcts[i].ad.isEmpty() && dcts[i].coct
													.name.equals(coctTemp.name)){
												tempP = dcts[i].ad.poll();
												tempP.whereAd = "CO";
												coctTemp.ad.offer(tempP);
												break;
											}
										}
									}
								}
							}
						}
					}
					if(!findSDT.dct.coct.chct.ad.contains(recycleP)){
						//System.out.println("���������p,˵�����p����ռ��");
						if(!findSDT.dct.coct.chct.ad.isEmpty())
							findSDT.dct.coct.chct.ad.poll();
						if(findSDT.dct.coct.chct.ad.isEmpty()){
							COCT coctTemp = null;
							for(int i=0;i<numberCOCT;i++){
								if(!cocts[i].ad.isEmpty() && cocts[i].chct
										.name.equals(findSDT.dct.coct.chct.name)){
									tempP = cocts[i].ad.poll();
									tempP.whereAd = "CH";
									findSDT.dct.coct.chct.ad.offer(tempP);
									coctTemp = cocts[i];
									break;
								}
							}
							if(coctTemp == null){
								//COCT������û����,������DCT
								DCT dctTemp = null;
								for(int i=0;i<numberDCT;i++){
									if(!dcts[i].ad.isEmpty() && dcts[i].coct.chct
											.name.equals(findSDT.dct.coct.chct.name)){
										tempP = dcts[i].ad.poll();
										tempP.whereAd = "CH";
										findSDT.dct.coct.chct.ad.offer(tempP);
										dctTemp = dcts[i];
										break;
									}
								}
								if(dctTemp == null){
									findSDT.dct.coct.chct.isBusy = false;
									findSDT.dct.coct.isBusy = false;
									findSDT.dct.isBusy = false;
								}
							}
							if(coctTemp != null){
								if(coctTemp.ad.isEmpty()){
									for(int i=0;i<numberDCT;i++){
										if(!dcts[i].ad.isEmpty() && dcts[i].coct
												.name.equals(coctTemp.name)){
											tempP = dcts[i].ad.poll();
											tempP.whereAd = "CO";
											coctTemp.ad.offer(tempP);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		showWaiting();
	}
	void addDevice(){
		System.out.println("����������豸ָ��(A-�豸��-�豸����):");
		String s = sc.next();
		String IOName = s.split("-")[1];
		String IOType = s.split("-")[2];
		SDT newSDT = new SDT();
		DCT newDCT = new DCT();
		newSDT.name = IOName;
		newSDT.type = IOType;
		newSDT.dct = newDCT;
		newDCT.name = "d" + (numberDCT+1);
		newDCT.type = IOType;
		newDCT.isBusy = false;
		COCT getCOCT = null;
		for(int i=0;i<numberDCT;i++)
			if(dcts[i].type.equals(newSDT.dct.type))
				getCOCT = dcts[i].coct;
		if(getCOCT == null){
			getCOCT = new COCT();
			getCOCT.name = "co" + (numberCOCT+1);
			getCOCT.isBusy = false;
			getCOCT.chct = ch1;
			cocts[numberCOCT++] = getCOCT;
		}
		newDCT.coct = getCOCT;
		sdts[numberSDT++] = newSDT;	
		dcts[numberDCT++] = newDCT;
		showAllDevice();
	}
	void deleteDevice(){
		//���ڳ��ֵ�bug��Ҫɾ��D-k1,����ɾ����D-K2,
		//��������
		System.out.println("�������豸ɾ��ָ��(D-�豸��):");
		String s = sc.next();
		String IOName = s.split("-")[1];
		SDT findSDT = new SDT();
		for(int i=0;i<numberSDT;i++)
			if(sdts[i] != null &&sdts[i].name.equals(IOName))
				findSDT = sdts[i];
		findSDT.dct.coct.chct = null; //ȡ��ͨ������
		boolean isOtherHave = false; //ȷ���Ƿ��б���豸ָ�����CO
		
		for(int i=0;i<numberDCT;i++)
			if(dcts[i] != null && dcts[i].coct.name.equals(findSDT.dct.coct.name)
					&& !dcts[i].name.equals(findSDT.dct.name)){
				//�б���豸ָ����
				findSDT.dct.coct = null;
				isOtherHave = true;
				break;
			}
		if(!isOtherHave){
			for(int i=0;i<numberCOCT;i++)
				if(cocts[i] != null && cocts[i].name.equals(findSDT.dct.coct.name))
					cocts[i] = null;
			findSDT.dct.coct = null;
		}
		for(int i=0;i<numberDCT;i++)
			if(dcts[i] != null && dcts[i].name.equals(findSDT.dct.name))
				dcts[i] = null;
		for(int i=0;i<numberSDT;i++)
			if(sdts[i] != null && sdts[i].name.equals(findSDT.name))
				sdts[i] = null;
		findSDT.dct = null;
		findSDT = null;
		showAllDevice();
	}
	void showWaiting(){
		System.out.println("��ǰ������Wainting����:");
		for(int i=0;i<chcts.length;i++)
			for(PCB p:chcts[i].ad)	
				System.out.print(chcts[i].name+"��Waiting����:"+p.name+" ");
		for(int i=0;i<cocts.length;i++)
			if(cocts[i] != null)
				for(PCB p:cocts[i].ad)
						System.out.print(cocts[i].name+"��Waiting����:"+p.name+" ");
		for(int i=0;i<dcts.length;i++)
			if(dcts[i] != null)
				for(PCB p:dcts[i].ad)
						System.out.print(dcts[i].name+"��Waiting����:"+p.name+" ");
		System.out.println();
	}
	void showAllDevice(){
		System.out.println("��ǰ�������豸:");
		for(int i=0;i<chcts.length;i++)
			System.out.print(chcts[i].name+"\t");
		for(int i=0;i<numberCOCT;i++)
			if(cocts[i] != null)
				System.out.print(cocts[i].name+"\t");
		for(int i=0;i<numberDCT;i++)
			if(dcts[i] != null)
				System.out.print(dcts[i].name+"\t");
		for(int i=0;i<numberSDT;i++)
			if(sdts[i] != null)
				System.out.print(sdts[i].name+"\t");
		System.out.println();
	}
	void showIndependence(){
		for(int i=0;i<numberSDT;i++){
			System.out.print("�豸����:"+sdts[i].name+"\t");
			System.out.println("�豸����:"+sdts[i].type);
		}
	}
	public static void main(String[] args) {
		//�˵�Bug
		Device d = new Device();
		(d).whileCat();
	}
}
