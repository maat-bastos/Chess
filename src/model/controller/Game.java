import java.util.ArrayList;

import javax.swing.JOptionPane;private List moveHistory;
import model.board.Board;
import model.board.Position;
import model.pieces.*;

public class Game {
    private Board board;
    private boolean isWhiteTurn;
    private boolean isGameOver;
    private Piece selectedPiece;

    public Game() {
        board = new Board();
        isWhiteTurn = true;
        isGameOver = false;
        setupPieces();
        moveHistory = new ArrayList<>();
    }

    private void setupPieces() {
        // Colocar peças na posição inicial
        // Peças brancas
        board.placePiece(new Rook(board, true), new Position(7, 0));
        board.placePiece(new Knight(board, true), new Position(7, 1));
        board.placePiece(new Bishop(board, true), new Position(7, 2));
        board.placePiece(new Queen(board, true), new Position(7, 3));
        board.placePiece(new King(board, true), new Position(7, 4));
        board.placePiece(new Bishop(board, true), new Position(7, 5));
        board.placePiece(new Knight(board, true), new Position(7, 6));
        board.placePiece(new Rook(board, true), new Position(7, 7));
        for (int col = 0; col < 8; col++) {
            board.placePiece(new Pawn(board, true), new Position(6, col));
        }
        // Peças pretas (mesma lógica)
        // ...
    }

    public Board getBoard() {
        return board;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
    // Métodos para selecionar peça e fazer movimento
    // ... (continuação na próxima slide)

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void selectPiece(Position position) {
        Piece piece = board.getPieceAt(position);
        // Só pode selecionar peça da cor do jogador atual
        if (piece != null && piece.isWhite() == isWhiteTurn) {
            selectedPiece = piece;
        }
    }

public boolean movePiece(Position destination) {

    // No método movePiece() da classe Game
if (selectedPiece instanceof King &&
Math.abs(destination.getColumn() -
originalPosition.getColumn()) == 2) {
// É um movimento de roque
int rookColumn = destination.getColumn() >
originalPosition.getColumn() ? 7 : 0;
int newRookColumn = destination.getColumn() >
originalPosition.getColumn() ?
destination.getColumn() - 1 :
destination.getColumn() + 1;
Position rookPosition = new Position(
originalPosition.getRow(), rookColumn);
Position newRookPosition = new Position(
originalPosition.getRow(), newRookColumn);
Piece rook = board.getPieceAt(rookPosition);
board.removePiece(rookPosition);
board.placePiece(rook, newRookPosition);
}

if (selectedPiece == null || isGameOver) {
return false;
}
// Verificar se o movimento é válido
if (!selectedPiece.canMoveTo(destination)) {
return false;
}
// Criar um objeto Move para o histórico
Move move = new Move(originalPosition, destination,
selectedPiece, capturedPiece);
if (/* é um roque */) {
move.setCastling(true);
} else if (/* é um en passant */) {
move.setEnPassant(true);
} else if (/* é uma promoção */) {
move.setPromotion(true);
}
// Adicionar ao histórico
moveHistory.add(move);
// Verificar se o movimento deixa o rei em xeque
if (moveCausesCheck(selectedPiece, destination)) {
return false;
}
// Capturar peça, se necessário
Piece capturedPiece = board.getPieceAt(destination);
// Guardar posição original para desfazer o movimento, se necessário
Position originalPosition = selectedPiece.getPosition();
// Fazer o movimento
board.removePiece(originalPosition);
board.placePiece(selectedPiece, destination);
// Verificar condições especiais (promoção de peão, etc.)
checkSpecialConditions(selectedPiece, destination);
// Verificar se o oponente está em xeque ou xeque-mate
checkGameStatus();
// Passar o turno
isWhiteTurn = !isWhiteTurn;
selectedPiece = null;
return true;
}

    // Criar um objeto Move para o histórico
    Move move = new Move(originalPosition, destination,
            selectedPiece, capturedPiece);
            if(/* é um roque */)
    {
        move.setCastling(true);
    }
    elseif(/* é um en passant */)
    {
        move.setEnPassant(true);
    }elseif(/* é uma promoção */)
    {
        move.setPromotion(true);
    }
    // Adicionar ao histórico
    moveHistory.add(move);

    private boolean moveCausesCheck(Piece piece, Position destination) {
        // Implementação para verificar se um movimento deixa o próprio rei em xeque
        // ...
        return false;
    }

    private void checkSpecialConditions(Piece piece, Position destination) {
        // Implementação para promoção de peão, roque, en passant, etc.
        // ...
        // No método checkSpecialConditions() da classe Game
        // Verificar promoção de peão
        if (piece instanceof Pawn) {
            if ((piece.isWhite() && destination.getRow() == 0) ||
                    (!piece.isWhite() && destination.getRow() == 7)) {
                // Perguntar ao jogador para qual peça deseja promover
                String[] options = { "Rainha", "Torre", "Bispo", "Cavalo" };
                int choice = JOptionPane.showOptionDialog(null,
                        "Escolha uma peça para promoção:",
                        "Promoção de Peão",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                // Criar a nova peça
                Piece newPiece;
                switch (choice) {
                    case 0:
                        newPiece = new Queen(board, piece.isWhite());
                        break;
                    case 1:
                        newPiece = new Rook(board, piece.isWhite());
                        break;
                    case 2:
                        newPiece = new Bishop(board, piece.isWhite());
                        break;
                    case 3:
                        newPiece = new Knight(board, piece.isWhite());
                        break;
                    default:
                        newPiece = new Queen(board, piece.isWhite());
                }
                // Substituir o peão pela nova peça
                board.removePiece(destination);
                board.placePiece(newPiece, destination);
            }
        }
        // Verificar outras condições especiais (en passant, etc.)
        // ...
    }

    // Na classe Game, adicionar um campo para rastrear o último movimento
    private Position lastPawnDoubleMove;
    // No método movePiece()
    if(selectedPiece instanceof Pawn)
    {
        // Verificar movimento duplo
        if (Math.abs(destination.getRow() -
                originalPosition.getRow()) == 2) {
            lastPawnDoubleMove = destination;
        } else {
            // Verificar captura en passant
            if (Math.abs(destination.getColumn() -
                    originalPosition.getColumn()) == 1 &&
                    board.getPieceAt(destination) == null) {
                // É uma captura en passant
                Position capturedPawnPos = new Position(
                        originalPosition.getRow(),
                        destination.getColumn());
                board.removePiece(capturedPawnPos);
            }
        }
    }else
    {
        lastPawnDoubleMove = null;
    }
    // Na classe Pawn, adicionar ao método getPossibleMoves()
    // Verificar capturas en passant
    if(lastPawnDoubleMove!=null&&lastPawnDoubleMove.getRow()==position.getRow())
    {
        if (Math.abs(lastPawnDoubleMove.getColumn() -
                position.getColumn()) == 1) {
            Position enPassantPos = new Position(
                    position.getRow() + direction,
                    lastPawnDoubleMove.getColumn());
            moves.add(enPassantPos);
        }
    }
}

private void checkGameStatus() {
// Implementação para verificar xeque, xeque-mate e empate
boolean whiteKingInCheck = isInCheck(true);
boolean blackKingInCheck = isInCheck(false);
if (whiteKingInCheck && isCheckmate(true)) {
isGameOver = true;
// Pretas vencem
} else if (blackKingInCheck && isCheckmate(false)) {
isGameOver = true;
// Brancas vencem
}
// Verificar empate (stalemate, repetição, etc.)
// ...
}

    // Na classe Game
private boolean isInCheck(boolean whiteKing) {
// Encontrar a posição do rei
Position kingPosition = null;
for (int row = 0; row < 8; row++) {
for (int col = 0; col < 8; col++) {
Position pos = new Position(row, col);
Piece piece = board.getPieceAt(pos);
if (piece instanceof King &&
piece.isWhite() == whiteKing) {
kingPosition = pos;
break;
}
}
if (kingPosition != null) break;
}
// Verificar se alguma peça adversária pode capturar o rei
return board.isUnderAttack(kingPosition, !whiteKing);
}

private boolean isCheckmate(boolean whiteKing) {
if (!isInCheck(whiteKing)) {
return false;
}
// Verificar se há algum movimento legal para sair do xeque
for (int row = 0; row < 8; row++) {
for (int col = 0; col < 8; col++) {
Position pos = new Position(row, col);  
Piece piece = board.getPieceAt(pos);
if (piece != null && piece.isWhite() == whiteKing) {
for (Position movePos : piece.getPossibleMoves()) {
// Testar se o movimento tira o rei do xeque
if (!moveCausesCheck(piece, movePos)) {
return false;
}
}
}
}
}
return true;
}