package domain;

//public class CasillaGolden extends Casilla {
//    @Override
//    public void aplicarEfecto(Board board, int row, int col) {
//        // Proporciona al jugador un tipo de piedra aleatorio.
//        // Si es normal, el jugador debe usar dos piedras normales en el siguiente turno.
//
//        Player currentPlayer = board.getCurrentPlayer();
//        int randomPieceType = new Random().nextInt(6) + 1;  // Número aleatorio entre 1 y 6
//
//        if (randomPieceType == 1 || randomPieceType == 2) {
//            // Si es normal, el jugador debe usar dos piedras normales en el siguiente turno
//            currentPlayer.addTemporaryPiece(randomPieceType);
//            currentPlayer.addTemporaryPiece(randomPieceType);
//        } else {
//            // Si no es normal, simplemente agregar la piedra al jugador
//            currentPlayer.addPiece(randomPieceType);
//        }
//    }
//}