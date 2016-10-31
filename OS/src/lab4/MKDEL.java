package lab4;

import java.util.ArrayDeque;

import javax.swing.JTextArea;

//空闲盘块
class FreeStack1{
	int size;
	ArrayDeque<Integer> adI = new ArrayDeque();
	ArrayDeque<FreeStack2> adF = new ArrayDeque();
	JTextArea jta;
 	void init(JTextArea jta){
 		this.jta = jta;
 		size = 10;
 		for(int i=0;i<size;i++)
 			adI.offer(300-i);
 		FreeStack2 fs2 = new FreeStack2();
 		adF.offer(fs2);
	}
 	void show(){
 		jta.append("fs1栈的大小："+size+"\n");
 		//System.out.println("fs1栈的大小："+size);
 		for(Integer adIi:adI)
 			jta.append(adIi+"\n");
 			//System.out.println(adIi);
 		adF.getFirst().show();
 	}
}
class FreeStack2{
	int size;
	ArrayDeque<Integer> adI = new ArrayDeque();
	ArrayDeque<FreeStack3> adF = new ArrayDeque();
	JTextArea jta;
	void init(JTextArea jta){
		this.jta = jta;
		size = 10;
		FreeStack3 fs3 = new FreeStack3();
		adF.offer(fs3);
		for(int i=0;i<size;i++)
			adI.offer(400-i);
	}
	void show(){
		jta.append("fs2栈的大小："+size+"\n");
 		for(Integer adIi:adI)
 			jta.append(adIi+"\n");
 		adF.getFirst().show();
	}
}
class FreeStack3{
	int size;
	ArrayDeque<Integer> adI = new ArrayDeque();
	JTextArea jta;
	void init(JTextArea jta){
		this.jta = jta;
		size = 10;
		for(int i=0;i<size;i++)
			adI.offer(500-i);
	}
	void show(){
		jta.append("fs3栈的大小："+size+"\n");
 		//System.out.println("fs1栈的大小："+size);
 		for(Integer adIi:adI)
 			jta.append(adIi+"\n");
	}
}

class INode{
	int node0;
	int node1;
	int node2;
	int[] singleIndirect = new int[5];
	int[][] doubleIndirect = new int[5][5];
	void show(JTextArea jta){
		if(node0 != 0)
			jta.append("node0:"+node0+"\n");
		if(node1 != 0)
			jta.append("node1:"+node1+"\n");
		if(node2 != 0)
			jta.append("node2:"+node2+"\n");
		
		if(singleIndirect[0] != 0){
			jta.append("singleIndirect:"+"\n");
			for(int i=0;i<5;i++)
				jta.append(singleIndirect[i]+"\n");
		}
		if(doubleIndirect[0][0] != 0){
			jta.append("doubleIndirect:"+"\n");
			for(int i=0;i<5;i++){
				for(int j=0;j<5;j++)
					if(j==0)
						jta.append((doubleIndirect[i][j])+"->");
					else
						jta.append(doubleIndirect[i][j]+" ");
				jta.append("\n");
			}
		}
	}
}



public class MKDEL {
	JTextArea jta ;
	FreeStack1 fs = new FreeStack1();
	INode inode = new INode();
	public void init(JTextArea jta){
		fs.init(jta);
		fs.adF.peek().init(jta);
		fs.adF.peek().adF.peek().init(jta);
		this.jta = jta;
	}
	public void distribution(int type){
		//参数是分配文件的字节数,传入的type是2000,3000,对应要2个盘块，3个盘块
		//默认盘块大小是1KB，盘块所占字节数为1/5KB，即为一个目录能存5个盘块(INode表)
		int pieceC = type / 1000;
		if(type % 1000 != 0)
			pieceC ++ ;
		if(pieceC > 25){
			System.out.println("文件请求过大，不能满足");
			return ;
		}
		ArrayDeque<Integer> alTmp = new ArrayDeque(); //从空闲盘块移至索引表的记录
		if(fs.size != 0 && pieceC < fs.size){
			//pieceC<当前剩余空闲盘块数M
			//修改当前盘块size大小
			for(int i=0;i<pieceC;i++)
				alTmp.offer(fs.adI.poll());
			fs.size = fs.size - pieceC;
		}
		else if(fs.adF.peek().size != 0 &&
				pieceC < fs.size + fs.adF.peek().size){
			for(int i = 0;i<fs.size;i++)
				alTmp.offer(fs.adI.poll());
			pieceC = pieceC - fs.size;
			fs.size = 0;
			for(int i = 0; i<pieceC;i++){
				alTmp.offer(fs.adF.peek().adI.poll());
				fs.adF.peek().size--; 
			}
		}
		else {
			for(int i = 0;i<fs.size;i++)
				alTmp.offer(fs.adI.poll());
			for(int i = 0;i<fs.adF.peek().size;i++)
				alTmp.offer(fs.adF.peek().adI.poll());
			pieceC = pieceC - fs.size - fs.adF.peek().size;
			fs.size = 0;
			fs.adF.peek().size = 0;
			for(int i = 0; i<pieceC;i++){
				alTmp.offer(fs.adF.peek().adF.peek().adI.poll());
				fs.adF.peek().adF.peek().size -- ;
			}
		}
		
		inode.node0 = alTmp.poll();
		if(alTmp.peek() != null)
			inode.node1 = alTmp.poll();
		if(alTmp.peek() != null)
			inode.node2 = alTmp.poll();
		for(int i=0;i <inode.singleIndirect.length &&alTmp.peek()!=null;i++)
			inode.singleIndirect[i] = alTmp.poll();
		for(int i=0;alTmp.peek()!=null && i<5;i++)
			for(int j=0;alTmp.peek()!=null && j<5;j++)
				inode.doubleIndirect[i][j] = alTmp.poll();
	}
	public void recycle(){
		ArrayDeque<Integer> tmp = new ArrayDeque();//从多级混合索引表获得的文件盘块
		if(inode.node0 != 0){
			tmp.offer(inode.node0);
			inode.node0 = 0;
			if(inode.node1 != 0){
				tmp.offer(inode.node1);
				inode.node1 = 0;
			}
			if(inode.node2 != 0){
				tmp.offer(inode.node2);
				inode.node2 = 0;
			}
		}
		if(inode.singleIndirect[0] != 0){
			for(int i=0;i < inode.singleIndirect.length && 
					inode.singleIndirect[i] != 0;i++){
				tmp.offer(inode.singleIndirect[i]);
				inode.singleIndirect[i] = 0;
			}
		}
			int j = 0,i = 0;
			//回收二级索引有问题
			for(i=0;i<5 && inode.doubleIndirect[i][j] != 0 ;i++){
				for(j=0;j<5 && inode.doubleIndirect[i][j] != 0;j++){
					tmp.offer(inode.doubleIndirect[i][j]);
					inode.doubleIndirect[i][j] = 0;
				}
				j = 0;
			}
		jta.append("回收的块信息："+tmp+"\n");
		
		int Re = 30 - (fs.size+fs.adF.peek().size+fs.adF.peek().adF.size());
		if(Re > tmp.size()){
			while(tmp.peek() != null && fs.size <10){
				fs.adI.offer(tmp.poll());
				fs.size ++;
			}
			while(tmp.peek()!= null && fs.adF.peek().size <10){
				fs.adF.peek().adI.offer(tmp.poll());
				fs.adF.peek().size++;
			}
			while(tmp.peek()!= null && fs.adF.peek().adF.size() <10){
				fs.adF.peek().adF.peek().adI.offer(tmp.poll());
				fs.adF.peek().adF.peek().size++;
			}
		}
	}
	public void show(){
		fs.show();
		inode.show(jta);
	}
	
	
	public static void main(String[] args){
		/*MKDEL t = new MKDEL();
		t.init();
		t.distribution(2000);
		t.recycle();
		t.distribution(2789);    这个//mk t 2789 建立一个t文件,分配2000字节
		t.recycle();
		t.distribution(3000);    
		t.recycle();
		t.distribution(4242);    这个
		t.recycle();
		
		t.distribution(6666);
		t.recycle();
		t.distribution(11234);
		t.recycle();
		t.distribution(24283);   这个
		t.recycle();
		t.distribution(28999);
		t.recycle();
		t.show();*/
	}
}