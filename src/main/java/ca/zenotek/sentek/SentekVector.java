package ca.zenotek.sentek;

public class SentekVector {

	private int x;
	private int y;
	private int z;
	private boolean platform;
	private int distance;
	
	public SentekVector(int x, int y, int z, boolean platform, int distance){
		this.x = x;
		this.y = y;
		this.z = z;
		this.platform = platform;
		this.distance = distance;
	}
	
	public SentekVector(int x, int y, int z){
		this(x, y, z, false, 0);
	}
	
	public SentekVector(){
		this(0, 0, 0);
	}
	
	public SentekVector(boolean bool){
		this(0, 0, 0, bool, 0);
	}
	
	public SentekVector(int distance){
		this(0, 0, 0, false, distance);
	}
	
	public void addVector(SentekVector vector){
		this.x += vector.getX();
		this.y += vector.getY();
		this.z += vector.getZ();
		this.platform = this.platform || vector.platform;
		this.distance += vector.getDistance();
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getZ(){
		return this.z;
	}
	
	public boolean getPlatform(){
		return this.platform;
	}
	
	public int getDistance(){
		return this.distance;
	}
	
}
