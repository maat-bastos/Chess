import controller.Game;
import model.board.Position;
import model.pieces.Piece;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

private JTextArea moveHistoryTextArea;

public class ChessGUI extends JFrame {
    private Game game;
    private JPanel boardPanel;
    private JButton[][] squares;
    private Map pieceIcons;

    public ChessGUI() {
        game = new Game();
        initializeGUI();
        loadPieceIcons();
        updateBoardDisplay();
    }

    private void initializeGUI()
    {

        setTitle("Jogo de Xadrez em Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 630);
        setLayout(new BorderLayout());
        // Painel superior com informações do jogo
        JPanel infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(600, 30));
        add(infoPanel, BorderLayout.NORTH);
        // Painel do tabuleiro
        boardPanel = new JPanel(new GridLayout(8, 8));
        add(boardPanel, BorderLayout.CENTER);
        // Criar as casas do tabuleiro
        squares = new JButton[8][8];
        boolean isWhite = true;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col] = new JButton();
                squares[row][col].setPreferredSize(new Dimension(70, 70));
                squares[row][col].setBackground(isWhite ? Color.WHITE : Color.GRAY);
                final int r = row;
                final int c = col;
                squares[row][col].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleSquareClick(r, c);
                    }
                });
                boardPanel.add(squares[row][col]);
                isWhite = !isWhite;
            }
            isWhite = !isWhite;
        }
        setLocationRelativeTo(null);
        setVisible(true);

        // Painel superior com informações do jogo
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(600, 30));
        turnLabel = new JLabel("Turno: Brancas");
        turnLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(turnLabel, BorderLayout.CENTER);
        // Botões de controle
        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("Novo Jogo");
        newGameButton.addActionListener(e -> {
            game = new Game();
            updateBoardDisplay();
            turnLabel.setText("Turno: Brancas");
        });
        controlPanel.add(newGameButton);
        JButton undoButton = new JButton("Desfazer");
        undoButton.addActionListener(e -> {
            // Implementar funcionalidade de desfazer
        });
        controlPanel.add(undoButton);
        add(infoPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(150, 600));
        JLabel historyLabel = new JLabel("Histórico de Movimentos");
        historyLabel.setHorizontalAlignment(JLabel.CENTER);
        rightPanel.add(historyLabel, BorderLayout.NORTH);
        moveHistoryTextArea = new JTextArea();
        moveHistoryTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(moveHistoryTextArea);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        // Atualizar o histórico após um movimento
        private void updateMoveHistory() {
        List history = game.getMoveHistory();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < history.size(); i++) {
        if (i % 2 == 0) {
        sb.append((i / 2) + 1).append(". ");
        }
        sb.append(history.get(i).toString()).append(" ");
        if (i % 2 == 1) {
        sb.append("\n");
        }
        }
        moveHistoryTextArea.setText(sb.toString());
        }
    }

    private void loadPieceIcons() {
        pieceIcons = new HashMap<>();
        String[] pieces = { "king", "queen", "rook", "bishop", "knight", "pawn" };
        String[] colors = { "white", "black" };
        for (String color : colors) {
            for (String piece : pieces) {
                String key = color.substring(0, 1) + piece.substring(0, 1).toUpperCase();
                String path = "resources/" + color + "_" + piece + ".png";
                pieceIcons.put(key, new ImageIcon(getClass().getResource(path)));
            }
        }
    }

    private void updateBoardDisplay() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = game.getBoard().getPieceAt(new Position(row, col));
                if (piece == null) {
                    squares[row][col].setIcon(null);
                } else {
                    String key = (piece.isWhite() ? "w" : "b") + piece.getSymbol();
                    squares[row][col].setIcon(pieceIcons.get(key));
                }
            }
        }
    }

    private void handleSquareClick(int row, int col) {
        Position position = new Position(row, col);

        if (game.getSelectedPiece() == null) {
            // Primeira seleção: escolher uma peça
            game.selectPiece(position);
            // Destacar a peça selecionada
            if (game.getSelectedPiece() != null) {
                squares[row][col].setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                // Destacar movimentos possíveis
                for (Position pos : game.getSelectedPiece().getPossibleMoves()) {
                    squares[pos.getRow()][pos.getColumn()].setBorder(
                            BorderFactory.createLineBorder(Color.GREEN, 3));
                }
            }
        } else {
            // Segunda seleção: mover a peça ou selecionar outra
            Piece selectedPiece = game.getSelectedPiece();
            // Limpar todos os destaques
            clearHighlights();
            if (selectedPiece.getPosition().equals(position)) {
                // Clicou na mesma peça, deselecionar
                game.selectPiece(null);
            } else if (selectedPiece.isWhite() == game.getBoard().getPieceAt(position) != null &&
                    game.getBoard().getPieceAt(position).isWhite()) {
                // Clicou em outra peça da mesma cor, mudar seleção
                game.selectPiece(position);
                // Destacar a nova peça selecionada
                if (game.getSelectedPiece() != null) {
                    squares[row][col].setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                    // Destacar movimentos possíveis
                    for (Position pos : game.getSelectedPiece().getPossibleMoves()) {
                        squares[pos.getRow()][pos.getColumn()].setBorder(
                                BorderFactory.createLineBorder(Color.GREEN, 3));
                    }
                }
            } else {
                // Tentar mover a peça
                boolean moveSuccessful = game.movePiece(position);
                if (moveSuccessful) {
                    updateBoardDisplay();
                    // Verificar fim de jogo
                    // Atualizar o método handleSquareClick() para atualizar o turnLabel
                    if (moveSuccessful) {
                        updateBoardDisplay();
                        turnLabel.setText("Turno: " +
                                (game.isWhiteTurn() ? "Brancas" : "Pretas"));
                        // Verificar xeque
                        if (game.isInCheck(game.isWhiteTurn())) {
                            JOptionPane.showMessageDialog(this,
                                    "Xeque!");
                        }
                        // Verificar fim de jogo
                        if (game.isGameOver()) {
                            String winner = game.isWhiteTurn() ? "Pretas" : "Brancas";
                            JOptionPane.showMessageDialog(this,
                                    winner + " vencem! Xeque-mate.");
                        }
                    }
                }
                if (game.isGameOver()) {
                    String winner = game.isWhiteTurn() ? "Pretas" : "Brancas";
                    JOptionPane.showMessageDialog(this,
                            winner + " vencem! Jogo terminado.");
                }
            }
        }
    }
}

private void clearHighlights() {
    for (int r = 0; r < 8; r++) {
        for (int c = 0; c < 8; c++) {
            squares[r][c].setBorder(null);
        }
    }
}

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new ChessGUI());
}