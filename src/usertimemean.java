
public class usertimemean {
	
	public double [] utmean(int [][] time_buffer){
		int NU = time_buffer.length;
		double [] usertimemean = new double [NU];
		
		
		removeZeroArray rem = new removeZeroArray();
		IntegerArrayCalculator inac = new IntegerArrayCalculator();
		
		
		for(int i=0;i<NU;i++){
		int [] timeWithoutZero =rem.getArrayNoZeroNew(time_buffer[i]);   // get all rating moments of user target. 
		usertimemean[i] = inac.getMean(timeWithoutZero);
		}
		
		return usertimemean;
	}
	
	
	

}
