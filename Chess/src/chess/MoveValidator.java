package chess;
import java.util.ArrayList;
import java.util.Arrays;

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

	public static ArrayList<ReturnPiece> processRegularMove_Works(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
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
		if (pieceIndex != -1) {
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
				if (DEBUG) System.out.println("new? pieceType: " + piece.pieceType );
			}

			// Update the piece with the new position
			piece.pieceFile = ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1));
			piece.pieceRank = Integer.parseInt(destinationSquare.substring(1));
			if (DEBUG) System.out.println("pieceType: " + piece.pieceType);

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

	public static ArrayList<ReturnPiece> processRegularMoveWorks(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
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

		// Check if the move involves a pawn reaching the last rank
		if (piece.pieceType == ReturnPiece.PieceType.WP && destinationSquare.charAt(1) == '8') {
			// Pawn promotion needed for white pawn
			boolean promotionSuccess = processPawnPromotion(sourceSquare, destinationSquare, "Q", piece, newPiecesOnBoard);
			if (!promotionSuccess) {
				// Handle failed promotion (if needed)
				System.out.println("failed pawn promotion White");
			}
		} else if (piece.pieceType == ReturnPiece.PieceType.BP && destinationSquare.charAt(1) == '1') {
			// Pawn promotion needed for black pawn
			boolean promotionSuccess = processPawnPromotion(sourceSquare, destinationSquare, "Q", piece, newPiecesOnBoard);
			if (!promotionSuccess) {
				// Handle failed promotion (if needed)
				System.out.println("failed pawn promotion Black");
			}
		} else {
			// Update the piece with the new position
			piece.pieceFile = ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1));
			piece.pieceRank = Integer.parseInt(destinationSquare.substring(1));

			// Add the updated piece to the new position
			newPiecesOnBoard.add(piece);
		}


		if (pieceIndex != -1) {
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
				if (DEBUG) System.out.println("new? pieceType: " + piece.pieceType );
			}

			// Update the piece with the new position
			piece.pieceFile = ReturnPiece.PieceFile.valueOf(destinationSquare.substring(0, 1));
			piece.pieceRank = Integer.parseInt(destinationSquare.substring(1));
			if (DEBUG) System.out.println("pieceType: " + piece.pieceType);

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


	public static boolean checkQueenMove(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
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
	public static boolean checkKingMove(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
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


	// Helper method to check if the path between sourceSquare and destinationSquare is occupied
	private static boolean isPathOccupied(String sourceSquare, String destinationSquare, ArrayList<ReturnPiece> piecesOnBoard) {
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
		ReturnPiece piece = null, piece2 = null;
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
				piece2 = piecesOnBoard.get(destinationIndex);
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

	private static boolean isEnPassantMove(String sourceSquare, String destinationSquare, ArrayList<ReturnPiece> piecesOnBoard) {
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


	private static boolean isFirstMove(ReturnPiece piece) {
		// Check if it's the pawn's first move
		return (piece.pieceType == ReturnPiece.PieceType.WP && piece.pieceRank == 2)
				|| (piece.pieceType == ReturnPiece.PieceType.BP && piece.pieceRank == 7);
	}


	public static boolean processPawnPromotion(String sourceSquare, String destinationSquare, String promotionPiece, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
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

	// Helper method to find a piece at a specific square with a given type
	private static ReturnPiece findPieceAtSquare(String square, ArrayList<ReturnPiece> piecesOnBoard, ReturnPiece.PieceType... types) {
		for (ReturnPiece piece : piecesOnBoard) {
			if (piece.pieceFile == ReturnPiece.PieceFile.valueOf(square.substring(0, 1))
					&& piece.pieceRank == Integer.parseInt(square.substring(1))
					&& Arrays.asList(types).contains(piece.pieceType)) {
				return piece;
			}
		}
		return null;
	}

	// Helper method to determine the promotion piece type based on the promotion string and pawn type
	private static ReturnPiece.PieceType getPromotionPieceType(String promotionPiece, ReturnPiece.PieceType pawnType) {
		switch (promotionPiece.toUpperCase()) {
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

