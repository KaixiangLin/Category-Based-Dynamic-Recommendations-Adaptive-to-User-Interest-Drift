
public class EuclideanDistance {
	// calculate the Euclidean distance between 2 vectors that have same length
	// INPUT: 2 vectors can be both integer or double 
//	
//	private int [] x1, x2;
//	private double [] y1, y2;
//	
//	public EuclideanDistance(int [] x1, int [] x2){
//		   this.x1=x1;
//		   this.x2=x2;
//	
//	}
//	public EuclideanDistance(double [] x1, double [] x2){
//		    this.y1=x1;
//		    this.y2=x2;
//	}

	
	public double CalculateDis(int [] x1, int [] x2){
		
		double dis=0;
		int len= x1.length;
		
		for(int i=0; i<len; i++){
			dis+= (x1[i]-x2[i])*(x1[i]-x2[i]);
		}
		
		dis= Math.sqrt(dis);
		
		return dis;
	}
	
	public double CalculateDis(double [] x1, double [] x2){
		
		double dis=0;
		int len= x1.length;
		
		for(int i=0; i<len; i++){
			dis+= (x1[i]-x2[i])*(x1[i]-x2[i]);
		}
		
		dis= Math.sqrt(dis);
		
		return dis;
	}
	
	public double CalculateDis(int [][] x1, int [][] x2){
	//  if the format of matrices is not match, then return the norm=-1, represent input is wrong 
    //  INPUT: INT matrices with the same size.
		
		int m1=x1.length, n1=x1[0].length;
		int m2=x2.length, n2=x2[0].length;
		double norm=0;
		
		if(m1==m2&n1==n2){
		for(int i=0;i<m1;i++){
			for(int j=0;j<n1;j++){
			 norm+=(x1[i][j]-x2[i][j])*(x1[i][j]-x2[i][j]);	
			}
		}
		
		norm=Math.sqrt(norm);
		
		}
		else{
			norm=-1;
		}
	
		
		return norm;
		
	}
	
	public double CalculateDis(double [][] x1, double [][] x2){
	//  if the format of matrices is not match, then return the norm=-1, represent input is wrong 
    //  INPUT: INT matrices with the same size.
		
		int m1=x1.length, n1=x1[0].length;
		int m2=x2.length, n2=x2[0].length;
		double norm=0;

		if(m1==m2&n1==n2){
		for(int i=0;i<m1;i++){
			for(int j=0;j<n1;j++){
			 norm+=(x1[i][j]-x2[i][j])*(x1[i][j]-x2[i][j]);	
			
			}
		}
		
		norm=Math.sqrt(norm);
		
		}
		else{
			norm=-1;
		}
	
		
		return norm;
		
	}
	
}
