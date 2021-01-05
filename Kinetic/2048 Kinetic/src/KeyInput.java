import java.util.Scanner;

public class KeyInput{
	/*
	 * 1 - left
	 * 2 - down
	 * 3 - right
	 * 5 - up
	 */
	public int direction = -1;
	private Scanner read; 
	private String temp;
	
	public KeyInput() {
		read = new Scanner(System.in);
	}
	
	public void readDirection() {
		temp = read.nextLine();
		
		if(temp.equals("1")) {
			direction = 1;
		}else if(temp.equals("2")) {
			direction =  2;
		}else if(temp.equals("3")) {
			direction = 3;
		}else if(temp.equals("5")) {
			direction =  5;
		}
	}
	
	public int getDirection() {
		return direction;
	}
}
