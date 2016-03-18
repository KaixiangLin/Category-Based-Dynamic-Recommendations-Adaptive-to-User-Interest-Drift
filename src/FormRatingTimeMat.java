
public class FormRatingTimeMat {
// FORM THE RATING MATRIX WITH TIME FOR EACH USER. 
// RESULT STORE IN ratingTimeMat	
    int [][][] ratingTimeMat;
	int [] userTest ;
	
	
	// parameters for method  getRatTimeMatSpecific
	int [] markspecific; // this mark the specific user's rating category in each moment
	public void getRatTimeMat(int [][] time, int [] label, int [][] rating, int num,int k)
	{
		int NU=time.length;
		
		
		userTest = new int [NU];
		ratingTimeMat = new int [NU][num][k];
		for(int i=0;i<NU;i++){
			
			removeZeroArray rem = new removeZeroArray();
			int [] timeWithoutZero =rem.getArrayNoZeroNew(time[i]);
			int [] index = rem.index;
			
			int [] timeSorted = new int [timeWithoutZero.length];
			int [] indexSorted = new int [index.length];
			
			Sort s = new Sort();
			
			timeSorted = s.getSortedArray(timeWithoutZero,index);
			indexSorted = s.sortedIndex;
			
				
			int [] NumIndex = new int [num];                       // store id of item that are rated by user i at the first num st moment
			int t=indexSorted.length-num;
			System.arraycopy(indexSorted,t,NumIndex,0,num);       // cut the first num elements of indexSorted[] to NumIndex.
			
			
	        for(int j=0;j<num;j++){
	        	
	        	ratingTimeMat[i][j][label[NumIndex[j]]]=rating[i][NumIndex[j]];
	        }		
//	        userTest[i] = NumIndex[num-1];
		}
	}
	
	
	public int [][]  getRatTimeMatSpecific(int [][] time, int [] label, int[][] rating, int k, int target)
	{
 
		 		 
			
			removeZeroArray rem = new removeZeroArray();
			int [] timeWithoutZero =rem.getArrayNoZeroNew(time[target]);
			int [] index = rem.index;
			int len = timeWithoutZero.length;
			int [][] ratingTimeMatin = new int [len][k];
			
			int [] timeSorted = new int [len];
			int [] indexSorted = new int [index.length];
			markspecific= new int[len];
			
			Sort s = new Sort();
			
			timeSorted = s.getSortedArray(timeWithoutZero,index);
			indexSorted = s.sortedIndex;
			
			
	        for(int j=0;j<len;j++){ 
	        	int index1 = label[indexSorted[j]];
	        	ratingTimeMatin[j][index1]=rating[target][indexSorted[j]];
	        	markspecific[j]=index1;
	        }		
	        
		return ratingTimeMatin;
	}
	
	public double  [][]  getRatTimeMatSpecific(int [][] time, int [] label, double[][] rating, int k, int target)
	{
        // this function is to form rating time matrix for user target. include all ratings of user target in time matrix 
		// INPUT: 
		// target is user's id. 
		// label[] mark the item to its category index. e.g. item 1 belong to category 2 means label[1]=2; 		 
		// rating[][] rating value of all user at all items.
		// time [][] rating times of all user at all items.
		
		// OUTPUT
		// ratingTimeMatin[][]  the rating time matrix for user target. 
		// markspecific[]     mark the real rating at which column of ratingTimeMatin.
			
			removeZeroArray rem = new removeZeroArray();
			int [] timeWithoutZero =rem.getArrayNoZeroNew(time[target]);   // get all rating moments of user target. 
			int [] index = rem.index;
			int len = timeWithoutZero.length;                              // how many ratings does this user have 
			double [][] ratingTimeMatin = new double [len][k];
			
			int [] timeSorted = new int [len];
			int [] indexSorted = new int [index.length];
			markspecific= new int[len];
			
			Sort s = new Sort();
			
			timeSorted = s.getSortedArray(timeWithoutZero,index);
			indexSorted = s.sortedIndex;
			
			
	        for(int j=0;j<len;j++){ 
	        	int index1 = label[indexSorted[j]];
	        	ratingTimeMatin[j][index1]=rating[target][indexSorted[j]];
	        	markspecific[j]=index1;
	        }		
	        
		return ratingTimeMatin;
	}
	
	
	public int [][]  getRatTimeMatSpecificold(int [][] time, int [] label, int[][] rating, int k, int target)
	{
 
		 		 
			
			removeZeroArray rem = new removeZeroArray();
			int [] timeWithoutZero =rem.getArrayNoZeroNew(time[target]);
			int [] index = rem.index;
			int len = timeWithoutZero.length;
			int [][] ratingTimeMatin = new int [len][k];
			
			int [] timeSorted = new int [len];
			int [] indexSorted = new int [index.length];
			markspecific= new int[len];
			
			Sort s = new Sort();
			
			timeSorted = s.getSortedArray(timeWithoutZero,index);
			indexSorted = s.sortedIndex;
			
			
	        for(int j=0;j<len;j++){ 
	        	ratingTimeMatin[j][label[indexSorted[j]]]=rating[target][indexSorted[j]];
	        }		
	        
		return ratingTimeMatin;
	}
}
