package lab3;

import java.util.ArrayDeque;
import java.util.Scanner;

class SDT{
	//系统设备表SDT 名称,类型,与DCT
	String name;
	String type;
	DCT dct;
}
class DCT{
	//设备控制表DCT 名称,类型,状态(忙还是闲),队列,与COCT
	String name;
	String type;
	boolean isBusy = false;
	ArrayDeque<PCB> ad = new ArrayDeque<>();
	COCT coct;
}
class COCT{
	//控制器控制表COCT 名称,状态,队列,与CHCT
	String name;
	boolean isBusy = false;
	ArrayDeque<PCB> ad = new ArrayDeque<>();
	CHCT chct;
}
class CHCT{
	//通道控制表CHCT 名称,状态,队列
	String name;
	boolean isBusy = false;
	ArrayDeque<PCB> ad = new ArrayDeque<>();
}
//这里的PCB指的是提出请求io的PCB
class PCB {
	String name;  //进程名
	String IOName; //提出的io请求名
	boolean isWaiting;  //该进程所属状态
	String whereAd;  //该进程被挂在I/O的等待队列中的名字(没有就是无)
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
	//初始化
	void init(){
		//设备的初始化
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
		
		//键盘
		K = new SDT();
		K.name = "K";
		K.type = "I";
		K.dct = d1;
		//鼠标
		M = new SDT();
		M.name = "M";
		M.type = "I";
		M.dct = d2;
		//显示器
		T = new SDT();
		T.name = "T";
		T.type = "O";
		T.dct = d3;
		//打印机
		P = new SDT();
		P.name = "P";
		P.type = "O";
		P.dct = d4;
		sdts[0] = K;
		sdts[1] = M;
		sdts[2] = T;
		sdts[3] = P;
	}
	//菜单
	void cat(){
		System.out.println("1.设备分配");
		System.out.println("2.回收流程");
		System.out.println("3.添加设备");
		System.out.println("4.删除设备");
		System.out.println("5.体现设备独立性");
		System.out.println("0.退出");
	}
	void whileCat(){
		init();  //设备初始化
		cat();   //菜单初始化
		boolean flag = true;
		boolean flag1 = true;
		boolean flag2 = true;
		String cin = sc.next();
		while(flag){
			switch(cin){
			case "1": 
					while(flag1){
						deviceDistribution();
						System.out.println("按S继续分配,否则退出");
						String s = sc.next();
						if(!s.equals("S"))
							flag1 = false;
					}
					break;
			case "2": 
					while(flag2){
						recycle();
						System.out.println("按R继续回收,否则退出");
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
		System.out.println("请输入设备分配指令(S-进程名-请求IO):");
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
			//判断是否有这个设备
			System.out.println("没有该设备");
		else{
			if(findSDT.dct.isBusy){
				System.out.println("该设备的DCT状态位为1,正忙");
				System.out.println("将之挂在DCT的等待队列中");
				p.isWaiting = true;
				p.whereAd = "DCT";
				allPCBs[sizePCB++] = p;
				findSDT.dct.ad.offer(p);
			}
			else{
				System.out.println("DCT状态位置1");
				findSDT.dct.isBusy = true;
				if(findSDT.dct.coct.isBusy){
					System.out.println("该设备的COCT状态位为1,正忙");
					System.out.println("将之挂在COCT的等待队列中");
					p.isWaiting = true;
					p.whereAd = "CO";
					allPCBs[sizePCB++] = p;
					findSDT.dct.coct.ad.offer(p);
				}
				else{
					System.out.println("COCT状态位置1");
					findSDT.dct.coct.isBusy = true;
					if(findSDT.dct.coct.chct.isBusy){
						System.out.println("该设备的CHCT状态位为1,正忙");
						System.out.println("将之挂在CHCT的等待队列中");
						p.isWaiting = true;
						p.whereAd = "CH";
						allPCBs[sizePCB++] = p;
						findSDT.dct.coct.chct.ad.offer(p);
					}else{
						System.out.println("CHCT状态位置1");
						findSDT.dct.coct.chct.isBusy = true;
						p.isWaiting = false;
						p.whereAd = "";
						allPCBs[sizePCB++] = p;
						System.out.println("该进程分配到了IO设备由阻塞变为执行进程");
					}
				}
			}
		}
		showWaiting();
	}
	void recycle(){
		System.out.println("请输入回收指令(R-进程名-释放设备):");
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
			//判断是否有这个设备
			System.out.println("没有该设备");
		else{
			for(int i=0;i<sizePCB;i++)
				if(allPCBs[i].name.equals(PCBName)){
					recycleP = allPCBs[i];
				}
			if(recycleP == null){
				System.out.println("无该进程记录");
			}
			else{
				if(recycleP.whereAd.equals("DCT")){
					findSDT.dct.ad.remove(recycleP);
					if(findSDT.dct.ad.isEmpty())
						findSDT.dct.isBusy = false;
					System.out.println("该进程在DCT的Wainting队列中,释放成功");
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
					System.out.println("该进程在COCT的Wainting队列中,释放成功");
				}
				if(recycleP.whereAd.equals("CH")||recycleP.whereAd.equals("")){
					if(findSDT.dct.coct.chct.ad.contains(recycleP)){
						//System.out.println("包含这个p");
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
									//COCT队列下没东西,检索下DCT
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
						//System.out.println("不包含这个p,说明这个p正在占用");
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
								//COCT队列下没东西,检索下DCT
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
		System.out.println("请输入添加设备指令(A-设备名-设备类型):");
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
		//现在出现的bug是要删除D-k1,但是删的是D-K2,
		//次序有用
		System.out.println("请输入设备删除指令(D-设备名):");
		String s = sc.next();
		String IOName = s.split("-")[1];
		SDT findSDT = new SDT();
		for(int i=0;i<numberSDT;i++)
			if(sdts[i] != null &&sdts[i].name.equals(IOName))
				findSDT = sdts[i];
		findSDT.dct.coct.chct = null; //取消通道关联
		boolean isOtherHave = false; //确定是否有别的设备指向这个CO
		
		for(int i=0;i<numberDCT;i++)
			if(dcts[i] != null && dcts[i].coct.name.equals(findSDT.dct.coct.name)
					&& !dcts[i].name.equals(findSDT.dct.name)){
				//有别的设备指向他
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
		System.out.println("当前的所有Wainting队列:");
		for(int i=0;i<chcts.length;i++)
			for(PCB p:chcts[i].ad)	
				System.out.print(chcts[i].name+"的Waiting队列:"+p.name+" ");
		for(int i=0;i<cocts.length;i++)
			if(cocts[i] != null)
				for(PCB p:cocts[i].ad)
						System.out.print(cocts[i].name+"的Waiting队列:"+p.name+" ");
		for(int i=0;i<dcts.length;i++)
			if(dcts[i] != null)
				for(PCB p:dcts[i].ad)
						System.out.print(dcts[i].name+"的Waiting队列:"+p.name+" ");
		System.out.println();
	}
	void showAllDevice(){
		System.out.println("当前的所有设备:");
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
			System.out.print("设备名称:"+sdts[i].name+"\t");
			System.out.println("设备类型:"+sdts[i].type);
		}
	}
	public static void main(String[] args) {
		//菜单Bug
		Device d = new Device();
		(d).whileCat();
	}
}
