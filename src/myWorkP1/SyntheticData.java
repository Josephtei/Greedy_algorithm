package myWorkP1;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Object;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;




public class SyntheticData {

	private ArrayList<Point> allPoint;
	public Point [] restaurantPoint;
	public Point [] candidatePoint;
	public Point [] peoplePoint;
	int restaurantNumber;
	int candidateNumber;
	int peopleNumber;
	int rPtr;
	int cPtr;
	int pPtr;
	int xBound;
	int yBound;

	public void generateUniformData(int xRange, int yRange, int rNum, int cNum, int pNum){
		
		Point temp;
		int randomNum;
		
		restaurantNumber = rNum;
		candidateNumber = cNum;
		peopleNumber = pNum;
		
		xBound = xRange;
		yBound = yRange;
		
		allPoint = new ArrayList<Point>();
		restaurantPoint = new Point[rNum];
		candidatePoint = new Point[cNum];
		peoplePoint = new Point[pNum];
		
		for (int i = 0; i < xBound; i++)
			for (int j = 0; j < yBound; j++) {
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
		//System.out.println(allPoint.size());
	}
	
	
	public void generateUniformDataV2(int xRange, int yRange, int rNum, int cNum, int pNum){
		
		Point temp;
		
		restaurantNumber = rNum;
		candidateNumber = cNum;
		peopleNumber = pNum;
		
		xBound = xRange;
		yBound = yRange;
		
		restaurantPoint = new Point[rNum];
		candidatePoint = new Point[cNum];
		peoplePoint = new Point[pNum];
		
		for(int i=0;i<rNum;i++){
			temp = new Point((float)(Math.random()*xBound),(float)(Math.random()*yBound));
			restaurantPoint[i] = temp;
			restaurantPoint[i].nodeID = i;
		}
		
		for(int i=0;i<cNum;i++){
			temp = new Point((float)(Math.random()*xBound),(float)(Math.random()*yBound));
			candidatePoint[i] = temp;
			candidatePoint[i].nodeID = i;
		}
		
		for(int i=0;i<pNum;i++){
			temp = new Point((float)(Math.random()*xBound),(float)(Math.random()*yBound));
			peoplePoint[i] = temp;
			peoplePoint[i].nodeID = i;
		}
	}
	
	public void normalData(double mean,double sigma2,int xRange, int yRange, int rNum, int cNum, int pNum){
		
		double meanArray [] = new double[2];
		double covarianceArray [][] = new double[2][2];
		double tempSample [];
		int rNumber = 0;
		int cNumber = 0;
		int pNumber = 0;
		Point temp;
		
		xBound = xRange;
		yBound = yRange;
		
		meanArray[0] = mean;
		meanArray[1] = mean;
		
		covarianceArray[0][0] = sigma2;
		covarianceArray[1][1] = sigma2;
		covarianceArray[0][1] = 0;
		covarianceArray[1][0] = 0;
		
		restaurantNumber = rNum;
		candidateNumber = cNum;
		peopleNumber = pNum;
		
		restaurantPoint = new Point[rNum];
		candidatePoint = new Point[cNum];
		peoplePoint = new Point[pNum];
		
		MultivariateNormalDistribution mu = new MultivariateNormalDistribution(meanArray,covarianceArray);
		
		while(true){
			
			if(rNumber < rNum){
				tempSample = mu.sample();
				if(tempSample[0] <= xBound/2 && tempSample[0] >= -xBound/2 && tempSample[1] <= yBound/2 && tempSample[1] >= -yBound/2){
					temp = new Point((float)tempSample[0],(float)tempSample[1]);
					restaurantPoint[rNumber] = temp;
					restaurantPoint[rNumber].nodeID = rNumber;
					rNumber++;
				}
			}
			
			if(cNumber < cNum){
				tempSample = mu.sample();
				if(tempSample[0] <= xBound/2 && tempSample[0] >= -xBound/2 && tempSample[1] <= yBound/2 && tempSample[1] >= -yBound/2){
					temp = new Point((float)tempSample[0],(float)tempSample[1]);
					candidatePoint[cNumber] = temp;
					candidatePoint[cNumber].nodeID = cNumber;
					cNumber++;
				}
			}
			
			if(pNumber < pNum){
				tempSample = mu.sample();
				if(tempSample[0] <= xBound/2 && tempSample[0] >= -xBound/2 && tempSample[1] <= yBound/2 && tempSample[1] >= -yBound/2){
					temp = new Point((float)tempSample[0],(float)tempSample[1]);
					peoplePoint[pNumber] = temp;
					peoplePoint[pNumber].nodeID = pNumber;
					pNumber++;
				}
			}
			
			if(rNumber == rNum && cNumber == cNum && pNumber == pNum)
				break;		
		}
	}
	
	public void zifData(int n ,double sigma2,int xRange, int yRange, int rNum, int cNum, int pNum){
		
		int maxRnum;
		int maxCnum;
		int maxPnum;
		float tempValue = 0;
		double randomX;
		double randomY;
		
		rPtr = 0;
		cPtr = 0;
		pPtr = 0;
		restaurantNumber = rNum;
		candidateNumber = cNum;
		peopleNumber = pNum;
		restaurantPoint = new Point[rNum];
		candidatePoint = new Point[cNum];
		peoplePoint = new Point[pNum];
		xBound = xRange;
		yBound = yRange;
		
		
		for(int i=1;i<=n;i++)
			tempValue += (1/i);
		
		maxRnum = (int)(rNum*(1/tempValue));
		maxCnum = (int)(cNum*(1/tempValue));
		maxPnum = (int)(pNum*(1/tempValue));
		
		for(int i=1;i<=(n+1);i++){ 
			
			randomX = Math.random()*(xRange-100)+50;
			randomY = Math.random()*(yRange-100)+50;
			
			rPtr = normalGeneator(restaurantPoint,rPtr,maxRnum*(1/i),randomX,randomY,sigma2);
			cPtr = normalGeneator(candidatePoint,cPtr,maxCnum*(1/i),randomX,randomY,sigma2);
			pPtr = normalGeneator(peoplePoint,pPtr,maxPnum*(1/i),randomX,randomY,sigma2);
		}
	}
	
	private int normalGeneator(Point [] dataKind , int ptrNum , int dataNum , double meanX , double meanY , double sigma2){
		
		int counter = 0;
		double meanArray [] = new double[2];
		double covarianceArray [][] = new double[2][2];
		double tempSample [];
		Point temp;
		int dataPtr = ptrNum;
		
		
		meanArray[0] = meanX;
		meanArray[1] = meanY;
		
		covarianceArray[0][0] = sigma2;
		covarianceArray[1][1] = sigma2;
		covarianceArray[0][1] = 0;
		covarianceArray[1][0] = 0;
		
		MultivariateNormalDistribution mu = new MultivariateNormalDistribution(meanArray,covarianceArray);
		
		while(counter < dataNum){
			if(dataPtr > dataKind.length)
				break;
			
			tempSample = mu.sample();
			
			if(tempSample[0] <= xBound && tempSample[0] >= 0 && tempSample[1] <= yBound && tempSample[1] >= 0){
				temp = new Point((float)tempSample[0],(float)tempSample[1]);
				dataKind[dataPtr] = temp;
				dataKind[dataPtr].nodeID = dataPtr;
				dataPtr++;
				counter++;
			}
		}
		
		return dataPtr;
	}
	
	public void realData(String popuName , int popuNum , String culName , int culNum){
		
		BufferedReader br;
		String [] line;
		Point temp;
		int randomNum;
		ArrayList <Point> culPoint = new ArrayList<Point>();
		
		restaurantNumber = culNum/2;
		candidateNumber = culNum/2;
		peopleNumber = popuNum;
		
		restaurantPoint = new Point[culNum/2];
		candidatePoint = new Point[culNum/2];
		peoplePoint = new Point[popuNum];
		
		System.out.println(popuName);
		
		try{
			br = new BufferedReader(new FileReader(popuName));
			while(br.ready()){
				line = br.readLine().split("\\s:\\s");
				
				temp = new Point(Float.parseFloat(line[0]),Float.parseFloat(line[1]));
				temp.nodeID = Integer.parseInt(line[2])-1;
				peoplePoint[temp.nodeID] = temp;
				
			}
			br.close();
			
			br = new BufferedReader(new FileReader(culName));
			while(br.ready()){
				line = br.readLine().split("\\s:\\s");
				
				temp = new Point(Float.parseFloat(line[0]),Float.parseFloat(line[1]));
				culPoint.add(temp);
				
			}
			br.close();
		}
		catch(IOException e){
			System.out.println("IOException: " + e);
		}
		
		for (int i = 0; i < (culNum/2); i++) { // 餐廳data
			randomNum = (int) (Math.random() * (culPoint.size()));
			restaurantPoint[i] = culPoint.get(randomNum);
			restaurantPoint[i].nodeID = i;
			culPoint.remove(randomNum);
		}
		
		for (int i = 0; i < (culNum/2); i++) { // 候選data
			randomNum = (int) (Math.random() * (culPoint.size()));
			candidatePoint[i] = culPoint.get(randomNum);
			candidatePoint[i].nodeID = i;
			culPoint.remove(randomNum);
		}
		
		//System.out.println(culNum/2);
	}

}
