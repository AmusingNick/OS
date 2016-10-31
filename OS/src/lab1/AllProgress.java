package lab1;

import java.awt.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;


public class AllProgress{
	//阻塞队列
	Queue<PCB> blockQueue = new LinkedBlockingQueue<PCB>();  
	//就绪队列
	Queue<PCB> alreadyNow = new LinkedBlockingQueue<PCB>();
	//正在执行的程序:初始化为无
	PCB p = new PCB();
	//1时间片:5000ms , 5s
	static final int timeA = 5000;
}
