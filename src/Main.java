
//package 
//import java.io.BufferedReader;
 

import java.awt.Event;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

 




public class Main {
	
	//change big data set you should change 2 things read in data and this.
//	static int NU=3000  , NI=8594  ;
	static int NU=943  , NI=1682  ;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
          
		Main rd = new Main();
		int [][] predata_buffer =new int [NU][NI];

		
		// read in 10M data file 
//		Read10Mdata rdm = new Read10Mdata();
//	    rdm.read(3000);
//		int ni = rdm.ni;
//	    NI= ni;
//	    System.out.println("NI="+ni);
	   
		// readin data
		Readindata rid = rd.new Readindata();
		rid.runreadindata();
		int fold = 5; // 5 fold cross validation. 
		int [][] oridata_buffer = rid.oridata_buffer;
		int [][] time_buffer = rid.time_buffer;
		int [] usertest = rid.split(oridata_buffer, time_buffer, fold);
		int [][] test = rid.test;
		
		//**********************************************************************//
		//******************** five fold cross validation **********************//
		//**********************************************************************//		
		double rmse = 0;
		for(int f=0;f<fold;f++){
		// get the training rating matrix 
		rid.trainMat(oridata_buffer,time_buffer, test[f], usertest, fold);
		int [][] data_buffer = rid.data_buffer;
		int [][] time_bufferT = rid.time_bufferT;
		// statistic the time characteristic of data
//		stat s = rd.new stat();
//		s.runStat(oridata_buffer, time_buffer);
		
		// statistic data 
		rid.statistic(data_buffer);
		double [] ratingMean = rid.ratingMean;
	    double overallMean = rid.overallMean;
	    double [] bi = rid.bi ;                    
		double [] bu = rid.bu ;    
		
//		// baseline 
		 
		// DYNAMIC MODEL
//		baselinePredictor bp =  rd.new baselinePredictor();
//		bp.timechangingfactor(time_buffer,time_bufferT, oridata_buffer, data_buffer, overallMean, bu, bi, usertest,test[f], fold);
//		System.out.println("end of time changing factor");
		
//		bp.Userlinearmodel(time_buffer, time_bufferT, oridata_buffer, data_buffer,  overallMean, bu, bi, usertest, test[f], fold);
		
//		bp.baslineItemtime(time_buffer, time_bufferT, oridata_buffer, data_buffer,overallMean, bu, bi, usertest, test[f], fold);
		
		
		// STASTIC MODEL
		
//		bp.funkSVD(time_bufferT, oridata_buffer, data_buffer, overallMean, bu, bi, usertest, test[f], fold);
//		System.out.println("end of funkSVD");
		
//		bp.baslinepredictor(time_bufferT, oridata_buffer, data_buffer,overallMean, bu, bi,  usertest, test[f], fold);
		
//		bp.baslineSVD(time_bufferT, oridata_buffer, data_buffer, overallMean, bu, bi, usertest, test[f], fold);
//		System.out.println("end of baselinesvd");
		
		
//		rmse+=bp.rmse;
		
		// kmeans
		int k = 3;
		kmeans km = rd.new kmeans();
		int chooseway =0;
	    km.runkmeans(data_buffer, overallMean,bu, bi, k,ratingMean,chooseway);
	    int [] label = km.label;                        // label each item to his category.
	    
	    // rating matrix and statistic parameters
	    int num = 20;
	    ratingMatrix  rm= rd.new ratingMatrix();
	    rm.runRatingStatistic(k, time_buffer, data_buffer, label,num,ratingMean,overallMean,bi);
	    int [][][] ratingMatAlluser = rm.ratingMatAlluser;
    	double [][] ratingMeanKclas= rm.ratingMeanKclas;                
	    double [] bik = rm.bik ; 
		double [][][] ratingMatAlluserFull = rm.ratingMatAlluserFull;

//	    // k nearest neighbors
	    knn knn = rd.new knn();
	    knn.runKnn(data_buffer, ratingMatAlluserFull, bi, bu, bik, ratingMean, ratingMatAlluser, k, overallMean, ratingMeanKclas, num,time_bufferT
	    		,label,oridata_buffer,usertest, test[f],  fold);
	     
		rmse+=knn.rmse;
		
		}
		
		rmse = rmse/fold;
		System.out.println(rmse);

 
	}
		
     public class Readindata{		
 
    	
    	 public int [][] time_buffer = new int [NU][NI];
    	 public int [][] oridata_buffer = new int [NU][NI];
 
    	 
    	 public void runreadindata(){
		/* READ the data from txt. */
		int i1=0,j1=0,k1=0;
		int [][] data_buffer = new int [NU][NI]; 						      // user id , item id , time stamp
		int [][] time_buffer = new int [NU][NI];
		
		File datafile = new File("C:\\Users\\Lin\\Desktop\\movieLens 100k data\\u1alldata.txt");
//		File datafile = new File("C:\\Users\\Lin\\Desktop\\movieLens 100k data\\10Msampledratings.txt");
		Reader reader =null;
		if (datafile.exists()&&datafile.isFile()){
//			System.out.println("read data from u1.base");
		}
		else{
			System.out.println("the file doesn't exist");
		}
//		System.out.println("文件");
		try{

			reader = new InputStreamReader(new FileInputStream(datafile));    // read one char once 

			int tempchar,n;
			char c;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                	
                   // System.out.print((char) tempchar);
                    c=(char)tempchar;
                    n=Character.getNumericValue(c);
                    if((tempchar!='\t')&&(k1==0))  							  // index of user 
                    i1=n+i1*10;
                    else if((tempchar=='\t')&&(k1==0))
                     { k1++;}
                    else if((tempchar!='\t')&&(k1==1)) 
                    { j1=n+j1*10;}                          				  // index of item
                    else if((tempchar=='\t')&&(k1==1))
                    	 {k1++;}
                    else if ((tempchar!='\t')&&(k1==2))
                    { data_buffer[i1-1][j1-1]=n+data_buffer[i1-1][j1-1]*10;   // rating 
                     // System.out.format("%d",data_buffer[i1-1][j1-1]);
                    }
                    else if ((tempchar=='\t')&&(k1==2))
                    { k1++; }
                    else if ((tempchar!='\n')&&(k1==3))       	
                    {time_buffer[i1-1][j1-1]=n+time_buffer[i1-1][j1-1]*10;    // time stamp
                    //System.out.format("%d",time_buffer[i1-1][j1-1]);
                    }
                                    
                    else if ((tempchar=='\n')&&(k1==3))
                    {k1=0;i1=0;j1=0;}
                }
                }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		this.oridata_buffer=data_buffer;
		this.time_buffer=time_buffer;
    	 }
			
  

    	 int [][] test;
    	 public int [] split(int [][] data_buffer, int [][] time_buffer, int fold){
    		int NU = data_buffer.length;
    		int NI = data_buffer[0].length;
    		int [] usertest = new int [NU*fold];
    		int [][] data_buffer1 = new int [NU][NI];
    		int [][] test = new int [fold][NU];
    		
    		for(int i=0;i<NU;i++){
    			for(int j=0;j<NI;j++){
    				data_buffer1[i][j]=data_buffer[i][j];
    			}
    				
    		}
    		for(int i=0;i<NU;i++){
    			
    			removeZeroArray rem = new removeZeroArray();
    			int [] timeWithoutZero =rem.getArrayNoZeroNew(time_buffer[i]);
    			int [] index = rem.index;
    			int len =timeWithoutZero.length;
    			int [] timeSorted = new int [len];
    			int [] indexSorted = new int [len];
    			
    			Sort s = new Sort();
    			
    			timeSorted = s.getSortedArray(timeWithoutZero,index);
    			indexSorted = s.sortedIndex;
    			
    			for(int j=0;j<fold;j++){ 
    			usertest[i*fold+j] = indexSorted[len-1-j];
    			}
    		}		
    	    
    		// get the test id in array usertest.
    		Set<Integer> testset = new HashSet<Integer>();
    		ArrayList<Integer> testList = new ArrayList<Integer>();
    		Random rand = new Random();
    		rand.setSeed(555L); 
    		while(testset.size()<(fold-1)*NU){
    			int temp = rand.nextInt(fold*NU);   // get an presudo random number between 0 to fold*NU.
    			if(!testset.contains(temp)){
    				testList.add(temp);
    			}
    			testset.add(temp);
    		}
    		int indextest =0;
    		for(int i=0;i<fold*NU;i++){
    			if(!testset.contains(i)){
    				test[0][indextest]=i;
    				indextest++;
    			}
    		}
    		Object [] arraytest = new Object [testset.size()];
    		testList.toArray(arraytest);
    		indextest=0;
    		for(int i=1;i<fold;i++){
    			for(int j=0;j<NU;j++){
    				test[i][j]=(Integer) arraytest[indextest];
    				indextest++;
    			}
    		}
 
    		this.test=test;
    	    return usertest;
    	}
    			
    	 double [] ratingMean;
    	 double overallMean;
    	 double [] bi  ;                    
 		 double [] bu  ;  
    	 public void statistic(int [][] data_buffer){
    		// get rating mean of each user
    		double [] ratingMean = new double [NU];                     // store mean of rating of each user store in ratingMean array.
    		removeZeroArray rem23 = new removeZeroArray();
    		IntegerArrayCalculator inac23 = new IntegerArrayCalculator();
    		// calculate rating mean of each user. 	
    		for(int i=0;i<NU;i++)
    		{
    			 ratingMean[i]=inac23.getMean(rem23.getArrayNoZeroNew(data_buffer[i]));
    		}
    		
    		double overallMean = inac23.getMean(ratingMean);
    		
    		
    		// get bu, bi, the bias of user and items.
    		double [] bi = new double [NI];                    // store the deviations of item respectively from the overall mean.
    		double [] bu = new double [NU];                    // store the deviations of user respectively from the overall mean. 
    		
    		double lambda3=5,lambda2=5;                        // INPUT: THOSE PARAMETERS SHOULD BE ADJUSTED.
    		for(int i=0; i<NI;i++)
    		{
    			double tempbi = 0;
    			int countforbi = 0;
    			for(int ii=0;ii<NU;ii++) 
    			{
    				if(data_buffer[ii][i]!=0)
    				{
    				tempbi+=data_buffer[ii][i]-overallMean;
    				countforbi++;
    				}
    				
    			}
    			
    			bi[i]=tempbi/(lambda2+countforbi);             // NOTICE: value in this array may exceed the range of rating, which means -5~5.
    		}
    		
    		for(int i=0;i<NU;i++)
    		{
    			double tempbu =0;
    			int countforbu=0;
    			for(int ii=0;ii<NI;ii++)
    			{
    				if(data_buffer[i][ii]!=0)
    				{
    					tempbu+=data_buffer[i][ii]-overallMean-bi[ii];
    					countforbu++;
    					
    				}
    		
    			}
    			bu[i]=tempbu/(lambda3+countforbu);
    			
    		}
    		
    
    		this.bi =bi;
    		this.bu=bu;
    		this.overallMean= overallMean;
    		this.ratingMean = ratingMean;
    	 }
    		
    	 public int [][] data_buffer = new int [NU][NI]; 
    	 public int [][] time_bufferT = new int [NU][NI]; // time_buffer for training. 
    	 public void trainMat(int [][] oridata_buffer,int [][] time_buffer, int [] test, int [] usertest,int fold){
    		 int [][] data_buffer = new int [NU][NI];
    		 int [][] time_bufferT= new int [NU][NI];
    		 int len = test.length;
    		 for(int i=0;i<NU;i++){
    			 for(int j=0;j<NI;j++){
    				 data_buffer[i][j]=oridata_buffer[i][j];
    				 time_bufferT[i][j]= time_buffer[i][j];
    			 }
    		 }
    		 for(int i=0;i<len;i++){
    			 int utid = test[i];
    			 int userid = utid/fold;
    		    data_buffer[userid][usertest[utid]]=0;
    		    time_bufferT[userid][usertest[utid]]=0;
    		 }
    		 this.data_buffer=data_buffer;
    		 this.time_bufferT=time_bufferT;
    	 }
     }
		
		//System.out.print("part 1");
		

/*	K-Means: vecK store the index of item of each category	
		     means store the k centers
		      */
		//get input matrix for kmeans
     public class kmeans{
    	 public int [] label = new int [NI];
    	 
    	 public void runkmeans(int [][] data_buffer,double overallMean, double [] bu,double []bi,int k, double [] ratingMean, int chooseway){ 
    		 int NU=data_buffer.length;
    		 int NI=data_buffer[0].length;
    		 double [][] data_buffer1 = new double [NI][NU];
    		 for(int i=0;i<NU;i++){
    			 for(int j=0;j<NI;j++){
    				 if(data_buffer[i][j]!=0){
    				 data_buffer1[j][i]=data_buffer[i][j];
    				 }
    				 else
    					 data_buffer1[j][i]=overallMean+bu[i]+bi[j];
//    					 data_buffer1[j][i]=(int)ratingMean[i];
    			 }
    		 }
    		 
    		 
    		 
//		tranMatrix tm= new tranMatrix();
//		int [][] data =tm.transpose(data_buffer);
                                                                // This represent the k categories
        int ktemp = 3*k;
		KMeans km=new KMeans(data_buffer1, ktemp);
    	 
		km.iniCenter(chooseway);           // There are 3 different way to initialize k centers. 0 represent randomly, 1 fixed, 2 use old one.
		km.getMeans(1000, 0.5);
//		Object [] vecK= km.vecK;
//		double [][] centerold = km.centerNew;
		Object [] vecK1=  km.vecK;;
		double [][] centeroldtemp= km.centerNew;
	 
		while(ktemp>k){
			ktemp--;
		    vecK1 = km.combine(ktemp, centeroldtemp, vecK1);
		    centeroldtemp = km.centertemp;            
		}
		Object [] vecK = vecK1;
		String [] vecKS =new String [k];
		for(int i=0;i<k;i++){
			vecKS[i]=vecK[i].toString();
			vecKS[i]=vecKS[i].substring(1,vecKS[i].length()-1);
			vecKS[i]=vecKS[i].replace(",","");
		}
    	 
	//**********************************************************************//
	//*****************Get the RATING TIME MATRIX **************************//
	//**********************************************************************//		
		// label each item to its category, store the category into int array label[]
		String [][] label1 = new String [k][];
		int [] label = new int [NI];
		for(int i=0;i<k; i++){
			 label1[i]=vecKS[i].split(" ");	 
			 int len= label1[i].length;
			 System.out.println(len);
			 for(int j=0;j<len;j++){
				 int index = Integer.parseInt(label1[i][j]);
				 label[index]=i;
			 }
			
		   }
    	
		this.label=label;
    	 
    	 }	
     }
     
     
     public class ratingMatrix{
		// System.out.print("part 2");
		// Form rating time matrices for all user. 
    	 int [][][] ratingMatAlluser;
//    	 double [] ratingMean;
    	 double [][] ratingMeanKclas;
//    	 double [] bi  ;                    
// 		 double [] bu  ;                    
 	     double [] bik  ; 
//    	 double overallMean;
    	 double [][][] ratingMatAlluserFull;
    	 int [][] markRealRating;
    	 int [ ] userTest ; 
    
    	 int [][] oridata_buffer= new int [NU][NI];
    	 public void runRatingStatistic(int k ,int [][] time_buffer,int [][] data_buffer, int [] label,int num, double [] ratingMean,double overallMean, double [] bi){
    		 
		                                             // NOIICE: num is the number of row of each user's matrix
		int [][][] ratingMatAlluser = new int [NU][num][k];
		FormRatingTimeMat   frt = new FormRatingTimeMat();
		frt.getRatTimeMat(time_buffer,label,data_buffer,num,k);       
		ratingMatAlluser = frt.ratingTimeMat;						// the bigger the id of second dimension, the fresher the rating records. 
//		userTest = frt.userTest;                                    // record the user's testing item index.
		
  
		IntegerArrayCalculator inac23 = new IntegerArrayCalculator();
	 
		
		// calculate each category's rating mean for all user.
		double [][] ratingMeanKclas = new double [NU][k];           // store mean of each category's rating of all user.
		
 
		for(int i=0;i<NU; i++)
		{
			ArrayList<Integer> [] category = new ArrayList [k];
			for(int i11=0;i11<k;i11++)category[i11]=new ArrayList<Integer>();
			
			for(int j=0;j<NI;j++)
			{
				if(data_buffer[i][j]!=0)
				{
					category[label[j]].add(data_buffer[i][j]);
				}
			}
			
			for(int p=0;p<k;p++)
			{

				if(category[p].size()!=0)
				{
					Object [] temp = new Object [category[p].size()];
					temp = category[p].toArray();
					ratingMeanKclas[i][p]= inac23.getMean(temp);
				}
				else
				{
					ratingMeanKclas[i][p]=ratingMean[i];
 
				}
			}
			
		}
 
    

		//**********************************************************************//
		//***************** Statistic the statistics of data *******************//
		//**********************************************************************//		
		 
  
		
//		double [] bi = new double [NI];                    // store the deviations of item respectively from the overall mean.
//		double [] bu = new double [NU];                    // store the deviations of user respectively from the overall mean. 
		double [] bik = new double [k];                    // store the deviations of item's categories respectively from the overall mean.
		
//		double lambda3=5,lambda2=5;                        // INPUT: THOSE PARAMETERS SHOULD BE ADJUSTED.
//		for(int i=0; i<NI;i++)
//		{
//			double tempbi = 0;
//			int countforbi = 0;
//			for(int ii=0;ii<NU;ii++) 
//			{
//				if(data_buffer[ii][i]!=0)
//				{
//				tempbi+=data_buffer[ii][i]-overallMean;
//				countforbi++;
//				}
//				
//			}
//			
//			bi[i]=tempbi/(lambda2+countforbi);             // NOTICE: value in this array may exceed the range of rating, which means -5~5.
//		}
//		
//		for(int i=0;i<NU;i++)
//		{
//			double tempbu =0;
//			int countforbu=0;
//			for(int ii=0;ii<NI;ii++)
//			{
//				if(data_buffer[i][ii]!=0)
//				{
//					tempbu+=data_buffer[i][ii]-overallMean-bi[ii];
//					countforbu++;
//					
//				}
//		
//			}
//			bu[i]=tempbu/(lambda3+countforbu);
//			
//		}
		
		int [] countforKclas = new int [k];
		// method 1. without modify 
		for(int i=0;i<NI;i++)
		{
			bik[label[i]]+=bi[i];
			countforKclas[label[i]]++;
		}
		for(int i=0;i<k;i++)
		{
			bik[i]=(double)bik[i]/countforKclas[i];
		}
		
		// end of method 1.


		
		// form the rating matrix to be used as the feature matrix. 
		// put the mean of rating to replace missing value in the rating matrix. 
		// the new matrix(ratingMatAlluserFull) will be used in finding neighbor.
		
		double [][][] ratingMatAlluserFull = new double [NU][num][k];
		int [][] markRealRating = new int [NU][num];                      // this array record the real rating's category of user at each moment.
		for(int i=0;i<NU;i++)for(int j=0;j<num;j++)for(int b=0;b<k;b++)
		{
		    if(ratingMatAlluser[i][j][b]==0)
		    {
				  ratingMatAlluserFull[i][j][b]=ratingMeanKclas[i][b];    // using mean of each category of this user to replace 0 element.
//				  ratingMatAlluserFull[i][j][b]=ratingMean[i];
			}
			else
			{
			    ratingMatAlluserFull[i][j][b]=ratingMatAlluser[i][j][b];
			    markRealRating[i][j]=b;
			}
		}
    	 
		 this.ratingMatAlluser = ratingMatAlluser;
//    	 this.ratingMean=ratingMean;
    	 this.ratingMeanKclas=ratingMeanKclas;
//    	 this.bi =bi ;                    
// 		 this.bu =bu ;                    
 	     this.bik =bik ; 
//    	 this.overallMean = overallMean;
    	 this.ratingMatAlluserFull= ratingMatAlluserFull;
    	 this.markRealRating = markRealRating;
		
    	 }
    	
    	 
     }
		
//		System.out.print("part 3");                                       // this mark is to notice the relative position, don't delete or change this.
		 
		public class knn{
	//**********************************************************************//
	//******* Find k nearest neighbors and get weighted matrix**************//
	//**********************************************************************//		
		int [][] predata_buffer = new int [NU][NI];	
		double rmse =0;
		public void runKnn(int [][] data_buffer,double [][][] ratingMatAlluserFull,double [] bi,double [] bu,double [] bik,double [] ratingMean
				, int[][][] ratingMatAlluser, int k,double overallMean, double [][] ratingMeanKclas,int num, int [][] time_buffer, int [] label, int [][] oridatabuffer
				,int [] usertest, int [] test, int fold) throws IOException{	
	
			int numOfNeigh=12;                              // INPUT: TARGET USER'S ID, TARGET

			 
			// step 1: initialize 
            // Following data using for training model parameters.
			Matrix [] a =new Matrix [NU];                   // store all users' model parameters
			Matrix  [] X  = new Matrix [NU] ;               // store all users' training samples
			int [][] allNeiID = new int [NU][numOfNeigh];   // all user's neighbors' ID
			double [][] allNeiSimi = new double [NU][numOfNeigh]; // user's similarity between it's neighbors
			Matrix [] Y = new Matrix [NU];       // should be NU column vectors
 
			
			double Matdisweight = 0.15;
			double weight3 = 1.5;
			double weight2 = 1;
			// initialize model a and label Y
			for(int i=0;i<NU;i++){
			    		a[i] = Matrix.random(k*k, 1);
			}
			
			// Following data using for test model, and get RMSE.
			Matrix []  xLast = new Matrix [NU];  // the last input data
//			double [] Ytest = new double [NU];   // the last output data
	  
			Matrix Yone = new Matrix(k,1,1);
  	        Yone=Yone.times(weight3);
//  	   Yone.print(1, 1);
  		   
  		// TO regularize the original rating matrix using estimated way.
  		   double [][] regdatabuffer = new double [NU][NI];  // this contain all the information from original data set
   		   for(int i=0;i<NU;i++){
   			  for(int j=0;j<NI;j++){
   				if(data_buffer[i][j]!=0)
   				regdatabuffer[i][j]=data_buffer[i][j]-overallMean-bu[i]-bi[j];
   			  }
   		    }
  		
 
			findNeighbor fn = new findNeighbor();
			int [] numAdapt = new int [NU];
			int [][] markRealRating = new int [NU][];
			for(int i =0;i<NU;i++){
				// initialize neighbors' ID, neighbors' similarity
				allNeiID[i]=fn.getNeighborbyclass(ratingMeanKclas, numOfNeigh, i);//getNeighbor(ratingMatAlluserFull, numOfNeigh, i,3);		// Measure similarity, 1 represent euclidean distance, 2 represent dCov.	3 represent cosine similarity between matrix.
                allNeiSimi[i]=fn.reSortedSimi;
				
        		
        	
                double [][] weightedMatrix = null; 
//              double [][] weightedMatrixknn = null;
                weightedMatrix wm = new weightedMatrix();
//        		weightedMatrixknn = wm.weightMatrix(regratingMatAlluser, allNeiID[i], i, allNeiSimi[i],ratingMean,ratingMeanKclas,bu,bik,nij,overallMean,4);// 1.put neighbors into weighted matrix. 2.only use target user's information 
                weightedMatrix = wm.userMatrix(time_buffer, regdatabuffer, label, i, k, num, ratingMeanKclas[i]);
                num=wm.num;                               // weightedMatrix 矩阵的行数
                numAdapt[i]=num;
                markRealRating[i]=wm.markRealRating;
//                Y[i] = new Matrix(k*(k+1)+num-1,1);
        	    X[i] = new Matrix(num-1,k*k);
	    		Y[i]= new Matrix(num-1+k*k,1);
 
	    		
 
        		for(int j=0;j<num-1;j++){
        			int index1 = markRealRating[i][j+1];
        		 
        		   Matrix tempUserdata = new Matrix(weightedMatrix[j],1);    // convert 1-D array to matrix.
        		   Matrix tempmat = new Matrix(num-1,k*k);
        		   tempmat.setMatrix(j, j, index1*k, index1*k+k-1, tempUserdata);
        	 
        		   tempmat=tempmat.times(Matdisweight);

        		   X[i]=X[i].plus(tempmat);   
        		
        		   Y[i].set(j, 0, weightedMatrix[j+1][index1]*Matdisweight);
        		}  
        		   
//        		
        		   Matrix temp = new Matrix(k*k,1);
        		   for(int j1=0;j1<numOfNeigh;j1++){
        			   int neiId = allNeiID[i][j1]; 
        			   double simi = allNeiSimi[i][j1];
        			   Matrix B = a[neiId].times(simi);
        			   temp=temp.plus(B);
        			 
        		   }
//        		   
        		   temp= temp.times(weight2);
        		   Y[i].setMatrix(num-1, num-1+k*k-1, 0, 0, temp);
//        		  
//        		   Y[i].setMatrix(num-1+k*k, num-1+k*(k+1)-1, 0, 0, Yone);
        		   
        		   
        		   // Get the test data 
        		    
                   // each user's last preference vector xLast
        		   xLast[i]= new Matrix(1,k); 
        		   Matrix tempUserdata = new Matrix(weightedMatrix[num-1],1);    // convert 1-D array to matrix.
            	   xLast[i]=xLast[i].plus(tempUserdata);
        		   
        		   
//        		   int indexlastout = usertest[i];
//        		   Ytest[i]=regdatabuffer[i][indexlastout];
        		   
        		   
			}
			
			
			
			// step 2, get the presudo inverse of data matrix X[i].
			int Nmax = 3;
			Matrix Xdown = new Matrix(k*k,k*k); 
			Xdown = Xdown.identity(k*k, k*k);
		    Xdown = Xdown.times(weight2);
			Matrix Xdown1 = new Matrix(k,k*k); 
			Matrix Xone = new Matrix(1,k,1);
			Xone = Xone.times(weight3);
			
			for(int j=0;j<k;j++){
				Xdown1.setMatrix(j,j, j*k, (j+1)*k-1, Xone);
    		}
			 
			Matrix XPseudo ;
			Matrix Xall;
	 
			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			int second = c.get(Calendar.SECOND);
			System.out.println("time of start presudo inverse: " +hour + ":" +minute + ":" + second+"s"); 
			
	
			
			CosineSimi cs = new CosineSimi();
			int glen =1;
			
			Matrix [][] GBOpt = new Matrix [glen][NU];
			
			for(int modelnum=0; modelnum<glen; modelnum++){
			int converge =0;
		    int N=0;
		    for(int i=0;i<NU;i++){
	    		a[i] =new  Matrix(k*k, 1,(double)1/k);              // using constant initialize to get the comparable results.
	    		}
			while(N<Nmax){
			
				Matrix [] storeA = a.clone();
	
				for(int i=0;i<NU;i++){
					
					
					// get data matrix for current user
					Xall = new Matrix(numAdapt[i]-1+k*k,k*k);
//					Xall = new Matrix(k*(k+1)+numAdapt[i]-1,k*k);
					Xall.setMatrix(0, numAdapt[i]-1-1, 0, k*k-1, X[i]);
					Xall.setMatrix(numAdapt[i]-1, k*k-1+numAdapt[i]-1, 0, k*k-1, Xdown);
//					Xall.setMatrix(k*k+numAdapt[i]-1, numAdapt[i]-1+k*(k+1)-1, 0, k*k-1, Xdown1);
//					
					
					// get pseudo inverse  
					Matrix Xallt = Xall.transpose();
					Matrix XalltXall = Xallt.times(Xall);
					XPseudo = new Matrix(k*k,k*k);
					XPseudo = XalltXall.inverse();
								
					a[i]=XPseudo.times(Xallt).times(Y[i]);
					
			
				    // update Y
				    if(converge ==0){
					 for(int i1=0;i1<NU;i1++){
						int mark=0;
						for(int jj=0;jj<numOfNeigh;jj++) 
						{
							if(allNeiID[i1][jj]==i)
								mark=1;
						}
						if(mark==1){
							Matrix temp = new Matrix(k*k,1);
							for(int j1=0;j1<numOfNeigh;j1++){
								int neiId = allNeiID[i1][j1]; 
								double simi = allNeiSimi[i1][j1];
								Matrix B = a[neiId].times(simi);
								temp=temp.plus(B);
							}
							temp=temp.times(weight2);
							Y[i1].setMatrix(numAdapt[i1]-1,numAdapt[i1]-1+k*k-1, 0, 0, temp);
						}
					 }
				    }
				    else if(converge!=0){
				    	for(int i1=0;i1<NU;i1++){
							int mark=0;
							
							for(int jj=0;jj<numOfNeigh;jj++) 
							{
								if(allNeiID[i1][jj]==i)
									mark=1;
							}
							if(mark==1){
								double [] simia = new double[k];
								Matrix temp = new Matrix(k*k,1);	
								Matrix ati1 = a[i1].transpose();
								double [][] ati1arr= ati1.getArray();
								
								double [] simi = new double [numOfNeigh];
								double tempsum = 0;
								for(int j1=0;j1<numOfNeigh;j1++){
									int neiId = allNeiID[i1][j1]; 
									Matrix atneiId = a[neiId].transpose();
									double [][] atneiIdarr = atneiId.getArray();
									simi[j1] = cs.getPcorrelation(ati1arr[0], atneiIdarr[0]);
//									simi[j1] = cs.getCosineSimi(ati1arr[0], atneiIdarr[0]);
									tempsum+=simi[j1];
								}
								
								for(int j1=0;j1<numOfNeigh;j1++){
									int neiId = allNeiID[i1][j1]; 
									simi[j1]=simi[j1]/tempsum;       // l2 normalization 
									Matrix B = a[neiId].times(simi[j1]);
									temp=temp.plus(B);
								}
								temp=temp.times(weight2);
								Y[i1].setMatrix(numAdapt[i1]-1,numAdapt[i1]-1+k*k-1, 0, 0, temp);
							}
						}
				    }
				}
				
				double normF =0;
				for(int i=0;i<NU;i++){
					Matrix temp = storeA[i].minus(a[i]);
					double [][] temparr = temp.getArray();
					normF += temp.normF();
				}
				
				int threhold=200;
				if(modelnum!=0){
					threhold=250;
				}
				if(normF<threhold){
					System.out.println("N: "+N);
					System.out.println("normF: "+normF);
					
				 
					converge ++;
//					if(converge==3)
//					break;
				}

				System.out.println(N+"st iteration: "+normF);
     		    N++;
     		    
			}
			GBOpt[modelnum]=a;
 
			
			}
			Matrix atemp  ;
		 
 
			// make prediction.
			double rmse =0;
			int lentest = test.length;
			 for(int i=0;i<lentest;i++){
    			 int utid = test[i];
    			 int userid = utid/fold;
    			 int itemid = usertest[utid];
    			 double real=oridatabuffer[userid][itemid]-overallMean-bu[userid]-bi[itemid];
    			 int category = label[itemid];
    			 Matrix xlastvector = new Matrix(1,k*k);
    			 xlastvector.setMatrix(0, 0, category*k, category*k+k-1, xLast[userid]);
    			 atemp = a[userid];
    			 Matrix yout = xlastvector.times(atemp);
    			 double y = yout.get(0, 0);  
 				 rmse += (real-y)*(real-y);
    		 }
			
//			for(int i=0;i<NU;i++){
////				int index = markRealRating[i][num-1]; 
//				atemp = a[i];
//				Matrix yout = xLast[i].times(atemp);
//				double y = yout.get(0, 0);  
//				rmse += (Ytest[i]-y)*(Ytest[i]-y);
//			}
			
			rmse = Math.sqrt(rmse/lentest);
			
			System.out.println("rmse: "+String.valueOf(rmse));
			
			Calendar c2 = Calendar.getInstance();
			hour = c2.get(Calendar.HOUR_OF_DAY);
			minute = c2.get(Calendar.MINUTE);
			second = c2.get(Calendar.SECOND);
			System.out.println("time of finish testing : " +hour + ":" +minute + ":" + second+"s"); 
			
		    this.rmse= rmse;
		}
	}
		
	
		public class neighborContrast{
			
			public void basline4(int [][] time_buffer,int [] label, int [][] oridata_buffer,int k,int [] usertest){
				FormRatingTimeMat   frt = new FormRatingTimeMat();
				double [] predict = new double[NU];
				
				double rmse=0;
				for(int i=0;i<NU;i++){
				
				int [][] currentMat = frt.getRatTimeMatSpecific(time_buffer,label,oridata_buffer,k,i); 
				int [] markspecific = frt.markspecific;
				int len = currentMat.length;
				
			
				double weight =0;
				for(int j=0;j<len-1;j++){
				   int dis = len-j;
				   int index = markspecific[j];
//				   double e = Math.exp(-dis);
				   double e =1;
				   predict[i]+=e*currentMat[j][index];
				   weight+=e;
				}
				predict[i]=predict[i]/weight	;
				
				int index1= usertest[i];
				int real = oridata_buffer[i][index1];
				double pre = predict[i];
				rmse += (pre-real)*(pre-real);
				
				}
				
				rmse=Math.sqrt(rmse/NU);
					
				System.out.println(rmse);
			}
			
			public void baseline3(int [][] data_buffer,int [][] oridata_buffer, int [][] time_buffer, double overallMean, int [] usertest){
				// using item's historical ratings as the prediction of this item for any user. 
					
				ArrayList<Integer> timeArr = new ArrayList<Integer>();
				int NU= data_buffer.length;
				int NI = data_buffer[0].length;
				removeZeroArray rm = new removeZeroArray();
				IntegerArrayCalculator inac = new IntegerArrayCalculator();
				Sort s = new Sort();
				
				for(int i=0;i<NU;i++){
					for(int j=0;j<NI;j++){
						if(j!=usertest[i]&&time_buffer[i][j]!=0){
						int t = time_buffer[i][j];   // I code moment without the test moment.
						timeArr.add(t);
						}
					}
				}
			 
				// sort all rating moment.
				int len = timeArr.size();
				int [] alltime1 = new int [len];
				int [] index = new int [len];
				
				for(int i=0;i<len;i++){
				   alltime1[i]=timeArr.get(i);
				   index[i]=i;
				}
				
//				int [] alltime = s.getSortedArray(alltime1, index);
				Arrays.sort(alltime1);
				int [] alltime = alltime1;
				// code all rating momen to from zero to len.
				Map time = new HashMap<Integer,Integer>();
				for(int i=0;i<len;i++){
//					System.out.println(alltime[i]);
					time.put(alltime[i], len-1-i);
				}

				double dis =alltime[len-1]- alltime[0];
				int max = alltime[len-1];
				
//				System.out.println(String.valueOf(dis));
				double [] prerating = new double [NI];
				double [] weightcount = new double [NI];
				for(int i=0;i<NU;i++){
					for(int j=0;j<NI;j++){
						if(data_buffer[i][j]!=0){
							int t = (Integer) time.get(time_buffer[i][j]);
//							double w = Math.exp(-(double)t/1000000);
							double w = 1/Math.pow((1+(max-t)*0.001),2);
//							double w = (1+(max-t))
//							double w =1;
							prerating[j]+= data_buffer[i][j]*w;
							weightcount[j]+=w;
						}
					}
				}
				
//				System.out.println("2");
				for(int j=0;j<NI;j++){
					if(weightcount[j]!=0){
						prerating[j]=prerating[j]/weightcount[j];
					}
					else{
						prerating[j]=overallMean;
					}
				}
				
				// give prediction
				double rmse =0;
				for(int i=0;i<NU;i++){
					int index1= usertest[i];
					int real = oridata_buffer[i][index1];
					double pre = prerating[index1];
					rmse += (pre-real)*(pre-real);
				}
                rmse=Math.sqrt(rmse/NU);
				
				System.out.println(rmse);
			}
			
			
			
			public void runNeigh1(int k,int []label,  int [][] databuffer, double overallmean, int []usertest,double [][] ratingMeanKclas, double []ratingMean
					 ){
				// using rating mean to make prediction 
				
				
				
				// give prediction 
				double rmse = 0;
				for(int i=0;i<NU;i++){
					int indexlast =usertest[i]; 
					int index = label[indexlast];
//					double pre = overallmean;
					double pre = ratingMean[i];
//					double pre =ratingMeanKclas[i][index];
					int real = databuffer[i][indexlast];
					rmse+=(pre-real)*(pre-real);
				}
				rmse=Math.sqrt(rmse/NU);
				
				System.out.println(rmse);
				
			}
			
			public void runNeigh(int k, int[] label, int [][] timebuffer,int [][] databuffer, double overallmean, int []usertest,double [][] ratingMeanKclas,int [][][] ratingMatAlluser
					){
				double [][] ratingMat = new double [NU][k];
				removeZeroArray rm = new removeZeroArray();
				IntegerArrayCalculator inac = new IntegerArrayCalculator();
				 
  
				// get the new rating matrix.
				for(int i=0;i<NU;i++){
					double [] numcont = new double[k];
					FormRatingTimeMat   frt = new FormRatingTimeMat();
					int [][] currentMat = frt.getRatTimeMatSpecific(timebuffer,label,databuffer,k,i); 
					int [] markspecific = frt.markspecific;
					int num = currentMat.length;
					for(int j=0;j<num-1;j++){

							double e = Math.exp(-(num-1-j)/10);
						int index = markspecific[j];
						ratingMat[i][index]+=currentMat[j][index]*e;
						if(currentMat[j][index]!=0){
						   numcont[index]+=e;
						}
					}
					for(int j=0;j<k;j++){
						if(numcont[j]!=0){
						ratingMat[i][j]=(double)ratingMat[i][j]/numcont[j];
						}
					}
				}
				
				
				// full fill the rating matrix
				for(int i=0;i<NU;i++){
					for(int j=0;j<k;j++){
						if(ratingMat[i][j]==0){
//							ratingMat[i][j]=overallmean;
							ratingMat[i][j]=ratingMeanKclas[i][j];
						}
					}
				}
				
				// give prediction 
				double rmse = 0;
				for(int i=0;i<NU;i++){
					int indexlast =usertest[i];
					int index = label[indexlast];
					double pre = ratingMat[i][index];
					int real = databuffer[i][indexlast];
					rmse+=(pre-real)*(pre-real);
				}
				rmse=Math.sqrt(rmse/NU);
				
				System.out.println(rmse);
			}
			
		}
		
		
		public class printtrainingdatase{
			
			public void print(int [] usertest, int [][] data_buffer, int [][] time_buffer) throws IOException{
				int NU = data_buffer.length;
				int NI = data_buffer[0].length;
				
				File f = new File("C:\\Users\\linkx\\Desktop\\lenskit-data\\trainingdatawith943testdata.txt");
				
				FileWriter fw = new FileWriter(f);
				
				BufferedWriter out = new BufferedWriter(fw);
				
				try{
				for(int i=0;i<NU;i++){
					for(int j=0;j<NI;j++){
						if(data_buffer[i][j]!=0){
							out.write(i+"\t"+j+"\t"+data_buffer[i][j]+"\t"+time_buffer[i][j]);
							out.newLine();
						}
					}
				}
				out.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}
		
		
		public class svdbaseline{
 
			// accomplish by lenskit SDK
			public void runsvd(int [][] originaldata, double overallMean, int [] usertest){
				
				
				int NU = originaldata.length;
				int NI = originaldata[0].length;
				
				double [][] data = new double [NU][NI];
				for(int i=0;i<NU;i++){
					for(int j=0;j<NI;j++){
						if(originaldata[i][j]==0||j==usertest[i]){
							data[i][j]=overallMean;
						}
						else{
							data[i][j]=originaldata[i][j];
						}
					}
				}
				
				
				Matrix D = new Matrix(data);
				
				SingularValueDecomposition svd = new SingularValueDecomposition(D.transpose());
				
				int numfeavalue = 300;
				Matrix s = svd.getS().getMatrix(0, numfeavalue, 0, numfeavalue);
				Matrix v = svd.getV().getMatrix(0, numfeavalue, 0, numfeavalue);
				Matrix d = svd.getU().getMatrix(0, NI, 0, numfeavalue);
				
				Matrix filldata = d.times(s).times(v.transpose());
				double [][] fd = filldata.transpose().getArray();
 
	        	// get rmse 
				double rmse =0;
				for(int i=0;i<NU;i++){
					for(int j=0;j<NI;j++){
						if(j==usertest[i]){
							rmse+=(data[i][j]-originaldata[i][j])*(data[i][j]-originaldata[i][j]);
						}
					}
				}
				
				rmse = Math.sqrt(rmse/NU);
				System.out.println(rmse);
			}
		
		}


		
		public class readinMatrix{
			
			public Matrix [] runReadinModel() throws IOException{
				Matrix [] a = new Matrix [NU];
				
				File f = new File("C:\\Users\\linkx\\Desktop\\alibaba\\UserModel.txt");
				
				RandomAccessFile raf = new RandomAccessFile(f,"rw");
				try{
				for(int i=0;i<NU;i++){
					a[i] = new Matrix(324,1);
					
					int k = 0;
					String s = null;
					
					s = raf.readLine();
//					while(k!=i){
//						k++;
//						s = raf.readLine();
//					}
					
					String [] sa = s.split("\t");
					int len = sa.length;
					double[][] tempa = new double [len][1];
					
					for(int j=0;j<len;j++){
						tempa[j][0]= Double.parseDouble(sa[j]);
					}
					
					a[i]= new Matrix(tempa);
					
				}
				
				raf.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				return a;
			}
			
			public Matrix [] runReadinY() throws IOException{
				Matrix [] y = new Matrix [NU];
				
                File f = new File("C:\\Users\\linkx\\Desktop\\alibaba\\userY.txt");
				
				RandomAccessFile raf = new RandomAccessFile(f,"rw");
		 
				try{
				for(int i=0;i<NU;i++){
					
					y[i] = new Matrix(360,1);
					int k = 0;
					String s = null;
					
					s = raf.readLine();
//					while(k!=i){
//						k++;
//						s = raf.readLine();
//					}
					
					String [] y1 = s.split("\t");
					int len = y1.length;
					double[][] tempa = new double [len][1];
					
					for(int j=0;j<len;j++){
						tempa[j][0]= Double.parseDouble(y1[j]);
					}
					
					y[i]= new Matrix(tempa);
					
				}
				raf.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				return y ;
			}
			
			
		}
		
		public class storeMatirx{
			
			public void runstoreMatrix(Matrix [] A, Matrix [] Y) throws IOException{
				
				File f = new File("C:\\Users\\linkx\\Desktop\\alibaba\\UserModel.txt");
				FileWriter fa = new FileWriter(f,true);
				
				try{
					for(int i=0;i<A.length;i++){
						
						double [][] a = A[i].getArray();
						
						for(int j=0;j<a.length;j++){
							fa.write(Double.toString(a[j][0])+"\t");
						}
						fa.write("\r\n");
					}
					fa.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
				File f2 = new File("C:\\Users\\linkx\\Desktop\\alibaba\\userY.txt");
				FileWriter fy = new FileWriter(f2,true);
				
				
				try{
					for(int i=0;i<Y.length;i++){
						
						double [][] y = Y[i].getArray();
						
						for(int j=0;j<y.length;j++){
							fy.write(Double.toString(y[j][0])+"\t");
						}
						fy.write("\r\n");
					}
					fy.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			
		}



		public class stat{
			
			public void runStat(int [][] oridata_buffer,int [][] time_buffer) throws IOException{
				
				Set<Integer> time = new HashSet<Integer>();
				
				for(int i=0;i<NU;i++){
					for(int j=0;j<NI;j++){
						if(time_buffer[i][j]!=0)
						time.add(time_buffer[i][j]);
					}
				}
				int len = time.size();
				Object [] a =new Object [len];
				time.toArray(a);
				 
				int [] ta = new int [len];
				for(int i=0;i<len;i++){
					ta[i]=(Integer)a[i];
				}
				
				Arrays.sort(ta);
				
				Map mta = new HashMap();
				
				for(int i=0;i<len;i++)
				mta.put(ta[i], i);
				
				int [] count = new int [len];
				double [] mean = new double [len];
				
				for(int i=0;i<NU;i++){
					for(int j=0;j<NI;j++){
						int t = time_buffer[i][j];
						if(t!=0){
							int index = (Integer) mta.get(t);
							mean[index]+=oridata_buffer[i][j];
							count[index]++;
						}
					}
				}
				
				int ini = ta[0];
				int end = ta[len-1];
				int dayseconds = 86400;
				int day = (end- ini)/dayseconds+1;
				double [] meanday = new double [day];
				int [] countday = new int [day];
				
				for(int i=0;i<len;i++){
					int index = (ta[i]-ini)/dayseconds;
					meanday[index ]+=mean[i];
					countday[index]+=count[i];
				}
				
				for(int i=0;i<day;i++){
					if(countday[i]!=0)
					meanday[i]=(double)meanday[i]/countday[i];
				}
				
//				int internal = 7040;
//				int len1 = len/internal+1;
//				double [] mean1 = new double [len1];
//				
//				for(int i=0;i<len1-1;i++){
//					double temp =0;
//					int tempcount =0;
//					for(int j=i*internal;j<internal+i*internal;j++){
//						temp += mean[j];
//						tempcount+=count[j];
//					}
//					mean1[i]=(double)temp/tempcount;
//				}
//				double temp1=0;
//				int tempcount1=0;
//				for(int j=(len1-1)*internal;j<len;j++){
//					temp1 += mean[j];
//					tempcount1+=count[j];
//				}
//				mean1[len1-1]=(double)temp1/tempcount1;
//				for(int i=0;i<len;i++){
//					if(count[i]!=0){
//					mean[i]=(double)mean[i]/count[i];
//					}
//					else
//						System.out.println("djaldkj");
//				}
				
				// item means movie ages
				double [] prerating = new double [NI];
				double [] weightcount = new double [NI];
				double [] begintime = new double [NI];
				for(int i=0;i<NI;i++){
					begintime[i]=end;
				}
				for(int i=0;i<NU;i++){
					for(int j=0;j<NI;j++){
						if(oridata_buffer[i][j]!=0){
							prerating[j]+= oridata_buffer[i][j];
							weightcount[j]++;
							if(time_buffer[i][j]<begintime[j])
							{
								begintime[j]=time_buffer[i][j];
							}
						}
					}
				}
				
				for(int i=0;i<NI;i++){
					prerating[i]=prerating[i]/weightcount[i];
					begintime[i] = (int)(begintime[i]-ini)/dayseconds;
				}
				
				double [] c = new double [day] ;
				double [] movieday = new double [day];
				for(int i=0;i<NI;i++){
					int index = (int)begintime[i];
					movieday[index]+=prerating[i]*weightcount[i];
					c[index]+=weightcount[i];
				}
		
				for(int i=0;i<day;i++){
					if(c[i]!=0)
					movieday[i]=movieday[i]/c[i]; 
				}
				
				
				
				File f2 = new File("C:\\Users\\linkx\\Desktop\\meantime.txt");
                FileWriter fy = new FileWriter(f2);
				
				
				try{
					for(int i=0;i<day;i++){
//						if(movieday[i]!=0)		 
			    		fy.write(Double.toString(movieday[i])+"\r\n");
					
//						fy.write("\r\n");
					}
					fy.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				System.out.println("end");
			}
			
			
			
			int [] time =null;
			Map maptime = null;
			public void timeorder(int [][] time_buffer){
				
                Set<Integer> time = new HashSet<Integer>();
				
				for(int i=0;i<NU;i++){
					for(int j=0;j<NI;j++){
						if(time_buffer[i][j]!=0)
						time.add(time_buffer[i][j]);
					}
				}
				int len = time.size();
				Object [] a =new Object [len];
				time.toArray(a);
				 
				int [] ta = new int [len];
				for(int i=0;i<len;i++){
					ta[i]=(Integer)a[i];
				}
				
				Arrays.sort(ta);
				
				Map mta = new HashMap();
				
				for(int i=0;i<len;i++)
				mta.put(ta[i], i);
				
				
				this.time= ta;
				this.maptime = mta;
			}
			
		}

		public class baselinePredictor{
			
			double [] bu = new double [NU];
			double [] bi = new double [NI];
			double rmse=0;
			public void baslinepredictor(int [][] time_buffer,int [][] oridata_buffer, int [][] data_buffer, double mu, double [] buold,double [] biold,
					int [] usertest, int [] test, int fold){
				
				double [] bu = new double [NU];
				double [] bi = new double [NI];
				
			    double oldbu =0;
			    double oldbi =0;
			    double err =0;
			    double step = 0.0005;
			    double lambda = 0.02; // regularization term parameter.
			    int loop =0;
			    int max = 200;   // the iternation time of 
			    
			    
			    // initialize 
			    bu= buold;
			    bi= biold;
			    
			    while(loop<max){
			    	
			    	for(int i=0;i<NU;i++){
			    		for(int j=0;j<NI;j++){
			    			if(data_buffer[i][j]!=0){
			    				
			    				err=data_buffer[i][j]-(mu+bu[i]+bi[j]);
			    				// updata bu
			    				oldbu=bu[i];
			    				bu[i]=oldbu+step*(err-lambda*bu[i]);
			    				
			    				// update bi
			    				oldbi=bi[j];
			    				bi[j]=oldbi+step*(err-lambda*bi[j]);
			    			
			    			}
			    		}
			    	}
			    	
			    	
			    	loop++;
			    }
				
			    // make prediciton
			    double rmse = 0;
			    int lentest = test.length;
			    
			    for(int i=0;i<lentest;i++){
			    	int utid=test[i];
			    	int userid = utid/fold;
			    	int itemid = usertest[utid];
//			        int id = usertest[i];
			        
			        double pre = mu+bu[userid]+bi[itemid];
			        double real = oridata_buffer[userid][itemid];
			        
			        rmse+=(pre-real)*(pre-real);
				         
			    }
			    rmse=Math.sqrt(rmse/lentest);
			    System.out.println(rmse);
			    this.rmse=rmse;
			}
			
			
			public void baslineItemtime(int [][] oritime_buffer,int [][] time_buffer,int [][] oridata_buffer, int [][] data_buffer, double mu, double [] buold,double [] biold,
					int [] usertest, int [] test, int fold){
				int binnumber = 100;    // seven months set an 
				double [] bu = new double [NU];
				double [] bi = new double [NI];
				double [][] bit = new double [NI][binnumber];
			    double oldbu =0;
			    double oldbi =0;
			    double err =0;
			    double step = 0.0005;
			    double lambda = 0.002; // regularization term parameter.
			    int loop =0;
			    int max = 80;   // the iternation time of 
			    
			    
			  
			    // map time to bins 
			    stat s = new stat();
			    s.timeorder(oritime_buffer);
			    int [] ta = s.time;
			    Map mb = new HashMap();
			    int len = ta.length;                // number of moments.
			   
			    int mint = ta[0];
				int maxt = ta[len-1];
				
//				int bin = 2592000 ;            // the length of each bin, 30 days seconds. 
				int bin = (maxt-mint)/binnumber;
			    for(int i=0;i<len;i++){
			    	int tempbin = (ta[i]-mint)/bin;
			    	if(tempbin==binnumber){
			    		tempbin=binnumber-1;
//			    		System.out.println("6");
			    		
			    	}
			    	mb.put(ta[i], tempbin);
			    }
			    
			    // initialize 
			    bu= buold;
			    bi= biold;
			    
			    while(loop<max){
			    	
			    	for(int i=0;i<NU;i++){
			    		for(int j=0;j<NI;j++){
			    			
			    			if(data_buffer[i][j]!=0){
			    				
			    				// get the current time 
			    				int currenttime = time_buffer[i][j];
			    				int currentbin = (Integer) mb.get(currenttime);
			    				// calculate the error.
			    				err=data_buffer[i][j]-(mu+bu[i]+bi[j]+bit[j][currentbin]);
			    				
			    				// updata bu
			    				oldbu=bu[i];
			    				bu[i]=oldbu+step*(err-lambda*bu[i]);
			    				
			    				// update bi
			    				oldbi=bi[j];
			    				bi[j]=oldbi+step*(err-lambda*bi[j]);
			    			
			    				
			    				// update bit
			    				bit[j][currentbin]=bit[j][currentbin]+step*(err-lambda*bit[j][currentbin]);
			    			}
			    		}
			    	}
			    	
			    	
			    	loop++;
			    }
				
			    // make prediciton
			    double rmse = 0;
			    int lentest =test.length;
			    for(int i=0;i<lentest;i++){ 
			    	int utid = test[i];
	    			int userid = utid/fold;
	    			int itemid = usertest[utid];
			        int currenttime = oritime_buffer[userid][itemid];
    				int currentbin = (Integer) mb.get(currenttime);
 
			 
	    		    double pre = mu+bu[userid]+bi[itemid]+bit[itemid][currentbin];
				    double real = oridata_buffer[userid][itemid];
				        
				    rmse+=(pre-real)*(pre-real);
					         
				 }
				    rmse=Math.sqrt(rmse/lentest);
				    System.out.println(rmse);
				    this.rmse=rmse;
			    
			}
			
			
			public void baslineSVD(int [][] time_buffer,int [][] oridata_buffer, int [][] data_buffer, double mu, double [] buold,double [] biold,
					int [] usertest, int []test , int fold){
				int binnumber = 215;    // seven months set 215 bins
				int feature = 30;
				
				double [][] pu = new double [NU][feature];
				double [][] qi = new double [NI][feature];
				
				// baseline predictor
				double [] bu = new double [NU];
				double [] bi = new double [NI];
				double [][] bit = new double [NI][binnumber];
			    double oldbu =0;
			    double oldbi =0;
			    double err =0;
			    double step = 0.0005;
			    double lambda = 0.02; // regularization term parameter.
			    int loop =0;
			    int max = 800;   // the iternation time of 
			    
			    
			    
			    
			    // map time to bins 
			    stat s = new stat();
			    s.timeorder(time_buffer);
			    int [] ta = s.time;
			    Map mb = new HashMap();
			    int len = ta.length;                // number of moments.
			   
			    int mint = ta[0];
				int maxt = ta[len-1];
				
//				int bin = 2592000 ;            // the length of each bin, 30 days seconds. 
				int bin = (maxt-mint)/binnumber;
//			    for(int i=0;i<len;i++){
//			    	int tempbin = (ta[i]-mint)/bin;
//			    	if(tempbin==binnumber){
//			    		tempbin=binnumber-1;
////			    		System.out.println("6");
//			    		
//			    	}
//			    	mb.put(ta[i], tempbin);
//			    }
			    
			    // initialize 
			    bu= buold;
			    bi= biold;
			    for(int i=0;i<NU;i++){
		    		for(int k =0;k<feature;k++){
		    				pu[i][k]=0.05;
		    		}	    
			    }
			    for(int i=0;i<NI;i++){
		    		for(int k =0;k<feature;k++){
		    				qi[i][k]=0.05;
		    		}	    
			    }
			    
			    while(loop<max){
			    	
			    	for(int i=0;i<NU;i++){
			    		for(int j=0;j<NI;j++){
			    			
			    			if(data_buffer[i][j]!=0){
			    				
//			    				// get the current time 
//			    				int currenttime = time_buffer[i][j];
//			    				int currentbin = (Integer) mb.get(currenttime);
			    				// calculate the error.
			    				double lfmvalue = 0;
			    				for(int k =0;k<feature;k++){
			    					lfmvalue+=pu[i][k]*qi[j][k];
			    				}
			    				err=data_buffer[i][j]-(mu+bu[i]+bi[j]+lfmvalue);
			    				
			    				// updata bu
			    				oldbu=bu[i];
			    				bu[i]=oldbu+step*(err-lambda*bu[i]);
			    				
			    				// update bi
			    				oldbi=bi[j];
			    				bi[j]=oldbi+step*(err-lambda*bi[j]);
			    			
			    				
//			    				// update bit
//			    				bit[j][currentbin]=bit[j][currentbin]+step*(err-lambda*bit[j][currentbin]);
			    				
			    				// update pu and qi 
			    				for(int k =0;k<feature;k++){
			    					pu[i][k]=pu[i][k]+step*(err*qi[j][k]-lambda*pu[i][k]);
			    					qi[j][k]=qi[j][k]+step*(err*pu[i][k]-lambda*qi[j][k]);
			    				}
			    				
			    			}
			    		}
			    	}
			    	
			    	
			    	loop++;
			    }
				
			    // make prediciton
			    double rmse = 0;
			    int lentest =test.length;
			    for(int i=0;i<lentest;i++){ 
			        int utid = test[i];
	    			int userid = utid/fold;
	    			int itemid = usertest[utid];
			        double lfmvalue = 0;
    				for(int k =0;k<feature;k++){
    					lfmvalue+=pu[userid][k]*qi[itemid][k];
    				}
    				
			        double pre = mu+bu[userid]+bi[itemid]+lfmvalue;
			        double real = oridata_buffer[userid][itemid];
			        
			        rmse+=(pre-real)*(pre-real);
				         
			    }
			    rmse=Math.sqrt(rmse/lentest);
			    System.out.println(rmse);
			    this.rmse=rmse;
			}
			
			
			public void funkSVD(int [][] time_buffer,int [][] oridata_buffer, int [][] data_buffer, double mu, double [] buold,double [] biold,
					int [] usertest, int [] test, int fold){

				int binnumber = 215;    // seven months set 215 bins
				int feature = 45;
				
				// LATENT FACOTR MODEL
				double [][] pu = new double [NU][feature];
				double [][] qi = new double [NI][feature];
				
				 
			 
			    double err =0;
			    double step = 0.005;
			    double lambda = 0.02; // regularization term parameter.
			    int loop =0;
			    int max = 100;   // the iternation time of 
			    
			    
			    
			    
			    // map time to bins 
			    stat s = new stat();
			    s.timeorder(time_buffer);
			    int [] ta = s.time;
			    Map mb = new HashMap();
			    int len = ta.length;                // number of moments.
			   
			    int mint = ta[0];
				int maxt = ta[len-1];
				
//				int bin = 2592000 ;            // the length of each bin, 30 days seconds. 
				int bin = (maxt-mint)/binnumber;
			    for(int i=0;i<len;i++){
			    	int tempbin = (ta[i]-mint)/bin;
			    	if(tempbin==binnumber){
			    		tempbin=binnumber-1;
//			    		System.out.println("6");
			    		
			    	}
			    	mb.put(ta[i], tempbin);
			    }
			    
			    // initialize 
			    bu= buold;
			    bi= biold;
			    for(int i=0;i<NU;i++){
		    		for(int k =0;k<feature;k++){
		    				pu[i][k]=0.1;
		    		}	    
			    }
			    for(int i=0;i<NI;i++){
		    		for(int k =0;k<feature;k++){
		    				qi[i][k]=0.1;
		    		}	    
			    }
			    
			    while(loop<max){
			    	
			    	for(int i=0;i<NU;i++){
			    		for(int j=0;j<NI;j++){
			    			
			    			if(data_buffer[i][j]!=0){
			    				 
			    				// calculate the error.
			    				double lfmvalue = 0;
			    				for(int k =0;k<feature;k++){
			    					lfmvalue+=pu[i][k]*qi[j][k];
			    				}
			    				err=data_buffer[i][j]-lfmvalue;
			    		
			    				// update pu and qi 
			    				for(int k =0;k<feature;k++){
			    					pu[i][k]=pu[i][k]+step*(err*qi[j][k]-lambda*pu[i][k]);
			    					qi[j][k]=qi[j][k]+step*(err*pu[i][k]-lambda*qi[j][k]);
			    				}
			    				
			    			}
			    		}
			    	}
			    	
			    	
			    	loop++;
			    }
				
			    // make prediciton
			    double rmse = 0;
			    int lentest = test.length;
			    for(int i=0;i<lentest;i++){
//			        int id = usertest[i];
//			        int currenttime = time_buffer[i][id];
//    				int currentbin = (Integer) mb.get(currenttime);
			        int utid = test[i];
	    			int userid = utid/fold;
	    			int itemid = usertest[utid];
			        
			        double lfmvalue = 0;
    				for(int k =0;k<feature;k++){
    					lfmvalue+=pu[userid][k]*qi[itemid][k];
    				}
    				
    				
			        double pre = lfmvalue;
			        double real = oridata_buffer[userid][itemid];
			        
			        rmse+=(pre-real)*(pre-real);
				         
			    }
			    rmse=Math.sqrt(rmse/lentest);
			    System.out.println(rmse);
			    
			
				this.rmse=rmse;
			}
		
		
		
			public void baslineUsertime(int [][] time_buffer,int [][] oridata_buffer, int [][] data_buffer, double mu, double [] buold,double [] biold,
					int [] usertest){
				int binnumber = 100;    // seven months set an 
				double [] bu = new double [NU];
				double [] bi = new double [NI];
				double [][] but = new double [NU][binnumber];
			    double oldbu =0;
			    double oldbi =0;
			    double err =0;
			    double step = 0.0005;
			    double lambda = 0.02; // regularization term parameter.
			    int loop =0;
			    int max = 100;   // the iternation time of 
			    
			    
			  
			    // map time to bins 
			    stat s = new stat();
			    s.timeorder(time_buffer);
			    int [] ta = s.time;
			    Map mb = new HashMap();
			    int len = ta.length;                // number of moments.
			   
			    int mint = ta[0];
				int maxt = ta[len-1];
				
//				int bin = 2592000 ;            // the length of each bin, 30 days seconds. 
				int bin = (maxt-mint)/binnumber;
			    for(int i=0;i<len;i++){
			    	int tempbin = (ta[i]-mint)/bin;
			    	if(tempbin==binnumber){
			    		tempbin=binnumber-1;
//			    		System.out.println("6");
			    		
			    	}
			    	mb.put(ta[i], tempbin);
			    }
			    
			    // initialize 
			    bu= buold;
			    bi= biold;
			    
			    while(loop<max){
			    	
			    	for(int i=0;i<NU;i++){
			    		for(int j=0;j<NI;j++){
			    			
			    			if(data_buffer[i][j]!=0){
			    				
			    				// get the current time 
			    				int currenttime = time_buffer[i][j];
			    				int currentbin = (Integer) mb.get(currenttime);
			    				// calculate the error.
			    				err=data_buffer[i][j]-(mu+bu[i]+bi[j]+but[i][currentbin]);
			    				
			    				// updata bu
			    				oldbu=bu[i];
			    				bu[i]=oldbu+step*(err-lambda*bu[i]);
			    				
			    				// update bi
			    				oldbi=bi[j];
			    				bi[j]=oldbi+step*(err-lambda*bi[j]);
			    			
			    				
			    				// update bit
			    				but[i][currentbin]=but[i][currentbin]+step*(err-lambda*but[i][currentbin]);
			    			}
			    		}
			    	}
			    	
			    	
			    	loop++;
			    }
				
			    // make prediciton
			    double rmse = 0;
			    for(int i=0;i<NU;i++){
			        int id = usertest[i];
			        int currenttime = time_buffer[i][id];
    				int currentbin = (Integer) mb.get(currenttime);
    				
			        double pre = mu+bu[i]+bi[id]+but[i][currentbin];
			        double real = oridata_buffer[i][id];
			        
			        rmse+=(pre-real)*(pre-real);
				         
			    }
			    rmse=Math.sqrt(rmse/NU);
			    System.out.println(rmse);
			    
			}
		
			public void baslineUsertimePlusSVD(int [][] time_buffer,int [][] oridata_buffer, int [][] data_buffer, double mu, double [] buold,double [] biold,
					int [] usertest){
				int binnumber = 100;    // seven months set an 
				double [] bu = new double [NU];
				double [] bi = new double [NI];
				double [][] but = new double [NU][binnumber];
			    double oldbu =0;
			    double oldbi =0;
			    double err =0;
			    double step = 0.0005;
			    double lambda = 0.02; // regularization term parameter.
			    int loop =0;
			    int max = 200;   // the iternation time of 
			    
			    // SVD parameters
                int feature = 30;
				double [][] pu = new double [NU][feature];
				double [][] qi = new double [NI][feature];
			  
			    // map time to bins 
			    stat s = new stat();
			    s.timeorder(time_buffer);
			    int [] ta = s.time;
			    Map mb = new HashMap();
			    int len = ta.length;                // number of moments.
			   
			    int mint = ta[0];
				int maxt = ta[len-1];
				
//				int bin = 2592000 ;            // the length of each bin, 30 days seconds. 
				int bin = (maxt-mint)/binnumber;
			    for(int i=0;i<len;i++){
			    	int tempbin = (ta[i]-mint)/bin;
			    	if(tempbin==binnumber){
			    		tempbin=binnumber-1;
//			    		System.out.println("6");
			    		
			    	}
			    	mb.put(ta[i], tempbin);
			    }
			    
			    // initialize 
			    bu= buold;
			    bi= biold;
			    
			    while(loop<max){
			    	
			    	for(int i=0;i<NU;i++){
			    		for(int j=0;j<NI;j++){
			    			
			    			if(data_buffer[i][j]!=0){
			    				
			    				double lfmvalue = 0;
			    				for(int k =0;k<feature;k++){
			    					lfmvalue+=pu[i][k]*qi[j][k];
			    				}
			    				
			    				
			    				// get the current time 
			    				int currenttime = time_buffer[i][j];
			    				int currentbin = (Integer) mb.get(currenttime);
			    				// calculate the error.
			    				err=data_buffer[i][j]-(mu+bu[i]+bi[j]+but[i][currentbin]+lfmvalue);
			    				
			    				// updata bu
			    				oldbu=bu[i];
			    				bu[i]=oldbu+step*(err-lambda*bu[i]);
			    				
			    				// update bi
			    				oldbi=bi[j];
			    				bi[j]=oldbi+step*(err-lambda*bi[j]);
			    			
			    				
			    				// update but
			    				but[i][currentbin]=but[i][currentbin]+step*(err-lambda*but[i][currentbin]);
			    				
			    				// update pu and qi 
			    				for(int k =0;k<feature;k++){
			    					pu[i][k]=pu[i][k]+step*(err*qi[j][k]-lambda*pu[i][k]);
			    					qi[j][k]=qi[j][k]+step*(err*pu[i][k]-lambda*qi[j][k]);
			    				}
			    				
			    			}
			    		}
			    	}
			    	
			    	
			    	loop++;
			    }
				
			    // make prediciton
			    double rmse = 0;
			    for(int i=0;i<NU;i++){
			        int id = usertest[i];
			        int currenttime = time_buffer[i][id];
    				int currentbin = (Integer) mb.get(currenttime);
    				  
			        double lfmvalue = 0;
    				for(int k =0;k<feature;k++){
    					lfmvalue+=pu[i][k]*qi[id][k];
    				}
    				
    				
    				
			        double pre = mu+bu[i]+bi[id]+but[i][currentbin]+lfmvalue;
			        double real = oridata_buffer[i][id];
			        
			        rmse+=(pre-real)*(pre-real);
				         
			    }
			    rmse=Math.sqrt(rmse/NU);
			    System.out.println(rmse);
			    
			}
		
		
			public void Userlinearmodel(int [][] oritime_buffer, int [][] time_buffer,int [][] oridata_buffer, int [][] data_buffer, double mu, double [] buold,double [] biold,
					int [] usertest, int [] test, int fold){
				int binnumber = 100;    // set binnumber bins for seven months.  for user aspect.
				int bininumber =100;    // set bininumber bins for item aspect.
				double [] bu = new double [NU];
				double [] bi = new double [NI];
				double [][] but = new double [NU][binnumber];
				double [][] bit = new double [NI][bininumber];
				double [] alphaUser = new double [NU];
				devu devu = new devu(); 
				double betha = 0.04;
				usertimemean utm = new usertimemean();
				double [] timemean = utm.utmean(time_buffer);
				
				
			    double oldbu =0;
			    double oldbi =0;
			    double err =0;
			    double step = 0.0005;
			    double lambda = 0.02; // regularization term parameter.
			    int loop =0;
			    int max = 50;   // the iternation time of 
			    
			    
			  
			    // map time to bins 
			    stat s = new stat();
			    s.timeorder(oritime_buffer);
			    int [] ta = s.time;
			    Map mb = new HashMap();
			    Map mbitem = new HashMap();
			    int len = ta.length;                // number of moments.
			   
			    int mint = ta[0];
				int maxt = ta[len-1];
				
 
				// map the user's time to bins
				int bin = (maxt-mint)/binnumber;
			    for(int i=0;i<len;i++){
			    	int tempbin = (ta[i]-mint)/bin;
			    	if(tempbin==binnumber){
			    		tempbin=binnumber-1;
//			    		System.out.println("6");
			    		
			    	}
			    	mb.put(ta[i], tempbin);
			    }
			    
			    // 
				int binitem = (maxt-mint)/bininumber;
			    for(int i=0;i<len;i++){
			    	int tempbin = (ta[i]-mint)/binitem;
			    	if(tempbin==bininumber){
			    		tempbin=bininumber-1;
//			    		System.out.println("6");
			    		
			    	}
			    	mbitem.put(ta[i], tempbin);
			    }
			    
			    // initialize 
			    bu= buold;
			    bi= biold;
			    for(int i=0;i<NU;i++){
			    	alphaUser[i]=0.01;
			    }
			    
			    while(loop<max){
			    	
			    	for(int i=0;i<NU;i++){
			    		for(int j=0;j<NI;j++){
			    			
			    			if(data_buffer[i][j]!=0){
			    				
			    				// get the current time 
			    				int currenttime = time_buffer[i][j];
			    				int currentbin = (Integer) mb.get(currenttime);
			    				int currentbinitem = (Integer)mbitem.get(currenttime);
			    				// calculate the error.
			    				err=data_buffer[i][j]-(mu+bu[i]+bi[j]+bit[j][currentbinitem]+but[i][currentbin]+alphaUser[i]*devu.devu(currenttime, timemean[i], betha));
			    				
			    				// updata bu
			    				oldbu=bu[i];
			    				bu[i]=oldbu+step*(err-lambda*bu[i]);
			    				
			    				// update bi
			    				oldbi=bi[j];
			    				bi[j]=oldbi+step*(err-lambda*bi[j]);
			    			
			    				
			    				// update but
			    				but[i][currentbin]=but[i][currentbin]+step*(err-lambda*but[i][currentbin]);
			    				
			    				// update bit
			    				bit[j][currentbinitem]=bit[j][currentbinitem]+step*(err-lambda*bit[j][currentbinitem]);
			    				
			    				// update alpha
			    				alphaUser[i]=alphaUser[i]+step*(err*devu.devu(currenttime, timemean[i], betha)-lambda*alphaUser[i]);
			    			
			    			
			    			
			    			}
			    		}
			    	}
			    	
			    	
			    	loop++;
			    }
				
			    // make prediciton
			    double rmse = 0; 
			    int lentest =test.length;
			    for(int i=0;i<lentest;i++){ 
			    	int utid = test[i];
	    			int userid = utid/fold;
	    			int itemid = usertest[utid];
			        int currenttime = oritime_buffer[userid][itemid];
    				int currentbin = (Integer) mb.get(currenttime);
    				int currentbinitem = (Integer) mbitem.get(currenttime);
			 
	    		    double pre = mu+bu[userid]+bi[itemid]+bit[itemid][currentbinitem]+but[userid][currentbin]+alphaUser[userid]*devu.devu(currenttime, timemean[userid], betha);
				    double real = oridata_buffer[userid][itemid];
				        
				    rmse+=(pre-real)*(pre-real);
					         
				 }
				    rmse=Math.sqrt(rmse/lentest);
				    System.out.println(rmse);
				    this.rmse=rmse;
			    
			}
			
		
			public void timechangingfactor(int [][] oritime_buffer, int [][] time_buffer,int [][] oridata_buffer, int [][] data_buffer, double mu, double [] buold,double [] biold,
					int [] usertest, int [] test, int fold){
				
				
				// prediction parameters
				int binnumber = 200;    // set binnumber bins for seven months.  for user aspect. turn 100 to 200 from 1.8147684708233658 to 1.814720542614634
				int bininumber =100;    // set bininumber bins for item aspect.
				int binfactor = 100;    // set the binfactor bins for user's factor vector.
				double [] bu = new double [NU];
				double [] bi = new double [NI];
				double [][] but = new double [NU][binnumber];
				double [][] bit = new double [NI][bininumber];
				double [] alphaUser = new double [NU];
				devu devu = new devu(); 
				double betha = 0.04;
				usertimemean utm = new usertimemean();
				double [] timemean = utm.utmean(time_buffer);
				// LATENT FACOTR MODEL
				int feature = 45;
				double [][] pu = new double [NU][feature];
				double [][] qi = new double [NI][feature];
				double [][] alphauk = new double [NU][feature];
				double [][][] pukt = new double [NU][feature][binfactor];
				
				
			    double oldbu =0;
			    double oldbi =0;
			    double err =0;
			    double step = 0.0005;   // turn to 0.005  worse.
			    double lambda = 0.02; // regularization term parameter.
			    int loop =0;
			    int max = 100;   // the iternation time of   turn to 50 result become worse.
			    
			    
			  
			    // map time to bins 
			    stat s = new stat();
			    s.timeorder(oritime_buffer);
			    int [] ta = s.time;
			    Map mb = new HashMap();
			    Map mbitem = new HashMap();
			    Map mbf = new HashMap();
			    int len = ta.length;                // number of moments.
			   
			    int mint = ta[0];
				int maxt = ta[len-1];
				
 
				// map the user's time to bins
				int bin = (maxt-mint)/binnumber;
			    for(int i=0;i<len;i++){
			    	int tempbin = (ta[i]-mint)/bin;
			    	if(tempbin==binnumber){
			    		tempbin=binnumber-1;
//			    		System.out.println("6");
			    		
			    	}
			    	mb.put(ta[i], tempbin);
			    }
			    
			    // 
				int binitem = (maxt-mint)/bininumber;
			    for(int i=0;i<len;i++){
			    	int tempbin = (ta[i]-mint)/binitem;
			    	if(tempbin==bininumber){
			    		tempbin=bininumber-1;
//			    		System.out.println("6");
			    		
			    	}
			    	mbitem.put(ta[i], tempbin);
			    }
			    
			    int binf = (maxt-mint)/binfactor;
			    for(int i=0;i<len;i++){
			    	int tempbin = (ta[i]-mint)/binf;
			    	if(tempbin==binfactor){
			    		tempbin=binfactor-1;
//			    		System.out.println("6");
			    		
			    	}
			    	mbf.put(ta[i], tempbin);
			    }
			    
			    // initialize 
			    bu= buold;
			    bi= biold;
			    for(int i=0;i<NU;i++){
			    	alphaUser[i]=0.01;
			    }
			    for(int i=0;i<NU;i++){
		    		for(int k =0;k<feature;k++){
		    				pu[i][k]=0.1;
		    		}	    
			    }
			    for(int i=0;i<NI;i++){
		    		for(int k =0;k<feature;k++){
		    				qi[i][k]=0.1;
		    		}	    
			    }
			    while(loop<max){
			    	
			    	for(int i=0;i<NU;i++){
			    		for(int j=0;j<NI;j++){
			    			
			    			if(data_buffer[i][j]!=0){
			    				
			    				// get the current time 
			    				int currenttime = time_buffer[i][j];
			    				int currentbin = (Integer) mb.get(currenttime);
			    				int currentbinitem = (Integer)mbitem.get(currenttime);
			    				int currentbinfactor = (Integer)mbf.get(currenttime);
			    				
			    				double lfmvalue = 0;
			    				for(int k =0;k<feature;k++){
			    					lfmvalue+=(pu[i][k]+alphauk[i][k]*devu.devu(currenttime, timemean[i], betha)+pukt[i][k][currentbinfactor])*qi[j][k];
			    				}
			    				
			    				// calculate the error.
			    				err=data_buffer[i][j]-(mu+bu[i]+bi[j]+bit[j][currentbinitem]+but[i][currentbin]+alphaUser[i]*devu.devu(currenttime, timemean[i], betha)+lfmvalue);
			    				
			    				// updata bu
			    				oldbu=bu[i];
			    				bu[i]=oldbu+step*(err-lambda*bu[i]);
			    				
			    				// update bi
			    				oldbi=bi[j];
			    				bi[j]=oldbi+step*(err-lambda*bi[j]);
			    			
			    				
			    				// update but
			    				but[i][currentbin]=but[i][currentbin]+step*(err-lambda*but[i][currentbin]);
			    				
			    				// update bit
			    				bit[j][currentbinitem]=bit[j][currentbinitem]+step*(err-lambda*bit[j][currentbinitem]);
			    				
			    				// update alpha
			    				alphaUser[i]=alphaUser[i]+step*(err*devu.devu(currenttime, timemean[i], betha)-lambda*alphaUser[i]);
			    				
			    				// update pu and qi 
			    				for(int k =0;k<feature;k++){
			    					pu[i][k]=pu[i][k]+step*(err*qi[j][k]-lambda*pu[i][k]);
			    					alphauk[i][k]=alphauk[i][k]+step*(err*qi[j][k]*devu.devu(currenttime, timemean[i], betha)-lambda*(alphauk[i][k]));
			    					pukt[i][k][currentbinfactor]=pukt[i][k][currentbinfactor]+step*(err*qi[j][k]-lambda*pukt[i][k][currentbinfactor]);
			    					
			    					qi[j][k]=qi[j][k]+step*(err*pu[i][k]-lambda*qi[j][k]);
			    				}
			    			
			    			
			    			}
			    		}
			    	}
			    	
			    	
			    	loop++;
			    }
				
			    // make prediciton
			    double rmse = 0; 
			    int lentest =test.length;
			    for(int i=0;i<lentest;i++){ 
			    	int utid = test[i];
	    			int userid = utid/fold;
	    			int itemid = usertest[utid];
			        int currenttime = oritime_buffer[userid][itemid];
    				int currentbin = (Integer) mb.get(currenttime);
    				int currentbinitem = (Integer) mbitem.get(currenttime);
    				int currentbinfactor = (Integer)mbf.get(currenttime);
			 
    				double lfmvalue = 0;
    				for(int k =0;k<feature;k++){
    					lfmvalue+=(pu[userid][k]+alphauk[userid][k]*devu.devu(currenttime, timemean[userid], betha)+pukt[userid][k][currentbinfactor])*qi[itemid][k];
    				}
	    		    double pre = mu+bu[userid]+bi[itemid]+bit[itemid][currentbinitem]+but[userid][currentbin]+alphaUser[userid]*devu.devu(currenttime, timemean[userid], betha)
	    		    		+lfmvalue;
				    double real = oridata_buffer[userid][itemid];
				        
				    rmse+=(pre-real)*(pre-real);
					         
				 }
				    rmse=Math.sqrt(rmse/lentest);
				    System.out.println(rmse);
				    this.rmse=rmse;
			    
			}
			
		}
		
		
		public class devu{
			public double devu(int t, double tu ,double betha){
				double devalue =0;
				
				
				devalue = Math.signum(t-tu)*Math.pow(Math.abs(t-tu),betha);
				
				
				return devalue;
			}
		}

}
       	


	
	

 
