/**
 * Contributers: Akshaj Kammari, Gabe Nydick
 * CS213 Project 1
 * 2/26/2024
 * https://www.cs.rutgers.edu/courses/213/classes/spring_2024_venugopal/chess/chess.html
 */
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
	private static final boolean DEBUG = true;
	static ArrayList<ReturnPiece> piecesOnBoard = new ArrayList<ReturnPiece>();
	static int wKingFlag = 0, bKingFlag = 0;
	static boolean drawFlag = false;

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
		String checkStringForValidMove;
		checkStringForValidMove = (moveParts.length >= 2) ?  moveParts[0] + " " + moveParts[1] : moveParts[0]; //does not include possible draw or promotion piece
		if (MoveValidator.findPieceAtSquare(checkStringForValidMove.substring(0,2), piecesOnBoard, ReturnPiece.PieceType.WP, ReturnPiece.PieceType.BP, ReturnPiece.PieceType.WK,ReturnPiece.PieceType.BK,
				ReturnPiece.PieceType.WQ, ReturnPiece.PieceType.BQ, ReturnPiece.PieceType.WR, ReturnPiece.PieceType.BR, ReturnPiece.PieceType.WN, ReturnPiece.PieceType.BN,
				ReturnPiece.PieceType.WB,ReturnPiece.PieceType.WB) == null) {
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			System.out.println("current: " + currentPlayer);
			currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
			System.out.println("new: " + currentPlayer);
			return result;
		}
		if (isValidMoveFormat(checkStringForValidMove) == true) {
			// Regular move or pawn promotion
			if (moveParts.length > 1) {
				String sourceSquare = moveParts[0].strip();
				String destinationSquare = moveParts[1].strip();
				String originalSourceSquare = sourceSquare;
				String originalDestinationSquare = destinationSquare;
				// Check for pawn promotion
			    if (moveParts.length == 3) {
			        String promotionPiece = moveParts[2];
			        if (isValidSquare(sourceSquare) && isValidSquare(destinationSquare)) {
			            for (ReturnPiece piece : piecesOnBoard) {
			                if (piece.pieceFile.toString().equals(sourceSquare.substring(0, 1))
			                        && piece.pieceRank == Character.getNumericValue(sourceSquare.charAt(1))
			                        && piece.pieceType == ReturnPiece.PieceType.WP) {
			                    if (MoveValidator.checkPawnMove(sourceSquare, destinationSquare, piece, piecesOnBoard)
			                            && MoveValidator.checkPawnPromotion(sourceSquare, destinationSquare, promotionPiece, piece, piecesOnBoard)) {
			                        piecesOnBoard = MoveValidator.processPawnPromotion(sourceSquare, destinationSquare, promotionPiece, piece, piecesOnBoard);
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
			                }
			            }
			        } else {
			            result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			            return result;
			        }
			    }
				/*if (!isValidSquare(sourceSquare) || !isValidSquare(destinationSquare)) {
					result.message = ReturnPlay.Message.ILLEGAL_MOVE;
					return result;
				} else {*/
					//piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(1), piecesOnBoard);
					for (int i = 0; i < piecesOnBoard.size(); i++) {
						char file = piecesOnBoard.get(i).pieceFile.toString().charAt(0);
						int rank = piecesOnBoard.get(i).pieceRank;
						String filerank = file + "" + rank;
						//System.out.println("before string length check");
						if (moveParts.length == 3) {
							if (moveParts[2].equalsIgnoreCase("draw?")) {
								if (DEBUG) System.out.println("draw found");
								drawFlag = true;
								String sendString = originalSourceSquare + " " + originalDestinationSquare;
								result = Chess.play(sendString);
								result.message = ReturnPlay.Message.DRAW;
								
								return result;
							} /*else {
								if (moveParts.length == 3) {
									String promotionPiece = moveParts[2];
									if (sourceSquare.equals(filerank) && sourceSquare.charAt(1) == filerank.charAt(1)) {
										if (MoveValidator.checkPawnMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard) == true 
												&& MoveValidator.checkPawnPromotion(sourceSquare, destinationSquare, promotionPiece, piecesOnBoard.get(i), piecesOnBoard) == true) {
											if (DEBUG) System.out.println("pawn promotion true in play() specified piece: " + promotionPiece);
											//piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard);
											piecesOnBoard = MoveValidator.processPawnPromotion(sourceSquare, destinationSquare, promotionPiece, piecesOnBoard.get(i), piecesOnBoard);
											System.out.println("current: " + currentPlayer);
											currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
											System.out.println("new: " + currentPlayer);
											result.piecesOnBoard = piecesOnBoard;
											return result;
										}
										else {
											result.message = ReturnPlay.Message.ILLEGAL_MOVE;
											return result;
										}
									}
								}
							}*/
						} else {
							if (sourceSquare.equals(filerank) && sourceSquare.charAt(1) == filerank.charAt(1) && moveParts.length == 2) {
								if (MoveValidator.checkPawnMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard) == true) {
									if (MoveValidator.checkPawnPromotion(sourceSquare, destinationSquare, "Q", piecesOnBoard.get(i), piecesOnBoard) == true) {
										if (DEBUG) System.out.println("pawn promotion move true in play(), default promotion");
										piecesOnBoard = MoveValidator.processPawnPromotion(sourceSquare, destinationSquare, "Q", piecesOnBoard.get(i), piecesOnBoard);
										result.piecesOnBoard = piecesOnBoard;
										System.out.println("current: " + currentPlayer);
										currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
										System.out.println("new: " + currentPlayer);
										return result;
									} else {
										if (DEBUG) System.out.println("pawn move true in play()");
										piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard);
										System.out.println("current: " + currentPlayer);
										currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
										System.out.println("new: " + currentPlayer);
										result.piecesOnBoard = piecesOnBoard;
										if (drawFlag) result.message = ReturnPlay.Message.DRAW;
										drawFlag = false;
										return result;
									}
								} else if (MoveValidator.checkQueenMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard) == true) {
									if (DEBUG) System.out.println("queen move true in play()");
									piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard);
									System.out.println("current: " + currentPlayer);
									currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
									System.out.println("new: " + currentPlayer);
									result.piecesOnBoard = piecesOnBoard;
									if (drawFlag) result.message = ReturnPlay.Message.DRAW;
									drawFlag = false;
									return result;
								} else if (MoveValidator.checkRookMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard) == true) { 
									if (DEBUG) System.out.println("rook move true in play()");
									piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard);
									System.out.println("current: " + currentPlayer);
									currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
									System.out.println("new: " + currentPlayer);
									result.piecesOnBoard = piecesOnBoard;
									if (drawFlag) result.message = ReturnPlay.Message.DRAW;
									drawFlag = false;
									return result;
								} else if (MoveValidator.checkBishopMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard) == true) { 
									if (DEBUG) System.out.println("bishop move true in play()");
									piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard);
									System.out.println("current: " + currentPlayer);
									currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
									System.out.println("new: " + currentPlayer);
									result.piecesOnBoard = piecesOnBoard;
									if (drawFlag) result.message = ReturnPlay.Message.DRAW;
									drawFlag = false;
									return result;
								} else if (MoveValidator.checkKnightMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard) == true) { 
									if (DEBUG) System.out.println("rook move true in play()");
									piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard);
									System.out.println("current: " + currentPlayer);
									currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
									System.out.println("new: " + currentPlayer);
									result.piecesOnBoard = piecesOnBoard;
									if (drawFlag) result.message = ReturnPlay.Message.DRAW;
									drawFlag = false;
									return result;
								} else if (MoveValidator.checkKingMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard) == true) { 
									if (MoveValidator.isCastlingMove(sourceSquare, destinationSquare,
											piecesOnBoard.get(i), piecesOnBoard) == true) {
										if (DEBUG) System.out.println("king castling move true in play()");
										// If king move is valid, process the move
										piecesOnBoard = MoveValidator.handleCastling(sourceSquare, destinationSquare,
												piecesOnBoard.get(i), piecesOnBoard);
										currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
										result.piecesOnBoard = piecesOnBoard;
										if (drawFlag) result.message = ReturnPlay.Message.DRAW;
										drawFlag = false;
										return result;
									} else {
										if (DEBUG) System.out.println("king move true in play()");
										piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard);
										System.out.println("current: " + currentPlayer);
										currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
										System.out.println("new: " + currentPlayer);
										result.piecesOnBoard = piecesOnBoard;
										if (drawFlag) result.message = ReturnPlay.Message.DRAW;
										drawFlag = false;
										return result;
									}
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
						//}
					}





					// Additional handling for pawn promotion
					/*if (moveParts.length == 3) {
						String promotionPiece = moveParts[2];
						// Handle promotionPiece (e.g., "N" for knight, "Q" for queen, etc.)
						for (int i = 0; i < piecesOnBoard.size(); i++) {
							char file = piecesOnBoard.get(i).pieceFile.toString().charAt(0);
							int rank = piecesOnBoard.get(i).pieceRank;
							String filerank = file + "" + rank;
							if (sourceSquare.equals(filerank) && sourceSquare.charAt(1) == filerank.charAt(1)) {
								if (MoveValidator.checkPawnMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard) == true 
										&& MoveValidator.processPawnPromotion(sourceSquare, destinationSquare, promotionPiece, piecesOnBoard.get(i), piecesOnBoard)) {
									if (DEBUG) System.out.println("pawn promotion true in play() specified piece: " + promotionPiece);
									piecesOnBoard = MoveValidator.processRegularMove(sourceSquare, destinationSquare, piecesOnBoard.get(i), piecesOnBoard);
									System.out.println("current: " + currentPlayer);
									currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
									System.out.println("new: " + currentPlayer);
									result.piecesOnBoard = piecesOnBoard;
									return result;
								}
							}
						}

					}*/

					// Now you have sourceSquare and destinationSquare, you can process the move
					// Example: result.piecesOnBoard = processRegularMove(sourceSquare,
					// destinationSquare);
					/*else if (moveParts.length == 3 && moveParts[2].equals("draw?")) {
						// Draw offer
						sourceSquare = moveParts[0];
						destinationSquare = moveParts[1];
						// Example: result.piecesOnBoard = processDrawOffer(sourceSquare,
						// destinationSquare);
						//result.message = ReturnPlay.Message.DRAW;
						drawFlag = true;
						Chess.play(sourceSquare + " " + destinationSquare);
					}*/ 
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
		wKingFlag = 0;
		bKingFlag = 0;
		drawFlag = false;
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
