
public class regularization {
	
	
	
	
	
	public double [][] regularization(double [][] mat)
	{
		int row = mat.length;
		int column  = mat[0].length;
		double [][] newMat = new double [row][column];
		
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<column;j++)
			{
				 double t= mat[i][j];
				 
				 if(t>0&t<1.5){newMat[i][j]=1;}                                   // if t==0.0 then the value is missing, and this value need to be predicted.
				   else if(t>=1.5&t<2.5){newMat[i][j]=2;}							  // if t>0 but t<1, then the value is exist, and the user doesn't like this kind of item. So, t should be 1
				   else if(t>=2.5&t<3.5){newMat[i][j]=3;}
				   else if(t>=3.5&t<4.5){newMat[i][j]=4;}
				   else if(t>=4.5){newMat[i][j]=5;}
				   else {newMat[i][j]=0;}
			}
			
		}
		return newMat;
	}

	public double [] regularization(double [] array)
	{
		int len = array.length;
	
		double [] newMat = new double [len];
		
		for(int i=0;i<len;i++)
		{
			
				 double t= array[i];
				 
				 if(t>0&t<1.5){newMat[i]=1;}                                   // if t==0.0 then the value is missing, and this value need to be predicted.
				   else if(t>=1.5&t<2.5){newMat[i]=2;}							  // if t>0 but t<1, then the value is exist, and the user doesn't like this kind of item. So, t should be 1
				   else if(t>=2.5&t<3.5){newMat[i]=3;}
				   else if(t>=3.5&t<4.5){newMat[i]=4;}
				   else if(t>=4.5){newMat[i]=5;}
					
				   else {newMat[i]=0;}
			
			
		}
		return newMat;
	}
	
	
	public double regularization(double array)
	{

	    double t = array;
		double newMat = 0;
		

				 if(t>0&t<1.5){newMat=1;}                                   // if t==0.0 then the value is missing, and this value need to be predicted.
				   else if(t>=1.5&t<2.5){newMat =2;}							  // if t>0 but t<1, then the value is exist, and the user doesn't like this kind of item. So, t should be 1
				   else if(t>=2.5&t<3.5){newMat =3;}
				   else if(t>=3.5&t<4.5){newMat =4;}
				   else if(t>=4.5){newMat =5;}
					
				   else {newMat =0;}
			
			
		
		return newMat;
	}
	
	
	public double [] regularizationTo1(double [] mat)  
	{  // because the max value of mat is 5, so divided by 5 can make sure all elements of mat less equal than 1. 

	    
		
		int len = mat.length;
		
		for(int i=0;i<len;i++){
			mat[i]=mat[i]/5;	
		}
		
               
				
			
			
		
		return mat;
	}
	
}
