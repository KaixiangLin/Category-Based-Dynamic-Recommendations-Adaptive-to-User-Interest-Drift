
public class tranMatrix {
	// transpose matrix: 1. transform matrix from m*n to n*m 
	// 2. get several rows of matrix and put it into new matrix 

		

	public int [][] transpose(int [][] mat){
		int row=mat.length;
		int column=mat[0].length;	
		int [][] newMat =new  int [column][row];
		
		
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				
				newMat[j][i]=mat[i][j];
				
			}
		}

		return newMat;
	}
	
	// get first n rows and first m columns of mat
	public double [][] getSubMatrix(double [][] mat, int n, int m)
	{
		
		int row=mat.length;
		int column=mat[0].length;	
		double [][] newMat = new double [n][m];
		
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<m;j++)
			{
				newMat[i][j]=mat[i][j];
				
			}
		}
		
		
		return newMat;
	}
	
	// get first n rows and first m columns of mat
		public int [][] getSubMatrix(int [][] mat, int n, int m)
		{
			
			int row=mat.length;
			int column=mat[0].length;	
			int [][] newMat = new int [n][m];
			
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<m;j++)
				{
					newMat[i][j]=mat[i][j];
					
				}
			}
			
			
			return newMat;
		}
	
	// get the n1 st row to n2 st row of mat.
	public double [][] getSubRowMatrix(double [][] mat , int n1,int n2)
	{
		
		int row=mat.length;
		int column=mat[0].length;
		double [][] newMat = new double [n2-n1+1][column];
		
		
		for(int i=n1-1;i<n2;i++)// index of n1 st row is n1-1.
		{
			for(int j=0;j<column;j++)
			{
				newMat[i-(n1-1)][j]=mat[i][j];
				
			}
		}
		
		return newMat;
	}
	
	// get the n1 st row to n2 st row of mat.
	public int [][] getSubRowMatrix(int [][] mat , int n1,int n2)
	{
		
		int row=mat.length;
		int column=mat[0].length;
		int [][] newMat = new int [n2-n1+1][column];
		
		
		for(int i=n1-1;i<n2;i++)// index of n1 st row is n1-1.
		{
			for(int j=0;j<column;j++)
			{
				newMat[i][j]=mat[i][j];
				
			}
		}
		
		return newMat;
	}
	
	// get the n column of mat. n represent the subscript of column, start from zero.
	public double [] getSubColumnMatrix(double [][] mat , int n)
	{

		int row=mat.length;
		 
		double [] columnArray = new double [row];
		
		for(int i=0;i<row;i++)
		{
			columnArray[i]= mat[i][n];
		}
		
		return columnArray;
		
	}
}
