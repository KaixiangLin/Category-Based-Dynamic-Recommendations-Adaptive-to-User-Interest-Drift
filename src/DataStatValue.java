
public class DataStatValue {
	//this class is to calculate the statistics of matrices time.
	
	
	private int [][] time ;
	private int timeRow;
	private int timeColumn; 
    static int maxtime;
    static int mintime;
    static double [] meanOfRow;
    static double [] SDofRow;
    static int [] nZeroOfRow; // the number of none zero elements of each row
    static int minNumberRow; // the min of the number of none zero elements of each row
    static int [] DisOfNum; // the distribution of nZeroOfRow.
    static int maxNumberRow;
	public DataStatValue(int [][] time ){
	
		 this.timeRow=time.length;
		 this.timeColumn=time[0].length; 
         this.time=time;
		 this.meanOfRow= new double [time.length];	
		 this.SDofRow= new double [time.length];
		 this.nZeroOfRow= new int [time.length];
	}
	
	
	public double [] userTime(){
		
		IntegerArrayCalculator calculator = new IntegerArrayCalculator();
		removeZeroArray rem = new removeZeroArray();
			
		// get the max value of matrix time.
		int maxTime=0;
		for(int i=0;i<timeRow;i++){
			int t=0;
			int []timeWithoutZero;         // remove all zero elements of the matrix time.
			timeWithoutZero=rem.getArrayNoZeroNew(time[i]);
			
			t=calculator.getMax(timeWithoutZero);
			if(t>maxTime)
				maxTime=t;
		}
		this.maxtime=maxTime;
		
		// get the min value of matrix time
		int minTime=0;
		for(int i=0;i<timeRow;i++){
			int t=0;
			int []timeWithoutZero;         // remove all zero elements of the matrix time.
			timeWithoutZero=rem.getArrayNoZeroNew(time[i]);
			
			t=calculator.getMin(timeWithoutZero);
			if(i==0){
				minTime=t;
			}
			if(t<minTime)
				minTime=t;
		}
		this.mintime=minTime;
		
		// Get the mean of each row of matrix time.
		
		for(int i=0;i<timeRow;i++){
		int []timeWithoutZero;         // remove all zero elements of the matrix time.
		timeWithoutZero=rem.getArrayNoZeroNew(time[i]);
		meanOfRow[i]=calculator.getMean(timeWithoutZero);
		}
		
		// get the standard deviation of each row of matrix time.
		for(int i=0;i<timeRow;i++){
			int []timeWithoutZero;         // remove all zero elements of the matrix time.
			timeWithoutZero=rem.getArrayNoZeroNew(time[i]);
			SDofRow[i]=calculator.getVarSD(timeWithoutZero, 1);
		}
		
		// Get the distribution of mean of row
		int t= (maxtime-mintime)/(timeRow/2); 
		int n= timeRow/2;
		double [] MeanOftimeDis = new double [n];
		for(int i=0;i<timeRow;i++){
			for(int j=0;j<n;j++){
				if((meanOfRow[i]>mintime+j*t)&&(meanOfRow[i]<mintime+(j+1)*t)){
					MeanOftimeDis[j]++;
				}
			}
		}
		
		// Get the number of none zero element of each row
		for(int i=0;i<timeRow;i++){
			int []timeWithoutZero;         // remove all zero elements of the matrix time.
			timeWithoutZero=rem.getArrayNoZeroNew(time[i]);
			nZeroOfRow[i]=timeWithoutZero.length;
		}
		
		// Get the distribution of number of rating.
		int maxNum=calculator.getMax(nZeroOfRow);
		int minNum=calculator.getMin(nZeroOfRow);
		minNumberRow= minNum;
		maxNumberRow=maxNum;
		int t1=20;
		int n1=(maxNum-minNum)/t1;
		DisOfNum = new int [n1] ;
		for(int i=0;i<timeRow;i++){
			for(int j=0;j<n1;j++ ){
				if((nZeroOfRow[i]>minNum+j*t1)&&(nZeroOfRow[i]<minNum+(j+1)*t1))
				DisOfNum[j]++;
			}
		}
		
		
		return MeanOftimeDis;
	}
	
	
	
	

}
