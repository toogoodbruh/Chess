package chess;
import java.util.ArrayList;
import chess.Chess.Player;
import chess.ReturnPiece.PieceFile;

public class MoveValidator {
	private static final boolean DEBUG = false;
	private final String WHITE [] = {"WP","WR","WN","WQ", "QK", "WB"};
	private final String BLACK [] = {"BP","BR","BN","BQ", "BK", "BB"};

	public static ArrayList<ReturnPiece> processsRegularMove(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
		// Implement the logic to process a regular move
		// Update the state of the chess board based on the move
		//ArrayList<ReturnPiece> newPiecesOnBoard = new ArrayList<ReturnPiece>();
		ArrayList<ReturnPiece> newPiecesOnBoard = piecesOnBoard;
		System.out.println("processRegularMove");
		int arrListIndex = 0;
		System.out.println("destinationSquare: " + destinationSquare);
		for (int i = 0; i < piecesOnBoard.size(); i++) {
			char file = piecesOnBoard.get(i).pieceFile.toString().charAt(0);
			int rank = piecesOnBoard.get(i).pieceRank;
			String filerank = file + "" + rank;
			filerank.strip();
			destinationSquare.strip();
			System.out.println("filerank:: " + filerank + " i: " + i);
			if (destinationSquare.equals(filerank)) {
				System.out.println("filerank: " + filerank);
			}
			if (sourceSquare.equals(filerank)) {
				System.out.println("sourcefilerank: " + filerank);
				arrListIndex = i;
			}

			if (destinationSquare.equals(filerank)) {
				piece.pieceRank = rank;
				piece.pieceFile = newPiecesOnBoard.get(i).pieceFile;
				newPiecesOnBoard.remove(arrListIndex);
				System.out.println(filerank);
				break;
			} /*else if ((piece.pieceRank - rank) < 2 ) {  
    	    	rank = (int)destinationSquare.charAt(1);
    	    	piece.pieceRank = rank;
    	        //piece.pieceFile = newPiecesOnBoard.get(i).pieceFile;
    	    	for (PieceFile pFile : ReturnPiece.PieceFile.values()) {
        	        if (pFile.toString().equals(Character.toString(destinationSquare.charAt(0)))) {
        	        	System.out.println("found file in else: " + pFile);
        	        	piece.pieceFile = pFile;
        	        }
    	    	}
    	    	newPiecesOnBoard.remove(arrListIndex);
    	        System.out.println(filerank);
    	        break;
    	    }*/
		}


		//piece.pieceRank = 5;
		newPiecesOnBoard.add(piece);

		return newPiecesOnBoard;
	}
	public static ArrayList<ReturnPiece> processRegularMove1(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
		ArrayList<ReturnPiece> newPiecesOnBoard = new ArrayList<>(piecesOnBoard);
		System.out.println("processRegularMove");

		// Find the index of the piece being moved
		int pieceIndex = newPiecesOnBoard.indexOf(piece);

		if (pieceIndex != -1) {
			// Remove the piece from the old position
			newPiecesOnBoard.remove(pieceIndex);

			// Update the piece with the new position
			piece.pieceFile = ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1));
			piece.pieceRank = Integer.parseInt(destinationSquare.substring(1));

			// Add the updated piece to the new position
			//pieceIndex = newPiecesOnBoard.indexOf(piece);
			newPiecesOnBoard.add(piece);
		}


		return piecesOnBoard;
	}

	public static ArrayList<ReturnPiece> processRegularMoveSomewhatworks(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
		ArrayList<ReturnPiece> newPiecesOnBoard = new ArrayList<>(piecesOnBoard);
		System.out.println("processRegularMove");
		Player piecePlayer = (piece.pieceType == ReturnPiece.PieceType.WP) ? Player.white : Player.black;

	    // Check if it's the correct player's turn
	    if (Chess.currentPlayer != piecePlayer) {
	        return piecesOnBoard;
	    }


		// Find the index of the piece being moved
		int pieceIndex = -1;
		for (int i = 0; i < newPiecesOnBoard.size(); i++) {
			if (newPiecesOnBoard.get(i).equals(piece)) {
				pieceIndex = i;
				break;
			}
		}

		if (pieceIndex != -1) {
			// Remove the piece from the old position
			newPiecesOnBoard.remove(pieceIndex);

			// Check if there is a piece at the destination square
			int destinationIndex = -1;
			for (int i = 0; i < newPiecesOnBoard.size(); i++) {
				if (newPiecesOnBoard.get(i).pieceFile == ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1))
						&& newPiecesOnBoard.get(i).pieceRank == Integer.parseInt(destinationSquare.substring(1))) {
					destinationIndex = i;
					break;
				}
			}


			// Remove the existing piece at the destination square, if any
			/*if (destinationIndex != -1 && isSquareOccupied(destinationSquare, newPiecesOnBoard)) {
				
				newPiecesOnBoard.remove(destinationIndex);
			}*/

			// Update the piece with the new position
			piece.pieceFile = ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1));
			piece.pieceRank = Integer.parseInt(destinationSquare.substring(1));

			// Add the updated piece to the new position
			newPiecesOnBoard.add(piece);
		}
		

		return newPiecesOnBoard;
	}
	
	public static ArrayList<ReturnPiece> processRegularMove(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
		ArrayList<ReturnPiece> newPiecesOnBoard = new ArrayList<>(piecesOnBoard);

		// Find the index of the piece being moved
		int pieceIndex = -1;
		for (int i = 0; i < newPiecesOnBoard.size(); i++) {
			if (newPiecesOnBoard.get(i).equals(piece)) {
				pieceIndex = i;
				break;
			}
		}

		if (pieceIndex != -1) {
			// Remove the piece from the old position
			newPiecesOnBoard.remove(pieceIndex);

			// Check if there is a piece at the destination square
			int destinationIndex = -1;
			for (int i = 0; i < newPiecesOnBoard.size(); i++) {
				if (newPiecesOnBoard.get(i).pieceFile == ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1))
						&& newPiecesOnBoard.get(i).pieceRank == Integer.parseInt(destinationSquare.substring(1))) {
					destinationIndex = i;
					break;
				}
			}
			if (isSquareOccupied(destinationSquare, newPiecesOnBoard) == true) {
				newPiecesOnBoard.remove(destinationIndex);
				//if (piece.pieceType == ReturnPiece.PieceType.WP)  piece.pieceType = ReturnPiece.PieceType.BP; 
				//if (piece.pieceType == ReturnPiece.PieceType.BP)  piece.pieceType = ReturnPiece.PieceType.WP; 
				System.out.println("new? pieceType: " + piece.pieceType );
			}
			// Remove the existing piece at the destination square, if any
			// if (destinationIndex != -1 && isSquareOccupied(destinationSquare, newPiecesOnBoard)) {
			//     newPiecesOnBoard.remove(destinationIndex);
			// }

			// Update the piece with the new position
			piece.pieceFile = ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1));
			piece.pieceRank = Integer.parseInt(destinationSquare.substring(1));
			System.out.println("pieceType: " + piece.pieceType);

			// Add the updated piece to the new position
			newPiecesOnBoard.add(piece);
		}
		if (DEBUG) {
			for (ReturnPiece p: newPiecesOnBoard) {
				if (p.pieceType == ReturnPiece.PieceType.BP || p.pieceType == ReturnPiece.PieceType.WP) {
					System.out.println(p);
				}
			}
			System.out.println("size: " + newPiecesOnBoard.size());
		}

		return newPiecesOnBoard;
	}



	public static boolean checkPawnMove(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
	    /*System.out.println("Piece: " + piece);
	    System.out.println("Current Player: " + Chess.currentPlayer);
	    System.out.println("Source Square: " + sourceSquare);
	    System.out.println("Destination Square: " + destinationSquare);
		*/

	    if (piece.pieceType != ReturnPiece.PieceType.WP && piece.pieceType != ReturnPiece.PieceType.BP) {
	        // Not a pawn
	        return false;
	    }

	    Player currentPlayer = (piece.pieceType == ReturnPiece.PieceType.WP) ? Player.white : Player.black;

	    // Check if it's the correct player's turn
	    if (currentPlayer != Chess.currentPlayer) {
	        System.out.println("Mismatch player and piece");
	        return false;
	    }

	    int sourceFile = sourceSquare.charAt(0) - 'a';
	    int sourceRank = Character.getNumericValue(sourceSquare.charAt(1));

	    int destFile = destinationSquare.charAt(0) - 'a';
	    int destRank = Character.getNumericValue(destinationSquare.charAt(1));

	    // Check if the pawn is moving forward
	    int direction = (piece.pieceType == ReturnPiece.PieceType.WP) ? 1 : -1;

	    System.out.println("Direction: " + direction);
	 // Check if it's a standard forward move
	    if (destRank - sourceRank == direction && sourceFile == destFile) {
	        if (!isSquareOccupied(destinationSquare, piecesOnBoard)) {
	            return true;
	        } else {
	            System.out.println("Square occupied");
	        }
	    } else if (sourceFile == destFile && !isSquareOccupied(destinationSquare, piecesOnBoard) && isFirstMove(piece)) {
	        // Check if it's a double move on the first piece move
	        int doubleMoveRank = sourceRank + 2 * direction;
	        String doubleMoveSquare = sourceSquare.substring(0, 1) + doubleMoveRank;
	        // System.out.println("Double Move Square: " + doubleMoveSquare);
	        if (destinationSquare.equals(doubleMoveSquare)) {
	            return true;
	        }
	    } else {
	        // Check if it's a capture
	        if (Math.abs(destFile - sourceFile) == 1 && isSquareOccupied(destinationSquare, piecesOnBoard)) {
	            return true;
	        } else {
	            System.out.println("Invalid capture");
	        }
	    }


	    /*
	     * // Check if it's a double move on the first piece move
if (sourceFile == destFile && !isSquareOccupied(destinationSquare, piecesOnBoard) && isFirstMove(piece)) {
    int doubleMoveRank = sourceRank + direction; // Adjust the calculation
    String doubleMoveSquare = sourceSquare.substring(0, 1) + doubleMoveRank;
    System.out.println("Double Move Square: " + doubleMoveSquare);
    if (destinationSquare.equals(doubleMoveSquare)) {
        return true;
    }
}

	     */
	    // Check if it's a double move on the first piece move
	    /*if (sourceFile == destFile && !isSquareOccupied(destinationSquare, piecesOnBoard) && isFirstMove(piece) && Chess.currentPlayer == Chess.Player.white) {
	    	//if (Chess.currentPlayer == Chess.Player.white) {
	    		int doubleMoveRank = sourceRank + 2 * direction;
	    		String doubleMoveSquare = sourceSquare.substring(0, 1) + doubleMoveRank;
	    		//System.out.println("Double Move Square: " + doubleMoveSquare);
	    		if (destinationSquare.equals(doubleMoveSquare)) {
	    			return true;
	    		}
	    	//} 
	    		/*else {
	    			int doubleMoveRank = sourceRank + (2 * direction); // Adjust the calculation
	    			String doubleMoveSquare = sourceSquare.substring(0, 1) + doubleMoveRank;
	    			System.out.println("sourceSquare[0]" + sourceSquare.substring(0,1) + "doubleMoveRank: " + doubleMoveRank);
	    			//System.out.println("Double Move Square: " + doubleMoveSquare);
	    			if (destinationSquare.equals(doubleMoveSquare)) {
	    				return true;
	    			}
	    	}*/
	   /* } 
	    else if (sourceFile == destFile && !isSquareOccupied(destinationSquare, piecesOnBoard) && isFirstMove(piece) && Chess.currentPlayer == Chess.Player.black) {
	    	int doubleMoveRank = sourceRank + (2 * direction); // Adjust the calculation
			String doubleMoveSquare = sourceSquare.substring(0, 1) + doubleMoveRank;
			System.out.println("sourceSquare[0]" + sourceSquare.substring(0,1) + "doubleMoveRank: " + doubleMoveRank);
			//System.out.println("Double Move Square: " + doubleMoveSquare);
			if (destinationSquare.equals(doubleMoveSquare)) {
				return true;
			}
	    } else {
	    
	        if (destRank - sourceRank != direction) {
	            // Pawn must move forward by one square
	            System.out.println("Invalid rank difference");
	            return false;
	        } else {
	            // Check if it's a standard forward move
	            if (sourceFile == destFile) {
	                if (!isSquareOccupied(destinationSquare, piecesOnBoard)) {
	                    return true;
	                } else {
	                    System.out.println("Square occupied");
	                }
	            } else {
	                // Check if it's a capture
	                if (Math.abs(destFile - sourceFile) == 1 && isSquareOccupied(destinationSquare, piecesOnBoard)) {
	                    return true;
	                } else {
	                    System.out.println("Invalid capture");
	                }
	            }
	        }
	    }*/

	    // TODO: Add more conditions for special pawn moves like en passant and promotion
	    return false;
	}


	private static boolean isFirstMove(ReturnPiece piece) {
		// Check if it's the pawn's first move
		return (piece.pieceType == ReturnPiece.PieceType.WP && piece.pieceRank == 2)
				|| (piece.pieceType == ReturnPiece.PieceType.BP && piece.pieceRank == 7);
	}

	private static boolean isSquareOccupied(String square, ArrayList<ReturnPiece> piecesOnBoard) {
		for (ReturnPiece piece : piecesOnBoard) {
			if (piece.pieceFile.toString().equals(square.substring(0, 1))
					&& piece.pieceRank == Character.getNumericValue(square.charAt(1))) {
				return true;
			}
		}
		return false;
	}



	public static void processPawnPromotion(String sourceSquare, String destinationSquare, String promotionPiece, ArrayList<ReturnPiece> piecesOnBoard) {
		// Implement the logic to process a pawn promotion
		// Update the state of the chess board based on the promotion
	}

	public static void processDrawOffer(String sourceSquare, String destinationSquare, ReturnPlay result) {
		// Implement the logic to handle a draw offer
		// Update the result message if necessary
	}

	public static void processResignation(Player currentPlayer, ReturnPlay result) {
		// Implement the logic to handle a resignation
		// Update the result message based on the current player
	}
}

