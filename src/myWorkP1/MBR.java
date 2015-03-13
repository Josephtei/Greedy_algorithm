package myWorkP1;

public class MBR extends QueueEntry{
	float maxX;
	float maxY;
	float minX;
	float minY;
	int maxDegree;
	int nowDegree;
	int level;
	boolean isLeaf;
	MBR [] mbrPtr;
	Point [] dataPtr;
	MBR parentPtr;
	
	public MBR(int de , boolean l){
		maxX = -1;
		maxY = -1;
		minX = 99999;
		minY = 99999;
		maxDegree = de;
		nowDegree = 0;
		level = 0;
		isLeaf = l;
		parentPtr = null;
		
		if(isLeaf == true)
			dataPtr = new Point[maxDegree+1];
		else
			mbrPtr = new MBR[maxDegree+1];
	}
	
	public MBR(){
	}
}
