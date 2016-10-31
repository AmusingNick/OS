package lab2;

import java.util.ArrayList;

public class test {
	public static void main(String[] args) {
		ArrayList<Integer> al = new ArrayList<>();
		al.add(2);
		al.add(3);
		al.add(4);
		al.add(1);
		al.add(3);
		al.add(2);
		al.add(4);
		al.remove(0);
		System.out.println(al.get(0));
		//System.out.println(al.indexOf(1));
		
		/*ArrayList<Integer> al1 = new ArrayList();
		al1.addAll(al);
		for(int i=0;i<3;i++)
			al1.remove(i);
		System.out.println(al1);*/
		
	}
}
