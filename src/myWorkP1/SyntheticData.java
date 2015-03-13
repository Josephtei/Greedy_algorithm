package myWorkP1;

import java.util.ArrayList;

public class SyntheticData {

	private ArrayList<Point> allPoint;
	public Point [] restaurantPoint;
	public Point [] candidatePoint;
	public Point [] peoplePoint;
	Point temp;
	int randomNum;
	int restaurantNumber;
	int candidateNumber;
	int peopleNumber;

	public void generateUniformData(int xRange, int yRange, int rNum, int cNum, int pNum){
		
		restaurantNumber = rNum;
		candidateNumber = cNum;
		peopleNumber = pNum;
		
		allPoint = new ArrayList<Point>();
		restaurantPoint = new Point[rNum];
		candidatePoint = new Point[cNum];
		peoplePoint = new Point[pNum];
		
		for (int i = 0; i < xRange; i++)
			for (int j = 0; j < yRange; j++) {
				temp = new Point(i,j);
				allPoint.add(temp);
			}
		
		/*temp = allPoint.get(0);
		allPoint.remove(0);
		System.out.println(temp.getY() + " " + allPoint.get(0).getY());*/
		
		for (int i = 0; i < rNum; i++) { // 餐廳data
			randomNum = (int) (Math.random() * (allPoint.size()));
			restaurantPoint[i] = allPoint.get(randomNum);
			restaurantPoint[i].nodeID = i;
			allPoint.remove(randomNum);
		}

		for (int i = 0; i < cNum; i++) { // 候選地點data
			randomNum = (int) (Math.random() * (allPoint.size()));
			candidatePoint[i] = allPoint.get(randomNum);
			candidatePoint[i].nodeID = i;
			allPoint.remove(randomNum);
		}

		for (int i = 0; i < pNum; i++) { // 人群data
			randomNum = (int) (Math.random() * (allPoint.size()));
			peoplePoint[i] = allPoint.get(randomNum);
			peoplePoint[i].nodeID = i;
			allPoint.remove(randomNum);
		}
		System.out.println(allPoint.size());
	}

}
