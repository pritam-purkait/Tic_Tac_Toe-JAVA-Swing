import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.*;

    public class TicTacToe {
        int boardWidth = 600;
        int boardHeight = 700;  // Increased height to accommodate reset button

        JFrame frame = new JFrame("Tic_Tac_Toe");
        JLabel textLabel = new JLabel();
        JPanel textPanel = new JPanel();
        JPanel boardPanel = new JPanel();
        JPanel controlPanel = new JPanel(); // Panel for the reset button

        JButton[][] board = new JButton[3][3];
        JButton resetButton = new JButton("Reset");

        String playerX = "X";
        String playerO = "O";
        String currentPlayer = playerX;

        boolean gameOver = false;
        int turn = 0;

        TicTacToe() {
            frame.setVisible(true);
            frame.setSize(boardWidth, boardHeight);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            textLabel.setBackground(Color.BLACK);
            textLabel.setForeground(Color.WHITE);
            textLabel.setFont(new Font("Century Gothic", Font.BOLD, 50));
            textLabel.setHorizontalAlignment(JLabel.CENTER);
            textLabel.setText(" TIC TAC TOE ");
            textLabel.setOpaque(true);

            textPanel.setLayout(new BorderLayout());
            textPanel.add(textLabel);
            frame.add(textPanel, BorderLayout.NORTH);

            boardPanel.setLayout(new GridLayout(3, 3));
            boardPanel.setBackground(Color.BLACK);
            frame.add(boardPanel);

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    JButton tile = new JButton();
                    board[r][c] = tile;
                    boardPanel.add(tile);

                    tile.setBackground(Color.DARK_GRAY);
                    tile.setForeground(Color.WHITE);
                    tile.setFont(new Font("Carlito", Font.BOLD, 120));
                    tile.setFocusable(false);

                    tile.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gameOver) {
                                return;
                            }
                            JButton tile = (JButton) e.getSource();
                            if (tile.getText().isEmpty()) {

                                tile.setText(currentPlayer);
                                turn++;
                                checkWinner();
                                if (!gameOver) {
                                    currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                    textLabel.setText(currentPlayer + "'s turn");
                                }
                            }
                        }
                    });
                }
            }

            // Set up the control panel for the reset button
            controlPanel.setLayout(new FlowLayout());
            controlPanel.setBackground(Color.BLACK);

            resetButton.setFont(new Font("Century Gothic", Font.BOLD, 30));
            resetButton.setFocusable(false);
            resetButton.setForeground(Color.WHITE);
            resetButton.setBackground(Color.DARK_GRAY);
            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetGame();
                }
            });
            controlPanel.add(resetButton);
            frame.add(controlPanel, BorderLayout.SOUTH);
        }

        void checkWinner() {
            // Check horizontal, vertical, and diagonal wins

            // Horizontal
            for (int r = 0; r < 3; r++) {
                if (board[r][0].getText().isEmpty()) continue;
                if (board[r][0].getText().equals(board[r][1].getText()) &&
                        board[r][1].getText().equals(board[r][2].getText())) {

                    for (int i = 0; i < 3; i++) {
                        setWinner(board[r][i]);
                    }

                    gameOver = true;
                    return;
                }
            }
            // Vertical
            for (int c = 0; c < 3; c++) {
                if (board[0][c].getText().isEmpty()) continue;

                if (Objects.equals(board[0][c].getText(), board[1][c].getText()) &&
                        Objects.equals(board[1][c].getText(), board[2][c].getText())) {
                    for (int i = 0; i < 3; i++) {
                        setWinner(board[i][c]);
                    }
                    gameOver = true;
                    return;
                }
            }
            // Diagonal
            if (board[0][0].getText().equals(board[1][1].getText()) &&
                    board[1][1].getText().equals(board[2][2].getText()) &&
                    !board[0][0].getText().isEmpty()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][i]);
                }
                gameOver = true;
                return;
            }
            // Anti-diagonal
            if (board[0][2].getText().equals(board[1][1].getText()) &&
                    board[1][1].getText().equals(board[2][0].getText()) &&
                    !board[0][2].getText().isEmpty()) {
                setWinner(board[0][2]);
                setWinner(board[1][1]);
                setWinner(board[2][0]);
                gameOver = true;
                return;
            }

            // Check for tie
            if (turn == 9) {
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        setTie(board[r][c]);
                    }
                }
                gameOver = true;
            }
        }

        void setWinner(JButton tile) {
            tile.setForeground(Color.GREEN);
            tile.setBackground(Color.GRAY);
            textLabel.setText(currentPlayer + " is the winner!");
        }

        void setTie(JButton tile) {
            tile.setForeground(Color.ORANGE);
            tile.setBackground(Color.GRAY);
            textLabel.setText("Tie!");
        }

        void resetGame() {
            // Reset the game board
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    board[r][c].setText("");
                    board[r][c].setForeground(Color.WHITE);
                    board[r][c].setBackground(Color.DARK_GRAY);
                }
            }
            // Reset game variables
            currentPlayer = playerX;
            turn = 0;
            gameOver = false;
            textLabel.setText(" TIC TAC TOE ");
        }
    }

