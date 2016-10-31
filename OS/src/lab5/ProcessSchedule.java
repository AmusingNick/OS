package lab5;

import java.util.ArrayDeque;

public class ProcessSchedule {
	static public Object[] FCFS(int[] ComeMomment,int []ServiceTime){
		//返回Object[0] 是开始时间  Object[1]是完结时间
		//Object[2] 是W（周转时间）  Object[3]是T （加权周转时间）  
		//Object[3] 平均周转时间  Object[4] 是 平均加权周转时间
		/*测试过:0,1,2,3,4测试过:1,2,3,4,5测试过:0,0,0,0,0  测试过:5,5,5,5,5
		测试过:0,5,6,7,4测试过:1,5,6,7,4测试过:0,5,5,5,2测试过:0,5,5,5,5
		测试过:1,5,5,5,5测试过:5,4,2,3,1   2,1,2,3,4,4,1,2     都行~~！！*/		
		int[] a = new int[ComeMomment.length];
		int[] tempA = new int[ComeMomment.length];
		int[] b = new int[ServiceTime.length];
		for(int i=0;i<a.length;i++){
			a[i] = ComeMomment[i];
			tempA[i] = ComeMomment[i];
			b[i] = ServiceTime[i];
		}
		int[] c = new int[a.length];
		int[] d = new int[a.length];
		int changed = a.length;
		int finT = a[0];
		int isChangingIndex = 0;   //根据这个修改c,d
		int first =0; 
		while(changed > 0){
			isChangingIndex = getChangeIndex(finT,a);//选择isChangingIndex函数
			if(first==0){
				c[isChangingIndex] = tempA[isChangingIndex];
				finT = b[isChangingIndex] + tempA[isChangingIndex];
				first++;
			}else{
				c[isChangingIndex] = finT;
				finT += b[isChangingIndex];
			}
			d[isChangingIndex] = finT;
			changed --;
		}
		int[] e  = (int[])getEFandAvg(tempA,d,b)[0];
		double[] f = (double[])getEFandAvg(tempA,d,b)[1];
		int avgE = (int)getEFandAvg(tempA,d,b)[2];
		double avgF = (double)getEFandAvg(tempA,d,b)[3];
		return new Object[]{c,d,e,f,avgE,avgF};
	}
	private static int getChangeIndex(int finT, int[] a) {
		int min = a[0];
		int w = 0;
		for(int i=0;i<a.length;i++)
			if(a[i] >= 0){
				w = i;
				min = a[i];
				break;
			}
		for(int i=w;i<a.length;i++){
			if(a[i] <= finT && a[i]>=0 && min >= a[i]){
				w = i;
				min = a[i];
			}
		}
		a[w] = -1;
		return w;
	}
	private static Object[] getEFandAvg(int[] tempA,int[] d,int[] b){
		int[] e = new int[d.length];
		double[] f = new double[d.length];
		for(int i=0;i<d.length;i++)
			e[i] = d[i] - tempA[i];
		for(int i=0;i<d.length;i++)
			f[i] = (double)e[i] / (double)b[i];
		int avgE=0,allE=0;
		double avgF=0,allF=0;
		for(int i = 0;i<d.length;i++){
			allE += e[i];
			allF += f[i];
		}
		avgE = allE/d.length;
		avgF = (double)allF/d.length;
		return new Object[]{e,f,avgE,avgF};
	}
	static public Object[] SJF(int[] ComeMomment,int []ServiceTime){
		int[] a = new int[ComeMomment.length];
		int[] tempA = new int[ComeMomment.length];
		int[] b = new int[ServiceTime.length];
		for(int i=0;i<a.length;i++){
			a[i] = ComeMomment[i];
			tempA[i] = ComeMomment[i];
			b[i] = ServiceTime[i];
		}
		int[] c = new int[a.length];
		int[] d = new int[a.length];
		int changed = a.length;
		int finT = a[0];
		for(int i=0;i<a.length;i++)
			if(finT > a[i])
				finT = a[i];
		int isChangingIndex = 0;   //根据这个修改c,d
		int first =0; 
		while(changed > 0){
			isChangingIndex = getChangeSJFIndex(finT,a,b);//选择isChangingIndex函数
			if(first==0){
				c[isChangingIndex] = tempA[isChangingIndex];
				finT = b[isChangingIndex] + tempA[isChangingIndex];
				first++;
			}else{
				c[isChangingIndex] = finT;
				finT += b[isChangingIndex];
			}
			d[isChangingIndex] = finT;
			changed --;
		}
		int[] e  = (int[])getEFandAvg(tempA,d,b)[0];
		double[] f = (double[])getEFandAvg(tempA,d,b)[1];
		int avgE = (int)getEFandAvg(tempA,d,b)[2];
		double avgF = (double)getEFandAvg(tempA,d,b)[3];
		return new Object[]{c,d,e,f,avgE,avgF};
	}
	private static int getChangeSJFIndex(int finT, int[] a,int[] b) {
		//0,1,2,3,4  4,3,5,2,4  //1,2,3,4,5  2,2,1,1,2
		//1,2,3,4,5  1,1,1,1,1  //0,0,0,0,0  3,2,1,1,2 
		//5,4,3,2,1  1,2,3,4,5  //2,1,2,3,4,4,1,2 1,4,5,2,3,2,2,6  都没问题
		boolean[] flagChange = new boolean[a.length];
		int minBi = 0;
		for(int i=0;i<a.length;i++)
			if(a[i] <= finT && a[i] >= 0)
				flagChange[i] = true;
		for(int i=0;i<a.length;i++)
			if(flagChange[i]){
				minBi = b[i];
				break;
			} 
		int w = 0;
		for(int i=0;i<a.length;i++)
			if(minBi >= b[i] && flagChange[i] && a[i] >= 0){
				w = i;
				minBi = b[i];
			}
		a[w] = -1;
		return w;
	}
	static public Object[] RR1(int[] ComeMomment,int []ServiceTime){
		int[] a = new int[ComeMomment.length];
		int[] tempA = new int[ComeMomment.length];
		int[] b = new int[ServiceTime.length];
		int[] c = new int[a.length];
		int[] d = new int[a.length];
		for(int i=0;i<a.length;i++){
			a[i] = ComeMomment[i];
			tempA[i] = a[i];
			b[i] = ServiceTime[i];
			c[i] = a[i];
		}
		int finT = 0;
		ArrayDeque<Integer> countTime = new ArrayDeque<>();
		ArrayDeque<Integer> forWhileCount = new ArrayDeque<>();
		for(int i=0;i<a.length;i++)
			forWhileCount.offer(a[i]);
		for(int i=0;i<a.length;i++)
			countTime.offer(b[i]);
		do{ 
			finT ++;
			int temp = countTime.poll() - 1;
			if(temp != 0){
				countTime.offer(temp);
				int aI = forWhileCount.poll();
				forWhileCount.offer(aI);
			}
			else
				d[forWhileCount.poll()] = finT;
		}while(countTime.peek() != null);
		int[] e  = (int[])getEFandAvg(tempA,d,b)[0];
		double[] f = (double[])getEFandAvg(tempA,d,b)[1];
		int avgE = (int)getEFandAvg(tempA,d,b)[2];
		double avgF = (double)getEFandAvg(tempA,d,b)[3];
		return new Object[]{c,d,e,f,avgE,avgF};
	}
	static public Object[] RR2(int[] ComeMomment,int []ServiceTime){
		int[] a = new int[ComeMomment.length];
		int[] tempA = new int[ComeMomment.length];
		int[] b = new int[ServiceTime.length];
		int[] c = new int[a.length];
		int[] d = new int[a.length];
		for(int i=0;i<a.length;i++){
			a[i] = ComeMomment[i];
			tempA[i] = a[i];
			b[i] = ServiceTime[i];
			c[i] = a[i]*2;
		}
		int finT = 0;
		ArrayDeque<Integer> countTime = new ArrayDeque<>();
		ArrayDeque<Integer> forWhileCount = new ArrayDeque<>();
		for(int i=0;i<a.length;i++)
			forWhileCount.offer(a[i]);
		for(int i=0;i<a.length;i++)
			countTime.offer(b[i]);
		do{ 
			finT = finT + 2;
			int temp = countTime.poll() - 2;
			if(temp > 0){
				countTime.offer(temp);
				int aI = forWhileCount.poll();
				forWhileCount.offer(aI);
			}
			else{
				if(temp < 0)
					finT--;
				d[forWhileCount.poll()] = finT;
			}
		}while(countTime.peek() != null);
		int[] e  = (int[])getEFandAvg(tempA,d,b)[0];
		double[] f = (double[])getEFandAvg(tempA,d,b)[1];
		int avgE = (int)getEFandAvg(tempA,d,b)[2];
		double avgF = (double)getEFandAvg(tempA,d,b)[3];
		return new Object[]{c,d,e,f,avgE,avgF};
	}
	public static void main(String[] args) {
		//0,1,2,3,4 4,3,5,2,4  
		int[] ComeMomment = new int[]{0,1,2,3,4};
		int[] ServiceTime = new int[]{4,3,5,2,4};
		ProcessSchedule.RR2(ComeMomment, ServiceTime);
	}
}
