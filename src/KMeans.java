import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
public class KMeans{
	// HOW TO USE: 
	// First:  proceed constructor KMeans
	// Second: proceed method KMeans.iniCenter()
	// Third:  proceed method KMeans.getMeans()
	// Fourth: Access to KMeans.centerNew to get the k centers, which formed as an matrix k*n, n is the dimension of center
	//         Access to KMeans.vecK to get the index allocate to each center, where vecK is k Vector instances and each instance contain index of vectors belong to this category
	
	// INPUT is a matrix, each row is one vector that will be clustered into this one category. 
	// k is the number of categories   
	// m is the number of items. 
	// n is the number of dimensions of item vector.
	// iniCenterIndex is the first K center index 
	// NOTICE: all index of vectors and matrix start from 0
	
	   private double [][] vectorMatrix;
	   private int k, n, m;
	   private int [] iniCenterIndex;
	   public double [][] centerOld, centerNew;
	   public Object []vecK;
 
	
       public KMeans(double vectorMatrix[][], int k){
    	   this.k=k;
    	   this.vectorMatrix=vectorMatrix;
	       this.n=vectorMatrix.length;         // the number of rows of vectorMatrix
	       this.m=vectorMatrix[0].length;      // the number of column of vectorMatrix 
           this.centerNew=new double [k][m];
           this.centerOld=new double [k][m];
           this.iniCenterIndex= new int [k];
           this.vecK=new Object[k];
           for(int i=0;i<k;i++){        
        	   vecK[i]=new Vector();
           }
   }
	// Step First: Calculate the first K random center
       
       public void iniCenter(int chooseway){
    	   
    	   int count=0;
    	   if(chooseway==0){
    	   //Produce k index in 0 to n-1, which indicates the index of first k centers.
    	   for(int i=0;i<k;i++)iniCenterIndex[i]=-1;                                               ///////////
    	   while(count<k){
    		   int num = (int) Math.round(Math.random()*(n-1));   // produce random int from 0 to n-1, without repeat
    		   boolean flag = true;
    		   for(int i=0;i<k; i++){
    			   if(iniCenterIndex[i]==num){
    				   flag = false;
    				   break;
    			   }  			   
    		   }
    		   if(flag=true)
    		   {
    			   iniCenterIndex[count]=num;
    			   count++;
    		   }   
    	   }
    	   // the first k center is saved in centerOld, each row is a center 
    	   for(int i=0; i<k ;i++){
    		   for(int j=0;j<m;j++)
    		   centerOld[i][j]=vectorMatrix[iniCenterIndex[i]][j];
    	   }
    	   }
    	   else if(chooseway==1)
    	   {
    	   // second way to produce first k center.
    	   EuclideanDistance ed = new EuclideanDistance();
    	   double [][] simiMatrix = new double [this.n][this.n];
    	   for(int i=0;i<this.n;i++)
    	   {
    		   for(int j=i;j<this.n;j++)
    		   {
    			   simiMatrix[i][j]=ed.CalculateDis(vectorMatrix[i], vectorMatrix[j]);
    			   simiMatrix[j][i]= simiMatrix[i][j];
    		   }
    	   }
    	   FindKMostDiffUser fmdu = new FindKMostDiffUser();
    	   iniCenterIndex=fmdu.getMostDiffUid(simiMatrix, this.k);
    	   
    	   // the first k center is saved in centerOld, each row is a center 
    	   for(int i=0; i<k ;i++){
    		   for(int j=0;j<m;j++)
    		   centerOld[i][j]=vectorMatrix[iniCenterIndex[i]][j];
    	   }
    	   }
    	   else if(chooseway ==2)
    	   {
    		   readKiniCenters r  = new readKiniCenters();
    		   centerOld = r.getiniCenters(k,this.m);
    	   }
    	   // record ini centers
    	   try{ File outfile = new File("C:\\Users\\Lin\\Desktop\\movieLens 100k data\\recordKmeansCenter.txt"); BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
   		   for(int i=0; i< centerOld.length;i++){for(int j=0;j<centerOld[0].length;j++) out.write(String.valueOf(centerOld[i][j])+" "); out.newLine();}out.close();
   		   }catch( Exception e)
   		   {
   			e.printStackTrace();
   		   }
    	   
    	   // cluster items to k categories    	   
    	   Cluster FirstCenter =new Cluster(centerOld, vectorMatrix, k);    		 
    	   vecK=FirstCenter.proceedCluster();
       }
       
    // Step2: Get the k st means and allocate vectors to k means return the result vecK
       
       public void getMeans(int maxLoop, double threshold){
    	   
    	   int m1=0;                                               // the maximum times of loop
    	   
    	   while(m1<maxLoop){
    		 
    		   // initial the Net center		   
    		   for(int i=0; i<k; i++)                             
    		   {
    			   for(int j=0; j<m; j++)
    				   centerNew[i][j]=0;
    		   }
    		   
    		   int [] mark = new int[k];// mark if there is any center who didn't have a item belong to.
    		   // get new center
    		   for(int i=0; i<k; i++)
    		   {
    			   int len= ((Vector) vecK[i]).size();
    			   if(len!=0){
    			   mark[i]=len;
    			   int [] index =new int [len]; 
    			   for(int ii=0; ii<len; ii++)
    			   {  
    				  index[ii]=(Integer) ((Vector) vecK[i]).get(ii);        // index store the index of vectors belong to i st category 
    			   }
    			   
    			   for(int j=0; j<m; j++){
    			   for(int ii=0; ii<len; ii++){   
    			   centerNew[i][j] += vectorMatrix[index[ii]][j];
    			   }
    			   centerNew[i][j]=centerNew[i][j]/len;           // the mean of kst center jst dimension
    			   }
    			   }
    		   }
    		   int maxlen = 0;
    		   int index =0;
			   for(int j=0;j<k;j++){
				   if(maxlen<mark[j]){
					   index = j;
					   maxlen = mark[j];
				   }
			   }
    		   
    		   for(int i=0;i<k;i++){
    			   int len= ((Vector) vecK[i]).size();
    			   if(len==0){
    				   int randomid = (int) (maxlen*Math.random());
    				   int id = (Integer)((Vector)vecK[index]).get(randomid);
    				   ((Vector)vecK[index]).removeElement(id);
    				   ((Vector)vecK[i]).add(id);
    				   for(int j=0; j<m; j++){
    	    			   centerNew[i][j] += vectorMatrix[id][j];
    	    		    }
    			   }
    		   }
    		   
    		   // compare the new center with the old center
    		   double [] error =new double [k];
    		   double errKmeans = 0;                              // the distance of k centers between old and new
    		   for(int i=0; i<k; i++){
    			   EuclideanDistance EculDis= new EuclideanDistance();
    			   error[i]=EculDis.CalculateDis(centerNew[i], centerOld[i]);
    			   errKmeans+= error[i];
    		   }
//    		   System.out.println("there ");
    		   errKmeans=Math.sqrt(errKmeans);
    		   // if the kmeans don't vary a lot between old one and new one then break. Access to vec and 
    		   System.out.println(errKmeans);
    		   if(errKmeans<threshold){
    			   break;
    		   }
    		  
    		   // if the means still vary, then continue to cluster
    		   centerOld=centerNew;
    		   for(int i=0; i<k; i++){
    			   ((Vector) vecK[i]).clear();
    		   }
    		   Cluster FirstCenter =new Cluster(centerOld, vectorMatrix, k);    		 
        	   vecK=FirstCenter.proceedCluster();
    	  
    	    m1++;
    	    System.out.println(m1);
    	   
    	   }
    	   
    	   if(m1==maxLoop)
           {
        	   System.out.println("can't converge");
           }
           
    	
       }
      
       double [][] centertemp= null;
       public Object[] combine(int k, double [][ ] centerold, Object [] veckold){
    	   // converge kold centers to k centers.
    	   int kold = centerold.length;
    	   double [][] simicenter = new double [kold][kold];
    	   EuclideanDistance ed = new EuclideanDistance();
    	   
    	   
    	   // get the 2 centers that need to be combined.
    	   double min = ed.CalculateDis(centerold[0], centerold[1]);
    	   int [] index = new int [2];
    	   index[0]=0;
    	   index[1]=1;
    	   int numOfcneterold = centerold.length;
    	   for(int i=0;i<numOfcneterold;i++)
    	   {
    		   for(int j=i+1;j<numOfcneterold;j++)
    		   {
    			   simicenter[i][j]=ed.CalculateDis(centerold[i], centerold[j]);
    			   simicenter[j][i]= simicenter[i][j];
    			   
    			   if(min>simicenter[i][j]){
    				   min = simicenter[i][j];
    				   index[0]=i;
    				   index[1]=j;
    			   }
    		   }
    	   }
    	   
    	   
    	   double [][] centerNewConverge = new double [k][this.m];
//    	   int len = ((Vector)vecK[index[0]]).size()+((Vector)vecK[index[1]]).size();
    	   
    	   
    	   for(int i=0;i<2;i++){
    		   int len = ((Vector)veckold[index[i]]).size();
    		    
    	       int [] indextemp =new int [len]; 
			   for(int ii=0; ii<len; ii++)
			   {  
				   indextemp[ii]=(Integer) ((Vector) veckold[index[i]]).get(ii);        // index store the index of vectors belong to i st category 
			   }
			   
			   for(int j=0; j<m; j++){
    			   for(int ii=0; ii<len; ii++){   
    				   centerNewConverge[0][j] += vectorMatrix[indextemp[ii]][j];
    			   }
			   }
    	   }
    	   
    	   
    	   // get the new center of 2 combined centers.
    	   int len2 = ((Vector)veckold[index[0]]).size()+((Vector)veckold[index[1]]).size();
    	   for(int j=0;j<m;j++){
    	   centerNewConverge[0][j]=centerNewConverge[0][j]/len2;
    	   }
    	   int count=1;
    	   for(int i = 0;i<centerold.length;i++){
    		   if(i!=index[0]&&i!=index[1]){
    		    centerNewConverge[count]=centerold[i];
    		    count++;
    		   }
    	   }
    	   
    	   // combine 2 center's item to one 
    	   
    	   Object [] vecKnew =new Object [k];
    	   for(int i=0;i<k;i++){
    		   vecKnew[i] = new Vector();
    	   }
    	   
    	   int l1 = ((Vector)veckold[index[0]]).size();
    	   int l2 = ((Vector)veckold[index[1]]).size();
    	   for(int i=0;i<l1;i++){
    		  int id = (Integer) ((Vector) veckold[index[0]]).get(i);
    	       ((Vector)vecKnew[0]).add(id);
    	   }
    	   
    	   for(int i=0;i<l2;i++){
     		  int id = (Integer) ((Vector) veckold[index[1]]).get(i);
     	       ((Vector)vecKnew[0]).add(id);
     	   }
    	   int count2=1;
    	   for(int i = 0;i<veckold.length;i++){
    		   if(i!=index[0]&&i!=index[1]){
    			   vecKnew[count2]=veckold[i];
    		    count2++;
    		   }
    	   }
    	   
    	   this.centertemp = centerNewConverge;
    	   return vecKnew;
       }
	
}
