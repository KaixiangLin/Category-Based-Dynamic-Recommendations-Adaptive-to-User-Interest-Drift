import java.io.BufferedWriter;
import java.io.File;
import java.io.*;
import java.io.FileWriter;


public class readKiniCenters {

	
	public double [][] getiniCenters(int k, int NU)
	{
		String [] s1 = new String [k];
		double [][] inicenter = new double [k][NU];
		try{
			FileReader f = new FileReader("C:\\Users\\linkx\\Desktop\\lenskit-data\\recordKmeansCenter.txt");
			BufferedReader b = new BufferedReader(f);
			
			String s ;
			 
			int i = 0;
			while((s=b.readLine())!=null)
			{
			    s1[i]=s;
			    i++;
			}
			f.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		for(int i =0;i<k;i++)
		{
			String [] temps = s1[i].split(" ");
			for(int j=0;j<NU;j++)
			{
				inicenter[i][j]=Double.parseDouble(temps[j]);
			}
		}
		
		
		return inicenter;
		
	}
	
	
}
