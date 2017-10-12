package ca.zenotek.sentek;

public class SentekCatchVector{

	private boolean catchval;
	private SentekVector vector;
	
	public SentekCatchVector(SentekVector vector, boolean bool){
		this.vector = vector;
		this.catchval = bool;
	}
	
	public SentekVector getVector(){
		return this.vector;
	}

	public boolean getCatch(){
		return this.catchval;
	}
	
}
