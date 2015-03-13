package myWorkP1;

import java.util.*;

public class QueueEntry implements Comparable{
	float distance;
	
	@Override
	public int compareTo(Object o){
		QueueEntry e = (QueueEntry)o;
		if(e.distance < this.distance)
			return 1;
		if(e.distance > this.distance)
			return -1;
		return 0;
	}
}
