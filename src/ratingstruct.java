
public class ratingstruct {
    public int userid ;
    public int itemid;
    public int ratings;
    public int  timestamp;
	
	
	public ratingstruct(int userid, int itemid, int ratings, int timestamp){
		
		this.userid = userid;
		this.itemid = itemid;
		this.ratings = ratings;
		this.timestamp = timestamp;
		
	}
	
	
	public int uid(){
		return this.userid;
	}
	
	public int itemid(){
		return this.itemid;
	}
	
	public int ratings(){
		return this.ratings;
	}
	
	public int time(){
		return this.timestamp;
	}
}
