package lab1;

import java.awt.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;


public class AllProgress{
	//��������
	Queue<PCB> blockQueue = new LinkedBlockingQueue<PCB>();  
	//��������
	Queue<PCB> alreadyNow = new LinkedBlockingQueue<PCB>();
	//����ִ�еĳ���:��ʼ��Ϊ��
	PCB p = new PCB();
	//1ʱ��Ƭ:5000ms , 5s
	static final int timeA = 5000;
}
