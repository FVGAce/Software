import java.util.Random;

public class BoardMethods {
	public static Random rand = new Random();
	
	public static tile[][] createBoard(){
		tile[][] board = new tile[4][4];
		
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				board[row][col] = new tile();
			}
		}
		
		return board;
	}
	
	public static void printboard(tile[][] board) {
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				if(col == 0) {
					System.out.print("|");
				}
								
				System.out.print(board[row][col].getTileValue() + "|");
			}
			System.out.println();
		}
		
		System.out.println();
	}
	
	public static int checkFreeSpaces(tile[][] board) {
		int freeSpaces = 0;
		
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				if(board[row][col].getTileValue() == 0) {
					freeSpaces++;
				}
			}
		}
		
		return freeSpaces;
	}
	
	public static boolean checkBoard(tile[][] board) {
		if(checkFreeSpaces(board) == 0) {
			for(int row = 0; row < board.length; row++) {
				for(int col = 0; col < board.length; col++) {
					 if(row == 0) {
						 if(col == 0) {
							 if(board[row][col].getTileValue() == board[row + 1][col].getTileValue() || board[row][col].getTileValue() == board[row][col + 1].getTileValue()) {
								 return true;
							 }
						 } else if(col == 3) {
							 if(board[row][col].getTileValue() == board[row + 1][col].getTileValue() || board[row][col].getTileValue() == board[row][col - 1].getTileValue()) {
								 return true;
							 }
						 } else {
							 if(board[row][col].getTileValue() == board[row + 1][col].getTileValue() || board[row][col].getTileValue() == board[row][col + 1].getTileValue() || board[row][col].getTileValue() == board[row][col - 1].getTileValue()) {
								 return true;
							 }
						 }
					 } else if(row == 3) {
						 if(col == 0) {
							 if(board[row][col].getTileValue() == board[row - 1][col].getTileValue() || board[row][col].getTileValue() == board[row][col + 1].getTileValue()) {
								 return true;
							 }
						 } else if(col == 3) {
							 if(board[row][col].getTileValue() == board[row - 1][col].getTileValue() || board[row][col].getTileValue() == board[row][col - 1].getTileValue()) {
								 return true;
							 }
						 } else {
							 if(board[row][col].getTileValue() == board[row - 1][col].getTileValue() || board[row][col].getTileValue() == board[row][col + 1].getTileValue() || board[row][col].getTileValue() == board[row][col - 1].getTileValue()) {
								 return true;
							 }
						 }
					 } else {
						 if(col == 0) {
							 if(board[row][col].getTileValue() == board[row - 1][col].getTileValue() || board[row][col].getTileValue() == board[row + 1][col].getTileValue() || board[row][col].getTileValue() == board[row][col + 1].getTileValue()) {
								 return true;
							 }
						 } else if(col == 3) {
							 if(board[row][col].getTileValue() == board[row - 1][col].getTileValue() || board[row][col].getTileValue() == board[row + 1][col].getTileValue() || board[row][col].getTileValue() == board[row][col - 1].getTileValue()) {
								 return true;
							 }
						 } else {
							 if(board[row][col].getTileValue() == board[row - 1][col].getTileValue() || board[row][col].getTileValue() == board[row + 1][col].getTileValue() || board[row][col].getTileValue() == board[row][col + 1].getTileValue() || board[row][col].getTileValue() == board[row][col - 1].getTileValue()) {
								 return true;
							 }
						 }
					 }
				}
			}
		} else if(checkFreeSpaces(board) > 0 ){
			return true;
		}
		
		return false;
	}
	
	public static int checkHighestValue(tile[][] board) {
		int highestValue = 0;
		
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				if(board[row][col].getTileValue() > highestValue) {
					highestValue = board[row][col].getTileValue();
				}
			}
		}		
		
		return highestValue;
	}
	
	public static int chooseValue(int highestValue) {
		int choosenValue = rand.nextInt(2);
		
		if(highestValue < 16) {
			return 2;
		} else {
			if(choosenValue == 0) {
				return 2;
			} else {
				return 4;
			}
		}
	}
	
	public static void placeValue(tile[][] board) {
		int row = rand.nextInt(4), col = rand.nextInt(4);
		int placementTimes = rand.nextInt(2) + 1;
		int highestValue = checkHighestValue(board);
		
		if(checkFreeSpaces(board) < 3) {
			while(board[row][col].getTileValue() != 0) {
				row = rand.nextInt(4);
				col = rand.nextInt(4);
			}
			
			board[row][col].setTileValue(chooseValue(highestValue));
		} else if(checkFreeSpaces(board) == 16) {
			placementTimes = 2;
			
			while(placementTimes > 0) {
				while(board[row][col].getTileValue() != 0) {
					row = rand.nextInt(4);
					col = rand.nextInt(4);
				}
				
				board[row][col].setTileValue(chooseValue(highestValue));
				
				placementTimes--;
			}
		} else {
			while(placementTimes > 0) {
				while(board[row][col].getTileValue() != 0) {
					row = rand.nextInt(4);
					col = rand.nextInt(4);
				}
				
				board[row][col].setTileValue(chooseValue(highestValue));
				
				placementTimes--;
			}
		}
	}
	
	public static boolean mergeLeft(tile[][] board) {
		boolean movement = false;
		
		for(int row = 0; row < board.length; row++) {
			for(int col = 1; col < board.length; col++) {
				if(board[row][col].getTileValue() != 0) {
					int placeCol = col;
					boolean move = false, combine = false;
					
					for(int tempCol = col - 1; tempCol >= 0; tempCol--) {
						if(board[row][tempCol].getTileValue() == 0) {
							placeCol = tempCol;
							move = true;
						}
					}
					
					if(placeCol != 0) {
						if(board[row][placeCol - 1].getTileValue() == board[row][col].getTileValue()) {
							board[row][placeCol - 1].addTileValue(board[row][col].getTileValue());
							move = true;
							combine = true;
						}
					}
					
					if(move){
						if(!combine) {
							board[row][placeCol].setTileValue(board[row][col].getTileValue());
						}
						
						board[row][col].setTileValue(0);
						movement = true;
					}
				}
			}
		}
		
		return movement;
	}
	
	public static boolean mergeDown(tile[][] board) {	
		boolean movement = false;
		
		for(int col = (board[0].length - 1); col >= 0; col--) {
			for(int row = (board.length - 2); row >= 0; row--) {
				if(board[row][col].getTileValue() != 0) {
					int placeRow = row;
					boolean move = false, combine = false;
					
					for(int tempRow = col + 1; tempRow < board.length; tempRow++) {
						if(board[tempRow][col].getTileValue() == 0) {
							placeRow = tempRow;
							move = true;
						}
					}
					
					if(placeRow != 3) {
						if(board[placeRow + 1][col].getTileValue() == board[row][col].getTileValue()) {
							board[placeRow + 1][col].addTileValue(board[row][col].getTileValue());
							move = true;
							combine = true;
						}
					}
									
					if(move) {
						if(!combine) {
							board[placeRow][col].setTileValue(board[row][col].getTileValue());
						}
						
						board[row][col].setTileValue(0);
						movement = true;
					}
				}
			}
		}
		
		return movement;
	}
	
	public static boolean mergeRight(tile[][] board) {
		boolean movement = false;
		
		for(int row = (board.length - 1); row >= 0; row--) {
			for(int col = (board[0].length - 2); col >= 0; col--) {
				if(board[row][col].getTileValue() != 0) {
					int placeCol = col;
					boolean move = false, combine = false;
					
					for(int tempCol = col + 1; tempCol < board[0].length; tempCol++) {
						if(board[row][tempCol].getTileValue() == 0) {
							placeCol = tempCol;
							move = true;
						}
					}
					
					if(placeCol != 3) {
						if(board[row][placeCol + 1].getTileValue() == board[row][col].getTileValue()) {
							board[row][placeCol + 1].addTileValue(board[row][col].getTileValue());
							move = true;
							combine = true;
						}
					}
										
					if(move) {
						if(!combine) {
							board[row][placeCol].setTileValue(board[row][col].getTileValue());
						}
						
						board[row][col].setTileValue(0);
						movement = true;
					}
				}
			}
		}	
		
		return movement;
	}
	
	public static boolean mergeUp(tile[][] board) {
		boolean movement = false;
		
		for(int col = 0; col < board[0].length; col++) {
			for(int row = 0; row < board.length; row++) {
				if(board[row][col].getTileValue() != 0) {
					int placeRow = row;
					boolean move = false, combine = false;
					
					for(int tempRow = row - 1; tempRow >= 0; tempRow--) {
						if(board[tempRow][col].getTileValue() == 0) {
							move = true;
							placeRow = tempRow;
						}
					}
					
					if(placeRow != 0) {
						if(board[placeRow - 1][col].getTileValue() == board[row][col].getTileValue()) {
							board[placeRow - 1][col].addTileValue(board[row][col].getTileValue());
							move = true;
							combine = true;
						}
					}
					
					if(move) {
						if(!combine) {
							board[placeRow][col].setTileValue(board[row][col].getTileValue());
						}
						
						board[row][col].setTileValue(0);
						movement = true;
					}					
				}
			}
		}
		
		return movement;
	}
}