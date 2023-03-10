

public class SudokuModel {
    int[] board[];

    int[] marks[][];
    int N;
    int SRN;
    int K;

    int correctAnswers;
    int incorrectAnswers;
    int hintsUsed;

    int mode;

    SudokuModel(int N, int K)
    {
        this.N = N;
        this.K = K;

        Double SRNd = Math.sqrt(N);
        SRN = SRNd.intValue();

        board = new int[N][N];

        marks = new int[19][36][SRN];

        correctAnswers = 0;
        incorrectAnswers = 0;
        hintsUsed = 0;

        mode = 1;

    }

    public void fillValues()
    {
        fillDiagonal();

        fillRemaining(0, SRN);

    }

    void fillDiagonal()
    {

        for (int i = 0; i<N; i=i+SRN)
            fillBox(i, i);
    }


    boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i<SRN; i++)
            for (int j = 0; j<SRN; j++)
                if (board[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }

    void fillBox(int row,int col)
    {
        int num;
        for (int i=0; i<SRN; i++)
        {
            for (int j=0; j<SRN; j++)
            {
                do
                {
                    num = randomGenerator(N);
                }
                while (!unUsedInBox(row, col, num));

                board[row+i][col+j] = num;
            }
        }
    }

    int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }

    boolean CheckIfSafe(int i,int j,int num)
    {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%SRN, j-j%SRN, num));
    }

    boolean unUsedInRow(int i,int num)
    {
        for (int j = 0; j<N; j++)
            if (board[i][j] == num)
                return false;
        return true;
    }

    boolean unUsedInCol(int j,int num)
    {
        for (int i = 0; i<N; i++)
            if (board[i][j] == num)
                return false;
        return true;
    }

    boolean fillRemaining(int i, int j)
    {
        if (j>=N && i<N-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=N && j>=N)
            return true;

        if (i < SRN)
        {
            if (j < SRN)
                j = SRN;
        }
        else if (i < N-SRN)
        {
            if (j==(int)(i/SRN)*SRN)
                j =  j + SRN;
        }
        else
        {
            if (j == N-SRN)
            {
                i = i + 1;
                j = 0;
                if (i>=N)
                    return true;
            }
        }

        for (int num = 1; num<=N; num++)
        {
            if (CheckIfSafe(i, j, num))
            {
                board[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                board[i][j] = 0;
            }
        }
        return false;
    }

    public void removeKDigits()
    {
        int count = K;
        while (count != 0)
        {
            int cellId = randomGenerator(N*N)-1;

            int i = (cellId/N);
            int j = cellId%9;
            if (j != 0)
                j = j - 1;

            if (board[i][j] != 0)
            {
                count--;
                board[i][j] = 0;
            }
        }
    }
}
