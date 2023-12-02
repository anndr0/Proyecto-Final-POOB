package domain;


public class HumanPlayer extends Player {
    public HumanPlayer(int playerNumber) {
        super(playerNumber);
    }

//    @Override
//    public int makeMove(Board board, int selectedPieceType) {
////        // Verificar si el jugador tiene fichas disponibles del tipo seleccionado
////        if (fichas.contains(selectedPieceType)) {
////            fichas.remove(fichas.indexOf(selectedPieceType));  // Remover la ficha seleccionada
////            return selectedPieceType;  // Devolver el tipo de ficha utilizado
////        } else {
////            // Implementar manejo de caso en el que no hay fichas disponibles del tipo seleccionado
////            // Puedes mostrar un mensaje al usuario indicando que no hay fichas disponibles
////            return makeMove(board, selectedPieceType);  // Llamada recursiva para obtener una ficha válida
////        }
////    }
//    }


    public boolean hasPieceOfType(int pieceType) {
        return fichas.contains(pieceType);
    }

    public void removePiece(int pieceType) {
        fichas.remove(Integer.valueOf(pieceType));
    }

}
