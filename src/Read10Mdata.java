import java.io.*;
import java.util.*;


public class Read10Mdata {
	
	public int ni=0;
	
	public void read(int nu ) throws IOException{
		
		
		
		
		// READ IN Data 
		File datafile = new File("C:\\Users\\linkx\\Desktop\\10Mratings.txt");
		FileReader fr = new FileReader(datafile);
		BufferedReader br = new BufferedReader(fr);
		String line ="";
		
 
		int num =10000054;
		
		ratingstruct []  rst = new ratingstruct[num];
		int i=0;
		
		Set<Integer> user = new HashSet<Integer>();
		Set<Integer> item = new HashSet<Integer>();
		
		while((line=br.readLine())!=null){
			String[] s = line.split(" ");
			int userid = Integer.parseInt(s[0]);
			int itemid = Integer.parseInt(s[1]);
			double rating1 =2*Double.parseDouble(s[2]);
			int ratings = (int) rating1;
			int timestamp = Integer.parseInt(s[3]);
			if(ratings==0){
				
				System.out.println("err");
			}
			rst[i] = new ratingstruct(userid, itemid, ratings, timestamp);
			
			user.add(userid);
			item.add(itemid);
			
			i++;
		}
		
		// DOWN SAMPLE USERS AND ITEMS
//		int nu = 10000;
		int ni = 0;
		
		int usernumber = user.size();
		int itemnumber = item.size();
		
		int [] userarray = new int [usernumber];
		Object [] tempu = new Object [usernumber];
        user.toArray(tempu);
		for(int j=0;j<usernumber;j++){
			userarray[j]=(Integer)tempu[j];
		}
		
		Set<Integer> sampleuser = new HashSet<Integer>();
		Set<Integer> sampleitem = new HashSet<Integer>();
		
		
		Random rand = new Random();
		rand.setSeed(555L); 
		while(sampleuser.size()!=nu){
			int temp = rand.nextInt(usernumber); 
		
			
			sampleuser.add(userarray[temp]);
			
		}
//		while(sampleitem.size()!=ni){
//			int temp = rand.nextInt(itemnumber+1);
//			if(temp!=0)
//			sampleitem.add(temp);
//		}
		
		// remove those user's who don't have enough ratings.
		
		
		int numofratings=0; // this record the number of ratings after sampled by user and item
		for(ratingstruct j : rst){
			if(sampleuser.contains(j.userid)){
				numofratings ++;
			}
		}
		
		ratingstruct []  rstsed = new ratingstruct[numofratings];  // this record the ratings after sampling. 
        // filter ratings: get the ratings after sampled
		int i1=0;
		for(ratingstruct j:rst){
//			if(sampleuser.contains(j.userid)&&sampleitem.contains(j.itemid)){
			if(sampleuser.contains(j.userid)){
 
				rstsed[i1]=j;
				i1++;
				sampleitem.add(j.itemid);
			}
		}
		
		ni = sampleitem.size();
		this.ni = ni;
		// map sampled user and item id from zero to nu-1 and ni-1
	    Object [] suser = new Object [nu];
		sampleuser.toArray(suser);
		
		Object [] sitem = new Object [ni];
		sampleitem.toArray(sitem);
		
		
		int [] suserint = new int [nu];
		int [] sitemint = new int [ni];
		for(int j=0;j<nu;j++){
			suserint[j]=(Integer)suser[j];
		}
		for(int j=0;j<ni;j++){
			sitemint[j]=(Integer)sitem[j];
		}
	 
		Arrays.sort(suserint);
		Arrays.sort(sitemint);
		
		Map<Integer,Integer> usermap = new HashMap();
		Map<Integer,Integer> itemmap = new HashMap();
		
		for(int j=0;j<nu;j++){
			usermap.put(suserint[j], j+1);
		}
		for(int j=0;j<ni;j++){
			itemmap.put(sitemint[j], j+1);
		}
		
		// write in file.
		
		File f = new File("C:\\Users\\linkx\\Desktop\\10Msampledratings.txt");
		FileWriter fa = new FileWriter(f,true);
		
		try{
			for(int j=0;j<numofratings;j++){
				
				 int userid = rstsed[j].userid;
				 int itemid = rstsed[j].itemid;
				 int rating = rstsed[j].ratings;
				 int time  = rstsed[j].timestamp;
				 int u1 = usermap.get(userid);
				 int it1= itemmap.get(itemid);
				 
//				 if(rating==0){
//					 System.out.println("err");
//				}
//				 if(u1==71){
//					 System.out.println("err71");
//				}
				 
				 String u = String.valueOf(u1);
				 String it = String.valueOf(it1);
				 String r = String.valueOf(rating);
				 if(r=="0"){
					 System.out.println("err");
				}
				 String t = String.valueOf(time);
			 
				fa.write(u+"\t"+it+"\t"+r+"\t"+t);				
				fa.write("\n");
			
			}
			fa.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	
		System.out.println("end");
		
	}

   
}







