
public class weightedMatrix {
	
	
	public double [][] weightMatrix(double [][][] array, int [] neighborID, int target, double [] weight, double [] ratingMean , double[][] ratingMeanKclas,double [] bu,double [] bik, double [] nij, double overallMean,int chooseway){
		
		int Matrow = array[0].length;
		int Matcolumn = array[0][0].length;
		int MatNum = array.length;
		int numOfnei = neighborID.length;
		double [][] weightMat = new double [Matrow][Matcolumn];
		
		if(chooseway==1){
		
		// method 1 for forming weighted rating matrix 
			// this way use neighbors to form matrix.
			
			for(int j=0;j<Matrow;j++){
				for(int p=0;p<Matcolumn;p++){
					double temp=0;
					for(int i=0;i<numOfnei;i++){
						if(array[neighborID[i]][j][p]!=0){
							weightMat[j][p]+= weight[i]*(array[neighborID[i]][j][p]-ratingMean[neighborID[i]]);;
							temp+=weight[i];
						}
					}	
					
					if(temp!=0) {
					weightMat[j][p]=weightMat[j][p]/temp;
					}
					weightMat[j][p]+=ratingMean[target];
				}
			}
			
			// if target user have a value here, then discard the value from the neighbor
			
			for(int i=0;i<Matrow;i++)
			{
				for(int j=0;j<Matcolumn;j++)
				{
					if(array[target][i][j]!=0)
					{
						weightMat[i][j]=array[target][i][j];
					}
					if(weightMat[i][j]==0)
					{
						weightMat[i][j]=ratingMean[target];
					}
					if(weightMat[i][j]>5)
					{
						weightMat[i][j]=5;
					}
					if(weightMat[i][j]<0)
					{
						weightMat[i][j]=0;
					}
				}
				
			}
		}
		else if (chooseway==2){
			
		// method 2 for forming rating weighted matrix.
			
			for(int j=0;j<Matrow;j++){
				for(int p=0;p<Matcolumn;p++){
//					for(int i=0;i<neighborID.length;i++){
//						if(array[neighborID[i]][j][p]!=0){
//					//weightMat[j][p]+= weight[i]*(array[neighborID[i]][j][p]-ratingMeanKclas[neighborID[i]][p]);
//					weightMat[j][p]+= weight[i]*(array[neighborID[i]][j][p]-ratingMean[neighborID[i]]);
//						}
//					
//					}
					weightMat[j][p]+=ratingMean[target];
				}
			}
			
			for(int i=0;i<Matrow;i++)
			{
				for(int j=0;j<Matcolumn;j++)
				{
					if(array[target][i][j]!=0)
					{
						weightMat[i][j]=array[target][i][j];
					}
					
				}
				
			}
			
			int count =0;
			for(int i=0;i<Matrow;i++)
			{
				for(int j=0;j<Matcolumn;j++)
				{
					if(weightMat[i][j]<0)
					{
						weightMat[i][j]=0;
//				count++;    this count whether there is zero elements in the matrix.
					}
					
					
				}
				
			}
			
			
			//System.out.println(count);
		}
		
		else if(chooseway ==3)
		{
			

			
			for(int j=0;j<Matrow;j++){
				for(int p=0;p<Matcolumn;p++){
					double temp=0;
					for(int i=0;i<numOfnei;i++){
						if(array[neighborID[i]][j][p]!=0){
							weightMat[j][p]+= nij[i]*weight[i]*(array[neighborID[i]][j][p]-(overallMean+bu[neighborID[i]]+bik[p]));
							temp+=nij[i]*weight[i];
						}
					}	
					
					if(temp!=0) {
					weightMat[j][p]=weightMat[j][p]/temp;
					}
					weightMat[j][p]+=overallMean+bu[target]+bik[p];
				}
			}
			
			// if target user have a value here, then discard the value from the neighbor
			
//						for(int i=0;i<Matrow;i++)
//						{
//							for(int j=0;j<Matcolumn;j++)
//							{
//								if(array[target][i][j]!=0)
//								{
//									weightMat[i][j]=array[target][i][j];
//								}
//								if(weightMat[i][j]==0)
//								{
//									weightMat[i][j]=ratingMean[target];
//								}
//								if(weightMat[i][j]>5)
//								{
//									weightMat[i][j]=5;
//								}
//								if(weightMat[i][j]<0)
//								{
//									weightMat[i][j]=0;
//								}
//							}
//							
//						}
		
		}else if(chooseway ==4){
			for(int j=0;j<Matrow;j++){
				for(int p=0;p<Matcolumn;p++){
					double temp=0;
					for(int i=0;i<numOfnei;i++){
						if(array[neighborID[i]][j][p]!=0){
							weightMat[j][p]+= nij[i]*weight[i]*(array[neighborID[i]][j][p] );
							temp+=nij[i]*weight[i];
						}
					}	
					
					if(temp!=0) {
					weightMat[j][p]=weightMat[j][p]/temp;
					}
//					weightMat[j][p]+=overallMean+bu[target]+bik[p];
				}
			}
			
			
		}
		
		
		return weightMat;
	}
	
	int num ;
	int [] markRealRating;
	public double [][] userMatrix(int [][] timebuffer,double [][] databuffer, int [] label, int target, int k, int num1, double [] ratingMeanKclas ){
	
		// 得到最后使用的用户的矩阵，每一行是时间加权后的数据向量。 或者说是偏好向量。
		
		FormRatingTimeMat   frt = new FormRatingTimeMat();
		double [][] currentMat = frt.getRatTimeMatSpecific(timebuffer,label,databuffer,k,target); // all ratings in timebuffer of target user form an rating time mat.
		// 以timebuffer 为准。 databuffer 中如果有多出的评分并不计入
		int [] markspecific = frt.markspecific;
		
		int len = currentMat.length;   // 所有时刻的评分的数量。
		
		int num = (int) (len*0.8);     // 用于训练的时刻数。
 
		double [][] userMat = new double [num][k];
		for(int i=0;i<num;i++){
			double [] numcont = new double [k];
			for(int j=0;j<len-num+1+i;j++){
			   int dis = len-num+i+1-j;
			   double e = Math.exp(-dis);
			   int index = markspecific[j];
			   userMat[i][index]+=currentMat[j][index]*e;
			   numcont[index]+=e;
			}
			for(int j=0;j<k;j++){
				if(numcont[j]!=0)
				userMat[i][j]=userMat[i][j]/numcont[j];
			}
		}
		
//		for(int i=0;i<num;i++){
//			for(int j=0;j<k;j++){
//				if(userMat[i][j]==0){
//					userMat[i][j]=ratingMeanKclas[j];
//				}
//			}
//		}
		int [] markspecificAdp = new int[num];
		for(int i=0;i<num;i++){
			markspecificAdp[num-1-i]=markspecific[len-1-i];
		}
		
		this.markRealRating=markspecificAdp;
		this.num=num;
		return userMat;
	}
	
	
	
	
	

}
