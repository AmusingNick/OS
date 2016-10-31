package lab1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class RAM {
	//可变分区的内存
	static final int size = 400;   //内存共计大小
	static final int beginAddress = 100;  //内存起始地址
	//key代表大小,value代表起始地址
	Map<Integer,Integer> free = new TreeMap<>(); 
	//空闲表:初始状态:400,100
	Map<Integer,Integer> busy = new TreeMap<>(); 
	//占用表:初始状态:0,0
	//分配采用最佳适应算法，碎片大小为2Kb,最后回收所有进程的空间。可以查看进程所占的空间和系统空闲空间。
	RAM(){
		free.put(400, 100);
	}
	public boolean BF(PCB p){
		/*
		 * 传入PCB将他根据BF算法安放到对应内存中
		 * */
		System.out.println("添加进程"+p.name+"到内存");
		for(Integer m:free.keySet()){
			if(m >= p.PCBsize){
				System.out.println("这个分区大小能装的下");
				setAllP(p);//完善p信息
				updateBusy(p);
				busy.put(p.size,p.benginAddress); //占用表更新 
				updateFree(p,m);//空闲表更新:将占用的剔除
				break;
			}
			System.out.println("这个分区不符合");
		}
		lookFreeBusy();
		return false;
	}
	private void recycleFirst(PCB p){
		System.out.println("回收第一个");
		if(free.get(nextPSize(p.benginAddress)) != null){
			if(free.get(nextPSize(p.benginAddress))
					==p.size+p.benginAddress){
				//下面是空的
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
		System.out.println("回收最后一个");
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
		//1.先释放w2,在释放w1,会出现边界问题~
		//2.先释放w3,在释放w4,会出现边界问题
		System.out.println("将进程"+p.name+"从内存中移除");
		//将p的占用空间,更新free表
		busy.remove(valueGetKey(busy,p.benginAddress));
		if(p.benginAddress == beginAddress){
			//如果回收的是第一个
			recycleFirst(p);
			return;
		}
		if(p.benginAddress +p.size ==beginAddress+size){
			//如果回收的是最后一个
			recycleLast(p);
			return ;
		}	
		free.put(p.size, p.benginAddress);//相当于上占下占
		int put1=0 ,put2=0;
		int remove = 0;
		for(Integer i:free.values()){
			for(Integer j:busy.values()){
				if(i + valueGetKey(free,i)==p.benginAddress
						&&p.benginAddress+p.size==j){
					System.out.println("上空下占");
					put1 = p.size+valueGetKey(free,i);
					put2 = i;
					remove = i;
					break;
				}
				if(j+valueGetKey(busy,j)==p.benginAddress
						&&i==p.benginAddress+p.size){
					System.out.println("下空上占");
					put1 = p.size+valueGetKey(free,i);
					put2 = p.benginAddress;
					remove = i;
					break;
				}
			}
		}
		if(put1!=0 && put2!=0){
			System.out.println("合并分区");
			free.remove(valueGetKey(free,p.benginAddress));
			free.remove(valueGetKey(free,remove));
			free.put(put1, put2);
		}
		int remove1=0,remove2=0;
		for(Integer i:free.values()){
			if(i + valueGetKey(free,i)==p.benginAddress){
				System.out.println("上空下空");
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
		//m是传入空闲分区大小
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
			//不能加所有,应该是加到分区前~
			if(valueGetKey(free, i) >= p.size)
				p.benginAddress = i;
	}
	public void lookFreeBusy(){
		//输出占用表,空闲表
		System.out.println("占用表(大小,起始地址):");
		System.out.println(busy);
		//空闲表
		System.out.println("空闲表(大小,起始地址):");
		System.out.println(free);
	}
	private static int valueGetKey(Map map,int value) {
	    Set set = map.entrySet();//新建一个不可重复的集合
	    Iterator it = set.iterator();//遍历的类
	    int s = 0;
	    while(it.hasNext()) {
	      Map.Entry entry = (Map.Entry)it.next();//找到所有key-value对集合
	      if(entry.getValue().equals(value)) {//通过判断是否有该value值
	        s = (int)entry.getKey();//取得key值
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
