import java.util.Vector;

 


public class Cluster {
	// center is the current center
	// k is the number of center, n is the number of vectors that will be clustered, m is the dimension of the vector.  
	// data is the vectors of items
	// vecK is k arrays of Vector class, which store the index of each categories
	
	private double [][] center;
	private int k, n, m;
	private double [][]data;
	private Object [] vecK;
    
	public Cluster(double [][] center, double [][]data, int k){
		this.center=center;
		this.k=k;
		this.data=data;
		this.vecK=new Object [k];
	    this.n=data.length;
	    this.m=data[0].length;
	    for(int i=0;i<k;i++){
	    vecK[i]=new Vector();
	    }
	}
	
	// this method is to allocate vectors to the center that is the nearest to them
	public Object [] proceedCluster(){
		
		for(int i=0; i<n; i++)                           // NOTICE: all vector index start from zero
		{                          
			
			double [] x1= data[i];                          // label is to remember the nearest center to the current vector x1
			int label=0;                                 // if all center's similarity is lessequ than zero then this vector belong to 0st category. 
			double similarity= 0;
			
			for(int j=0; j<k; j++)
			{	
			
				double[] x2= center[j];
				double s=0,s1=0,s2=0, newSimi=0;
                for(int ii=0; ii<m; ii++){
                	s= s+ x1[ii]*x2[ii];
                	s1= s1+ x1[ii]*x1[ii];
                	s2= s2+ x2[ii]*x2[ii];
                }
				newSimi= s/(Math.sqrt(s1)*Math.sqrt(s2));
				if(newSimi>similarity)
				{
					similarity= newSimi;
					label=j;
				}	
			}
			((Vector) vecK[label]).add(i);                           // ist vector belong to label st category
		}

          	return vecK;
	}
	
	
}
