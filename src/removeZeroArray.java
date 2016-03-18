import java.util.Stack;


public class removeZeroArray {
	//To remove the zero from old array, and keep the order no change.

    //Order: remember the index of the none zero element of old array.
	
	int [] index;   // This is only for Integer array
	
	public long [] getArrayNoZeroNew(long [] array){
		int l=0;
	    int len= array.length;
	    Stack<Long> s= new Stack<Long>();
	    
	    for(int i=0;i<len; i++){
	    	
	    if(array[i]!=0){
	        
	    	s.push(array[i]);
	    	l++;
	    }
	    }
	    
	    long [] a = new long [l];
	    for(int j=0;j<l;j++){
	    	a[l-1-j]=(Long) s.pop();
	    }
	    
	    
	    return a;
	}
	
	
	public double [] getArrayNoZeroNew(double [] array){
		
		int l=0;
		int len= array.length;
		Stack<Double> s = new Stack<Double>();
		
		
		for(int i=0;i<len; i++){
	    	
		    if(array[i]!=0){
		        
		    	s.push(array[i]);
		    	l++;
		    }
		    }
		    
		    double [] a = new double [l];
		    for(int j=0;j<l;j++){
		    	a[l-1-j]=(Double) s.pop();
		    }
		    
		    
		    return a;
		
		
		
	}
	
public int [] getArrayNoZeroNew(int [] array){
		
		int l=0;
		int len= array.length;
		
		Stack<Integer> s = new Stack<Integer>();
		Stack<Integer> s1 = new Stack<Integer>();
		
		for(int i=0;i<len; i++){
	    	
		    if(array[i]!=0){
		        
		    	s.push(array[i]);
		    	s1.push(i);
		    	l++;
		    }
		    }
		    
		    int [] a = new int [l];
		    index= new int [l];
		    for(int j=0;j<l;j++){
		    	a[l-1-j]=(Integer) s.pop();
		    	index[l-1-j]=(Integer)s1.pop();
		    }
		    
		    
		    return a;
		
		
		
	}
	
	

}
