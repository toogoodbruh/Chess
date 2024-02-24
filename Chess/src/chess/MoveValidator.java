/**
 * Contributers: Akshaj Kammari, Gabe Nydick
 * CS213 Project 1
 * 2/26/2024
 * https://www.cs.rutgers.edu/courses/213/classes/spring_2024_venugopal/chess/chess.html
 */
package chess;
import java.util.ArrayList;
import java.util.Arrays;

import chess.Chess.Player;
import chess.ReturnPiece.PieceFile;

public class MoveValidator {
	private static final boolean DEBUG = false;
	private final String WHITE [] = {"WP","WR","WN","WQ", "QK", "WB"};
	private final String BLACK [] = {"BP","BR","BN","BQ", "BK", "BB"};

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

		if (destinationIndex != -1) {
			// Destination square is occupied
			ReturnPiece occupyingPiece = newPiecesOnBoard.get(destinationIndex);

			// Check if the occupying piece is of the same color
			if (occupyingPiece.pieceType.toString().substring(0, 1).equals(piece.pieceType.toString().substring(0, 1))) {
				// Pieces of the same color, do not switch places
				newPiecesOnBoard.add(piece); // Add the piece back to its original position
			} else {
				// Update the piece with the new position
				piece.pieceFile = ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1));
				piece.pieceRank = Integer.parseInt(destinationSquare.substring(1));

				// Remove the existing piece at the destination square
				newPiecesOnBoard.remove(destinationIndex);

				// Add the updated piece to the new position
				newPiecesOnBoard.add(piece);
			}
		} else {
			// Destination square is empty, move the piece
			piece.pieceFile = ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1));
			piece.pieceRank = Integer.parseInt(destinationSquare.substring(1));

			// Add the updated piece to the new position
			newPiecesOnBoard.add(piece);
		}

		return newPiecesOnBoard;
	}
	public static boolean checkBishopMove(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
		System.out.println("Checking bishop move from " + sourceSquare + " to " + destinationSquare);
		if (piece.pieceType != ReturnPiece.PieceType.WB || piece.pieceType != ReturnPiece.PieceType.BB) {
			System.out.println("piece not bishop sent to checkBishopMove");
			return false;
		}
		// Check if the move is diagonal
		if (isDiagonalMove(sourceSquare, destinationSquare)) {
			// Check if the path is clear (no pieces in the way)
			if (!isPathOccupied(sourceSquare, destinationSquare, piecesOnBoard)) {
				// Check if the destination square is unoccupied or has an opponent's piece
				if (!isSquareOccupiedBySameColor(destinationSquare, piece.pieceType, piecesOnBoard)) {
					return true; // Valid bishop move
				}
			}
		}

		return false;
	}

	// Helper method to check if the move is diagonal
	private static boolean isDiagonalMove(String sourceSquare, String destinationSquare) {
		int sourceFile = sourceSquare.charAt(0) - 'a';
		int sourceRank = Character.getNumericValue(sourceSquare.charAt(1));

		int destFile = destinationSquare.charAt(0) - 'a';
		int destRank = Character.getNumericValue(destinationSquare.charAt(1));

		return Math.abs(destFile - sourceFile) == Math.abs(destRank - sourceRank);
	}
	public static boolean checkKnightMove(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
		System.out.println("Checking knight move from " + sourceSquare + " to " + destinationSquare);
		if (piece.pieceType != ReturnPiece.PieceType.WN || piece.pieceType != ReturnPiece.PieceType.BN) {
			System.out.println("king not sent to checkKnightMove");
			return false;
		}
		int sourceFile = sourceSquare.charAt(0) - 'a';
		int sourceRank = Character.getNumericValue(sourceSquare.charAt(1));

		int destFile = destinationSquare.charAt(0) - 'a';
		int destRank = Character.getNumericValue(destinationSquare.charAt(1));

		// Check if the move is an L-shape (knight move)
		int fileDifference = Math.abs(destFile - sourceFile);
		int rankDifference = Math.abs(destRank - sourceRank);

		return (fileDifference == 1 && rankDifference == 2) || (fileDifference == 2 && rankDifference == 1);
	}


	public static boolean checkRookMove(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
		System.out.println("Checking rook move from " + sourceSquare + " to " + destinationSquare);
		if (piece.pieceType != ReturnPiece.PieceType.WR || piece.pieceType != ReturnPiece.PieceType.BR) {
			System.out.println("rook not sent to checkRookMove");
			return false;
		}
		// Check if the move is either vertical or horizontal
		boolean isVerticalMove = sourceSquare.charAt(0) == destinationSquare.charAt(0);
		boolean isHorizontalMove = sourceSquare.charAt(1) == destinationSquare.charAt(1);

		// Check if the provided piece is indeed a rook
		if (piece.pieceType != ReturnPiece.PieceType.WR && piece.pieceType != ReturnPiece.PieceType.BR) {
			return false;
		}

		// Check if there are no pieces in the path of the rook
		if (isVerticalMove || isHorizontalMove) {
			boolean res = !isPathOccupied(sourceSquare, destinationSquare, piecesOnBoard);
			System.out.println(res);
			return res;
		}

		return false;
	}


	public static boolean checkQueenMove_Org(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
		System.out.println("Checking queen move from " + sourceSquare + " to " + destinationSquare);

		// Check if the move is either vertical, horizontal, or diagonal
		boolean isVerticalMove = sourceSquare.charAt(0) == destinationSquare.charAt(0);
		boolean isHorizontalMove = sourceSquare.charAt(1) == destinationSquare.charAt(1);
		boolean isDiagonalMove = Math.abs(sourceSquare.charAt(0) - destinationSquare.charAt(0)) ==
				Math.abs(sourceSquare.charAt(1) - destinationSquare.charAt(1));

		// Check if the provided piece is indeed a queen
		if (piece.pieceType != ReturnPiece.PieceType.WQ && piece.pieceType != ReturnPiece.PieceType.BQ) {
			return false;
		}

		// Check if there are no pieces in the path of the queen
		if (isVerticalMove || isHorizontalMove || isDiagonalMove) {
			boolean res = !isPathOccupied(sourceSquare, destinationSquare, piecesOnBoard)
					&& !isSquareOccupiedBySameColor(destinationSquare, piece.pieceType, piecesOnBoard);
			System.out.println(res);
			return res;
		}

		System.out.println("Result: " + !isPathOccupied(sourceSquare, destinationSquare, piecesOnBoard));
		return !isPathOccupied(sourceSquare, destinationSquare, piecesOnBoard);
	}
	public static boolean checkQueenMove(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
	    System.out.println("Checking queen move from " + sourceSquare + " to " + destinationSquare);
	    if (piece.pieceType != ReturnPiece.PieceType.WQ || piece.pieceType != ReturnPiece.PieceType.BQ) {
			System.out.println("queen not sent to checkQueenMove");
			return false;
		}
	    // Check if the move is either vertical, horizontal, or diagonal
	    boolean isVerticalMove = sourceSquare.charAt(0) == destinationSquare.charAt(0);
	    boolean isHorizontalMove = sourceSquare.charAt(1) == destinationSquare.charAt(1);
	    boolean isDiagonalMove = Math.abs(sourceSquare.charAt(0) - destinationSquare.charAt(0)) ==
	            Math.abs(sourceSquare.charAt(1) - destinationSquare.charAt(1));

	    // Check if the provided piece is indeed a queen
	    if (piece.pieceType != ReturnPiece.PieceType.WQ && piece.pieceType != ReturnPiece.PieceType.BQ) {
	        return false;
	    }

	    // Check if there are no pieces in the path of the queen
	    if (isVerticalMove || isHorizontalMove || isDiagonalMove) {
	        boolean res = !isPathOccupied(sourceSquare, destinationSquare, piecesOnBoard)
	                && !isSquareOccupiedBySameColor(destinationSquare, piece.pieceType, piecesOnBoard);
	        System.out.println(res);
	        return res;
	    }

	    System.out.println("Result: " + !isPathOccupied(sourceSquare, destinationSquare, piecesOnBoard));
	    return !isPathOccupied(sourceSquare, destinationSquare, piecesOnBoard);
	}

	public static boolean checkKingMove(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
		if (piece.pieceType != ReturnPiece.PieceType.WK || piece.pieceType != ReturnPiece.PieceType.BK) {
			System.out.println("king not sent to checkKingMove");
			return false;
		}
		// Check if it's a castling move
		if (isCastlingMove(sourceSquare, destinationSquare, piece, piecesOnBoard)) {
			// Implement logic for castling
			// Example: return handleCastling(sourceSquare, destinationSquare, piece, piecesOnBoard);
			return false; // Replace with actual implementation

		}
		int sourceFile = sourceSquare.charAt(0) - 'a';
		int sourceRank = Character.getNumericValue(sourceSquare.charAt(1));

		int destFile = destinationSquare.charAt(0) - 'a';
		int destRank = Character.getNumericValue(destinationSquare.charAt(1));

		// Check if it's a standard king move
		if (Math.abs(destFile - sourceFile) <= 1 && Math.abs(destRank - sourceRank) <= 1) {
			if (!isSquareOccupied(destinationSquare, piecesOnBoard)) {
				return true;
			} else {
				System.out.println("Square occupied");
			}
		} else {
			System.out.println("Invalid king move");
		}

		// TODO: Add more conditions for special king moves like castling
		return false;
	}
	// Rest of the existing code for standard king move check...
	public static boolean isCastlingMove(String sourceSquare, String destinationSquare, ReturnPiece king, ArrayList<ReturnPiece> piecesOnBoard) {
		int sourceFile = sourceSquare.charAt(0) - 'a';
		int sourceRank = Character.getNumericValue(sourceSquare.charAt(1));

		int destFile = destinationSquare.charAt(0) - 'a';
		int destRank = Character.getNumericValue(destinationSquare.charAt(1));

		// Check if it's a castling move
		if (king.pieceType == ReturnPiece.PieceType.WK && sourceFile == 4 && sourceRank == 1 && destFile == 6 && destRank == 1) {
			// White kingside castling
			return true;
		} else if (king.pieceType == ReturnPiece.PieceType.WK && sourceFile == 4 && sourceRank == 1 && destFile == 2 && destRank == 1) {
			// White queenside castling
			return true;
		} else if (king.pieceType == ReturnPiece.PieceType.BK && sourceFile == 4 && sourceRank == 8 && destFile == 6 && destRank == 8) {
			// Black kingside castling
			return true;
		} else if (king.pieceType == ReturnPiece.PieceType.BK && sourceFile == 4 && sourceRank == 8 && destFile == 2 && destRank == 8) {
			// Black queenside castling
			return true;
		}

		return false;
	}

	public static ArrayList<ReturnPiece> handleCastling(String sourceSquare, String destinationSquare, ReturnPiece king, ArrayList<ReturnPiece> piecesOnBoard) {
		int sourceFile = sourceSquare.charAt(0) - 'a';
		int sourceRank = Character.getNumericValue(sourceSquare.charAt(1));

		int destFile = destinationSquare.charAt(0) - 'a';
		int destRank = Character.getNumericValue(destinationSquare.charAt(1));

		// Check if it's a kingside castling move
		if (king.pieceType == ReturnPiece.PieceType.WK && sourceFile == 4 && sourceRank == 1 && destFile == 6 && destRank == 1) {
			// White kingside castling
			// Move the king
			piecesOnBoard = processRegularMove(sourceSquare, destinationSquare, king, piecesOnBoard);

			// Move the kingside rook
			ReturnPiece kingsideRook = findPieceAtSquare("h1", piecesOnBoard, ReturnPiece.PieceType.WR);
			piecesOnBoard = processRegularMove("h1", "f1", kingsideRook, piecesOnBoard);

			// Update flags
			Chess.wKingFlag++;

			return piecesOnBoard;
		} else if (king.pieceType == ReturnPiece.PieceType.BK && sourceFile == 4 && sourceRank == 8 && destFile == 6 && destRank == 8) {
			// Black kingside castling
			// Move the king
			piecesOnBoard = processRegularMove(sourceSquare, destinationSquare, king, piecesOnBoard);

			// Move the kingside rook
			ReturnPiece kingsideRook = findPieceAtSquare("h8", piecesOnBoard, ReturnPiece.PieceType.BR);
			processRegularMove("h8", "f8", kingsideRook, piecesOnBoard);

			// Update flags
			Chess.bKingFlag++;

			return piecesOnBoard;
		} else if (king.pieceType == ReturnPiece.PieceType.WK && sourceFile == 4 && sourceRank == 1 && destFile == 2 && destRank == 1) {
			// White queenside castling
			// Move the king
			piecesOnBoard = processRegularMove(sourceSquare, destinationSquare, king, piecesOnBoard);

			// Move the queenside rook
			ReturnPiece queensideRook = findPieceAtSquare("a1", piecesOnBoard, ReturnPiece.PieceType.WR);
			processRegularMove("a1", "d1", queensideRook, piecesOnBoard);

			// Update flags
			Chess.wKingFlag++;

			return piecesOnBoard;
		} else if (king.pieceType == ReturnPiece.PieceType.BK && sourceFile == 4 && sourceRank == 8 && destFile == 2 && destRank == 8) {
			// Black queenside castling
			// Move the king
			piecesOnBoard = processRegularMove(sourceSquare, destinationSquare, king, piecesOnBoard);

			// Move the queenside rook
			ReturnPiece queensideRook = findPieceAtSquare("a8", piecesOnBoard, ReturnPiece.PieceType.BR);
			piecesOnBoard = processRegularMove("a8", "d8", queensideRook, piecesOnBoard);

			// Update flags
			Chess.bKingFlag++;

			return piecesOnBoard;
		}

		return piecesOnBoard;
	}

	// Helper method to check if the king has already moved
	private static boolean hasKingMoved(ReturnPiece king) {
		return (king.pieceType == ReturnPiece.PieceType.WK && Chess.wKingFlag > 0) ||
				(king.pieceType == ReturnPiece.PieceType.BK && Chess.bKingFlag > 0);
	}


	// Helper method to check if the square is occupied by a piece of the same color
	private static boolean isSquareOccupiedBySameColor(String square, ReturnPiece.PieceType pieceType, ArrayList<ReturnPiece> piecesOnBoard) {
		for (ReturnPiece piece : piecesOnBoard) {
			if (piece.pieceFile.toString().equals(square.substring(0, 1))
					&& piece.pieceRank == Character.getNumericValue(square.charAt(1))
					&& piece.pieceType.toString().substring(0, 1).equals(pieceType.toString().substring(0, 1))) {
				return true;
			}
		}
		return false;
	}

	// Update the isSquareOccupied method
	private static boolean isSquareOccupied(String square, ArrayList<ReturnPiece> piecesOnBoard) {
		for (ReturnPiece piece : piecesOnBoard) {
			if (piece.pieceFile.toString().equals(square.substring(0, 1))
					&& piece.pieceRank == Character.getNumericValue(square.charAt(1))) {
				if (isSquareOccupiedBySameColor(square, piece.pieceType, piecesOnBoard)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	private static boolean isPathOccupied(String sourceSquare, String destinationSquare, ArrayList<ReturnPiece> piecesOnBoard) {
	    ReturnPiece piece = findPieceAtSquare(sourceSquare, piecesOnBoard);

	    int sourceFile = sourceSquare.charAt(0) - 'a';
	    int sourceRank = Character.getNumericValue(sourceSquare.charAt(1));
	    int destFile = destinationSquare.charAt(0) - 'a';
	    int destRank = Character.getNumericValue(destinationSquare.charAt(1));

	    int fileDirection = Integer.compare(destFile, sourceFile);
	    int rankDirection = Integer.compare(destRank, sourceRank);

	    int currentFile = sourceFile + fileDirection;
	    int currentRank = sourceRank + rankDirection;

	    while (currentFile != destFile || currentRank != destRank) {
	        String currentSquare = "" + (char) ('a' + currentFile) + currentRank;
	        if (isSquareOccupied(currentSquare, piecesOnBoard)) {
	            return true; // Path is occupied
	        }
	        currentFile += fileDirection;
	        currentRank += rankDirection;
	    }

	    return false; // Path is not occupied
	}

	// Helper method to check if the path between sourceSquare and destinationSquare is occupied
	private static boolean isPathOccupied1(String sourceSquare, String destinationSquare, ArrayList<ReturnPiece> piecesOnBoard) {
		int sourceFile = sourceSquare.charAt(0) - 'a';
		int sourceRank = Character.getNumericValue(sourceSquare.charAt(1));
		int destFile = destinationSquare.charAt(0) - 'a';
		int destRank = Character.getNumericValue(destinationSquare.charAt(1));

		int fileDirection = Integer.compare(destFile, sourceFile);
		int rankDirection = Integer.compare(destRank, sourceRank);

		int currentFile = sourceFile + fileDirection;
		int currentRank = sourceRank + rankDirection;
		// Find the index of the piece being moved
		int pieceIndex = -1;
		ReturnPiece piece = null;
		for (int i = 0; i < piecesOnBoard.size(); i++) {
			if (piecesOnBoard.get(i).equals(piece)) {
				pieceIndex = i;
				piece = piecesOnBoard.get(pieceIndex);
				break;
			}
		}
		if (pieceIndex != -1) {
			// Check if there is a piece at the destination square
			int destinationIndex = -1;
			for (int i = 0; i < piecesOnBoard.size(); i++) {
				if (piecesOnBoard.get(i).pieceFile == ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1))
						&& piecesOnBoard.get(i).pieceRank == Integer.parseInt(destinationSquare.substring(1))) {
					destinationIndex = i;
					break;
				}
			}
			if (isSquareOccupied(destinationSquare, piecesOnBoard) == true) {
				//piecesOnBoard.remove(destinationIndex);
				//if (piece.pieceType == ReturnPiece.PieceType.WP)  piece.pieceType = ReturnPiece.PieceType.BP; 
				//if (piece.pieceType == ReturnPiece.PieceType.BP)  piece.pieceType = ReturnPiece.PieceType.WP; 
				if (DEBUG) System.out.println("new? pieceType: " + piece.pieceType );
				piece = piecesOnBoard.get(destinationIndex);
			}
		}

		while (currentFile != destFile || currentRank != destRank) {
			String currentSquare = "" + (char) ('a' + currentFile) + currentRank;
			if (isSquareOccupied(currentSquare, piecesOnBoard)) {
				return true; // Path is occupied
			}
			currentFile += fileDirection;
			currentRank += rankDirection;
		}

		return false; // Path is not occupied
	}

	public static boolean checkPawnMove(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
		System.out.println("Checking pawn move from " + sourceSquare + " to " + destinationSquare);
		if (DEBUG) {
			System.out.println("Piece: " + piece);
			System.out.println("Current Player: " + Chess.currentPlayer);
			System.out.println("Source Square: " + sourceSquare);
			System.out.println("Destination Square: " + destinationSquare);
		}

		if (piece.pieceType != ReturnPiece.PieceType.WP && piece.pieceType != ReturnPiece.PieceType.BP) {
			// Not a pawn
			System.out.println("piece not pawn sent to checkPawnMove");
			return false;
		}
		System.out.println("piece: " + piece);
		Player currentPlayer = (piece.pieceType == ReturnPiece.PieceType.WP) ? Player.white : Player.black;

		// Check if it's the correct player's turn
		if (currentPlayer != Chess.currentPlayer) {
			System.out.println("Mismatch player and piece\n" + "currentPlayer in MV: " + currentPlayer + " Chess currentPlayer: " + Chess.currentPlayer +" pieceType: " + piece.pieceType);
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
			if ((Math.abs(destFile - sourceFile) == 1) && (Math.abs(destRank - sourceRank) == direction)
					&& isSquareOccupied(destinationSquare, piecesOnBoard)) {
				return true;
			} else {
				System.out.println("Pawn Invalid capture");
			}
		}

		// Check for en passant
		if (isEnPassantMove(sourceSquare, destinationSquare, piecesOnBoard)) {
			return true;
		}

		// TODO: Add more conditions for special pawn moves like en passant and promotion
		return false;
	}

	private static boolean isEnPassantMove1(String sourceSquare, String destinationSquare, ArrayList<ReturnPiece> piecesOnBoard) {
		System.out.println("isEnPassantMove method");
		int sourceFile = sourceSquare.charAt(0) - 'a';
		int sourceRank = Character.getNumericValue(sourceSquare.charAt(1));

		int destFile = destinationSquare.charAt(0) - 'a';
		int destRank = Character.getNumericValue(destinationSquare.charAt(1));

		// Check if the move is diagonal and one square away
		if (Math.abs(destFile - sourceFile) == 1 && Math.abs(destRank - sourceRank) == 1) {
			// Check for en passant conditions
			// En passant capture can occur if there is a pawn next to the destination square

			// Determine the potential en passant capture square
			String enPassantCaptureSquare;
			if (Chess.currentPlayer == Player.white) {
				enPassantCaptureSquare = "" + (char) ('a' + destFile) + (destRank - 1);
			} else {
				enPassantCaptureSquare = "" + (char) ('a' + destFile) + (destRank + 1);
			}

			// Check if there is a pawn at the en passant capture square
			for (ReturnPiece piece : piecesOnBoard) {
				if (piece.pieceType == ReturnPiece.PieceType.WP || piece.pieceType == ReturnPiece.PieceType.BP) {
					if (piece.pieceFile.toString().equals(enPassantCaptureSquare.substring(0, 1))
							&& piece.pieceRank == Character.getNumericValue(enPassantCaptureSquare.charAt(1))) {
						// En passant capture is valid, remove the captured pawn from the board
						piecesOnBoard.remove(piece);
						return true;
					}
				}
			}
		}

		return false;
	}
	private static boolean isEnPassantMove(String sourceSquare, String destinationSquare, ArrayList<ReturnPiece> piecesOnBoard) {
	    System.out.println("isEnPassantMove method");
	    int sourceFile = sourceSquare.charAt(0) - 'a';
	    int sourceRank = Character.getNumericValue(sourceSquare.charAt(1));

	    int destFile = destinationSquare.charAt(0) - 'a';
	    int destRank = Character.getNumericValue(destinationSquare.charAt(1));

	    // Check if the move is a two-step advance
	    if (Math.abs(destFile - sourceFile) == 0 && Math.abs(destRank - sourceRank) == 2) {
	        // Check for en passant conditions
	        // En passant capture can occur if there is a pawn next to the destination square

	        // Determine the potential en passant capture square
	        String enPassantCaptureSquare;
	        if (Chess.currentPlayer == Player.white) {
	            enPassantCaptureSquare = "" + (char) ('a' + destFile) + (destRank - 1);
	        } else {
	            enPassantCaptureSquare = "" + (char) ('a' + destFile) + (destRank + 1);
	        }

	        // Check if there is a pawn at the en passant capture square
	        for (ReturnPiece piece : piecesOnBoard) {
	            if (piece.pieceType == ReturnPiece.PieceType.WP || piece.pieceType == ReturnPiece.PieceType.BP) {
	                if (piece.pieceFile.toString().equals(enPassantCaptureSquare.substring(0, 1))
	                        && piece.pieceRank == Character.getNumericValue(enPassantCaptureSquare.charAt(1))) {
	                    // En passant capture is valid, remove the captured pawn from the board
	                    piecesOnBoard.remove(piece);
	                    return true;
	                }
	            }
	        }
	    }

	    return false;
	}



	private static boolean isFirstMove(ReturnPiece piece) {
		// Check if it's the pawn's first move
		return (piece.pieceType == ReturnPiece.PieceType.WP && piece.pieceRank == 2)
				|| (piece.pieceType == ReturnPiece.PieceType.BP && piece.pieceRank == 7);
	}


	public static boolean processPawnPromotion1(String sourceSquare, String destinationSquare, String promotionPiece, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
		// Find the pawn at the source square
		ReturnPiece pawnToPromote = findPieceAtSquare(sourceSquare, piecesOnBoard, ReturnPiece.PieceType.WP, ReturnPiece.PieceType.BP);

		// Check if the pawn is found
		if (pawnToPromote == null || !pawnToPromote.equals(piece)) {
			// Pawn not found or does not match the provided piece, illegal promotion
			return false;
		}

		// Remove the pawn from the board
		piecesOnBoard.remove(pawnToPromote);

		// Determine the promotion piece type
		ReturnPiece.PieceType promotionType = getPromotionPieceType(promotionPiece, pawnToPromote.pieceType);

		// Create a new piece of the promotion type at the destination square
		ReturnPiece promotedPiece = new ReturnPiece();
		promotedPiece.pieceType = promotionType;
		promotedPiece.pieceFile = ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1));
		promotedPiece.pieceRank = Integer.parseInt(destinationSquare.substring(1));

		// Add the promoted piece to the board
		piecesOnBoard.add(promotedPiece);

		return true;
	}

	public static boolean checkPawnPromotion(String sourceSquare, String destinationSquare, String promotionPiece, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
	    // Find the pawn at the source square
	    ReturnPiece pawnToPromote = findPieceAtSquare(sourceSquare, piecesOnBoard, ReturnPiece.PieceType.WP, ReturnPiece.PieceType.BP);

	    // Check if the pawn is found
	    if (pawnToPromote == null) {
	        // Pawn not found or does not match the provided piece, illegal promotion
	    	System.out.println("pawn null");
	        return false;
	    }
	    if (!pawnToPromote.equals(piece)) {
	    	// Pawn not found or does not match the provided piece, illegal promotion
	    	System.out.println("pawn does not match piece: " + piece);
	    	return false;
	    }

	    // Check if the pawn has reached the promotion rank
	    int promotionRank = (piece.pieceType == ReturnPiece.PieceType.WP) ? 8 : 1;
	    int direction = (piece.pieceType == ReturnPiece.PieceType.WP) ? 1 : -1;
	    if ((pawnToPromote.pieceRank + 1 * direction) != promotionRank) {
	    	System.out.println("pawn not at promotion rank");
	        // Pawn has not reached the promotion rank, illegal promotion
	        return false;
	    }

	    // Remove the pawn from the board
	    /*piecesOnBoard.remove(pawnToPromote);

	    // Determine the promotion piece type
	    ReturnPiece.PieceType promotionType = getPromotionPieceType(promotionPiece, pawnToPromote.pieceType);

	    // Create a new piece of the promotion type at the destination square
	    ReturnPiece promotedPiece = new ReturnPiece();
	    promotedPiece.pieceType = promotionType;
	    promotedPiece.pieceFile = ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1));
	    promotedPiece.pieceRank = Integer.parseInt(destinationSquare.substring(1));

	    // Add the promoted piece to the board
	    piecesOnBoard.add(promotedPiece);*/

	    return true;
	}


	public static ArrayList<ReturnPiece> processPawnPromotion2(String sourceSquare, String destinationSquare, String promotionPiece, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard){
		// Find the pawn at the source square
	    ReturnPiece pawnToPromote = findPieceAtSquare(sourceSquare, piecesOnBoard, ReturnPiece.PieceType.WP, ReturnPiece.PieceType.BP);

	    // Check if the pawn is found
	    /*if (pawnToPromote == null || !pawnToPromote.equals(piece)) {
	        // Pawn not found or does not match the provided piece, illegal promotion
	        return piecesOnBoard;
	    }

	    // Check if the pawn has reached the promotion rank
	    int promotionRank = (piece.pieceType == ReturnPiece.PieceType.WP) ? 8 : 1;
	    if (pawnToPromote.pieceRank != promotionRank) {
	        // Pawn has not reached the promotion rank, illegal promotion
	        return piecesOnBoard;
	    }*/
	    
	    // Remove the pawn from the board
	    piecesOnBoard.remove(pawnToPromote);

	    // Determine the promotion piece type
	    ReturnPiece.PieceType promotionType = getPromotionPieceType(promotionPiece, pawnToPromote.pieceType);

	    // Create a new piece of the promotion type at the destination square
	    ReturnPiece promotedPiece = new ReturnPiece();
	    promotedPiece.pieceType = promotionType;
	    promotedPiece.pieceFile = ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1));
	    promotedPiece.pieceRank = Integer.parseInt(destinationSquare.substring(1));

	    // Add the promoted piece to the board
	    piecesOnBoard.add(promotedPiece);
		return piecesOnBoard;
	}
	public static ArrayList<ReturnPiece> processPawnPromotion(String sourceSquare, String destinationSquare, String promotionPiece, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
	    // Find the pawn at the source square
	    ReturnPiece pawnToPromote = findPieceAtSquare(sourceSquare, piecesOnBoard, ReturnPiece.PieceType.WP, ReturnPiece.PieceType.BP);
	    if (checkPawnPromotion(sourceSquare, destinationSquare, promotionPiece, piece, piecesOnBoard)) {
	    // Remove the pawn from the board
	    piecesOnBoard.remove(pawnToPromote);

	    // Determine the promotion piece type
	    ReturnPiece.PieceType promotionType = getPromotionPieceType(promotionPiece, pawnToPromote.pieceType);

	    // Create a new piece of the promotion type at the destination square
	    ReturnPiece promotedPiece = new ReturnPiece();
	    promotedPiece.pieceType = promotionType;
	    promotedPiece.pieceFile = ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1));
	    promotedPiece.pieceRank = Integer.parseInt(destinationSquare.substring(1));

	    // Add the promoted piece to the board
	    piecesOnBoard.add(promotedPiece);
	    }
	    return piecesOnBoard;
	    
	}


	// Helper method to find a piece at a specific square with a given type
	public static ReturnPiece findPieceAtSquare(String square, ArrayList<ReturnPiece> piecesOnBoard, ReturnPiece.PieceType... types) {
		for (ReturnPiece piece : piecesOnBoard) {
			if (piece.pieceFile == ReturnPiece.PieceFile.valueOf(square.substring(0, 1))
					&& piece.pieceRank == Integer.parseInt(square.substring(1))
					&& Arrays.asList(types).contains(piece.pieceType)) {
				return piece;
			}
		}
		return null;
	}
	public static boolean isCheck(ArrayList<ReturnPiece> piecesOnBoard, Chess.Player currentPlayer) {
		// Find the king of the current player
		ReturnPiece king = findKing(piecesOnBoard, currentPlayer);

		// Check if the opponent's pieces can attack the king's position
		for (ReturnPiece opponentPiece : piecesOnBoard) {
			if (opponentPiece.pieceType != ReturnPiece.PieceType.WK && opponentPiece.pieceType != ReturnPiece.PieceType.BK
					&& opponentPiece.pieceType != ReturnPiece.PieceType.WP && opponentPiece.pieceType != ReturnPiece.PieceType.BP // exclude pawns for simplicity
					&& isPieceAttackingSquare(opponentPiece, king.pieceFile, king.pieceRank)) {
				// The king is in check
				return true;
			}
		}

		// The king is not in check
		return false;
	}

	private static ReturnPiece.PieceType getOpponentPawnType(Chess.Player currentPlayer) {
	    // Determine the type of pawn for the opponent based on the current player
	    return (currentPlayer == Chess.Player.white) ? ReturnPiece.PieceType.BP : ReturnPiece.PieceType.WP;
	}

	private static boolean isPawnAttackingSquare(ReturnPiece pawn, char targetFile, int targetRank) {
	    // Check if the pawn is attacking the specified square diagonally
	    int pawnRank = pawn.pieceRank;
	    char pawnFile = pawn.pieceFile.toString().charAt(0);

	    // Determine the direction of pawn attack based on the opponent's position
	    int rankDifference = (pawn.pieceType == ReturnPiece.PieceType.WP) ? 1 : -1;

	    // Check if the target square is diagonally forward from the pawn
	    return Math.abs(targetFile - pawnFile) == 1 && targetRank == pawnRank + rankDifference;
	}

	private static ReturnPiece findKing(ArrayList<ReturnPiece> piecesOnBoard, Chess.Player player) {
	    // Find the king of the specified player
	    ReturnPiece.PieceType kingType = (player == Chess.Player.white) ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK;

	    for (ReturnPiece piece : piecesOnBoard) {
	        if (piece.pieceType == kingType) {
	            return piece;
	        }
	    }

	    // King not found
	    throw new RuntimeException("King not found for player: " + player);
	    // Alternatively, return null or another special value to indicate an error
	}

	private static ReturnPiece findKing1(ArrayList<ReturnPiece> piecesOnBoard, Chess.Player player) {
		// Find the king of the specified player
		ReturnPiece.PieceType kingType = (player == Chess.Player.white) ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK;

		for (ReturnPiece piece : piecesOnBoard) {
			if (piece.pieceType == kingType) {
				return piece;
			}
		}

		// King not found (this should not happen in a valid chess position)
		return null;
	}

	private static boolean isPieceAttackingSquare(ReturnPiece piece, ReturnPiece.PieceFile file, int rank) {
	    // Get the current position of the piece
	    ReturnPiece.PieceFile pieceFile = piece.pieceFile;
	    int pieceRank = piece.pieceRank;

	    // Check based on the piece type
	    switch (piece.pieceType) {
	        case WP:
	            // White pawn can attack diagonally forward left or right
	            return (file == pieceFile && rank == pieceRank + 1) || // Move forward
	                   ((file.ordinal() == pieceFile.ordinal() - 1 || file.ordinal() == pieceFile.ordinal() + 1) && rank == pieceRank + 1); // Attack diagonally

	        case BP:
	            // Black pawn can attack diagonally backward left or right
	            return (file == pieceFile && rank == pieceRank - 1) || // Move backward
	                   ((file.ordinal() == pieceFile.ordinal() - 1 || file.ordinal() == pieceFile.ordinal() + 1) && rank == pieceRank - 1); // Attack diagonally

	        case WR:
	        case BR:
	            // Rook can attack in the same file or rank
	            return file == pieceFile || rank == pieceRank;

	        case WN:
	        case BN:
	            // Knight can attack in an L-shape (two squares in one direction and one square perpendicular)
	            int fileDiff = Math.abs(file.ordinal() - pieceFile.ordinal());
	            int rankDiff = Math.abs(rank - pieceRank);
	            return (fileDiff == 2 && rankDiff == 1) || (fileDiff == 1 && rankDiff == 2);

	        case WB:
	        case BB:
	            // Bishop can attack diagonally
	            return Math.abs(file.ordinal() - pieceFile.ordinal()) == Math.abs(rank - pieceRank);

	        case WQ:
	        case BQ:
	            // Queen can attack in the same file, rank, or diagonally
	            return file == pieceFile || rank == pieceRank || Math.abs(file.ordinal() - pieceFile.ordinal()) == Math.abs(rank - pieceRank);

	        case WK:
	        case BK:
	            // King can attack one square in any direction
	            return Math.abs(file.ordinal() - pieceFile.ordinal()) <= 1 && Math.abs(rank - pieceRank) <= 1;

	        default:
	            return false; // Unknown piece type
	    }
	}



	// Helper method to determine the promotion piece type based on the promotion string and pawn type
	private static ReturnPiece.PieceType getPromotionPieceType(String promotionPiece, ReturnPiece.PieceType pawnType) {
		switch (promotionPiece.toUpperCase().strip()) {
		case "Q":
			return (pawnType == ReturnPiece.PieceType.WP) ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
		case "R":
			return (pawnType == ReturnPiece.PieceType.WP) ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR;
		case "N":
			return (pawnType == ReturnPiece.PieceType.WP) ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN;
		case "B":
			return (pawnType == ReturnPiece.PieceType.WP) ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB;
		default:
			// Invalid promotion piece, default to queen
			return (pawnType == ReturnPiece.PieceType.WP) ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
		}
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

