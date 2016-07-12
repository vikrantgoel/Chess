import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{
	
	static int mouseX, mouseY, newMouseX, newMouseY; 
	static int squareSize = 32;
	public void paintComponent(Graphics g){
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setBackground(Color.YELLOW);

		for (int i = 0; i < 64; i += 2){
			g.setColor(new Color(255, 200, 100));
			g.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
			g.setColor(new Color(150, 50, 30));
			g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);
		}
		Image chessPieceImage;
		if(AlphaBetaChess.humanAsWhite == 0){
			chessPieceImage = new ImageIcon("ChessPieces_inverted.png").getImage();
		}else{
			chessPieceImage = new ImageIcon("ChessPieces.png").getImage();
		}
		

		for(int i = 0; i < 64; i++){
			int j=-1, k=-1;
			switch(AlphaBetaChess.chessBoard[i/8][i%8]){
			case 'P': j=5; k=0;
			break;
			case 'p': j=5; k=1;
			break;
			case 'R': j=2; k=0;
			break;
			case 'r': j=2; k=1;
			break;
			case 'K': j=4; k=0;
			break;
			case 'k': j=4; k=1;
			break;
			case 'B': j=3; k=0;
			break;
			case 'b': j=3; k=1;
			break;
			case 'Q': j=1; k=0;
			break;
			case 'q': j=1; k=1;
			break;
			case 'A': j=0; k=0;
			break;
			case 'a': j=0; k=1;
			break;
			default:
				break;
			}
			if( j!=-1 && k!=-1){
				g.drawImage(chessPieceImage, (i%8)*squareSize, (i/8)*squareSize, (i%8+1)*squareSize, (i/8+1)*squareSize, j*64, k*64, (j+1)*64, (k+1)*64, this);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getX() < 8*squareSize && e.getY() < 8*squareSize){
			// inside the board
			mouseX = e.getX();
			mouseY = e.getY();
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getX() < 8*squareSize && e.getY() < 8*squareSize){
			// inside the board
			newMouseX = e.getX();
			newMouseY = e.getY();
			if(e.getButton() == MouseEvent.BUTTON1){
				String dragMove = "";
				if(newMouseY/squareSize == 0 && mouseY/squareSize == 1 && (AlphaBetaChess.chessBoard[mouseY/squareSize][mouseX/squareSize] == 'P')){
					// pawn promotion
					dragMove = "" + mouseX/squareSize + newMouseX/squareSize + AlphaBetaChess.chessBoard[newMouseY/squareSize][newMouseX/squareSize] + "QP";
				} else if ((AlphaBetaChess.chessBoard[mouseY/squareSize][mouseX/squareSize] == 'A') && (newMouseY/squareSize == mouseY/squareSize) && (Math.abs(newMouseX/squareSize - mouseX/squareSize) == 2)){
					// castle
					dragMove = "" + mouseY/squareSize + mouseX/squareSize + newMouseY/squareSize + newMouseX/squareSize + "A";
				}else {
					// regular move
					dragMove = "" + mouseY/squareSize + mouseX/squareSize + newMouseY/squareSize + newMouseX/squareSize + AlphaBetaChess.chessBoard[newMouseY/squareSize][newMouseX/squareSize];
				}

				try {
					String userPossibilities = AlphaBetaChess.possibleMoves(false);
					if(userPossibilities.contains(dragMove)){
						// valid move
						System.out.println("Valid move: " + dragMove);

						AlphaBetaChess.makeMove(dragMove, false);
						AlphaBetaChess.flipBoard();

						System.out.print("Computer thinking... ");
						String computerMove = AlphaBetaChess.alphaBeta(AlphaBetaChess.globalDepth, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0);
						System.out.println("Computer's move + rating: " + computerMove);
						
						// check for checkmate or stalemate of computer
						if(computerMove.isEmpty()){
							Object[] option = {"Ok"};
							if(AlphaBetaChess.kingSafe()){
								// stalemate
								JOptionPane.showOptionDialog(null, "Stalemate! Its a draw.", "STALEMATE", JOptionPane.OK_OPTION, 
										JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
							}else{
								// checkmate
								JOptionPane.showOptionDialog(null, "Checkmate! You won!.", "CHECKMATE", JOptionPane.OK_OPTION, 
										JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
							}
							System.exit(0);
						}
						
						AlphaBetaChess.makeMove(computerMove, true);
						AlphaBetaChess.flipBoard();
						repaint();

						// check for checkmate or stalemate
						if(AlphaBetaChess.possibleMoves(false).isEmpty()){
							Object[] option = {"Ok"};
							if(AlphaBetaChess.kingSafe()){
								// stalemate
								JOptionPane.showOptionDialog(null, "Stalemate! Its a draw.", "STALEMATE", JOptionPane.OK_OPTION, 
										JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
							}else{
								// checkmate
								JOptionPane.showOptionDialog(null, "Checkmate! Although you played well.", "CHECKMATE", JOptionPane.OK_OPTION, 
										JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
							}
							System.exit(0);
						}
					}else{
						System.out.println("InValid move: " + dragMove);
						System.out.println(userPossibilities);
					}
				} catch (Exception e1) { }
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
