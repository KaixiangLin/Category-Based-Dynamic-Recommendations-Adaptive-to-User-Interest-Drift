
public class CosinToMatrix {

	
	public double getCosineOfMatrix(double [][] x1, double [][] x2)
	{
		// this is new method to calculate the similarity of 2 matirx have same size.
		
		
		double simi = 0;
		int row = x1.length;
		int column = x1[0].length;
		
		double [] simiBetweencolumn = new double [column];   // store the cosine similarity of 2 same column between 2 matrices x1 and x2
		
		CosineSimi cs = new CosineSimi();
		
		for(int i=0;i<column;i++)
		{
			double [] tempx1 = new double [row];
			double [] tempx2 = new double [row];
			for(int j=0;j<row ;j++){
				
				tempx1[j]=x1[j][i];
				tempx2[j]=x2[j][i];
				
			}
			
			simiBetweencolumn[i]=cs.getCosineSimi(tempx1, tempx2);
		
 		}
		
		
	
		IntegerArrayCalculator iac = new IntegerArrayCalculator();
		
		simi= iac.getMean(simiBetweencolumn);
		
		return simi;
	}
	
	
}
