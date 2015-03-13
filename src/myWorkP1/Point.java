package myWorkP1;

import java.util.*;

public class Point extends QueueEntry{
	private float xAxis;
	private float yAxis;
	int nodeID;
	float nnDistance; //給people point用
	Point nnPoint; //給people point用
	List <Float> distList; //給candidate point用
	ArrayList <Point> peoList; //給candidate point用
	float nowContribution; //給candidate point用

	public Point(float x, float y){
		xAxis = x;
		yAxis = y;
		nodeID = -1;
		nnDistance = -1;
		nnPoint = null;
		distList = new ArrayList<Float>();
		peoList = new ArrayList<Point>();
		nowContribution = -1;
	}
	
	public Point(){
	}
	
	public void setX(float x){
		xAxis = x;
	}
	public void setY(float y){
		yAxis = y;
	}
	public float getX(){
		return xAxis;
	}
	public float getY(){
		return yAxis;
	}
	public float calculateEnPerimeter(float x1, float y1 , float x2 , float y2){
		if(xAxis >= x1 && xAxis <= x2 && yAxis >= y1 && yAxis <= y2) //落在矩形內
			return -1/((x2-x1)*(y2-y1));
		else{
			float newMinX;
			float newMinY;
			float newMaxX;
			float newMaxY;
			
			if(xAxis < x1)
				newMinX = xAxis;
			else 
				newMinX = x1;
			if(xAxis > x2)
				newMaxX = xAxis;
			else 
				newMaxX = x2;
			if(yAxis < y1)
				newMinY = yAxis;
			else 
				newMinY = y1;
			if(yAxis > y2)
				newMaxY = yAxis;
			else 
				newMaxY = y2;
			
			return ((newMaxX - newMinX) + (newMaxY - newMinY)) - ((x2 - x1) + (y2 - y1));
		}
	}
	
	public void findNN(RTree t){ 
		QueueEntry temp;
		MBR tempMbr;
		PriorityQueue<QueueEntry> pq = new PriorityQueue<QueueEntry>();
		t.root.distance = pointToEntryDist(t.root.minX,t.root.minY,t.root.maxX,t.root.maxY);
		pq.add(t.root);
		while(true){
			temp = pq.poll();
			if(temp instanceof Point){
				nnDistance = temp.distance;
				nnPoint = (Point)temp;
				return ;
			}
			else{
				tempMbr = (MBR)temp;
				for(int i = 0 ; i<tempMbr.nowDegree ; i++){
					if(tempMbr.isLeaf == true){
						tempMbr.dataPtr[i].distance = pointToEntryDist(tempMbr.dataPtr[i].getX(),tempMbr.dataPtr[i].getY(),tempMbr.dataPtr[i].getX(),tempMbr.dataPtr[i].getY());
						pq.add(tempMbr.dataPtr[i]);
					}
					else{
						tempMbr.mbrPtr[i].distance = pointToEntryDist(tempMbr.mbrPtr[i].minX,tempMbr.mbrPtr[i].minY,tempMbr.mbrPtr[i].maxX,tempMbr.mbrPtr[i].maxY);
						pq.add(tempMbr.mbrPtr[i]);
					}
				}
			}
		}
	}
	
	private float pointToEntryDist(float x1, float y1 , float x2 , float y2){
		float xDist = 0;
		float yDist = 0;
		if(xAxis >= x1 && xAxis <= x2 && yAxis >= y1 && yAxis <= y2) //落在矩形內
			return 0;
		else{
			if(xAxis < x1)
				xDist = Math.abs(xAxis - x1);
			if(xAxis > x2)
				xDist = Math.abs(xAxis - x2);
			if(yAxis < y1)
				yDist = Math.abs(yAxis - y1);
			if(yAxis > y2)
				yDist = Math.abs(yAxis - y2);
			return (float)Math.sqrt((xDist*xDist + yDist*yDist));
		}
	}
	
	public void findHelpPeoples(Point [] p){
		float tempRes;
		nowContribution = 0;
		for(Point tempP : p){
			tempRes = pointToEntryDist(tempP.getX(),tempP.getY(),tempP.getX(),tempP.getY());
			if(tempRes < tempP.nnDistance){
				distList.add(tempP.nnDistance - tempRes);
				peoList.add(tempP);
				nowContribution+=(tempP.nnDistance - tempRes);
			}
		}
	}
}
