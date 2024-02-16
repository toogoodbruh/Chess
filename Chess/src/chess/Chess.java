package chess;

import java.util.ArrayList;

class ReturnPiece {
	static enum PieceType {WP, WR, WN, WB, WQ, WK, 
		            BP, BR, BN, BB, BK, BQ};
	static enum PieceFile {a, b, c, d, e, f, g, h};
	
	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank;  // 1..8
	public String toString() {
		return ""+pieceFile+pieceRank+":"+pieceType;
	}
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece)other;
		return pieceType == otherPiece.pieceType &&
				pieceFile == otherPiece.pieceFile &&
				pieceRank == otherPiece.pieceRank;
	}
}

class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};
	
	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

public class Chess {
	static ArrayList<ReturnPiece> piecesOnBoard = new ArrayList<ReturnPiece>();
	enum Player { white, black }
	
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {

		/* FILL IN THIS METHOD */
		
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		ReturnPlay result = new ReturnPlay();
	    result.piecesOnBoard = new ArrayList<>(piecesOnBoard);

	    // Split the move into individual parts
	    String[] moveParts = move.split("\\s+");

	    if (moveParts.length == 2 || moveParts.length == 3) {
	        // Regular move or pawn promotion
	        String sourceSquare = moveParts[0];
	        String destinationSquare = moveParts[1];

	        // Additional handling for pawn promotion
	        if (moveParts.length == 3) {
	            String promotionPiece = moveParts[2];
	            // Handle promotionPiece (e.g., "N" for knight, "Q" for queen, etc.)
	        }

	        // Now you have sourceSquare and destinationSquare, you can process the move
	        // Example: result.piecesOnBoard = processRegularMove(sourceSquare, destinationSquare);
	    } else if (moveParts.length == 3 && moveParts[2].equals("draw?")) {
	        // Draw offer
	        String sourceSquare = moveParts[0];
	        String destinationSquare = moveParts[1];
	        // Example: result.piecesOnBoard = processDrawOffer(sourceSquare, destinationSquare);
	        result.message = ReturnPlay.Message.DRAW;
	    } else if (moveParts.length == 1 && moveParts[0].equals("resign")) {
	        // Resignation
	        // Example: result.message = ReturnPlay.Message.RESIGN_WHITE_WINS or RESIGN_BLACK_WINS;
	    } else {
	        // Illegal move
	        result.message = ReturnPlay.Message.ILLEGAL_MOVE;
	    }

		return result;
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

	    // White pieces
	    //initialPieces.add(new ReturnPiece(ReturnPiece.PieceType.WR, ReturnPiece.PieceFile.a, 1));
	    ReturnPiece a; // WP
	    ReturnPiece b = new ReturnPiece();
	    ReturnPiece c = new ReturnPiece();
	    ReturnPiece d = new ReturnPiece();
	    ReturnPiece e = new ReturnPiece();
	    ReturnPiece f = new ReturnPiece();
	    ReturnPiece g = new ReturnPiece();
	    ReturnPiece h = new ReturnPiece();
	    /*a.pieceFile = ReturnPiece.PieceFile.a;
	    a.pieceType = ReturnPiece.PieceType.BB;
	    a.pieceRank = 1;*/
	    for (ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()) { // adds white pawns to arraylist
	    	a = new ReturnPiece();
	        a.pieceType = ReturnPiece.PieceType.WP;
	        a.pieceFile = file;
	        a.pieceRank = 2;
	        initialPieces.add(a);
	        System.out.println(file);
	    }
	    for (ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()) { // adds black pawns to arraylist
	    	a = new ReturnPiece();
	        a.pieceType = ReturnPiece.PieceType.BP;
	        a.pieceFile = file;
	        a.pieceRank = 7;
	        initialPieces.add(a);
	        System.out.println(file);
	    }
	    //initialPieces.add(a);
	    /*for (ReturnPiece.PieceType type : ReturnPiece.PieceType.values()) {
	        for (ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()) {
	            // Adjust rank based on PieceType
	            int rank = (type == ReturnPiece.PieceType.WP || type == ReturnPiece.PieceType.BP) ? 2 : 7;

	            ReturnPiece piece = new ReturnPiece();
	            piece.pieceType = type;
	            piece.pieceFile = file;
	            piece.pieceRank = rank;

	            initialPieces.add(piece);
	        }
	    }*/
	    


	    return initialPieces;
	}
}
