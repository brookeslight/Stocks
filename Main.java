package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	
	//Date,Open,High,Low,Close,Adj Close,Volume
	public static void main(String[] args) {
		calculate(0.001f);
	}
	
	public static void calculate(float delta) {
		int n = 0;
		int openUp = 0;
		int openGreen = 0;
		int openRed = 0;
		
		int openSame = 0;
		int sameGreen = 0;
		int sameRed = 0;

		int openDown = 0;
		int downGreen = 0;
		int downRed = 0;
		//
		try(BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\data.txt"))) {
			String line;
			float prevClose = 0;
			while((line = br.readLine()) != null) {
				String[] lineData = line.split(","); //date,open,high,low,close,adj_close, volume
				float open = Float.valueOf(lineData[1]);
				float high = Float.valueOf(lineData[2]);
				float low = Float.valueOf(lineData[3]);
				float close = Float.valueOf(lineData[4]);
				//
				boolean up = ((high-open)/open) >= delta; //went up by at least delta %
				boolean down = ((open-low)/open) >= delta; //went down by at least delta %
				if(open > prevClose) { //open up
					openUp++;
					if(up) {
						openGreen++;
					} 
					if(down) {
						openRed++;
					}
				} else if(prevClose > open) { //open down
					openDown++;
					if(up) {
						downGreen++;
					}
					if(down) {
						downRed++;
					}
				} else { //open same
					openSame++;
					if(up) {
						sameGreen++;
					} 
					if(down) {
						sameRed++;
					}
				}
				//
				n++;
				prevClose = close;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
		float chanceOpen = 1.0f*openUp/n;
		float chanceDown = 1.0f*openUp/n;
		float chanceSame = 1.0f*openSame/n;
		System.out.println("Open Above Close: " + (100.0f*openUp)/n + "%");
		System.out.println("-Went Up: " + (100.0f*openGreen)/openUp + "%");
		System.out.println("-Went down: " + (100.0f*openRed)/openUp + "%");
		System.out.println();
		System.out.println("Open Same: " + (100.0f*openSame)/n + "%");
		System.out.println("-Went Up: " + (100.0f*sameGreen)/openSame + "%");
		System.out.println("-Went down: " + (100.0f*sameRed)/openSame + "%");
		System.out.println();
		System.out.println("Open Below Close: " + (100.0f*openDown)/n + "%");
		System.out.println("-Went Up: " + (100.0f*downGreen)/openDown + "%");
		System.out.println("-Went down: " + (100.0f*downRed)/openDown + "%");
		System.out.println("-------------------Global-------------------");
		System.out.println("Up-Went Up: " + (chanceOpen*100.0f*openGreen)/openUp + "%");
		System.out.println("Up-Went down: " + (chanceOpen*100.0f*openRed)/openUp + "%");
		System.out.println("Same-Went Up: " + (chanceSame*100.0f*downGreen)/openDown + "%");
		System.out.println("Same-Went down: " + (chanceSame*100.0f*downRed)/openDown + "%");
		System.out.println("Down-Went Up: " + (chanceDown*100.0f*downGreen)/openDown + "%");
		System.out.println("Down-Went down: " + (chanceDown*100.0f*downRed)/openDown + "%");
	}
	
}
