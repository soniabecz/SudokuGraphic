import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

public class SudokuView {

    public int N;
    public int K;
    public int row = 0;
    public int col = 0;
    public int ms = 0;
    public int s = 0;
    public int min = 0;
    public int h = 0;
    public int difficulty = 0;
    public boolean isEasy = false, isMedium = false , isHard = false;
    public String nick;
    public JFrame jFrame;
    private JPanel outer;
    private JPanel welcomePanel;
    private JPanel winnerPanel, loserPanel, exitPanel, difficultyPanel;
    private JPanel sudokuPanel;
    private JPanel[][] panel;
    private JPanel topPanel, bottomPanel;
    private JDialog difficultyWindow;
    public JButton[][] numButtons;
    public JButton solveButton, notesButton, hintButton, exitButton;
    public JButton playButton, closeButton;
    public JButton easy, medium, hard;
    public JTextField textBox;
    public JLabel msgLabel;
    public JLabel timer;
    public Menu menu;
    public MenuBar menuBar;
    public MenuItem menuItem;
    public DecimalFormat formatter = new DecimalFormat("00");

    public Timer t;

    public SudokuView(int N, int K) {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.N = N;
        this.K = K;

        outer = new ImagePanel(new ImageIcon("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/background.jpg").getImage());
        outer.setLayout(new BorderLayout(3,3));
        outer.setBorder(new EmptyBorder(new Insets(10,10,10,10)));
        setupTopPanel();
        setupBottomPanel();

        numButtons = new JButton[9][9]; // sudoku buttons
        sudokuPanel = new ImagePanel(new ImageIcon("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/background.jpg").getImage());
        sudokuPanel.setLayout(new GridLayout(0, 3));
        panel = new JPanel[3][3];
        outer.add(sudokuPanel);
        welcomePanel = new ImagePanel(new ImageIcon("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/background.jpg").getImage());
        welcomePanel.setLayout(new BoxLayout(welcomePanel,BoxLayout.Y_AXIS));
        welcomePanel.setBorder(new EmptyBorder(new Insets(100, 250, 100, 250)));
        winnerPanel = new ImagePanel(new ImageIcon("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/background.jpg").getImage());
        winnerPanel.setLayout(new GridBagLayout());
        loserPanel = new ImagePanel(new ImageIcon("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/background.jpg").getImage());
        loserPanel.setLayout(new GridBagLayout());
        exitPanel = new ImagePanel(new ImageIcon("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/background.jpg").getImage());
        exitPanel.setLayout(new GridBagLayout());
        easy = new JButton("Łatwy");
        medium = new JButton("Średni");
        hard = new JButton("Trudny");
        easy.setBackground(new Color(132, 165, 157));
        medium.setBackground(new Color(246, 189, 96));
        hard.setBackground(new Color(242, 132, 130));
        menuBar = new MenuBar();
        menu = new Menu("Menu");
        menuItem= new MenuItem("Zmień tryb gry");
        menu.add(menuItem);
        menuBar.add(menu);

        jFrame = new JFrame("Sudoku Solver");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setSize(1000, 800);
        jFrame.setMenuBar(menuBar);

    }

    class ImagePanel extends JPanel {

        private Image img;

        //public ImagePanel(String img) {
            //this(new ImageIcon(img).getImage());
        //}

        public ImagePanel(Image img) {
            this.img = img;
            Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
        }

        public void paintComponent(Graphics g) {
            g.drawImage(img, 0, 0, null);
        }

    }


    private void setupTopPanel() {

        topPanel = new ImagePanel(new ImageIcon("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/background.jpg").getImage());;
        topPanel.setLayout(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(200, 30));
        solveButton = new JButton("Rozwiąż");
        notesButton = new JButton("Notatki");
        hintButton = new JButton("Podpowiedź");
        exitButton = new JButton("Wyjdź");
        JButton button = new JButton();
        timer = new JLabel("Time: " + h + ":" + min + ":" + s + "," + ms);
        button.setMargin(new Insets(50,36,10,36));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        solveButton.setMargin(new Insets(10,36,10,36));
        solveButton.setBackground(new Color(132,165,157));
        solveButton.setFont(new Font("American Typewriter",Font.PLAIN,16));
        notesButton.setMargin(new Insets(10,40,10,40));
        notesButton.setBackground(new Color(132,165,157));
        notesButton.setFont(new Font("American Typewriter",Font.PLAIN,16));
        hintButton.setMargin(new Insets(10,23,10,23));
        hintButton.setBackground(new Color(132,165,157));
        hintButton.setFont(new Font("American Typewriter",Font.PLAIN,16));
        exitButton.setMargin(new Insets(10,45,10,45));
        exitButton.setBackground(new Color(132,165,157));
        exitButton.setFont(new Font("American Typewriter",Font.PLAIN,16));
        timer.setFont(new Font("American Typewriter",Font.PLAIN,22));
        topPanel.add(timer);
        topPanel.add(button);
        topPanel.add(solveButton);
        topPanel.add(notesButton);
        topPanel.add(hintButton);
        topPanel.add(exitButton);
        outer.add(topPanel, BorderLayout.EAST);
    }

    private void setupBottomPanel() {

        bottomPanel = new ImagePanel(new ImageIcon("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/background.jpg").getImage());;
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(outer.getWidth(), 30));
        msgLabel = new JLabel();
        msgLabel.setHorizontalAlignment(JLabel.CENTER);
        msgLabel.setFont(new Font("American Typewriter",Font.PLAIN,16));
        bottomPanel.add(msgLabel, BorderLayout.CENTER);
        outer.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void printSudoku(int board[][]) {

        int x = 0;
        int y = 0;
        Insets buttonMargin = new Insets(25, 25, 25, 25);

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    JPanel p = new JPanel(new GridLayout(0, 3));
                    panel[j][i] = p;
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if (board[k+y][l+x] != 0) {
                                JButton b = new JButton(board[k+y][l+x]+"");
                                b.setFont(new Font("Times New Roman",Font.BOLD,26));
                                b.setMargin(buttonMargin);
                                b.setBackground(new Color(247, 237, 226));
                                b.setBorder(BorderFactory.createEtchedBorder(1));
                                numButtons[k+y][l+x] = b;
                                panel[j][i].add(numButtons[k+y][l+x]);

                            }
                            else  {
                                JButton b = new JButton();
                                b.setFont(new Font("Times New Roman",Font.BOLD,26));
                                b.setMargin(buttonMargin);
                                b.setBackground(new Color(247, 237, 226));
                                b.setBorder(BorderFactory.createEtchedBorder(1));
                                numButtons[k+y][l+x] = b;
                                panel[j][i].add(numButtons[k+y][l+x]);
                            }
                        }
                    }
                    p.setBorder(BorderFactory.createLineBorder(new Color(246, 189, 96),1));
                    sudokuPanel.add(panel[j][i]);
                    x+=3;
                }
                y+=3;
                x=0;
            }

            sudokuPanel.setBorder(BorderFactory.createLineBorder(new Color(246, 189, 96),3));

    }

    public void getDifficultyWindow() {

        difficultyWindow = new JDialog(jFrame, "Choose difficulty and nick");

        difficultyPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        easy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                K = 10;
                difficultyWindow.setVisible(false);
                welcomePanel.setVisible(false);
                jFrame.add(getOuterPanel());
                getOuterPanel().setVisible(true);
            }
        });
        medium.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                K = 20;
                difficultyWindow.setVisible(false);
                welcomePanel.setVisible(false);
                jFrame.add(getOuterPanel());
                getOuterPanel().setVisible(true);
            }
        });
        hard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                K = 30;
                difficultyWindow.setVisible(false);
                welcomePanel.setVisible(false);
                jFrame.add(getOuterPanel());
                getOuterPanel().setVisible(true);
            }
        });
        textBox = new JTextField(10);
        JButton jButton = new JButton();
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
        jButton.setFocusPainted(false);
        JLabel nick = new JLabel("Podaj nick: ");
        JLabel level = new JLabel("Wybierz poziom: ");
        nick.setFont(new Font("American Typewriter",Font.PLAIN,22));
        level.setFont(new Font("American Typewriter",Font.PLAIN,22));
        easy.setFont(new Font("American Typewriter",Font.PLAIN,16));
        medium.setFont(new Font("American Typewriter",Font.PLAIN,16));
        hard.setFont(new Font("American Typewriter",Font.PLAIN,16));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        difficultyPanel.add(nick, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        difficultyPanel.add(textBox, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        difficultyPanel.add(jButton,gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        difficultyPanel.add(level,gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        difficultyPanel.add(easy,gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 3;
        difficultyPanel.add(medium,gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 4;
        difficultyPanel.add(hard,gbc);
        difficultyPanel.setBackground(new Color(247, 237, 226));
        difficultyWindow.add(difficultyPanel);
        difficultyWindow.setSize(600,500);
        difficultyWindow.setVisible(true);

    }

    public final JComponent getOuterPanel() {
        return outer;
    }


    public void welcomeWindow() throws IOException {
        welcomePanel.setFont(new Font("Times New Roman",Font.PLAIN,26));
        welcomePanel.setBackground(Color.pink);
        playButton = new JButton("PLAY");
        JLabel welcomeLabel = new JLabel("Witaj w grze");

        BufferedImage myPicture = ImageIO.read(new File("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/title.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        welcomePanel.add(picLabel);
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getDifficultyWindow();

            }
        });
        playButton.setMargin(new Insets(25,180,25,180));
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);
        playButton.setFont(new Font("American Typewriter",Font.PLAIN,48));
        //playButton.setBorder(new EmptyBorder(new Insets(50, 800, 50, 50)));
        playButton.setMinimumSize(new Dimension(75, 100));
        welcomePanel.add(playButton, BorderLayout.SOUTH);
        jFrame.add(welcomePanel);
        jFrame.setVisible(true);
    }

    public void winnerWindow() throws IOException {

        winnerPanel.setBackground(Color.pink);
        GridBagConstraints gbc = new GridBagConstraints();
        closeButton = new JButton("Exit");
        BufferedImage myPicture = ImageIO.read(new File("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/fireworks.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        winnerPanel.add(picLabel, gbc);

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                winnerPanel.setVisible(false);
                try {
                    jFrame.dispose();
                    main(null);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JButton help = new JButton();
        JButton help2 = new JButton();
        help.setBorderPainted(false);
        help.setContentAreaFilled(false);
        help.setFocusPainted(false);
        help2.setBorderPainted(false);
        help2.setContentAreaFilled(false);
        help2.setFocusPainted(false);
        help.setMargin(new Insets(0,135,0,135));
        help2.setMargin(new Insets(0,130,0,130));
        //closeButton.setMargin(new Insets(25,0,25,0));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth=1;
        winnerPanel.add(help2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth=1;
        winnerPanel.add(help, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth=1;
        winnerPanel.add(closeButton, gbc);

        getOuterPanel().setVisible(false);
        jFrame.add(winnerPanel);
    }

    public void loserWindow() throws IOException {

        loserPanel.setBackground(Color.pink);
        GridBagConstraints gbc = new GridBagConstraints();

        closeButton = new JButton("Exit");
        BufferedImage myPicture = ImageIO.read(new File("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/loser.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        loserPanel.add(picLabel, gbc);

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loserPanel.setVisible(false);
                try {
                    jFrame.dispose();
                    main(null);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JButton help = new JButton();
        JButton help2 = new JButton();
        help.setBorderPainted(false);
        help.setContentAreaFilled(false);
        help.setFocusPainted(false);
        help2.setBorderPainted(false);
        help2.setContentAreaFilled(false);
        help2.setFocusPainted(false);
        help.setMargin(new Insets(0,134,0,135));
        help2.setMargin(new Insets(0,134,0,135));
        //closeButton.setMargin(new Insets(25,0,25,0));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth=1;
        loserPanel.add(help2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth=1;
        loserPanel.add(help, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth=1;
        loserPanel.add(closeButton, gbc);

        getOuterPanel().setVisible(false);
        jFrame.add(loserPanel);
    }

    public void exitWindow(JFrame game) throws IOException {

        exitPanel.setBackground(Color.pink);

        GridBagConstraints gbc = new GridBagConstraints();

        closeButton = new JButton("Exit");
        BufferedImage myPicture = ImageIO.read(new File("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/exit.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        exitPanel.add(picLabel, gbc);

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                exitPanel.setVisible(false);
                try {
                    jFrame.dispose();
                    main(null);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JButton help = new JButton();
        JButton help2 = new JButton();
        help.setBorderPainted(false);
        help.setContentAreaFilled(false);
        help.setFocusPainted(false);
        help2.setBorderPainted(false);
        help2.setContentAreaFilled(false);
        help2.setFocusPainted(false);
        help.setMargin(new Insets(0,85,0,85));
        help2.setMargin(new Insets(0,86,0,86));
        //closeButton.setMargin(new Insets(25,0,25,0));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth=1;
        exitPanel.add(help2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth=1;
        exitPanel.add(help, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth=1;
        exitPanel.add(closeButton, gbc);


        getOuterPanel().setVisible(false);
        game.add(exitPanel);
    }

    public void color() {
        for (int k = 0; k < 9; k++) {
            for (int l = 0; l < 9; l++) {
                if (k == row && l == col) {
                    numButtons[k][l].setBackground(new Color(245, 202, 195));
                } else {
                    numButtons[k][l].setBackground(new Color(247, 237, 226));
                }
            }
        }
    }

    public void navigation(int board[][], int filledBoard[][], int mode, int correctAnswers, int incorrectAnswers, int marks[][][], int hintsUsed) throws IOException {

        t = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ms++;

                if(ms == 99) {
                    s++;
                    ms=0;
                }

                else if (s == 60) {
                    min++;
                    s=0;
                }

                else if (min == 60) {
                    h++;
                    min=0;
                }

                timer.setText("Time: " + h + ":" + formatter.format(min) + ":" + formatter.format(s) + "," + formatter.format(ms));
            }
        });
        t.start();

        final int[] finalMode = {mode};
        final int[] finalCorrectAnswers = {correctAnswers};
        final int[] finalIncorrectAnswers = {incorrectAnswers};
        final int[] finalHintsUsed = {hintsUsed};

        for (int k = 0; k < 9; k++) {
            for (int l = 0; l < 9; l++) {
                if (board[k][l] != 0) {
                    JButton b = numButtons[k][l];
                    b.setFont(new Font("American Typewriter",Font.PLAIN,25));
                }
                else {
                    JButton b = numButtons[k][l];
                    b.setFont(new Font("American Typewriter",Font.PLAIN,25));

                    int finalL = l;
                    int finalK = k;

                    b.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            row = finalK;
                            col = finalL;
                            color();
                            if (finalMode[0] == 3) {
                                if (finalHintsUsed[0] < 3) {
                                    finalHintsUsed[0]++;
                                    finalCorrectAnswers[0]++;
                                    board[row][col] = filledBoard[row][col];
                                    numButtons[row][col].setForeground(Color.yellow);
                                    numButtons[row][col].setText("" + board[row][col]);
                                    msgLabel.setText("Poprawne odpowiedzi: "+ finalCorrectAnswers[0] + "/" + K + ", Niepoprawne odpowiedzi: " + finalIncorrectAnswers[0] + "/3" + ", Wykorzystane podpowiedzi: " + finalHintsUsed[0] + "/3");
                                }
                            }
                        }
                    });

                    b.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            super.keyPressed(e);
                            int keyCode = e.getKeyCode();

                            if (keyCode == KeyEvent.VK_UP) {
                                try {
                                    up();
                                    color();
                                    System.out.println("up");
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            if (keyCode == KeyEvent.VK_DOWN) {
                                try {
                                    down();
                                    color();
                                    System.out.println("down");
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            if (keyCode == KeyEvent.VK_LEFT) {
                                try {
                                    left();
                                    color();
                                    System.out.println("left");
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            if (keyCode == KeyEvent.VK_RIGHT) {
                                try {
                                    right();
                                    color();
                                    System.out.println("right");
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            if (Character.getNumericValue(e.getKeyChar())>0 && Character.getNumericValue(e.getKeyChar())<=9){
                                try {
                                    insert(e.getKeyChar(),col,row,filledBoard,finalMode[0],board, finalCorrectAnswers[0],finalIncorrectAnswers[0],marks, finalHintsUsed[0]);
                                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                                    throw new RuntimeException(ex);
                                }

                                if (finalMode[0] == 1){
                                    if (Character.getNumericValue(e.getKeyChar())==filledBoard[row][col]) {
                                        finalCorrectAnswers[0]++;
                                    } else {
                                        finalIncorrectAnswers[0]++;
                                    }
                                }
                            }

                            if(isWon(finalCorrectAnswers[0])){
                                t.stop();
                                try {
                                    playSound("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/src/main/resources/Applause.wav", KeyStroke.getKeyStroke(e.getKeyChar()));

                                    if (difficulty == 1) {
                                        FileWriter myWriter = null;
                                        myWriter = new FileWriter("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/scores_easy.txt", true);
                                        myWriter.write(nick + " " + h + "." + min + "." + s + "," + ms + "\n");
                                        myWriter.close();
                                        System.out.println("Successfully wrote to the file.");
                                    }

                                    if (difficulty == 2) {
                                        FileWriter myWriter = null;
                                        myWriter = new FileWriter("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/scores_medium.txt", true);
                                        myWriter.write(nick + " " + h + "." + min + "." + s + "," + ms + "\n");
                                        myWriter.close();
                                        System.out.println("Successfully wrote to the file.");
                                    }

                                    if (difficulty == 3) {
                                        FileWriter myWriter = null;
                                        myWriter = new FileWriter("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/scores_hard.txt", true);
                                        myWriter.write(nick + " " + h + "." + min + "." + s + "," + ms + "\n");
                                        myWriter.close();
                                        System.out.println("Successfully wrote to the file.");
                                    }

                                    winnerWindow();

                                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            if(finalIncorrectAnswers[0] == 3){
                                t.stop();
                                try {
                                    playSound("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/src/main/resources/Fail.wav", KeyStroke.getKeyStroke(e.getKeyChar()));
                                    loserWindow();
                                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                        }
                    });
                }
            }
        }

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    playSound("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/src/main/resources/Disappointed.wav", KeyStroke.getKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_1,0,true).getKeyChar()));
                    exitWindow(jFrame);
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        solveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                finalMode[0] = 1;
                numButtons[row][col].requestFocus(true);
            }
        });

        notesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                finalMode[0] = 2;
                numButtons[row][col].requestFocus(true);
            }
        });

        hintButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                finalMode[0] = 3;
                numButtons[row][col].requestFocus(true);
            }
        });
    }

    public void up() throws IOException {
        if (row > 0) {
            row --;
        }
        else {
            row = 8;
        }
    }

    public void down() throws IOException {
        if (row < 8) {
            row ++;
        }
        else {
            row = 0;
        }
    }

    public void left() throws IOException {
        if (col > 0) {
            col --;
        }
        else {
            col = 8;
        }
    }

    public void right() throws IOException {
        if (col < 8) {
            col ++;
        }
        else {
            col = 0;
        }
    }

    public void insert(Character c, int col, int row, int [] filledBoard[], int mode, int board[][], int correctAnswers, int incorrectAnswers, int marks[][][], int hintsUsed) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_1,0,true);

            if (mode == 1) {
                if (filledBoard[row][col] == Character.getNumericValue(c)) {
                    try {
                        playSound("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/src/main/resources/CorrectAnswer.wav",key);
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }

                    board[row][col] = Character.getNumericValue(c);
                    numButtons[row][col].setForeground(Color.green);
                    numButtons[row][col].setText(""+c);
                    correctAnswers++;
                    msgLabel.setText("Poprawne odpowiedzi: "+correctAnswers+"/"+K+", Niepoprawne odpowiedzi: " + incorrectAnswers +"/3"+ ", Wykorzystane podpowiedzi: " + hintsUsed + "/3");
                }
                else {
                    try {
                        playSound("/Users/soniabecz/Desktop/school./semestr piąty/komunikacja_człowiek-komputer/sudoku_text/src/main/resources/WrongAnswer.wav", key);
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }
                    numButtons[row][col].setForeground(Color.red);
                    numButtons[row][col].setText(c+"");
                    incorrectAnswers++;
                    msgLabel.setText("Poprawne odpowiedzi: " + correctAnswers + "/" + K + ", Niepoprawne odpowiedzi: " + incorrectAnswers +"/3"+ ", Wykorzystane podpowiedzi: " + hintsUsed + "/3");
                }
            }

            if (mode == 2) {
                if (board[row][col] == 0) {
                    for (int i = 0; i < Math.sqrt(N); i++) {
                        if (marks[row][col][0] == 0) {
                            numButtons[row][col].setForeground(Color.gray);
                            numButtons[row][col].setText(c+"");
                            marks[row][col][0] = Character.getNumericValue(c);
                            break;
                        }
                        if (marks[row][col][0] != 0 && marks[col][row][1] == 0) {
                            numButtons[row][col].setForeground(Color.gray);
                            numButtons[row][col].setText(numButtons[row][col].getText()+" "+c);
                            marks[row][col][1] = Character.getNumericValue(c);
                            break;
                        }
                        if (marks[row][col][1] != 0 && marks[row][col][2] == 0) {
                            numButtons[row][col].setForeground(Color.gray);
                            numButtons[row][col].setText(numButtons[row][col].getText()+" "+c);
                            marks[row][col][2] = Character.getNumericValue(c);
                            break;
                        }
                    }
                }
            }


    }

    public boolean isWon(int correctAnswers) {
        if (correctAnswers == K) {
            return true;
        }
        else {
            return false;
        }
    }

    public void playSound (String soundStream, KeyStroke key) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        File file = new File(soundStream);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();

        if (key == null) {
            clip.stop();
            clip.close();
            audioStream.close();
        }
    }

    public static void main(String[] args) throws IOException {

        /*SudokuViewGraphic view = new SudokuViewGraphic();
        SudokuModel model = new SudokuModel(view.SIZE, view.K);

        int filledBoard[][] = new int[model.N][model.N];

        model.fillValues();


        for (int i = 0; i<9; i++)
        {
            for (int j = 0; j<9; j++) {
                filledBoard[i][j] = model.board[i][j];
            }

        }

        model.removeKDigits();


        for (int i = 0; i<9; i++)
        {
            for (int j = 0; j<9; j++)
                System.out.print(filledBoard[i][j] + " ");
            System.out.println();
        }
        System.out.println();

        view.buildSudokuPanel(model.board);*/



    }
}
