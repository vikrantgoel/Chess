import java.util.Arrays;

public class AlphaBetaChess {

	/**
	 * Capitals are white, others black
	 * P/p = pawn
	 * K/k = knight
	 * R/r = rook
	 * B/b = bishop
	 * Q/q = Queen
	 * A/a = King
	 * 
	 */
	static Character chessBoard[][] = {
			{' ', 'k', 'b', 'q', 'a', 'b', 'k', 'r'},
			{'P', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
			{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
			{'R', 'K', 'B', 'Q', 'A', 'B', 'K', 'R'}
	};
	static Character chessBoardClone[][] = chessBoard.clone();
	private static int kingPositionC = 0, kingPositionL = 0;

	private static void printChessBoard(){
		System.out.println();
		for(int i=0; i<8; i++){
			System.out.println(Arrays.toString(chessBoard[i]));
		}
		System.out.println();
	}
	private static boolean chessBoardChange(){
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(chessBoard[i][j]!=chessBoardClone[i][j])
					return true;
			}
		}
		return false;
	}

	/**
	 * x1, y1, x2, y2, capturedPiece
	 * y1, y2, capturedPiece, newPiece, P/p (for when Pawn reaches the other side of board)
	 * @return
	 * @throws Exception 
	 */
	public static String possibleMoves() throws Exception{
		StringBuilder list = new StringBuilder();
		String tempMoves = "";
		for(int i = 0; i < 64; i++){
			switch(chessBoard[i/8][i%8]){
			case 'P' :
				tempMoves = possibleP(i);
				if(tempMoves.length() > 0)
					System.out.println("P: Total moves: " + tempMoves.length()/5 + " : " + tempMoves);
				list.append(tempMoves);
				if(chessBoardChange())
					throw new Exception();
				break;
			case 'K' :
				tempMoves = possibleK(i);
				if(tempMoves.length() > 0)
					System.out.println("K: Total moves: " + tempMoves.length()/5 + " : " + tempMoves);
				list.append(tempMoves);
				if(chessBoardChange())
					throw new Exception();
				break;
			case 'R' :
				tempMoves = possibleR(i);
				if(tempMoves.length() > 0)
					System.out.println("R: Total moves: " + tempMoves.length()/5 + " : " + tempMoves);
				list.append(tempMoves);
				if(chessBoardChange())
					throw new Exception();
				break;
			case 'B' :
				tempMoves = possibleB(i);
				if(tempMoves.length() > 0)
					System.out.println("B: Total moves: " + tempMoves.length()/5 + " : " + tempMoves);
				list.append(tempMoves);
				if(chessBoardChange())
					throw new Exception();
				break;
			case 'A' :
				tempMoves = possibleA(i);
				if(tempMoves.length() > 0)
					System.out.println("A: Total moves: " + tempMoves.length()/5 + " : " + tempMoves);
				list.append(tempMoves);
				if(chessBoardChange())
					throw new Exception();
				break;
			case 'Q' :
				tempMoves = possibleQ(i);
				if(tempMoves.length() > 0)
					System.out.println("Q: Total moves: " + tempMoves.length()/5 + " : " + tempMoves);
				list.append(tempMoves);
				if(chessBoardChange())
					throw new Exception();
				break;
			default : continue;
			}
		}
		return list.toString();
	}

	private static String possibleB(int i) {

		StringBuilder list = new StringBuilder();
		char oldPiece;

		int distance = 1;
		int r = i/8, c = i%8;

		//eight combinations for eight directions our bishop can go to
		for(int j = -1; j <= 1; j++){
			for(int k = -1; k <= 1; k++){

				//so that it only moves diagonally
				if(j==0 || k==0)
					continue;

				//for cases where we are going out of the board
				try{
					distance = 1;
					while(chessBoard[r+distance*j][c+distance*k] == ' '){

						//suppose we make the switch
						oldPiece = chessBoard[r+distance*j][c+distance*k];
						chessBoard[r+distance*j][c+distance*k] = chessBoard[r][c];
						chessBoard[r][c] = ' ';

						//if valid move record it
						if(kingSafe()){
							//valid move
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+distance*j) 
							+ String.valueOf(c+distance*k) + String.valueOf(oldPiece));
						}

						//switch it back to be able to calculate more moves
						chessBoard[r][c] = chessBoard[r+distance*j][c+distance*k];
						chessBoard[r+distance*j][c+distance*k] = oldPiece;

						distance++;
					}
					if(Character.isLowerCase(chessBoard[r+distance*j][c+distance*k])){

						//suppose we make the switch
						oldPiece = chessBoard[r+distance*j][c+distance*k];
						chessBoard[r+distance*j][c+distance*k] = chessBoard[r][c];
						chessBoard[r][c] = ' ';

						//if valid move record it
						if(kingSafe()){
							//valid move
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+distance*j) 
							+ String.valueOf(c+distance*k) + String.valueOf(oldPiece));
						}

						//switch it back to be able to calculate more moves
						chessBoard[r][c] = chessBoard[r+distance*j][c+distance*k];
						chessBoard[r+distance*j][c+distance*k] = oldPiece;
					}

				}catch(Exception e){ }
			}
		}
		return list.toString();
	}

	private static String possibleQ(int i) {
		StringBuilder list = new StringBuilder();
		char oldPiece;

		int distance = 1;
		int r = i/8, c = i%8;

		//eight combinations for eight directions our queen can go to
		for(int j = -1; j <= 1; j++){
			for(int k = -1; k <= 1; k++){

				//for j=0 and k=0, we are basically not moving at all
				if(j==0 && k==0)
					continue;

				//for cases where we are going out of the board
				try{
					distance = 1;
					while(chessBoard[r+distance*j][c+distance*k] == ' '){

						//suppose we make the switch
						oldPiece = chessBoard[r+distance*j][c+distance*k];
						chessBoard[r+distance*j][c+distance*k] = chessBoard[r][c];
						chessBoard[r][c] = ' ';

						//if valid move record it
						if(kingSafe()){
							//valid move
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+distance*j) 
							+ String.valueOf(c+distance*k) + String.valueOf(oldPiece));
						}

						//switch it back to be able to calculate more moves
						chessBoard[r][c] = chessBoard[r+distance*j][c+distance*k];
						chessBoard[r+distance*j][c+distance*k] = oldPiece;

						distance++;
					}
					if(Character.isLowerCase(chessBoard[r+distance*j][c+distance*k])){

						//suppose we make the switch
						oldPiece = chessBoard[r+distance*j][c+distance*k];
						chessBoard[r+distance*j][c+distance*k] = chessBoard[r][c];
						chessBoard[r][c] = ' ';

						//if valid move record it
						if(kingSafe()){
							//valid move
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+distance*j) 
							+ String.valueOf(c+distance*k) + String.valueOf(oldPiece));
						}

						//switch it back to be able to calculate more moves
						chessBoard[r][c] = chessBoard[r+distance*j][c+distance*k];
						chessBoard[r+distance*j][c+distance*k] = oldPiece;
					}

				}catch(Exception e){ }
			}
		}
		return list.toString();
	}

	private static String possibleA(int i) {
		StringBuilder list = new StringBuilder();
		char oldPiece;
		int r = i/8, c = i%8;

		//eight combinations for eight directions our king can go to
		for(int j = -1; j <= 1; j++){
			for(int k = -1; k <= 1; k++){

				//for cases where we are going out of the board
				try{
					//we will not move on one of our own player
					if (!Character.isUpperCase(chessBoard[r+j][c+k])){
						//suppose we make the switch
						oldPiece = chessBoard[r+j][c+k];
						chessBoard[r+j][c+k] = chessBoard[r][c];
						chessBoard[r][c] = ' ';
						kingPositionC = 8*(r+j) + (c+k);

						//if valid move record it
						if(kingSafe()){
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+j) 
							+ String.valueOf(c+k) + String.valueOf(oldPiece));
						}

						//switch it back to be able to calculate more moves
						chessBoard[r][c] = chessBoard[r+j][c+k];
						chessBoard[r+j][c+k] = oldPiece;
						kingPositionC = 8*r + c;
					}
				}catch(Exception e){ }
			}
		}

		//TODO add castling logic
		return list.toString();
	}

	private static String possibleR(int i) {

		StringBuilder list = new StringBuilder();
		char oldPiece;

		int distance = 1;
		int r = i/8, c = i%8;

		//eight combinations for eight directions our rook can go to
		for(int j = -1; j <= 1; j++){
			for(int k = -1; k <= 1; k++){

				//
				if(!((j==0 && k!=0) || (j!=0 && k==0)))
					continue;

				//for cases where we are going out of the board
				try{
					distance = 1;
					while(chessBoard[r+distance*j][c+distance*k] == ' '){

						//suppose we make the switch
						oldPiece = chessBoard[r+distance*j][c+distance*k];
						chessBoard[r+distance*j][c+distance*k] = chessBoard[r][c];
						chessBoard[r][c] = ' ';

						//if valid move record it
						if(kingSafe()){
							//valid move
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+distance*j) 
							+ String.valueOf(c+distance*k) + String.valueOf(oldPiece));
						}

						//switch it back to be able to calculate more moves
						chessBoard[r][c] = chessBoard[r+distance*j][c+distance*k];
						chessBoard[r+distance*j][c+distance*k] = oldPiece;

						distance++;
					}
					if(Character.isLowerCase(chessBoard[r+distance*j][c+distance*k])){

						//suppose we make the switch
						oldPiece = chessBoard[r+distance*j][c+distance*k];
						chessBoard[r+distance*j][c+distance*k] = chessBoard[r][c];
						chessBoard[r][c] = ' ';

						//if valid move record it
						if(kingSafe()){
							//valid move
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+distance*j) 
							+ String.valueOf(c+distance*k) + String.valueOf(oldPiece));
						}

						//switch it back to be able to calculate more moves
						chessBoard[r][c] = chessBoard[r+distance*j][c+distance*k];
						chessBoard[r+distance*j][c+distance*k] = oldPiece;
					}

				}catch(Exception e){ }
			}
		}
		return list.toString();
	}

	private static String possibleK(int i) {

		StringBuilder list = new StringBuilder();
		char oldPiece;

		int r = i/8, c = i%8;

		//eight combinations for eight directions our king can go to
		for(int j = -2; j <= 2; j++){
			for(int k = -2; k <= 2; k++){

				//so that it only moves 2.5 spaces diagonally
				if(!(j!=k && j!=(-1)*k && j!=0 && k!=0))
					continue;

				//for cases where we are going out of the board
				try{
					if (!Character.isUpperCase(chessBoard[r+j][c+k])){

						//suppose we make the switch
						oldPiece = chessBoard[r+j][c+k];
						chessBoard[r+j][c+k] = chessBoard[r][c];
						chessBoard[r][c] = ' ';

						//if valid move record it
						if(kingSafe()){
							//valid move
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+j) 
							+ String.valueOf(c+k) + String.valueOf(oldPiece));
						}

						//switch it back to be able to calculate more moves
						chessBoard[r][c] = chessBoard[r+j][c+k];
						chessBoard[r+j][c+k] = oldPiece;
					}

				}catch(Exception e){ }
			}
		}
		return list.toString();

	}

	private static String possibleP(int i) {
		StringBuilder list = new StringBuilder();
		char oldPiece;

		int r = i/8, c = i%8;

		// when we reach the other side of board 
		char[] promo = {'Q', 'B', 'R', 'K'};

		//when we are eliminating an enemy
		for(int j = -1 ; j <= 1; j += 2){
			try{
				if (Character.isLowerCase(chessBoard[r-1][c+j])){

					//suppose we make the switch
					oldPiece = chessBoard[r-1][c+j];
					chessBoard[r-1][c+j] = chessBoard[r][c];
					chessBoard[r][c] = ' ';

					//if valid move record it
					if(kingSafe()){
						//valid move

						if(r>1){
							// not promo 
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r-1) 
							+ String.valueOf(c+j) + String.valueOf(oldPiece));
						}
						else{
							// promo
							for(int k = 0; k < promo.length; k++){
								list.append(String.valueOf(c) + String.valueOf(c+j) + String.valueOf(oldPiece) 
								+ String.valueOf(promo[k]) + "P");
							}
						}
					}

					//switch it back to be able to calculate more moves
					chessBoard[r][c] = chessBoard[r-1][c+j];
					chessBoard[r-1][c+j] = oldPiece;
				}
			} catch(Exception e){ }
		}

		//when we are moving straight ahead
		boolean canMoveTwoPlaces = false;
		for(int j=-1; j>=-2 && !canMoveTwoPlaces; j--){
			try{
				if(chessBoard[r+j][c] == ' '){

					//suppose we make the switch
					oldPiece = chessBoard[r+j][c];
					chessBoard[r+j][c] = chessBoard[r][c];
					chessBoard[r][c] = ' ';

					//if valid move record it
					if(kingSafe()){
						//valid move

						if(r>1){
							// not promo
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+j) 
							+ String.valueOf(c) + String.valueOf(oldPiece));
						}
						else{
							// promo
							for(int k = 0; k < promo.length; k++){
								list.append(String.valueOf(c) + String.valueOf(c) + String.valueOf(oldPiece) 
								+ String.valueOf(promo[k]) + "P");
							}
						}
					}

					//switch it back to be able to calculate more moves
					chessBoard[r][c] = chessBoard[r+j][c];
					chessBoard[r+j][c] = oldPiece;

					// we move two spaces only for row 6
					if(r!=6)
						canMoveTwoPlaces = true;
				}
				else{
					canMoveTwoPlaces = true;
				}
			} catch(Exception e){ }
		}

		return list.toString();
	}

	private static boolean kingSafe(){

		int r = kingPositionC/8, c = kingPositionC%8;
		int distance = 1;

		// bishop/queen/rook
		for(int j = -1; j <= 1; j++){
			for(int k = -1; k <= 1; k++){

				//for j=0 and k=0, we are basically not moving at all
				if(j==0 && k==0)
					continue;

				//for cases where we are going out of the board
				try{
					distance = 1;
					while(chessBoard[r+distance*j][c+distance*k] == ' ')
						distance++;

					// rook/queen
					if((j==0 && k!=0) || (j!=0 && k==0)){
						if(chessBoard[r+distance*j][c+distance*k]=='r' || chessBoard[r+distance*j][c+distance*k]=='q')
							return false;
					}

					// bishop/queen
					else if(j!=0 && k!=0){
						if(chessBoard[r+distance*j][c+distance*k]=='b' || chessBoard[r+distance*j][c+distance*k]=='q')
							return false;
					}
				}catch(Exception e){ }
			}
		}

		// knight
		for(int j = -2; j <= 2; j++){
			for(int k = -2; k <= 2; k++){

				//so that it only moves 2.5 spaces diagonally
				if(!(j!=k && j!=(-1)*k && j!=0 && k!=0))
					continue;

				//for cases where we are going out of the board
				try{
					if(chessBoard[r+j][c+k]=='k')
						return false;
				}catch(Exception e){ }
			}
		}

		// pawn
		// only for diagonal moves
		for(int j = -1 ; j <= 1; j += 2){
			try{
				if (chessBoard[r-1][c+j]=='p'){
					return false;
				}
			} catch(Exception e){ }
		}

		//king
		for(int j = -1; j <= 1; j++){
			for(int k = -1; k <= 1; k++){
				//for cases where we are going out of the board
				try{
					if (chessBoard[r+j][c+k]=='a')
						return false;
				}catch(Exception e){ }
			}
		}
		return true;
	} 

	public static void makeMoveC(String move){
		if(move.charAt(4)!='P'){
			// x1, y1, x2, y2, capturedPiece
			int x1 = Character.getNumericValue(move.charAt(0));
			int y1 = Character.getNumericValue(move.charAt(1));
			int x2 = Character.getNumericValue(move.charAt(2));
			int y2 = Character.getNumericValue(move.charAt(3));
			chessBoard[x2][y2] = chessBoard[x1][y1];
			chessBoard[x1][y1] = ' ';
		}
		else {
			// y1, y2, capturedPiece, newPiece, P (for when Pawn reaches the other side of board)
			int y1 = Character.getNumericValue(move.charAt(0));
			int y2 = Character.getNumericValue(move.charAt(1));
			char newPiece = move.charAt(3);
			chessBoard[0][y2] = newPiece;
			chessBoard[1][y1] = ' ';
		}
	}
	public static void undoMoveC(String move){
		if(move.charAt(4)!='P'){
			// x1, y1, x2, y2, capturedPiece
			int x1 = Character.getNumericValue(move.charAt(0));
			int y1 = Character.getNumericValue(move.charAt(1));
			int x2 = Character.getNumericValue(move.charAt(2));
			int y2 = Character.getNumericValue(move.charAt(3));
			char capturedPiece = move.charAt(4); 
			chessBoard[x1][y1] = chessBoard[x2][y2];
			chessBoard[x2][y2] = capturedPiece;
			
		}
		else {
			// y1, y2, capturedPiece, newPiece, P/p (for when Pawn reaches the other side of board)
			int y1 = Character.getNumericValue(move.charAt(0));
			int y2 = Character.getNumericValue(move.charAt(1));
			char capturedPiece = move.charAt(2);
			chessBoard[1][y1] = 'P';
			chessBoard[0][y2] = capturedPiece;
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		while(chessBoard[kingPositionC/8][kingPositionC%8]!='A')
			kingPositionC++;
		while(chessBoard[kingPositionL/8][kingPositionL%8]!='a')
			kingPositionL++;
		//		UserInterface ui = new UserInterface();
		//		
		//		JFrame f = new JFrame("Chess");
		//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		f.add(ui);
		//		f.setSize(500, 500);
		//		f.setVisible(true);
		String moves = "";
		
		printChessBoard();
		moves = possibleMoves();
		System.out.println("Total possible moves: " + moves.length()/5 + " : " + moves);
		
		makeMoveC("01kQP");
		
		printChessBoard();
		moves = possibleMoves();
		System.out.println("Total possible moves: " + moves.length()/5 + " : " + moves);
		
		undoMoveC("01kQP");
		
		printChessBoard();
		moves = possibleMoves();
		System.out.println("Total possible moves: " + moves.length()/5 + " : " + moves);
	}
}
