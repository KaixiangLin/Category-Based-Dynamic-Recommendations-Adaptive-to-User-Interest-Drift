import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.*;

import Jama.Matrix;


public class testKmeans {
	
	public static void main(String[] args) throws IOException{
		
		
		Random rand = new Random();
		rand.setSeed(555L); 
		for(int k=0;k<10;k++){
			System.out.println(rand.nextInt(20));
		}
		
		
		
//		double n1 = 1.2;
//		int n2=3;
//		double n3 = n1/n2;
//		
//		
//		double[][] array = {{1.568,2.,3},{4.,5.,6.},{7.,8.,10.}};
//		Matrix A = new Matrix(array);
//		
//	   double [][] array1 = {{1,2,3}};
//	   Matrix atemp = new Matrix(1,3);
//	   
//	   int [] array2 = {3,4,2,1,-1};
//	   Arrays.sort(array2);
//	   
//       Matrix ad = new Matrix(3,3);
//       A = A.times(2);
//       A.print(3, 1);
//       ad=ad.plus(A);
//       ad=ad.plus(A);
////       ad=ad.times((double)1/2);
//       ad.print(3, 1);
//		Matrix [] A1 = new Matrix[3];
//		
//		Matrix [][] abc  = new Matrix [3][3];
//		
//		abc[1]= A1;
//	 	
//		 
//		
//		File a = new File("C:\\Users\\linkx\\Desktop\\alibaba\\presudo.txt");
//
//		
//		FileWriter fw = new FileWriter(a,true);
//		RandomAccessFile rdaf = new RandomAccessFile(a, "rw"); 
//		
//		
//	
//		
//		try{
//			
//			
//		 for(int i=0;i<2;i++){
//			 for(int p=0;p<array.length;p++){
//				 for(int q=0;q<array[0].length;q++){
//					 fw.write(Double.toString(array[p][q])+"\t");
//				 }
//				 fw.write("\r\n");
//			 }
//			
//		 }
//		 fw.close();
//		 
//		 for(int i=0;i<2*array.length;i++){
//			 
////		 rdaf.seek(30);
//		 String s = rdaf.readLine();
//		 String[] tests =  s.split("\t");
//		 int num = tests.length;
//		 double [] a2 = new double [num];
//		 for(int k=0;k<num;k++){
//			 a2[k]= Double.parseDouble(tests[k]);
//		 }
//		 String s2 = rdaf.readLine();
//		 
//		 }
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
//		Matrix b = Matrix.random(3,1);
//		Matrix x = A.solve(b);
//		Matrix Residual = A.times(x).minus(b);
//		double rnorm = Residual.normInf();
//		Matrix I = new Matrix(3,3);
//		I= I.identity(3, 3);
//		
//		double [][] I1 = I.getArray();
//		A = A.getMatrix(0, 0, 0, 2);
//		Matrix b1 = A.transpose();
////		Matrix c = A.times(b1);
//		double cv = c.get(0, 0);
//		A.print(3, 1);
//
//		A=A.times(I).times(A.transpose());
//		
//		double [][] A1 = A.getArray();
		
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		System.out.println("time: " +hour + ":" +minute + ":" + second+"s"); 
		
	
	}

}
