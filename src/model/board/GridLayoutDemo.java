import java.awt.*;
import javax.swing.*;
public class GridLayoutDemo {
    GridLayoutDemo grid = new GridLayoutDemo();
public static void main(String[] args) {
JFrame frame = new JFrame("GridLayout Demo");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(400, 400);
// Criar painel com GridLayout 8x8 (como tabuleiro de xadrez)
JPanel panel = new JPanel(new GridLayout(8, 8));
// Adicionar 64 botões (como casas do tabuleiro)
boolean isWhite = true;
for (int i = 0; i < 64; i++) {
JButton button = new JButton();
button.setBackground(isWhite ? Color.WHITE : Color.GRAY);
panel.add(button);
// Alterna a cor a cada casa, exceto no final da linha
isWhite = (i % 8 == 7) ? isWhite : !isWhite;
}
frame.add(panel);
frame.setVisible(true);
}
}