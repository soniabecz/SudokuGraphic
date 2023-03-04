import javax.sound.sampled.*;
import java.io.*;

public class SudokuController {

    SudokuModel model;
    SudokuView view;

    public SudokuController(SudokuModel model, SudokuView view) {
        this.model = model;
        this.view = view;
    }

    public void buttons(int[][] board) {
        view.easy.addActionListener(easy -> {
            view.nick=view.textBox.getText();
            try {
                clickedDifficultyButton(9,10,1,board);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        view.medium.addActionListener(medium -> {
            try {
                view.nick=view.textBox.getText();
                clickedDifficultyButton(9,20,2,board);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        view.hard.addActionListener(hard -> {
            try {
                view.nick=view.textBox.getText();
                clickedDifficultyButton(9,30,3,board);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void play (int N, int K, int board[][]) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {

        model.N = N;
        model.K = K;

        model.fillValues();

        int [] filledBoard[] = new int[N][N];

        for (int i = 0; i<N; i++)
        {
            for (int j = 0; j<N; j++) {
                filledBoard[i][j] = board[i][j];
            }

        }

        model.removeKDigits();
        view.printSudoku(board);


        for (int i = 0; i<N; i++)
        {
            for (int j = 0; j<N; j++)
                System.out.print(filledBoard[i][j] + " ");
            System.out.println();
        }
        System.out.println();

        view.navigation(board, filledBoard, model.mode, model.correctAnswers, model.incorrectAnswers, model.marks, model.hintsUsed);
    }

    public void clickedDifficultyButton(int N, int K, int diff, int board[][]) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        view.N = N;
        view.K = K;
        view.difficulty = diff;
        System.out.println(view.nick);
        play(N,K, model.board);
    }
}
