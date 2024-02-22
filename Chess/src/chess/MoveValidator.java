package chess;
import java.util.ArrayList;
import chess.Chess.Player;
import chess.ReturnPiece.PieceFile;

public class MoveValidator {

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
    public static ArrayList<ReturnPiece> processRegularMove(String sourceSquare, String destinationSquare, ReturnPiece piece, ArrayList<ReturnPiece> piecesOnBoard) {
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
            newPiecesOnBoard.add(piece);
        }
        	/*for (int i = 0; i < piecesOnBoard.size(); i++) {
        		char file = piecesOnBoard.get(i).pieceFile.toString().charAt(0);
        	    int rank = piecesOnBoard.get(i).pieceRank;
        	    if (destinationSquare.charAt(0) == file && destinationSquare.charAt(1) == rank) {
        	    	piece.pieceRank = rank;
        	    	piece.pieceFile = piecesOnBoard.get(i).pieceFile;
        	    	piecesOnBoard.remove(i);
        	    	break;
        	    }
        	
        }*/

        return piecesOnBoard;
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

