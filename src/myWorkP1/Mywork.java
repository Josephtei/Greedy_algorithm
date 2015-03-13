package myWorkP1;

import java.util.*;

public class Mywork {
	
	public static RTree resRtree;
	public static ArrayList <Point> answerSet;
	public static float [] peopleDr;
	public static SyntheticData syndata;
	public static byte [] chooseCandidate;
	
	public static void main(String[] args) {
		syndata = new SyntheticData();
		syndata.generateUniformData(1000,1000,5000,10000,100000); //dataset(x範圍,y範圍,餐廳,候選,人群)
		resRtree = new RTree(2);
		
		
		
		for(Point tempP : syndata.restaurantPoint){ //現有餐廳R-tree
			resRtree.add(tempP);
		}
		
		for(Point tempP : syndata.peoplePoint) //每個people找離自己最近的現有餐廳
			tempP.findNN(resRtree);
		
		//syndata.candidatePoint[0].findHelpPeoples(syndata.peoplePoint);
		
		for(Point tempP : syndata.candidatePoint) //計算candidate可以為哪些people縮短多少距離
			tempP.findHelpPeoples(syndata.peoplePoint);
		
		long startTime = System.currentTimeMillis(); //起始時間
		
		peopleDr = new float[syndata.peopleNumber]; //目前對每個people減少多少距離
		for(int i = 0;i<syndata.peopleNumber;i++)
			peopleDr[i] = 0;
		
		chooseCandidate = new byte[syndata.candidateNumber]; //選過的candidate設為1
		for(int i = 0;i<syndata.candidateNumber;i++)
			chooseCandidate[i] = 0;
		
		answerSet = new ArrayList<Point>(); //new答案List
		
		findAnswer(512);
		
		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;
		System.out.println("Using Time:" + totTime);
		
		
//		for(int i=0;i<answerSet.size();i++)
//			System.out.println(answerSet.get(i).nowContribution);
//		for(int i = 0;i<syndata.candidateNumber;i++)
//			System.out.println(syndata.candidatePoint[i].nowContribution);
		//System.out.println(syndata.peoplePoint[4999].getX() + " " + syndata.peoplePoint[4999].getY() + " "+ syndata.peoplePoint[4999].nnPoint.getX() + " "+ syndata.peoplePoint[4999].nnPoint.getY() + " " + syndata.peoplePoint[4999].nnDistance);
		
		
		
//		Point p1 = new Point(5,7);
//		p1.findNN(resRtree);
//		System.out.println(p1.nnPoint.getX() + " " + p1.nnPoint.getY() + " " + p1.nnDistance);
		
			
//		ResPoint[] resTest = new ResPoint[7];
//		resTest[0] = setPoint(resTest[0], 1, 10);
//		resTest[1] = setPoint(resTest[1], 6, 2);
//		resTest[2] = setPoint(resTest[2], 4, 4);
//		resTest[3] = setPoint(resTest[3], 8, 6);
//		resTest[4] = setPoint(resTest[4], 2, 2);
//		resTest[5] = setPoint(resTest[5], 1, 6);
//		resTest[6] = setPoint(resTest[6], 5, 7);
//
//		for(int i = 0; i < 7; i++){
//			si.add(resTest[i]);
//		}
//		
//		Point tempP = new Point(4,3);
//		System.out.println(tempP.findNN(si).getX() + " " + tempP.findNN(si).getY() + " " + tempP.findNN(si).distance);
//		tempP = new Point(5,6);
//		System.out.println(tempP.findNN(si).getX() + " " + tempP.findNN(si).getY() + " " + tempP.findNN(si).distance);
//		
//		System.out.println(si.root.minX + " " + si.root.minY + " " + si.root.maxX + " " + si.root.maxY);
//		System.out.println(si.root.mbrPtr[0].minX + " " + si.root.mbrPtr[0].minY + " " + si.root.mbrPtr[0].maxX + " " + si.root.mbrPtr[0].maxY + " " );
//		System.out.println(si.root.mbrPtr[1].minX + " " + si.root.mbrPtr[1].minY + " " + si.root.mbrPtr[1].maxX + " " + si.root.mbrPtr[1].maxY + " " );
	}

	private static Point setPoint(Point p, int x, int y) {
		p = new Point(x,y);
		return p;
	}
	
	private static void findAnswer(int k){
		float maxSum = -1;
		float originNowContribution;
		Point maxP = null;
		int tempID;
		for(int i=0;i<k;i++){
			for(int j=0;j<syndata.candidateNumber;j++){
				if(chooseCandidate[j] != 1){
					originNowContribution = syndata.candidatePoint[j].nowContribution;
					for(int l=0;l<syndata.candidatePoint[j].peoList.size();l++){
						tempID = syndata.candidatePoint[j].peoList.get(l).nodeID;
						if(peopleDr[tempID]!=0)
							syndata.candidatePoint[j].nowContribution -= ((peopleDr[tempID] > syndata.candidatePoint[j].distList.get(l))?(syndata.candidatePoint[j].distList.get(l)):peopleDr[tempID]);
					}
					if(syndata.candidatePoint[j].nowContribution > maxSum){
						System.out.println(333);
						maxSum = syndata.candidatePoint[j].nowContribution;
						maxP = syndata.candidatePoint[j];
					}
					syndata.candidatePoint[j].nowContribution = originNowContribution;
				}
			}
			answerSet.add(maxP);
			chooseCandidate[maxP.nodeID] = 1;
			for(int j=0;j<maxP.peoList.size();j++){
				tempID = maxP.peoList.get(j).nodeID;
				if(maxP.distList.get(j) > peopleDr[tempID])
					peopleDr[tempID] = maxP.distList.get(j);
			}
			maxSum = -1;
		}
	}
}
