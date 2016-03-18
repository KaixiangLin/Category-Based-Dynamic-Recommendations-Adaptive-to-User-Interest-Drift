import Jama.Matrix;


public class findNeighbor {
	
	
	
	
	double [] reSortedSimi;   											   // stored the regularized sorted similarity value of neighbors and target user.
	
	
	
	public int [] getNeighborbyclass(double [][] ratingMeanKclas, int numOfNeigh, int target){
		// this is to calculate the similarity by the class vectors.
		
		double []  simi;
		int [] neighbor = new int [numOfNeigh];
		int NU = ratingMeanKclas.length;
		int [] index = new int [NU];
		
		EuclideanDistance cal= new EuclideanDistance();
//        dCov dcov = new dCov();
//		CosinToMatrix cs = new CosinToMatrix();
		CosineSimi cs = new CosineSimi();
		
		simi = new double[NU];
		
		for(int j=0;j<NU;j++){
	    if(j!=target){ 
	      	                                          // neighbor can't be target itself
//		   simi[j]=cal.CalculateDis(ratingMeanKclas[target],ratingMeanKclas[j]);	
	       simi[j] = cs.getCosineSimi(ratingMeanKclas[target],ratingMeanKclas[j]);
		   index[j]=j;
	    }
	    else if (j==target){
	    
	    	simi[j]=-1;                                                        // target can't be the neighbor of itself unless the number of neighbor is equal to number of all users.
	    	index[j]=j;
	    }
		}
		
		Sort s = new Sort();
		double [] sortedSimi = s.getSortedArray(simi, index);
		int [] neighborTEM = s.sortedIndex;
		
		System.arraycopy(neighborTEM, NU-numOfNeigh, neighbor, 0, numOfNeigh);
		
		
		reSortedSimi= new double [numOfNeigh];
		System.arraycopy(sortedSimi, NU-numOfNeigh,reSortedSimi,0,numOfNeigh);
		
		double t=0;
		for(int i=0;i<numOfNeigh;i++)
		{	
			t+=reSortedSimi[i];
		}
		
		for(int i=0;i<numOfNeigh;i++)
		{	
			reSortedSimi[i]=reSortedSimi[i]/t;
		}
		
		return neighbor;
		
	}
	
	public int [] getNeighbor(int [][][] ratingTimeMat, int numOfNeigh, int target, int chooseSimiway){
		// target is the id of target user that is the user you want to find neighbors for them.
		// chooseSimiway equal to 1 represent you use eculidean distance to calculate similarity 
		// chooseSimiway equal to 2 represent you use dCov to calculate similarity.
		
		
		
		double []  simi;
		int [] neighbor = new int [numOfNeigh];
		int NU = ratingTimeMat.length;
		int [] index = new int [NU];
		
		EuclideanDistance cal= new EuclideanDistance();
        dCov dcov = new dCov();
		
		
		
		simi = new double[NU];
		
		for(int j=0;j<NU;j++){
	    if(j!=target){ 
	    	
	       if(chooseSimiway==1){	                                          // neighbor can't be target itself
		   simi[j]=cal.CalculateDis(ratingTimeMat[target],ratingTimeMat[j]);	
	       }
	       else if(chooseSimiway==2)
	       {
	    	  simi[j]=dcov.getdCovEfficient(ratingTimeMat[target], ratingTimeMat[j]);
	       }
	    
		   index[j]=j;
	    }
	    else if (j==target){
	    
	    	simi[j]=-1;                                                        // target can't be the neighbor of itself unless the number of neighbor is equal to number of all users.
	    	index[j]=j;
	    }
		}
		
		Sort s = new Sort();
		double [] sortedSimi = s.getSortedArray(simi, index);
		int [] neighborTEM = s.sortedIndex;
		
		System.arraycopy(neighborTEM, NU-numOfNeigh, neighbor, 0, numOfNeigh);
		
		
		reSortedSimi= new double [numOfNeigh];
		System.arraycopy(sortedSimi, NU-numOfNeigh,reSortedSimi,0,numOfNeigh);
		
		double t=0;
		for(int i=0;i<numOfNeigh;i++)
		{	
			t+=reSortedSimi[i];
		}
		
		for(int i=0;i<numOfNeigh;i++)
		{	
			reSortedSimi[i]=reSortedSimi[i]/t;
		}
		
		return neighbor;
		
	}
	
	public int [] getNeighbor(double [][][] ratingTimeMat, int numOfNeigh, int target, int chooseSimiway){
		// target is the id of target user that is the user you want to find neighbors for them.
		// chooseSimiway equal to 1 represent you use eculidean distance to calculate similarity 
		// chooseSimiway equal to 2 represent you use dCov to calculate similarity.
		
		
		
		double []  simi;
		int [] neighbor = new int [numOfNeigh];
		int NU = ratingTimeMat.length;
		int [] index = new int [NU];
		
		EuclideanDistance cal= new EuclideanDistance();
        dCov dcov = new dCov();
        CosinToMatrix cs = new CosinToMatrix();
		
		
		simi = new double[NU];
		
		for(int j=0;j<NU;j++){
	    if(j!=target){ 
	    	
	       if(chooseSimiway==1){	                                          // neighbor can't be target itself
		   simi[j]=cal.CalculateDis(ratingTimeMat[target],ratingTimeMat[j]);	
	       }
	       else if(chooseSimiway==2)
	       {
	    	  simi[j]=dcov.getdCovEfficient(ratingTimeMat[target], ratingTimeMat[j]);
	       }
	       else if (chooseSimiway==3)
	       {
	    	   simi[j]=cs.getCosineOfMatrix(ratingTimeMat[target], ratingTimeMat[j]);
	       }
	    
		   index[j]=j;
	    }
	    else if (j==target){
	    
	    	simi[j]=-1;                                                        // target can't be the neighbor of itself unless the number of neighbor is equal to number of all users.
	    	index[j]=j;
	    }
		}
		
		Sort s = new Sort();
		double [] sortedSimi = s.getSortedArray(simi, index);
		int [] neighborTEM = s.sortedIndex;
		
		System.arraycopy(neighborTEM, NU-numOfNeigh, neighbor, 0, numOfNeigh);
		
		
		reSortedSimi= new double [numOfNeigh];
		System.arraycopy(sortedSimi, NU-numOfNeigh,reSortedSimi,0,numOfNeigh);
		
		double t=0;
		
		
		for(int i=0;i<numOfNeigh;i++)
		{	
			t+=reSortedSimi[i] ;
		}
		 
		for(int i=0;i<numOfNeigh;i++)
		{	
			reSortedSimi[i]=reSortedSimi[i]/t;
		}
		
		return neighbor;
		
	}
	
	
	public int [] getNeighborMethod3(double [][][] ratingTimeMat, Matrix [] a,double [] radio ,int numOfNeigh, int target, int chooseSimiway){
		// target is the id of target user that is the user you want to find neighbors for them.
		// chooseSimiway equal to 1 represent you use eculidean distance to calculate similarity 
		// chooseSimiway equal to 2 represent you use dCov to calculate similarity.
		// Matrix a store the model parameters of each user. 
		// radio is the weight of two methods for evaluating similarity between 2 user. 
		
		double []  simi;
		int [] neighbor = new int [numOfNeigh];
		int NU = ratingTimeMat.length;
		int [] index = new int [NU];
		
		double [][][]  modela = new double[NU][][];
		double [][][] modela1 = new double [NU][][];
		for(int i=0;i<NU;i++){
		
			Matrix atemp = a[i].transpose();
			modela1[i] = atemp.getArray();
			
		}
		if(chooseSimiway==1){	
			for(int i=0;i<NU;i++)
			modela[i] = a[i].getArray();   
		}
		
		
		EuclideanDistance cal= new EuclideanDistance();
        dCov dcov = new dCov();
        CosinToMatrix cs = new CosinToMatrix();
        CosineSimi cs2 = new CosineSimi();
		
		simi = new double[NU];
		
		for(int j=0;j<NU;j++){
	    if(j!=target){ 
	    	
	       if(chooseSimiway==1){	                                          // neighbor can't be target itself
		   simi[j]=cal.CalculateDis(ratingTimeMat[target],ratingTimeMat[j])*radio[0]+cal.CalculateDis(modela[target], modela[j])*radio[1];	
	       }
	       else if(chooseSimiway==2)
	       {
	    	  simi[j]=dcov.getdCovEfficient(ratingTimeMat[target], ratingTimeMat[j]);
	       }
	       else if (chooseSimiway==3)
	       {
	    	   
	    	   simi[j]=cs2.getPcorrelation(modela1[target][0], modela1[j][0]);;
	       }
	    
		   index[j]=j;
	    }
	    else if (j==target){
	    
	    	simi[j]=-1;                                                        // target can't be the neighbor of itself unless the number of neighbor is equal to number of all users.
	    	index[j]=j;
	    }
		}
		
		Sort s = new Sort();
		double [] sortedSimi = s.getSortedArray(simi, index);
		int [] neighborTEM = s.sortedIndex;
		
		System.arraycopy(neighborTEM, NU-numOfNeigh, neighbor, 0, numOfNeigh);
		
		
		reSortedSimi= new double [numOfNeigh];
		System.arraycopy(sortedSimi, NU-numOfNeigh,reSortedSimi,0,numOfNeigh);
		
		double t=0;
		
		
		for(int i=0;i<numOfNeigh;i++)
		{	
			t+=reSortedSimi[i] ;
		}
		 
		for(int i=0;i<numOfNeigh;i++)
		{	
			reSortedSimi[i]=reSortedSimi[i]/t;
		}
		
		return neighbor;
		
	}
	
	
	
	
	
	
	public int [] getNeighborMethod2(double [][][] ratingTimeMat, Matrix [] a, double [][] lweightalluser ,double [] radio ,int numOfNeigh, int target, int chooseSimiway){
		// target is the id of target user that is the user you want to find neighbors for them.
		// chooseSimiway equal to 1 represent you use eculidean distance to calculate similarity 
		// chooseSimiway equal to 2 represent you use dCov to calculate similarity.
		// Matrix a store the model parameters of each user. 
		// radio is the weight of two methods for evaluating similarity between 2 user. 
		
		double []  simi;
		int [] neighbor = new int [numOfNeigh];
		int NU = ratingTimeMat.length;
		int [] index = new int [NU];
		
		double [][][]  modela = new double[NU][][];
		double [][][] modela1 = new double [NU][][];
		for(int i=0;i<NU;i++){
		
			Matrix atemp = a[i].transpose();
			modela1[i] = atemp.getArray();
			
		}
		if(chooseSimiway==1){	
			for(int i=0;i<NU;i++)
			modela[i] = a[i].getArray();   
		}
		
		
		EuclideanDistance cal= new EuclideanDistance();
        dCov dcov = new dCov();
        CosinToMatrix cs = new CosinToMatrix();
        CosineSimi cs2 = new CosineSimi();
		
		simi = new double[NU];
		
		for(int j=0;j<NU;j++){
	    if(j!=target){ 
	    	
	       if(chooseSimiway==1){	                                          // neighbor can't be target itself
		   simi[j]=cal.CalculateDis(ratingTimeMat[target],ratingTimeMat[j])*radio[0]+cal.CalculateDis(modela[target], modela[j])*radio[1];	
	       }
	       else if(chooseSimiway==2)
	       {
	    	  simi[j]=dcov.getdCovEfficient(ratingTimeMat[target], ratingTimeMat[j]);
	       }
	       else if (chooseSimiway==3)
	       {
	    	   int mark =0;
	    	   double [] weighttemp = new double [lweightalluser[0].length];
				for(int w=0;w<weighttemp.length;w++){
					if(lweightalluser[target][w]==0&&lweightalluser[j][w]==0){
						weighttemp[w]=0;
					}
					else{
						weighttemp[w]=1;
						
					}
				}
			
				if(mark==0){
					for(int w=0;w<weighttemp.length;w++)weighttemp[w]=1;
				}
	    	   simi[j]=cs.getCosineOfMatrix(ratingTimeMat[target], ratingTimeMat[j])*radio[0]+cs2.getPcorrelation(modela1[target][0], modela1[j][0],weighttemp)*radio[1];;
	       }
	    
		   index[j]=j;
	    }
	    else if (j==target){
	    
	    	simi[j]=-1;                                                        // target can't be the neighbor of itself unless the number of neighbor is equal to number of all users.
	    	index[j]=j;
	    }
		}
		
		Sort s = new Sort();
		double [] sortedSimi = s.getSortedArray(simi, index);
		int [] neighborTEM = s.sortedIndex;
		
		System.arraycopy(neighborTEM, NU-numOfNeigh, neighbor, 0, numOfNeigh);
		
		
		reSortedSimi= new double [numOfNeigh];
		System.arraycopy(sortedSimi, NU-numOfNeigh,reSortedSimi,0,numOfNeigh);
		
		double t=0;
		
		
		for(int i=0;i<numOfNeigh;i++)
		{	
			t+=reSortedSimi[i] ;
		}
		 
		for(int i=0;i<numOfNeigh;i++)
		{	
			reSortedSimi[i]=reSortedSimi[i]/t;
		}
		
		return neighbor;
		
	}
	
	
	
	
	
	
}
