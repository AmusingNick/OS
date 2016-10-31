package lab5;

import java.util.Scanner;

public class AvoidDeadLock {
	int n;
	int m;
	int[] Available ;
	int[][] Max ;
	int[][] Allocation;
	int[][] Need;
	void Bander(){
		//init();
		falseInit();
		Scanner sc = new Scanner(System.in);
		if(isSafe()){
			//首次检查是否有安全序列：如果有，则可以开始银行家算法
			int bringUpProNum = 0;  //提出请求向量的进程标号
			int[] requestVetorPro = new int[3];  //提出的请求向量
			while(true){
				System.out.println("请输入请求向量的进程标号");
				bringUpProNum = sc.nextInt();
				if(bringUpProNum == -1){
					System.out.println("分配结束，拜拜");
					break;
				}
				System.out.println("请输入其提出的请求向量");
				for(int i=0;i<requestVetorPro.length;i++)
					requestVetorPro[i] = sc.nextInt();
				boolean flagNext = true;
				for(int i=0;i<3;i++)
					if(requestVetorPro[i] > Need[bringUpProNum][i]){
						System.out.println("所以需要的资源数超过它所宣布的最大值");
						flagNext = false;
					}
				if(flagNext == true){
					for(int i=0;i<3;i++)
						if(requestVetorPro[i] > Available[i]){
							System.out.println("尚无足够资源，pi需等待");
							flagNext = false;
						}
					//系统试探把资源分给进程pi，并修改下面的数据结构
					if(flagNext == true){
						for(int i=0;i<3;i++){
							Available[i] -= requestVetorPro[i];
							Allocation[bringUpProNum][i] += requestVetorPro[i];
							Need[bringUpProNum][i] -= requestVetorPro[i];
						}
						if(isSafe()){
							System.out.println("成功分配资源");
						}else{
							for(int i=0;i<3;i++){
								Available[i] += requestVetorPro[i];
								Allocation[bringUpProNum][i] -= requestVetorPro[i];
								Need[bringUpProNum][i] += requestVetorPro[i];
							}
							System.out.println("本次的试探分配作废，恢复原来的资源分配状态，让pi"
									+ "等待");
						}
					}
				}
			}
		}else
			//如果没有说明，输入此例(系统)本就不安全
			System.out.println("资源分配后系统处于不安全状态，"
						+ "系统不是安全的");
	}
	private void falseInit() {
		Available = new int[]{10,5,7};
		Max = new int[][]{{7,5,3},{3,2,2},{9,0,2},{2,2,2},{4,3,3}};
		Allocation = new int[][]{{0,1,0},{2,0,0},{3,0,2},{2,1,1},{0,0,2}};
		Need = new int[5][3];
		for(int i=0;i<5;i++)
			for(int j=0;j<3;j++)
				Need[i][j] = Max[i][j] - Allocation[i][j];
		for(int i=0;i<5;i++)
			for(int j=0;j<3;j++)
				Available[j] -= Allocation[i][j];
	}
	private void init() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入有n个进程");
		n = sc.nextInt();
		System.out.println("请输入有m个不同种资源");
		m = sc.nextInt();
		System.out.println("请输入各种资源的数量");
		Available = new int[m];
		for(int i=0;i<m;i++)
			Available[i] = sc.nextInt();
		Max = new int[n][m];
		Allocation = new int[n][m] ;
		Need = new int[n][m];
		for(int i=0;i<n;i++){
			System.out.println("请输入第"+(i)+"进程所需要的各项资源(Max)");
			for(int j=0;j<m;j++)
				Max[i][j] = sc.nextInt();
		}
		for(int i=0;i<n;i++){
			System.out.println("请输入第"+(i)+"已分资源(Allocation)");
			for(int j=0;j<m;j++)
				Allocation[i][j] = sc.nextInt();
		}
		for(int i=0;i<n;i++)
			for(int j=0;j<m;j++)
				Need[i][j] = Max[i][j] - Allocation[i][j];
		for(int i=0;i<n;i++)
			for(int j=0;j<m;j++)
				Available[j] -= Allocation[i][j];
		showInit();
	}
	boolean isSafe(){
		int[] Work = new int[3];
		boolean[] Finish = new boolean[5];
		for(int i=0;i<3;i++)
			Work[i] = Available[i];
		System.out.println("安全序列为：");
		System.out.print("\tMax  \t  Need      Allocation Work   \t\n");
		boolean[] allNeedRight = new boolean[3];
		do{
			for(int i=0;i<5;i++){
				for(int j=0;j<3;j++)
					if(Finish[i] == false && Need[i][j] <= Work[j])
						allNeedRight[j] = true;
					else
						break;
				if(isAllTrue(allNeedRight)){
					Finish[i] = true;
					for(int j=0;j<3;j++)
						Work[j] = Work[j] + Allocation[i][j];
					allNeedRight = new boolean[3];
					System.out.println();
					System.out.print("p:"+(i)+"\t");
					for(int k=0;k<3;k++)
						System.out.print(Available[k]+"  ");
					System.out.print("|");
					for(int k=0;k<3;k++)
						System.out.print(Need[i][k]+"  ");
					System.out.print("|");
					for(int k=0;k<3;k++)
						System.out.print(Allocation[i][k]+"  ");
					System.out.print("|");
					for(int k=0;k<3;k++)
						System.out.print(Work[k]+"  ");
				}
				else
					allNeedRight = new boolean[3];
			} 
			int[][] NotFinishNeeds = getNotFinishNeeds(Finish,Need);
			if( isNotFinishIsLowAvailable(NotFinishNeeds,Work) 
					&& !isAllTrue(Finish)){
				System.out.println("资源分配后系统处于不安全状态，本次试探分配作废"
						+ "pi等待(isSafe()函数)");
				break;
			}
		}while( !isAllTrue(Finish) );
		System.out.println();
		if(isAllTrue(Finish))
			return true;
		return false;
	}
	private boolean isNotFinishIsLowAvailable(int[][] notFinishNeeds, int[] work) {
		boolean[] allNeedRight = new boolean[3];
		for(int i=0;i<5;i++)
			for(int j=0;j<3;j++){
				if(notFinishNeeds[i][j] < work[j] && notFinishNeeds[i][j]!=-1)
					allNeedRight[j] = true;
				else 
					allNeedRight[j] = false;
				if(isAllTrue(allNeedRight))
					return false;
			}
		return true;
	}
	private int[][] getNotFinishNeeds(boolean[] finish, int[][] need2) {
		int[][] temps = new int[need2.length][need2[0].length];
		for(int i=0;i<5;i++)
			for(int j=0;j<3;j++)
				if(finish[i] == false )
					temps[i][j] = need2[i][j];
				else
					temps[i][j] = -1;
		return temps;
	}
	private boolean isAllTrue(boolean[] a) {
		for(int i=0;i<a.length;i++)
			if(a[i] == false)
				return false;
		return true;
	}
	void showInit(){
		System.out.println("Max");
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++)
				System.out.print(Max[i][j]+" ");
			System.out.println();
		}
		System.out.println("Allocation");
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++)
				System.out.print(Allocation[i][j]+" ");
			System.out.println();
		}
		System.out.println("Need");
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++)
				System.out.print(Need[i][j]+" ");
			System.out.println();
		}
		System.out.println("Available");
		for(int i=0;i<m;i++)
			System.out.println(Available[i]);
	}
	/*void showInitFalse(){
		System.out.println("\tMax\tNeed\tAllocation\t\n");
		for(int i=0;i<5;i++){
			System.out.print("进程"+(i)+" ");
			for(int j=0;j<3;j++){
				System.out.print(Max[i][j]+" ");
				System.out.print(Need[i][j]+" ");
				System.out.print(Allocation[i][j]+" ");
			}
			System.out.println();
		}
	}*/
	public static void main(String[] args) {
		new AvoidDeadLock().Bander();
	}
}
