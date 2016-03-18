import java.util.Stack;


public class FindKMostDiffUser {
	// try to find k most different users which mean they are far away from each other.
	// K must greater than 2.
	
//	public int [] getMostDiffUid( double [][] simimatrix, int k){
//		int [] iniCenterIndex = new int [k];
//		IntegerArrayCalculator inac = new IntegerArrayCalculator();
//		int NU = simimatrix.length;
//		Sort s = new Sort();
//		double min =1;
//		double temp=0;
//		int index1=0, index2=0;
//		Stack<Integer> userID = new Stack<Integer>();
//		
//		// get the first 2 far away center.
//		for(int i=0;i<NU;i++)
//		{
//			index1=i;
//		    temp = inac.getMin(simimatrix[i]); 
//		   if(temp< min)
//		   {
//			   index2=inac.index;
//		   }
//		}
//		
//		userID.add(index1);
//		userID.add(index2);
//		
//		
//		for(int i=2;i<k;i++)
//		{
//			int [] temID = new int [i+1]; // in this loop, find i+1 user id and store them in temID.
//			 for(int j=0;j<i;j++)
//			 {
//				 temID[j]= userID.pop();
//			 }
//				 
//			 double [] temMin = new double [i]; // store 
//			 int [] temIDhere = new int [i];
//			 for(int j=0;j<i;j++)
//			 {
//				//s.getSortedArray(array, index)
//			 }
//			 
//			 inac.getMin(temMin);
//			 temID[i]=temIDhere[inac.index];
//			 for(int j=0;j<i+1;j++)
//			 {
//				 userID.add(temID[j]);
//			 }
//			 
//		}
//		
//		for(int j=0;j<k;j++)
//		iniCenterIndex[j]= userID.pop();
//		
//		
//		
//		return iniCenterIndex;
//	}

	
	// this method is not a good choice, for the time consuming
	public int [] getMostDiffUid(double [][] simi, int k)
	{
		int [] 	iniCenterIndex = new int [k];
		int NU = simi.length;
		double [] simiArray = new double [NU*NU]; 
		int [] index = new int [NU*NU];
		int count =0 ;

		for(int i=0;i<NU;i++)                      // change matrix to array 
		{
			for(int j=0; j<NU;j++)
			{
				simiArray[count]=simi[i][j];
				index[count]=count;
				count++;
			}
		}
		
		Sort s = new Sort();
		
		s.getSortedArray(simiArray, index);        // index and simiArray have been sorted.
		
		System.out.println("end of sorted array");
		int num = 0;
		int count1= 0;
		Stack<Integer> userID = new Stack<Integer>();
		
	
		   userID.add(index[num]/NU);
		   if(index[num]/NU!=index[num]%NU)
		   {
			   userID.add(index[num]%NU);
		   }
		   int numofuserIDnow = userID.size();
		   while(count1<k-numofuserIDnow)
		   {
			   num++;
			   int t1= index[num]/NU;
			   int t2= index[num]%NU;
			   int flag1 = 0;
			   int flag2 = 0 ;
			   for(int j=0;j<userID.size();j++)
			   {
				   if(userID.elementAt(j)==t1)
				   {
					   flag1 =1;
				   }
				   if(userID.elementAt(j)==t2)
				   {
					   flag2 =1;
				   }
				   
			   }
			   if(flag1==0)
			   {
				   userID.add(t1);
				   count1++;
			   }
			   if((flag2==0)&&(t1!=t2))
			   {
				   userID.add(t2);
				   count1++;
			   }
			   
		   }
			
			for(int j=0;j<k; j++)iniCenterIndex[j]=userID.pop();
			
		
		
		
		return iniCenterIndex;
	}
}
