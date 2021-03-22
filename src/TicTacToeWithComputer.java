import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeWithComputer {
    boolean latestWasPlayer = false;
    JLabel label;
    JLabel counter;
    Cell[][] cells;
    JButton[][] buttons;
    GameScore gameScore = new GameScore();

    TicTacToeWithComputer() {
        cells = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
            }
        }

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
        label = new JLabel("");
        counter = new JLabel("Game score (0:0)");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[j][i] = new JButton();
                buttons[j][i].setPreferredSize(new Dimension(45, 45));
                gameField.add(buttons[j][i]);
                final Points point = new Points(j, i);
                buttons[j][i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int x = point.getX();
                        int y = point.getY();
                        if (cells[x][y].isClosed()) {
                            cells[x][y] = new Cross();
                            cells[x][y].open();
                            changeLatest();
                            computerStroke();
                            paintFieled();
                        }
                        if (checkWin() | checkDraw()) {
                            startNewRound();
                        }
                    }
                });
            }
        }

        window.add(counter);
        window.add(label);
        window.add(gameField);
        frame.setJMenuBar(menuBar);
        frame.setContentPane(window);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

    }

    public void changeLatest() {

        latestWasPlayer = !latestWasPlayer;
    }


    public void paintFieled() {
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

        if (checkWin()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    cells[j][i].open();
                }
            }
            if (latestWasPlayer) {
                gameScore.enlargeFirstScore();
            } else {
                gameScore.enlargeSecondScore();
            }
            if (latestWasPlayer) {
                label.setText("you win the round");
            } else {
                label.setText("you lose the round");
            }
        }
        counter.setText("Game score (" + gameScore.getFirstScore() + ":" + gameScore.getSecondScore() + ")");
        if (checkVictory()) {
            if (gameScore.getFirstScore() == 5) {
                label.setText("you win the game");
            } else {
                label.setText("you lose the game");
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    cells[j][i] = new Cell();
                    cells[j][i].open();
                }
            }
        }
    }

    public boolean checkWin() {
        if (!latestWasPlayer) {
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

    public boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!(cells[j][i] instanceof Cross) & !(cells[j][i] instanceof Zero)) {
                    return false;
                }
            }
        }
        if (!(checkWin())) {
            return true;
        } else {
            return false;
        }
    }

    public void startNewRound() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[j][i] = new Cell();
            }
        }
        latestWasPlayer = false;
        paintFieled();
    }

    public boolean checkVictory() {
        if (gameScore.getFirstScore() == 5 | gameScore.getSecondScore() == 5) {
            return true;
        }
        return false;
    }

    public void computerStroke() {
        if (!(checkWin()) & !(checkDraw())) {
            changeLatest();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (cells[0][i] instanceof Zero & cells[1][i] instanceof Zero & !(cells[2][i] instanceof Cross)) {
                        cells[2][i] = new Zero();
                        return;
                    } else if (cells[0][i] instanceof Zero & cells[2][i] instanceof Zero & !(cells[1][i] instanceof Cross)) {
                        cells[1][i] = new Zero();
                        return;
                    } else if (cells[j][0] instanceof Zero & cells[j][1] instanceof Zero & !(cells[j][2] instanceof Cross)) {
                        cells[j][2] = new Zero();
                        return;
                    } else if (cells[j][0] instanceof Zero & cells[j][2] instanceof Zero & !(cells[j][1] instanceof Cross)) {
                        cells[j][1] = new Zero();
                        return;
                    } else if (!(cells[0][i] instanceof Cross) & cells[1][i] instanceof Zero & cells[2][i] instanceof Zero) {
                        cells[0][i] = new Zero();
                        return;
                    } else if (!(cells[j][0] instanceof Cross) & cells[j][1] instanceof Zero & cells[j][2] instanceof Zero) {
                        cells[j][0] = new Zero();
                        return;
                    } else if (!(cells[0][0] instanceof Cross) & cells[1][1] instanceof Zero & cells[2][2] instanceof Zero) {
                        cells[0][0] = new Zero();
                        return;
                    } else if (!(cells[1][1] instanceof Cross) & cells[0][0] instanceof Zero & cells[2][2] instanceof Zero) {
                        cells[1][1] = new Zero();
                        return;
                    } else if (!(cells[2][2] instanceof Cross) & cells[1][1] instanceof Zero & cells[0][0] instanceof Zero) {
                        cells[2][2] = new Zero();
                        return;
                    } else if (!(cells[0][2] instanceof Cross) & cells[1][1] instanceof Zero & cells[2][0] instanceof Zero) {
                        cells[0][2] = new Zero();
                        return;
                    } else if (!(cells[1][1] instanceof Cross) & cells[0][2] instanceof Zero & cells[2][0] instanceof Zero) {
                        cells[1][1] = new Zero();
                        return;
                    } else if (!(cells[2][0] instanceof Cross) & cells[1][1] instanceof Zero & cells[0][2] instanceof Zero) {
                        cells[2][0] = new Zero();
                        return;
                    }
                }
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (cells[0][i] instanceof Cross & cells[1][i] instanceof Cross & !(cells[2][i] instanceof Zero)) {
                        cells[2][i] = new Zero();
                        return;
                    } else if (cells[0][i] instanceof Cross & cells[2][i] instanceof Cross & !(cells[1][i] instanceof Zero)) {
                        cells[1][i] = new Zero();
                        return;
                    } else if (cells[j][0] instanceof Cross & cells[j][1] instanceof Cross & !(cells[j][2] instanceof Zero)) {
                        cells[j][2] = new Zero();
                        return;
                    } else if (cells[j][0] instanceof Cross & cells[j][2] instanceof Cross & !(cells[j][1] instanceof Zero)) {
                        cells[j][1] = new Zero();
                        return;
                    } else if (!(cells[0][i] instanceof Zero) & cells[1][i] instanceof Cross & cells[2][i] instanceof Cross) {
                        cells[0][i] = new Zero();
                        return;
                    } else if (!(cells[j][0] instanceof Zero) & cells[j][1] instanceof Cross & cells[j][2] instanceof Cross) {
                        cells[j][0] = new Zero();
                        return;
                    } else if (!(cells[0][0] instanceof Zero) & cells[1][1] instanceof Cross & cells[2][2] instanceof Cross) {
                        cells[0][0] = new Zero();
                        return;
                    } else if (!(cells[1][1] instanceof Zero) & cells[0][0] instanceof Cross & cells[2][2] instanceof Cross) {
                        cells[1][1] = new Zero();
                        return;
                    } else if (!(cells[2][2] instanceof Zero) & cells[1][1] instanceof Cross & cells[0][0] instanceof Cross) {
                        cells[2][2] = new Zero();
                        return;
                    } else if (!(cells[0][2] instanceof Zero) & cells[1][1] instanceof Cross & cells[2][0] instanceof Cross) {
                        cells[0][2] = new Zero();
                        return;
                    } else if (!(cells[1][1] instanceof Zero) & cells[0][2] instanceof Cross & cells[2][0] instanceof Cross) {
                        cells[1][1] = new Zero();
                        return;
                    } else if (!(cells[2][0] instanceof Zero) & cells[1][1] instanceof Cross & cells[0][2] instanceof Cross) {
                        cells[2][0] = new Zero();
                        return;
                    }
                }
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (cells[0][i] instanceof Zero & !(cells[1][i] instanceof Cross) & !(cells[2][i] instanceof Cross)) {
                        cells[1][i] = new Zero();
                        return;
                    } else if (cells[j][0] instanceof Zero & !(cells[j][1] instanceof Cross) & !(cells[j][2] instanceof Cross)) {
                        cells[j][1] = new Zero();
                        return;
                    } else if (cells[1][i] instanceof Zero & !(cells[0][i] instanceof Cross) & !(cells[2][i] instanceof Cross)) {
                        cells[0][i] = new Zero();
                        return;
                    } else if (cells[2][i] instanceof Zero & !(cells[1][i] instanceof Cross) & !(cells[0][i] instanceof Cross)) {
                        cells[1][i] = new Zero();
                        return;
                    } else if (cells[j][1] instanceof Zero & !(cells[j][0] instanceof Cross) & !(cells[j][2] instanceof Cross)) {
                        cells[j][0] = new Zero();
                        return;
                    } else if (cells[j][2] instanceof Zero & !(cells[j][1] instanceof Cross) & !(cells[j][0] instanceof Cross)) {
                        cells[j][1] = new Zero();
                        return;
                    } else if (cells[0][0] instanceof Zero & !(cells[1][1] instanceof Cross) & !(cells[2][2] instanceof Cross)) {
                        cells[1][1] = new Zero();
                        return;
                    } else if (cells[2][2] instanceof Zero & !(cells[1][1] instanceof Cross) & !(cells[0][0] instanceof Cross)) {
                        cells[1][1] = new Zero();
                        return;
                    } else if (cells[0][2] instanceof Zero & !(cells[1][1] instanceof Cross) & !(cells[2][0] instanceof Cross)) {
                        cells[1][1] = new Zero();
                        return;
                    } else if (cells[2][0] instanceof Zero & !(cells[1][1] instanceof Cross) & !(cells[0][2] instanceof Cross)) {
                        cells[1][1] = new Zero();
                        return;
                    } else if (cells[1][1] instanceof Zero & !(cells[0][0] instanceof Cross) & !(cells[2][2] instanceof Cross)) {
                        cells[0][0] = new Zero();
                        return;
                    } else if (cells[1][1] instanceof Zero & !(cells[2][0] instanceof Cross) & !(cells[0][2] instanceof Cross)) {
                        cells[0][2] = new Zero();
                        return;
                    }
                }
            }

            while (true) {
                int x = (int) (Math.random() * 3);
                int y = (int) (Math.random() * 3);
                if (!(cells[x][y] instanceof Cross) & !(cells[x][y] instanceof Zero)) {
                    cells[x][y] = new Zero();
                    break;
                }
            }
            return;


        }
    }

    public static void main(String[] args) {
        TicTacToeWithComputer ticTacToe = new TicTacToeWithComputer();
    }

}
