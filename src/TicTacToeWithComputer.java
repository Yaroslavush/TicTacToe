import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeWithComputer {
    private boolean latestWasPlayer = false;
    private JLabel gameProgress;
    private JLabel counter;
    private Cell[][] cells;
    private JButton[][] buttons;
    private GameScore gameScore = new GameScore(5);

    TicTacToeWithComputer() {
        cells = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new EmptyCell();
            }
        }
        initView();
    }

    private void initView() {

        buttons = new JButton[3][3];
        JFrame frame = new JFrame("Tic tac toe");
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("Start new game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewRound();
                gameScore.nullifyScore();
                paintFieled(false, false);
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
        gameProgress = new JLabel("");
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

    private void clickOnCell(Point point) {
        int x = point.getX();
        int y = point.getY();
        boolean isVictory = gameScore.checkVictory();
        if (checkWin() && !(isVictory) || checkDraw() && !(isVictory)) {
            startNewRound();
        } else if (cells[x][y] instanceof EmptyCell && !(isVictory)) {
            cells[x][y] = new Cross();
            latestWasPlayer = !latestWasPlayer;
            computerStroke();
            boolean isWin = checkWin();
            if (isWin) {
                if (latestWasPlayer) {
                    gameScore.enlargeFirstScore();
                } else {
                    gameScore.enlargeSecondScore();
                }
            }
            isVictory = gameScore.checkVictory();
            paintFieled(isVictory, isWin);
        }
    }

    private void paintFieled(boolean isVictory, boolean isWin) {
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
        if (isVictory) {
            if (gameScore.getFirstScore() == gameScore.getVictoryScore()) {
                gameProgress.setText("you win the game");
            } else {
                gameProgress.setText("you lose the game");
            }
        } else if (isWin) {
            if (latestWasPlayer) {
                gameProgress.setText("you win the round");
            } else {
                gameProgress.setText("you lose the round");
            }
        }
        counter.setText("Game score (" + gameScore.getFirstScore() + ":" + gameScore.getSecondScore() + ")");

    }

    private boolean checkWin() {
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

    private boolean checkDraw() {
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

    private void startNewRound() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[j][i] = new EmptyCell();
            }
        }
        latestWasPlayer = false;
        paintFieled(false, false);
    }

    private void computerStroke() {
        if (!(checkWin()) & !(checkDraw())) {
            latestWasPlayer = !latestWasPlayer;
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
