package test;

public class test10 {
	double EPS = 0.000001;
	int n = 4;
	double[][] a = new double[][]{  {0,0,0,0,0},
	                    {0,-4,1,1,1},
	                    {0,1,-4,1,1},
	                    {0,1,1,-4,1},
	                    {0,1,1,1,-4}};
	double[] b = new double[] {0,1,1,1,1};

	void get(){
		int i,j;
	    double e ;
	    double x[] = new double[5];
	    for (i = 1;i <= n;i++){
	        x[i] = 0;
		}
		do{		 
			e= 0;
	   		for (i = 1;i <= n;i++){
	        	double y = b[i];
	    	    for (j = 1;j <= n;j++){
	        	    if (i == j)		
						continue;
		            y = y - a[i][j] * x[j];
	    	    }
	       		y = y / a[i][i];
		        if (Math.abs( x[i] - y) > e)
	    	        e = Math.abs(x[i] - y);
	       		x[i] = y;
	   		}
		}while(e >= EPS);
		System.out.println("x1[1]:"+x[1]);
		System.out.println("x1[2]:"+x[2]);
		System.out.println("x1[3]:"+x[3]);
		System.out.println("x1[4]:"+x[4]);
	}
	public static void main(String[] args) {
		new test10().get();
	}
}
