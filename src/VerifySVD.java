
public class VerifySVD {
	// To compare the 2 matrices before and after SVD, whether SVD change the highest value of each row of the matrix.
	
	public double compare(double [][] mat1, double mat2 [][])
	{
		int rownum= mat1.length;
		int columnnum= mat1[0].length;
		int [] m1 = new int [rownum];
		int [] m2 = new int [rownum];
		int [] index = new int [columnnum];
		int [] index2 = new int [columnnum];
		int count=0;
		double rate=0;
		
		
		Sort s1 = new Sort();
		
		for(int j=0;j<rownum;j++)
		{
			for(int i=0;i<columnnum;i++)
			{
				index[i]=i;
				index2[i]=i;
			}
		s1.getSortedArray(mat1[j], index);
		m1[j]=s1.sortedIndex[columnnum-1];
		s1.getSortedArray(mat2[j], index2);
		m2[j]=s1.sortedIndex[columnnum-1];
		if(m1[j]==m2[j])
		{
			count++;
		}
		
		}
		rate =(double)count/rownum;
		return rate;
	}
	
	

}
