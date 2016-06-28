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
			{'r', 'k', 'b', 'q', 'a', 'b', 'k', 'r'},
			{'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
			{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' ', 'b', 'b', ' ', ' '},
			{'P', 'P', 'P', 'b', 'b', 'b', 'P', 'P'},
			{'R', 'K', 'B', 'Q', 'A', 'B', 'K', 'R'}
	};
	static Character chessBoardClone[][] = chessBoard.clone();
	
	private static void printChessBoard(){
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				System.out.print(chessBoard[i][j]+" ");
			}
			System.out.println();
		}
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
	
	private static int kingPositionC = 60, kingPositionL = 4;

	/**
	 * x1, y1, x2, y2, capturedPiece
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
		printChessBoard();
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
						list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r-1) 
						+ String.valueOf(c+j) + String.valueOf(oldPiece));
					}

					//switch it back to be able to calculate more moves
					chessBoard[r][c] = chessBoard[r-1][c+j];
					chessBoard[r-1][c+j] = oldPiece;
				}
			} catch(Exception e){ }
		}

		//when we are moving straight ahead
		boolean failure = false;
		for(int j=-1; j>=-2 && !failure; j--){
			try{
				if(chessBoard[r+j][c] == ' '){

					//suppose we make the switch
					oldPiece = chessBoard[r+j][c];
					chessBoard[r+j][c] = chessBoard[r][c];
					chessBoard[r][c] = ' ';

					//if valid move record it
					if(kingSafe()){
						//valid move
						list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+j) 
						+ String.valueOf(c) + String.valueOf(oldPiece));
					}

					//switch it back to be able to calculate more moves
					chessBoard[r][c] = chessBoard[r+j][c];
					chessBoard[r+j][c] = oldPiece;
				}
				else{
					failure = true;
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
		
		return true;
	} 

	public static void main(String[] args) throws Exception {
		//		UserInterface ui = new UserInterface();
		//		
		//		JFrame f = new JFrame("Chess");
		//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		f.add(ui);
		//		f.setSize(500, 500);
		//		f.setVisible(true);

//		printChessBoard();
		String moves = possibleMoves();
		System.out.println("Total possible moves: " + moves.length()/5 + " : " + moves);
	}
}
