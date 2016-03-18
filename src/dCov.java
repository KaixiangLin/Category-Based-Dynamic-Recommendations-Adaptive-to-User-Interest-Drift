
public class dCov {
	
	
	
	public double getdCovEfficient(int [][] mat1, int [][] mat2 )
	{
		double dCov=0;
		int rowNumMat1= mat1.length;
		int columnNumMat1 = mat1[0].length;
		int rowNumMat2 = mat2.length;
		int columnNumMat2 = mat2[0].length;
		
		
		// get the Eculidean distance matrices of 2 mats. 
		
				EuclideanDistance ed = new EuclideanDistance();
				 
				double [][] edRowMat1 = new double [rowNumMat1][rowNumMat1];   // store the eculidean distance between rows of mat1. 
				double [][] edRowMat2 = new double [rowNumMat2][rowNumMat2];
				
				for(int i=0;i<rowNumMat1;i++)
				{
					for(int j=i;j<rowNumMat1;j++)
					{
					   edRowMat1[i][j]= ed.CalculateDis(mat1[i], mat1[j]); 	
					   edRowMat1[j][i]= edRowMat1[i][j];
					}
				}
				
				for(int i=0;i<rowNumMat2;i++)
				{
					for(int j=i;j<rowNumMat2;j++)
					{
					   edRowMat2[i][j]= ed.CalculateDis(mat2[i], mat2[j]); 	
					   edRowMat2[j][i]= edRowMat2[i][j];
					}
				}
		
		
		
		
		IntegerArrayCalculator intAC = new IntegerArrayCalculator();
		
		
		double [] rowMeanMat1 = new double [rowNumMat1];
		double [] columnMeanMat1 = new double [rowNumMat1];
		double [] rowMeanMat2 = new double [rowNumMat2];
		double [] columnMeanMat2 = new double [rowNumMat1];
		
		// get mean of row of 2 matrices.
		for(int i=0;i<rowNumMat1;i++)
		{
			rowMeanMat1[i]=intAC.getMean(edRowMat1[i]);
		}
		for(int i=0;i<rowNumMat2;i++)
		{
			rowMeanMat2[i]=intAC.getMean(edRowMat2[i]);
		}
		
		
		// get mean of column of 2 matrices.
		tranMatrix tm = new tranMatrix();
		
		for(int i=0;i<rowNumMat1;i++)
		{
			double [] temColumn = tm.getSubColumnMatrix(edRowMat1, i);
			columnMeanMat1[i]=intAC.getMean(temColumn);
		}
		for(int i=0;i<rowNumMat1;i++)
		{
			double [] temColumn = tm.getSubColumnMatrix(edRowMat2, i);
			columnMeanMat2[i]=intAC.getMean(temColumn);
		}
		
		// get the mean of the whole matrices.
		
		double meanOfMat1 = intAC.getMean(rowMeanMat1);
		
		double meanOfMat2 = intAC.getMean(rowMeanMat2);
		
		
		dCov = 0;
		double numerator =0;
		double denominator1=0, denominator2=0;
		
		for(int i=0;i<rowNumMat1;i++)
		{
			for(int j=0;j<rowNumMat1;j++)
			{
				double t1= edRowMat1[i][j]-rowMeanMat1[i]-columnMeanMat1[j]+meanOfMat1;
				double t2= edRowMat2[i][j]-rowMeanMat2[i]-columnMeanMat2[j]+meanOfMat2;
				numerator += t1*t2;
				denominator1+=t1*t1;
				denominator2+=t2*t2;
			}
		}
		
		denominator1= Math.sqrt(denominator1);
		denominator2= Math.sqrt(denominator2);
		
		
		
		
		dCov = numerator/(denominator1*denominator1);
		
		
		return dCov;
	}
	
	
	
	public double getdCovEfficient(double [][] mat1, double [][] mat2 )
	{
		double dCov=0;
		int rowNumMat1= mat1.length;
		int columnNumMat1 = mat1[0].length;
		int rowNumMat2 = mat2.length;
		int columnNumMat2 = mat2[0].length;
		
		
		// get the Eculidean distance matrices of 2 mats. 
		
				EuclideanDistance ed = new EuclideanDistance();
				 
				double [][] edRowMat1 = new double [rowNumMat1][rowNumMat1];   // store the eculidean distance between rows of mat1. 
				double [][] edRowMat2 = new double [rowNumMat2][rowNumMat2];
				
				for(int i=0;i<rowNumMat1;i++)
				{
					for(int j=i;j<rowNumMat1;j++)
					{
					   edRowMat1[i][j]= ed.CalculateDis(mat1[i], mat1[j]); 	
					   edRowMat1[j][i]= edRowMat1[i][j];
					}
				}
				
				for(int i=0;i<rowNumMat2;i++)
				{
					for(int j=i;j<rowNumMat2;j++)
					{
					   edRowMat2[i][j]= ed.CalculateDis(mat2[i], mat2[j]); 	
					   edRowMat2[j][i]= edRowMat2[i][j];
					}
				}
		
		
		
		
		IntegerArrayCalculator intAC = new IntegerArrayCalculator();
		
		
		double [] rowMeanMat1 = new double [rowNumMat1];
		double [] columnMeanMat1 = new double [rowNumMat1];
		double [] rowMeanMat2 = new double [rowNumMat2];
		double [] columnMeanMat2 = new double [rowNumMat1];
		
		// get mean of row of 2 matrices.
		for(int i=0;i<rowNumMat1;i++)
		{
			rowMeanMat1[i]=intAC.getMean(edRowMat1[i]);
		}
		for(int i=0;i<rowNumMat2;i++)
		{
			rowMeanMat2[i]=intAC.getMean(edRowMat2[i]);
		}
		
		
		// get mean of column of 2 matrices.
		tranMatrix tm = new tranMatrix();
		
		for(int i=0;i<rowNumMat1;i++)
		{
			double [] temColumn = tm.getSubColumnMatrix(edRowMat1, i);
			columnMeanMat1[i]=intAC.getMean(temColumn);
		}
		for(int i=0;i<rowNumMat1;i++)
		{
			double [] temColumn = tm.getSubColumnMatrix(edRowMat2, i);
			columnMeanMat2[i]=intAC.getMean(temColumn);
		}
		
		// get the mean of the whole matrices.
		
		double meanOfMat1 = intAC.getMean(rowMeanMat1);
		
		double meanOfMat2 = intAC.getMean(rowMeanMat2);
		
		
		dCov = 0;
		double numerator =0;
		double denominator1=0, denominator2=0;
		
		for(int i=0;i<rowNumMat1;i++)
		{
			for(int j=0;j<rowNumMat1;j++)
			{
				double t1= edRowMat1[i][j]-rowMeanMat1[i]-columnMeanMat1[j]+meanOfMat1;
				double t2= edRowMat2[i][j]-rowMeanMat2[i]-columnMeanMat2[j]+meanOfMat2;
				numerator += t1*t2;
				denominator1+=t1*t1;
				denominator2+=t2*t2;
			}
		}
		
		denominator1= Math.sqrt(denominator1);
		denominator2= Math.sqrt(denominator2);
		
		
		
		
		dCov = numerator/(denominator1*denominator1);
		
		
		return dCov;
	}

}
