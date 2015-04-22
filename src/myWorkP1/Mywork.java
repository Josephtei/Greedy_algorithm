package myWorkP1;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Mywork {
	
	public static RTree resRtree;
	public static ArrayList <Point> answerSet;
	public static float [] peopleDr;
	public static SyntheticData syndata;
	public static byte [] chooseCandidate;
	
	public static void main(String[] args) {
//		syndata = new SyntheticData();
//		syndata.generateUniformDataV2(1000,1000,1000,1000,100000);  //dataset(x�d��,y�d��,�\�U,�Կ�,�H�s)
		syndata.zifData(10, 6400, 1000, 1000, 1000, 1000, 100000);
//		syndata.normalData(0,6400,500,500,100,100,100000);
//		syndata.generateUniformData(500,500,100,100,100000); //dataset(x�d��,y�d��,�\�U,�Կ�,�H�s)
		resRtree = new RTree(2);
		
		//
		
		for(Point tempP : syndata.restaurantPoint){ //�{���\�UR-tree
			resRtree.add(tempP);
		}
		
		for(Point tempP : syndata.peoplePoint) //�C��people�����ۤv�̪񪺲{���\�U
			tempP.findNN(resRtree);
		
		//syndata.candidatePoint[0].findHelpPeoples(syndata.peoplePoint);
		
		for(Point tempP : syndata.candidatePoint) //�p��candidate�i�H������people�Y�u�h�ֶZ��
			tempP.findHelpPeoples(syndata.peoplePoint);
		
		long startTime = System.currentTimeMillis(); //�_�l�ɶ�
		
		peopleDr = new float[syndata.peopleNumber]; //�ثe��C��people��֦h�ֶZ��
		for(int i = 0;i<syndata.peopleNumber;i++)
			peopleDr[i] = 0;
		
		chooseCandidate = new byte[syndata.candidateNumber]; //��L��candidate�]��1
		for(int i = 0;i<syndata.candidateNumber;i++)
			chooseCandidate[i] = 0;
		
		answerSet = new ArrayList<Point>(); //new����List
		
		writeCandidate();
		//findAnswer(10);
		
		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;
		System.out.println("Using Time:" + totTime);
				
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
	
	private static void writeCandidate(){
		int counter = 0;
		try{
			FileWriter fw = new FileWriter("candidateInf1.out");
			for(Point tempP : syndata.candidatePoint){
				fw.write(Float.toString(tempP.nowContribution));
				for(int i = 0 ; i < tempP.peoList.size() ; i++){
					fw.write(" " + Integer.toString(tempP.peoList.get(i).nodeID) + " " + Float.toString(tempP.distList.get(i)));
				}
				fw.write("\r\n");
				counter++;
			}
			fw.close();
		}
		catch(IOException e){
			System.out.println("IOException: " + e);
		}
		System.out.println(counter);
	}
}
