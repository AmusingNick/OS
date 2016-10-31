package lab2;

import java.util.Random;

public class BitMap {
	//定义的一个代表64k的位视图
	int[][] bm = new int[8][8];
	BitMap(){
		Random rd = new Random();
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				bm[i][j] = rd.nextInt(2);
		//控制台显示
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.print(bm[i][j]);
			}
			System.out.println();
		}
	}
	public int[] getSequence(int size){
		//通过初始化的页面大小,获得位视图的空闲空间
		int[]a = new int[size];
		int w = 0;
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				if(bm[i][j] == 0 && w <= size){
					a[w++] = i*8+j;
				}
				if(w==size)
					return a;
			}
		return a;
	}
	/*public static void main(String[] args) {
		BitMap bmm = new BitMap();
		int[] test = bmm.getSequence(6);
		for(int i=0;i<test.length;i++)
			System.out.print(test[i]+"\t");
	}*/
}
