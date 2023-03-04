import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;


public class Sudoku {

    public static void main(String[] args) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        int N = 9, K = 0;


        SudokuModel model = new SudokuModel(N,K);
        SudokuView view = new SudokuView(N,K);

        SudokuController sudoku = new SudokuController(model,view);

        sudoku.buttons(model.board);
        view.welcomeWindow();

    }
}
