import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe {
    private boolean latestWasCross = false;
    private boolean crossStartedLast = false;
    private JLabel gameProgress;
    private JLabel counter;
    private Cell[][] cells;
    private JButton[][] buttons;
    private GameScore gameScore = new GameScore();

    TicTacToe() {
        cells = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
            }
        }
        view();
    }

    private void view() {

        buttons = new JButton[3][3];
        JFrame frame = new JFrame("Tic tac toe");
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("Start new game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewRound();
                gameScore.nullifyScore();
                paintFieled();
            }
        });
        JMenuItem end = new JMenuItem("End game");
        end.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(newGame);
        menu.addSeparator();
        menu.add(end);
        menuBar.add(menu);
        JPanel gameField = new JPanel();
        gameField.setLayout(new GridLayout(3, 3));
        JPanel window = new JPanel();
        window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
        gameProgress = new JLabel("first player's turn");
        counter = new JLabel("Game score (0:0)");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[j][i] = new JButton();
                buttons[j][i].setPreferredSize(new Dimension(45, 45));
                gameField.add(buttons[j][i]);
                final Point point = new Point(j, i);
                buttons[j][i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        clickOnCell(point);
                    }
                });
            }
        }

        window.add(counter);
        window.add(gameProgress);
        window.add(gameField);
        frame.setJMenuBar(menuBar);
        frame.setContentPane(window);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }

    private void clickOnCell (Point point) {
        int x = point.getX();
        int y = point.getY();
        if (cells[x][y].isClosed()) {
            if (latestWasCross) {
                cells[x][y] = new Zero();
            } else {
                cells[x][y] = new Cross();
            }
            cells[x][y].open();
            latestWasCross = !latestWasCross;
            if (checkWin()) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        cells[j][i].open();
                    }
                }
                if (latestWasCross) {
                    gameScore.enlargeFirstScore();
                } else {
                    gameScore.enlargeSecondScore();
                }
            }

            if(gameScore.checkVictory()){
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        cells[j][i] = new Cell();
                        cells[j][i].open();
                    }
                }
            }

            paintFieled();
        } else if (checkWin() | checkDraw()) {
            crossStartedLast = !crossStartedLast;
            startNewRound();
        }
    }


    private void paintFieled() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[j][i] instanceof Cross) {
                    buttons[j][i].setText("X");
                } else if (cells[j][i] instanceof Zero) {
                    buttons[j][i].setText("O");
                } else {
                    buttons[j][i].setText("");
                }
            }
        }
        if(gameScore.checkVictory()){
            if(gameScore.getFirstScore() == 5){
                gameProgress.setText("first player wins the game");
            }else{
                gameProgress.setText("second player wins the game");
            }
        } else if (checkWin()) {
            if (latestWasCross) {
                gameProgress.setText("first player wins the round");
            } else {
                gameProgress.setText("second player wins the round");
            }
        } else if (latestWasCross) {
            gameProgress.setText("second player's turn");
        } else {
            gameProgress.setText("first player's turn");
        }

        counter.setText("Game score (" + gameScore.getFirstScore() + ":" + gameScore.getSecondScore() + ")");

    }

    private boolean checkWin() {
        if (!latestWasCross) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (cells[j][i] instanceof Zero) {
                        if (j == 0) {
                            if (cells[1][i] instanceof Zero & cells[2][i] instanceof Zero) {
                                return true;
                            }
                        }
                        if (i == 0) {
                            if (cells[j][1] instanceof Zero & cells[j][2] instanceof Zero) {
                                return true;
                            }
                        }
                        if (j == 0 & i == 0) {
                            if (cells[1][1] instanceof Zero & cells[2][2] instanceof Zero) {
                                return true;
                            }
                        }
                        if (j == 0 & i == 2) {
                            if (cells[1][1] instanceof Zero & cells[2][0] instanceof Zero) {
                                return true;
                            }
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (cells[j][i] instanceof Cross) {
                        if (j == 0) {
                            if (cells[1][i] instanceof Cross & cells[2][i] instanceof Cross) {
                                return true;
                            }
                        }
                        if (i == 0) {
                            if (cells[j][1] instanceof Cross & cells[j][2] instanceof Cross) {
                                return true;
                            }
                        }
                        if (j == 0 & i == 0) {
                            if (cells[1][1] instanceof Cross & cells[2][2] instanceof Cross) {
                                return true;
                            }
                        }
                        if (j == 0 & i == 2) {
                            if (cells[1][1] instanceof Cross & cells[2][0] instanceof Cross) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!(cells[j][i] instanceof Cross) & !(cells[j][i] instanceof Zero)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void startNewRound() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[j][i] = new Cell();
            }
        }
        if(crossStartedLast){
            latestWasCross = true;
        }else{
            latestWasCross = false;
        }
        paintFieled();
    }


    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
    }

}
