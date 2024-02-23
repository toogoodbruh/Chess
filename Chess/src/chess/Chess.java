//Contributers: Akshaj Kammari, Gabe Nydick
package chess;

import java.util.ArrayList;

class ReturnPiece {
	static enum PieceType {
		WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BK, BQ
	};

	static enum PieceFile {
		a, b, c, d, e, f, g, h
	};

	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank; // 1..8

	public String toString() {
		return "" + pieceFile + pieceRank + ":" + pieceType;
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece) other;
		return pieceType == otherPiece.pieceType && pieceFile == otherPiece.pieceFile
				&& pieceRank == otherPiece.pieceRank;
	}
}

class ReturnPlay {
	enum Message {
		ILLEGAL_MOVE, DRAW, RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, CHECK, CHECKMATE_BLACK_WINS, CHECKMATE_WHITE_WINS,
		STALEMATE
	};

	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

public class Chess {
	static ArrayList<ReturnPiece> piecesOnBoard = new ArrayList<ReturnPiece>();

	enum Player {
		white, black
	}

	public static Player currentPlayer = Player.white;

	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move. See the
	 *         section "The Chess class" in the assignment description for details
	 *         of the contents of the returned ReturnPlay instance.
	 */


	public static ReturnPlay play(String move) {

		/* FILL IN THIS METHOD */

		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		ReturnPlay result = new ReturnPlay();
		result.piecesOnBoard = new ArrayList<>(piecesOnBoard);

		// Split the move into individual parts
		String[] moveParts = move.split("\\s+");

		if (isValidMoveFormat(move) == true) {
			// Regular move or pawn promotion
			if (moveParts.length > 1) {
				String sourceSquare = moveParts[0];
				String destinationSquare = moveParts[1];
				if (!isValidSquare(sourceSquare) || !isValidSquare(destinationSquare)) {
					result.message = ReturnPlay.Message.ILLEGAL_MOVE;
					return result;
				} else {
					//piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(1), piecesOnBoard);
					for (int i = 0; i < piecesOnBoard.size(); i++) {
						char file = piecesOnBoard.get(i).pieceFile.toString().charAt(0);
						int rank = piecesOnBoard.get(i).pieceRank;
						String filerank = file + "" + rank;
						//System.out.println(file + "" + rank);
						//if (moveParts[0].charAt(0) == file && (moveParts[0].charAt(1) == rank)) {
						//if (sourceSquare.charAt(0) == filerank.charAt(0) && sourceSquare.charAt(1) == filerank.charAt(1)) {
						if (sourceSquare.equals(filerank) && sourceSquare.charAt(1) == filerank.charAt(1)) {
							if (MoveValidator.checkPawnMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard) == true) {
								piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard);
								System.out.println("current: " + currentPlayer);
								currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
								System.out.println("new: " + currentPlayer);
								result.piecesOnBoard = piecesOnBoard;
								return result;
							} else if (MoveValidator.checkQueenMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard) == true) {
								piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard);
								System.out.println("current: " + currentPlayer);
								currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
								System.out.println("new: " + currentPlayer);
								result.piecesOnBoard = piecesOnBoard;
								return result;
							} else {
								result.message = ReturnPlay.Message.ILLEGAL_MOVE;
								System.out.println("current: " + currentPlayer);
								currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
								System.out.println("new: " + currentPlayer);
								return result;
							}
							//piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard);
							//break;
							//return result;
						}
							 /*else {
									result.message = ReturnPlay.Message.ILLEGAL_MOVE;
									System.out.println("current: " + currentPlayer);
									currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
									System.out.println("new: " + currentPlayer);
									return result;
								}*/
						} /*else {
							result.message = ReturnPlay.Message.ILLEGAL_MOVE;
							System.out.println("current: " + currentPlayer);
							currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
							System.out.println("new: " + currentPlayer);
							return result;
						}*/


					

					// Additional handling for pawn promotion
					if (moveParts.length == 3) {
						String promotionPiece = moveParts[2];
						// Handle promotionPiece (e.g., "N" for knight, "Q" for queen, etc.)
					}

					// Now you have sourceSquare and destinationSquare, you can process the move
					// Example: result.piecesOnBoard = processRegularMove(sourceSquare,
					// destinationSquare);
					else if (moveParts.length == 3 && moveParts[2].equals("draw?")) {
						// Draw offer
						sourceSquare = moveParts[0];
						destinationSquare = moveParts[1];
						// Example: result.piecesOnBoard = processDrawOffer(sourceSquare,
						// destinationSquare);
						result.message = ReturnPlay.Message.DRAW;
					} 
				}
			} else if (moveParts.length == 1 && moveParts[0].equals("resign")) {
				// Resignation
				result.message = (currentPlayer == Player.white) ? ReturnPlay.Message.RESIGN_BLACK_WINS : ReturnPlay.Message.RESIGN_WHITE_WINS;
			}
		} else {
			// Illegal move
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
		}
		System.out.println("current: " + currentPlayer);
		currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
		System.out.println("new: " + currentPlayer);

		return result;
	}

	private static boolean isValidMoveFormat(String move) {
		// Check if the move has a valid format
		return move.matches("[a-h][1-8] [a-h][1-8]( [NBRQ])?|(resign)|(draw\\?)");
	}

	private static boolean isValidSquare(String square) {
		// Check if the square is valid
		return square.matches("[a-h][1-8]");
	}

	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
		piecesOnBoard = initialSetup();
	}

	private static ArrayList<ReturnPiece> initialSetup() {
		ArrayList<ReturnPiece> initialPieces = new ArrayList<>();
		currentPlayer = Player.white;

		// White pieces
		// initialPieces.add(new ReturnPiece(ReturnPiece.PieceType.WR,
		// ReturnPiece.PieceFile.a, 1));
		ReturnPiece a = new ReturnPiece();
		ReturnPiece b = new ReturnPiece();
		ReturnPiece c = new ReturnPiece();
		ReturnPiece d = new ReturnPiece();
		ReturnPiece e = new ReturnPiece();
		ReturnPiece f = new ReturnPiece();
		ReturnPiece g = new ReturnPiece();
		ReturnPiece h = new ReturnPiece();

		//WR 1
		a.pieceType = ReturnPiece.PieceType.WR;
		a.pieceFile = ReturnPiece.PieceFile.a;
		a.pieceRank = 1;
		initialPieces.add(a);
		//WR 2
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.WR;
		a.pieceFile = ReturnPiece.PieceFile.h;
		a.pieceRank = 1;
		initialPieces.add(a);
		//BR 1
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.BR;
		a.pieceFile = ReturnPiece.PieceFile.a;
		a.pieceRank = 8;
		initialPieces.add(a);
		//BR 2
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.BR;
		a.pieceFile = ReturnPiece.PieceFile.h;
		a.pieceRank = 8;
		initialPieces.add(a);
		//WN 1
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.WN;
		a.pieceFile = ReturnPiece.PieceFile.b;
		a.pieceRank = 1;
		initialPieces.add(a);
		//WN 2
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.WN;
		a.pieceFile = ReturnPiece.PieceFile.g;
		a.pieceRank = 1;
		initialPieces.add(a);
		//BN 1
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.BN;
		a.pieceFile = ReturnPiece.PieceFile.b;
		a.pieceRank = 8;
		initialPieces.add(a);
		//BN 2
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.BN;
		a.pieceFile = ReturnPiece.PieceFile.g;
		a.pieceRank = 8;
		initialPieces.add(a);
		//WB 1
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.WB;
		a.pieceFile = ReturnPiece.PieceFile.c;
		a.pieceRank = 1;
		initialPieces.add(a);
		//WB 2
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.WB;
		a.pieceFile = ReturnPiece.PieceFile.f;
		a.pieceRank = 1;
		initialPieces.add(a);
		//BB 1
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.BB;
		a.pieceFile = ReturnPiece.PieceFile.c;
		a.pieceRank = 8;
		initialPieces.add(a);
		//BB 2
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.BB;
		a.pieceFile = ReturnPiece.PieceFile.f;
		a.pieceRank = 8;
		initialPieces.add(a);
		//WQ 1
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.WQ;
		a.pieceFile = ReturnPiece.PieceFile.d;
		a.pieceRank = 1;
		initialPieces.add(a);
		//BQ 2
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.BQ;
		a.pieceFile = ReturnPiece.PieceFile.d;
		a.pieceRank = 8;
		initialPieces.add(a);
		//WK 1
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.WK;
		a.pieceFile = ReturnPiece.PieceFile.e;
		a.pieceRank = 1;
		initialPieces.add(a);
		//BK 2
		a = new ReturnPiece();
		a.pieceType = ReturnPiece.PieceType.BK;
		a.pieceFile = ReturnPiece.PieceFile.e;
		a.pieceRank = 8;
		initialPieces.add(a);


		/*
		 * setup pawns on rows 2, 7
		 */
		for (ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()) { // adds white pawns to arraylist
			a = new ReturnPiece();
			a.pieceType = ReturnPiece.PieceType.WP;
			a.pieceFile = file;
			a.pieceRank = 2;
			initialPieces.add(a);
			//System.out.println(file);
		}
		for (ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()) { // adds black pawns to arraylist
			a = new ReturnPiece();
			a.pieceType = ReturnPiece.PieceType.BP;
			a.pieceFile = file;
			a.pieceRank = 7;
			initialPieces.add(a);
			//System.out.println(file);
		}

		return initialPieces;
	}
}
