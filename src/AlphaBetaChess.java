import javax.swing.JFrame;

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
			{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
			{'R', 'K', 'B', 'Q', 'A', 'B', 'K', 'R'}
	};
	private static int kingPositionC = 60, kingPositionL = 4;
	
	/**
	 * x1, y1, x2, y2, capturedPiece
	 * @return
	 */
	public static String possibleMoves(){
		StringBuilder list = new StringBuilder();
		
		for(int i = 0; i < 64; i++){
			switch(chessBoard[i/8][i%8]){
			case 'P' : list.append(possibleP(i));
				break;
			case 'K' : list.append(possibleK(i));
				break;
			case 'R' : list.append(possibleR(i));
				break;
			case 'B' : list.append(possibleB(i));
				break;
			case 'A' : list.append(possibleA(i));
				break;
			case 'Q' : list.append(possibleQ(i));
				break;
			default : continue;
			}
		}
		return list.toString();
	}
	
	private static String possibleB(int i) {
		StringBuilder list = new StringBuilder();
		
		return list.toString();
	}

	private static String possibleQ(int i) {
		StringBuilder list = new StringBuilder();
		
		return list.toString();
	}

	private static String possibleA(int i) {
		StringBuilder list = new StringBuilder();
		char oldPiece;
		int r = i/8, c = i%8;
		for (int j = 0; j < 9; j++){
			if (j != 4){
				
				//for cases where king is on border of the board
				if (((r-1) + (j/3) < 0) || ((c-1) + (j%3) < 0) || ((r-1) + (j/3) > 7) || ((c-1) + (j%3) > 7))
					continue;
				if (!Character.isUpperCase(chessBoard[(r-1) + (j/3)][(c-1) + (j%3)])){
					oldPiece = chessBoard[(r-1) + (j/3)][(c-1) + (j%3)];
					chessBoard[(r-1) + (j/3)][(c-1) + (j%3)] = chessBoard[r][c];
					chessBoard[r][c] = ' ';
					kingPositionC = 8*((r-1) + (j/3)) + ((c-1) + (j%3));
					if(kingSafe()){
						//valid move
						list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(((r-1) + (j/3))) 
						+ String.valueOf(((c-1) + (j%3))) + String.valueOf(oldPiece));
					}
					chessBoard[r][c] = chessBoard[(r-1) + (j/3)][(c-1) + (j%3)];
					chessBoard[(r-1) + (j/3)][(c-1) + (j%3)] = oldPiece;
					kingPositionC = 8*r + c;
				}
			}
		}
		//TODO add castling logic
		return list.toString();
	}

	private static String possibleR(int i) {
		StringBuilder list = new StringBuilder();
		
		return list.toString();
	}

	private static String possibleK(int i) {
		StringBuilder list = new StringBuilder();
		
		return list.toString();
	}

	private static String possibleP(int i) {
		StringBuilder list = new StringBuilder();
		
		return list.toString();
	}

	private static boolean kingSafe(){
		return true;
	} 
	
	public static void main(String[] args) {
//		UserInterface ui = new UserInterface();
//		
//		JFrame f = new JFrame("Chess");
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.add(ui);
//		f.setSize(500, 500);
//		f.setVisible(true);
		
		System.out.println(possibleMoves());
	}
}
