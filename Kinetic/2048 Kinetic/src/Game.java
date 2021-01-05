public class Game {
	public static tile[][] board = BoardMethods.createBoard();
	public static KeyInput keyInput = new KeyInput();
	
	public static void main(String[] args) {
		boolean movement = true;
		
		while(BoardMethods.checkBoard(board)) {
			if(movement) {
				BoardMethods.placeValue(board);
				BoardMethods.printboard(board);
				
				if(!BoardMethods.checkBoard(board)) {
					break;
				}
				
				System.out.println("What direction would you like to go in? 1 - left; 2 - down; 3 - right; 5 - up");
				
				keyInput.readDirection();
				
				while(keyInput.getDirection() == -1) {
					System.out.println("Direction choosed was invalide choose again. 1 - left; 2 - down; 3 - right; 5 - up");
					
					keyInput.readDirection();
				}
				
				if(keyInput.getDirection() == 1) {
					movement = BoardMethods.mergeLeft(board);
				}else if(keyInput.getDirection() == 2) {
					movement = BoardMethods.mergeDown(board);
				}else if(keyInput.getDirection() == 3) {
					movement = BoardMethods.mergeRight(board);
				}else if(keyInput.getDirection() == 5) {
					movement = BoardMethods.mergeUp(board);
				}
			} else {
				System.out.println("That was an invalid movement, try again");
				
				System.out.println("What direction would you like to go in? 1 - left; 2 - down; 3 - right; 5 - up");
				
				keyInput.readDirection();
				
				while(keyInput.getDirection() == -1) {
					System.out.println("Direction choosed was invalide choose again. 1 - left; 2 - down; 3 - right; 5 - up");
					
					keyInput.readDirection();
				}
				
				if(keyInput.getDirection() == 1) {
					movement = BoardMethods.mergeLeft(board);
				}else if(keyInput.getDirection() == 2) {
					movement = BoardMethods.mergeDown(board);
				}else if(keyInput.getDirection() == 3) {
					movement = BoardMethods.mergeRight(board);
				}else if(keyInput.getDirection() == 5) {
					movement = BoardMethods.mergeUp(board);
				}
			}
		}
		
		System.out.println("No movements possible, Game Over");
	}
}