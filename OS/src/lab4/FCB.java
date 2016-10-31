package lab4;

import java.util.Calendar;


public class FCB{
	 String name;
	 int size;			//大小
	 int firstBlock;		//第一块块号
	 char type;			//类型，1为文件，2为目录，0为已删除目录项 
	 Calendar datetime;	 		//日期时间，格式为yyyymmdd hhmmss
	 FCB f;  			//树形结构时指向的下一个fcb
}
