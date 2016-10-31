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
			//�״μ���Ƿ��а�ȫ���У�����У�����Կ�ʼ���м��㷨
			int bringUpProNum = 0;  //������������Ľ��̱��
			int[] requestVetorPro = new int[3];  //�������������
			while(true){
				System.out.println("���������������Ľ��̱��");
				bringUpProNum = sc.nextInt();
				if(bringUpProNum == -1){
					System.out.println("����������ݰ�");
					break;
				}
				System.out.println("���������������������");
				for(int i=0;i<requestVetorPro.length;i++)
					requestVetorPro[i] = sc.nextInt();
				boolean flagNext = true;
				for(int i=0;i<3;i++)
					if(requestVetorPro[i] > Need[bringUpProNum][i]){
						System.out.println("������Ҫ����Դ�������������������ֵ");
						flagNext = false;
					}
				if(flagNext == true){
					for(int i=0;i<3;i++)
						if(requestVetorPro[i] > Available[i]){
							System.out.println("�����㹻��Դ��pi��ȴ�");
							flagNext = false;
						}
					//ϵͳ��̽����Դ�ָ�����pi�����޸���������ݽṹ
					if(flagNext == true){
						for(int i=0;i<3;i++){
							Available[i] -= requestVetorPro[i];
							Allocation[bringUpProNum][i] += requestVetorPro[i];
							Need[bringUpProNum][i] -= requestVetorPro[i];
						}
						if(isSafe()){
							System.out.println("�ɹ�������Դ");
						}else{
							for(int i=0;i<3;i++){
								Available[i] += requestVetorPro[i];
								Allocation[bringUpProNum][i] -= requestVetorPro[i];
								Need[bringUpProNum][i] += requestVetorPro[i];
							}
							System.out.println("���ε���̽�������ϣ��ָ�ԭ������Դ����״̬����pi"
									+ "�ȴ�");
						}
					}
				}
			}
		}else
			//���û��˵�����������(ϵͳ)���Ͳ���ȫ
			System.out.println("��Դ�����ϵͳ���ڲ���ȫ״̬��"
						+ "ϵͳ���ǰ�ȫ��");
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
		System.out.println("��������n������");
		n = sc.nextInt();
		System.out.println("��������m����ͬ����Դ");
		m = sc.nextInt();
		System.out.println("�����������Դ������");
		Available = new int[m];
		for(int i=0;i<m;i++)
			Available[i] = sc.nextInt();
		Max = new int[n][m];
		Allocation = new int[n][m] ;
		Need = new int[n][m];
		for(int i=0;i<n;i++){
			System.out.println("�������"+(i)+"��������Ҫ�ĸ�����Դ(Max)");
			for(int j=0;j<m;j++)
				Max[i][j] = sc.nextInt();
		}
		for(int i=0;i<n;i++){
			System.out.println("�������"+(i)+"�ѷ���Դ(Allocation)");
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
		System.out.println("��ȫ����Ϊ��");
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
				System.out.println("��Դ�����ϵͳ���ڲ���ȫ״̬��������̽��������"
						+ "pi�ȴ�(isSafe()����)");
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
			System.out.print("����"+(i)+" ");
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
