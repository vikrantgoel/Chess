import java.util.Scanner;

public class Rating {
	protected static int rating(int moveListLength, int depth){
		// for testing
		if (AlphaBetaChess.testing){
			System.out.print("What is the score: ");
			Scanner sc = new Scanner(System.in);
			return sc.nextInt();
		}
		int counter = 0;
		counter += rateAttack();
		
		int material = rateMaterial();
		counter += material;
		counter += rateMoveability(moveListLength, depth, material);
		counter += ratePositional();

		AlphaBetaChess.flipBoard();

		counter -= rateAttack();
		
		material = rateMaterial();
		counter -= material;
		counter -= rateMoveability(moveListLength, depth, material);
		counter -= ratePositional(); 

		AlphaBetaChess.flipBoard();

		return -(counter + depth*50);
	}
	protected static int rateAttack(){
		int counter = 0;
		int temp = AlphaBetaChess.kingPositionC;
		for (int i = 0; i < 64; i++){
			switch(AlphaBetaChess.chessBoard[i/8][i%8]){
			case 'P': { AlphaBetaChess.kingPositionC=i; if(!AlphaBetaChess.kingSafe()) counter -= 64; }
			break;
			case 'Q': { AlphaBetaChess.kingPositionC=i; if(!AlphaBetaChess.kingSafe()) counter -= 900; }
			break;
			case 'R': { AlphaBetaChess.kingPositionC=i; if(!AlphaBetaChess.kingSafe()) counter -= 500; }
			break;
			case 'B': { AlphaBetaChess.kingPositionC=i; if(!AlphaBetaChess.kingSafe()) counter -= 300; } 
			break;
			case 'K': { AlphaBetaChess.kingPositionC=i; if(!AlphaBetaChess.kingSafe()) counter -= 300; }
			break;
			}
		}
		AlphaBetaChess.kingPositionC = temp;
		if (!AlphaBetaChess.kingSafe())
			counter -= 200;
		return counter/2;
	}
	protected static int rateMaterial(){
		int counter = 0, bishopCounter = 0;
		for (int i = 0; i < 64; i++){
			switch(AlphaBetaChess.chessBoard[i/8][i%8]){
			case 'P': counter += 100;
			break;
			case 'Q': counter += 900;
			break;
			case 'R': counter += 500;
			break;
			case 'B': bishopCounter++; 
			break;
			case 'K': counter += 300;
			break;
			}
		}
		if(bishopCounter >1){
			counter += 300*bishopCounter;
		}
		else if(bishopCounter == 1){
			counter += 250;
		}
		return counter;
	}
	protected static int rateMoveability(int listLength, int depth, int material){
		int counter = 0;
		counter += listLength; // 5 points per move
		if (listLength == 0){
			// checkmate or stalemate
			if(!AlphaBetaChess.kingSafe()){
				// checkmate
				counter -= 200000*depth; 
			} else{
				//stalemate
				counter -= 150000*depth;
			}
		}
		return 0;
	}
	protected static int ratePositional(){
		return 0;
	}

}
