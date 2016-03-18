
public class CosineSimi {
	// Calculate cosine similarity of 2 vectors
	
//public double getCosineSimi(int [] x1, int [] x2){
//		
//		double dis=0;
//		double dis1=0, dis2=0;
//		int len= x1.length;
//		
//		for(int i=0; i<len; i++){
//			dis+= (x1[i]-x2[i])*(x1[i]-x2[i]);
//			dis1+= x1[i]* x1[i];
//			dis2+= x2[i]*x2[i];
//		}
//		
//		dis= Math.sqrt(dis)/(Math.sqrt(dis2)*Math.sqrt(dis1));
//		
//		return dis;
//	}

public double getCosineSimi(double [] x1, double [] x2){
	
	double dis=0;
	double dis1=0, dis2=0;
	int len= x1.length;
	
	for(int i=0; i<len; i++){
		dis+= x1[i]*x2[i];
		dis1+= x1[i]* x1[i];
		dis2+= x2[i]*x2[i];
	}
	
	dis= dis/(Math.sqrt(dis2)*Math.sqrt(dis1));
	
	return dis;
}

public double getPcorrelation(double [] x1, double [] x2, double [] weight){
	// calculate the (positive) p correlation of x1 and x2, which have same size with each other.
	
	double p=0;
	IntegerArrayCalculator inac = new IntegerArrayCalculator();
	double x1mean = inac.getMean(x1);
	double x2mean = inac.getMean(x2);
	
	double up =0, down1=0,down2=0;
	int k = weight.length;
	for(int i=0;i<x1.length;i++){
		up+=(x1[i]-x1mean)*(x2[i]-x2mean)*weight[i/k];
		down1+=(x1[i]-x1mean)*(x1[i]-x1mean)*weight[i/k];
		down2+=(x2[i]-x2mean)*(x2[i]-x2mean)*weight[i/k];
	}
	
	p=up/Math.sqrt(down2*down1);
	
	p=Math.abs(p);
	return p;
}

public double getPcorrelation(double [] x1, double [] x2){
	// calculate the (positive) p correlation of x1 and x2, which have same size with each other.
	
	double p=0;
	IntegerArrayCalculator inac = new IntegerArrayCalculator();
	double x1mean = inac.getMean(x1);
	double x2mean = inac.getMean(x2);
	
	double up =0, down1=0,down2=0;
 
	for(int i=0;i<x1.length;i++){
		up+=(x1[i]-x1mean)*(x2[i]-x2mean) ;
		down1+=(x1[i]-x1mean)*(x1[i]-x1mean) ;
		down2+=(x2[i]-x2mean)*(x2[i]-x2mean) ;
	}
	
	p=up/Math.sqrt(down2*down1);
	
	p=Math.abs(p);
	return p;
}

}
