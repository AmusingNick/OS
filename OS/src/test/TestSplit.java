package test;

public class TestSplit {
	public static void main(String[] args) {
		String s = "C:@Users@hongyu@Desktop@";
		String[] ss = s.split("@");
		for(int i=0;i<ss.length;i++){
			System.out.println(ss[i]);
		}
	}
}
