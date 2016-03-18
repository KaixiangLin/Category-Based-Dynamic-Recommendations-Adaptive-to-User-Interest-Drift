
public class IntegerArrayCalculator {
	// Usage: Calculate the mean, variance of an integer array
	// NOTICE: The type of array is 'long'.
	int index;
	
	
	// calculate 2 vectors with same size.
		public double innerProduct(double []x1, double x2[]){
			double innerproduct=0;
			int len = x1.length;
			
			for(int i=0;i<len;i++)
			{
				innerproduct+=x1[i]*x2[i];
			}
			
			return innerproduct;
			
		}
		
		
		
		// l2 normalization of double vectors
		public double [] L2normalize(double [] array){
			int len = array.length;
			double[] normalizedv= new double [len];
			double norm=0;
			for(int i=0;i<len;i++) 
			{
				norm+=array[i]*array[i];
			}
			norm=Math.sqrt(norm);
			for(int i=0;i<len;i++)
			{
				normalizedv[i]=array[i]/norm;
			}
			return normalizedv;
		}
	
	// get mean of LONG array and store into variable mean.
	public double getMean(long [] array){
		double mean=0.0;		
		int len= array.length;
		if(len!=0){  
		for(int i=0;i<len; i++){
			if(i==0){
				mean=array[i];
			}
			else{
			mean=(i)*mean+array[i];
			mean=mean/(i+1);
			}
		}	
		}
		
		return mean;
	}
	
	// get mean of double array 
	public double getMean(double [] array){
		double mean=0.0;		
		int len= array.length;
		if(len!=0){  
		for(int i=0;i<len; i++){
			if(i==0){
				mean=array[i];
			}
			else{
			mean=(i)*mean+array[i];
			mean=mean/(i+1);
			}
		}	
		}
		
		return mean;
	}
	
	// get mean of Object array with Integer element.
	public double getMean(Object [] array){
		double mean=0.0;		
		int len= array.length;
		if(len!=0){  
		for(int i=0;i<len; i++){
			if(i==0){
				mean=(Integer)array[i];
			}
			else{
			mean=(i)*mean+(Integer)array[i];
			mean=mean/(i+1);
			}
		}	
		}
		
		return mean;
	}
	
	
	// get mean of int array 
		public double getMean(int [] array){
			double mean=0.0;		
			int len= array.length;
			if(len!=0){  
			for(int i=0;i<len; i++){
				if(i==0){
					mean=array[i];
				}
				else{
				mean=(i)*mean+array[i];
				mean=mean/(i+1);
				}
			}	
			}
			
			return mean;
		}
		
	
	// get variance of array and store into var.
	// n=0 represent get the variance, n=1 represent get the standard deviation
	public double getVarSD(long [] array, int n){
		double [] var= new double [2];
		double mean=0.0;		
		int len= array.length;
		if(len!=0){
			for(int i=0;i<len; i++){
				if(i==0){
					mean=array[i];
				}
				else{
				mean=(i)*mean+array[i];
				mean=mean/(i+1);
				}
			}	
		
		for(int j=0;j<len;j++){
			if(j==0){
			var[0]=(array[j]-mean)*(array[j]-mean);
			}
			else{
				var[0]=j*var[0]+(array[j]-mean)*(array[j]-mean);
				var[0]=var[0]/(j+1);
			}
		}
		 
        var[1]=Math.sqrt(var[0]);
		}
		
		return var[n];
	}
	
	// get standard deviation and variance of an double array
	public double getVarSD(double [] array, int n){
		double [] var= new double [2];
		double mean=0.0;		
		int len= array.length;
		if(len!=0){
			for(int i=0;i<len; i++){
				if(i==0){
					mean=array[i];
				}
				else{
				mean=(i)*mean+array[i];
				mean=mean/(i+1);
				}
			}	
		
		for(int j=0;j<len;j++){
			if(j==0){
			var[0]=(array[j]-mean)*(array[j]-mean);
			}
			else{
				var[0]=j*var[0]+(array[j]-mean)*(array[j]-mean);
				var[0]=var[0]/(j+1);
			}
		}
		 
        var[1]=Math.sqrt(var[0]);
		}
		
		return var[n];
	}
	
	// get standard deviation and variance of an int array
	public double getVarSD(int [] array, int n){
		double [] var= new double [2];
		double mean=0.0;		
		int len= array.length;
		if(len!=0){
			for(int i=0;i<len; i++){
				if(i==0){
					mean=array[i];
				}
				else{
				mean=(i)*mean+array[i];
				mean=mean/(i+1);
				}
			}	
		
		for(int j=0;j<len;j++){
			if(j==0){
			var[0]=(array[j]-mean)*(array[j]-mean);
			}
			else{
				var[0]=j*var[0]+(array[j]-mean)*(array[j]-mean);
				var[0]=var[0]/(j+1);
			}
		}
		 
        var[1]=Math.sqrt(var[0]);
		}
		
		return var[n];
	}
	
	
	public long getMax(long [] array ){
	
		long max=0;
		if(array.length!=0){
			 max=array[0];
		for(int i=0;i<array.length;i++){
			if(array[i]>max)
			max=array[i];
		}
		}
		return max;
	}
	
	
	public double getMax(double [] array ){
		double max=0;
		
		if(array.length!=0){
			max=array[0];
		for(int i=0;i<array.length;i++){
			if(array[i]>max)
			max=array[i];
		}
		}
		return max;
	}
	
	public int getMax(int [] array ){
		int max=0;
		
		if(array.length!=0){
			max=array[0];
		for(int i=0;i<array.length;i++){
			if(array[i]>max)
			max=array[i];
		}
		}
		return max;
	}
	
	public long getMin(long [] array ){
		long min=0;
		
		if(array.length!=0){
			
			min=array[0];
		for(int i=0;i<array.length;i++){
			if(array[i]<min)
				min=array[i];
		}
		}
		return min;
	}
	
	public double getMin(double [] array ){
		double min=0;
		if(array.length!=0){
			
		int index = 0;		
			min=array[0];
		for(int i=0;i<array.length;i++){
			if(array[i]<min)
			{	min=array[i];
			    index=i;
			
			}
		}
		
		this.index = index;
		}
		return min;
	}
	
	public int getMin(int [] array ){
		int min=0;
		
		if(array.length!=0){
			min=array[0];
		for(int i=0;i<array.length;i++){
			if(array[i]<min)
				min=array[i];
		}
		}
		return min;
	}
	
}
