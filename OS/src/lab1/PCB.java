package lab1;

public class PCB {
	String name = new String("");  //进程名
	int PCBsize;   //PCB所占空间大小
	//以上两个变量由用户输入
	int number;  //所在分区号
	int size;  //所占分区大小
	int benginAddress;  //起始地址
	boolean state;   //是否处于被占用状态
}
