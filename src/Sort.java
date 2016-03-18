

public class Sort {
	// index from 0 to length, value from min to max 
	    int [] sortedIndex;
	    double [] sortedIndex1;
	
	public int[]  getSortedArray(int [] array, int [] index){
		
	
		for(int i=0;i<array.length-1;i++){
			for(int j=0;j<array.length-1-i;j++){
				int t=0;
			  if(array[j]>array[j+1])
			  {
				  t=array[j];
				  array[j]=array[j+1];
				  array[j+1]=t;
				  
				  t=index[j];                              //keep the index have the same movement as value.
				  index[j]=index[j+1];
				  index[j+1]=t;
			  }
				
				
			}
		}
		
		
		this.sortedIndex= index;
		
		return array;
	}
	
	
	public int[]  getSortedArray(int [] array ){
		
		
		for(int i=0;i<array.length-1;i++){
			for(int j=0;j<array.length-1-i;j++){
				int t=0;
			  if(array[j]>array[j+1])
			  {
				  t=array[j];
				  array[j]=array[j+1];
				  array[j+1]=t;
				  
				 
			  }
				
				
			}
		}
		
 
		return array;
	}
	
	public double[]  getSortedArray(double [] array, int [] index){
		
		
		for(int i=0;i<array.length-1;i++){
			for(int j=0;j<array.length-1-i;j++){
				double t=0;
				int t1=0;
			  if(array[j]>array[j+1])
			  {
				  t=array[j];
				  array[j]=array[j+1];
				  array[j+1]=t;
				  
				  t1=index[j];                              //keep the index have the same movement as value.
				  index[j]=index[j+1];
				  index[j+1]=t1;
			  }
				
				
			}
		}
		
		
		this.sortedIndex= index;
		
		return array;
	}
	
	
	public double[]  getSortedArray(double [] array, double [] index){
		
		
		for(int i=0;i<array.length-1;i++){
			for(int j=0;j<array.length-1-i;j++){
				double t=0;
				double t1=0;
			  if(array[j]>array[j+1])
			  {
				  t=array[j];
				  array[j]=array[j+1];
				  array[j+1]=t;
				  
				  t1=index[j];                              //keep the index have the same movement as value.
				  index[j]=index[j+1];
				  index[j+1]=t1;
			  }
				
				
			}
		}
		
		
		this.sortedIndex1= index;
		
		return array;
	}
	
	
	
	

}
