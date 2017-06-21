package p20;

import java.util.ArrayList;
import java.util.List;

public class Mills extends Board<Move>{
	public int[][] mills = {{0,1,2}, {2,3,4}, {4,6,7}, {7,8,0}, {8,9,10}, {10,11,12}, {12,13,14}, 
			{14,15,8}, {16,17,18}, {18,19,20}, {20,21,22}, {22,23,16}, {1,9,17}, {19,11,3}, {21,13,5}, {23,15,7}};
	public int[][] neighbours = {{1,7}, {0,2,9}, {1,3}, {2,4,11}, {3,5}, {4,6,13}, {5,7}, {0,6,15},
			{9,15}, {1,8,10,17}, {9,11}, {3,10,12,19}, {11,13}, {5,12,14,21}, {13,15}, {7,8,14,23}, {17,23}, {9,16,18},
			{17,19}, {11,18,20}, {19,21}, {13,20,22}, {21,23}, {15,16,22}};

	@Override
	public List<Move> moves() {
		ArrayList<Move> moves = new ArrayList<>();
		if(getHistory().size() < 18){
			for(int i = 0; i < board.length; i++){
				if(board[i] == 0) moves.add(new Move(i));
			}
		}else{
			for(int i = 0; i < board.length; i++){
				if(board[i] == (isBeginnersTurn() ? 1 : -1)){
					for(int move : neighbours[i]){  //find possible neighbours
						
						for(int outer = 0; outer < mills.length; outer++){
							int sum = 0;
							for(int inner = 0; inner < mills[outer].length; inner++){
								sum += Math.abs(board[mills[outer][inner]]);
								
							}
						}
						
						
						if(board[move] == 0) moves.add(new Move(move, i)); //move to an empty neighbour field
					}
				}
			}
		}
	}
	
}
