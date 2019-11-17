package tugaspuzzleastar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import javafx.util.converter.ByteStringConverter;
import javax.swing.Timer;

/**
 * Kelas PuzzleControl
 * Berisi semua metode yang memanipulasi perubahan/pergerakan Puzzle
 */
public class PuzzleControl{
    
    public static enum MOVES{UP, DOWN, RIGHT, LEFT};
    public static enum SPEED{SLOW, MEDIUM, FAST};
    private int timerSpeed = 500;
    
    //Inisialisasi Array untuk GOAL State
    public static final byte[] GOAL = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    
    //Inisialisai Array untuk Current/Start State
    private byte[] current = {7, 2, 4, 5, 0, 6, 8, 3, 1};
    
    private boolean solving = false;
    
    //Method untuk memberikan nilai balik solving True atau False
    public boolean isSolving(){
        return this.solving;
    }
    
    //Method GETTER untuk mengambil nilai current
    public byte[] getCurrentBoard(){
        return current.clone();
    }
    
    //Method SETTER untuk mengatur nilai current sesuai parameter
    public void setCurrentBoard(byte[] b){
        this.current = b;
    }
    
    //Method ini digunakan untuk mengatur menu Kecepatan Algoritme A* dilakukan
    public void setTimerSpeed(SPEED speed){
        switch(speed){
            case SLOW:
                this.timerSpeed = 700;
                break;
            case MEDIUM:
                this.timerSpeed = 300;
                break;
            case FAST:
                this.timerSpeed = 100;
                break;
        }
    }
    
    //Method untuk menangani perpindahan Ubin/Button nilai Puzzle
    //mencari tahu posisi ubin relatif terhadap kosong dan bergerak sesuai perpindahan hasil Algoritme A*
    public void tilePressed(int btn){
        int blank = getBlankIndex(current);
        if(btn == blank-1){
            move(current, MOVES.LEFT);
        }else if(btn == blank+1){
            move(current, MOVES.RIGHT);
        }else if(btn == blank+3){
            move(current, MOVES.DOWN);
        }else if(btn == blank-3){
            move(current, MOVES.UP);
        }
    }
    
    //make a move on the given board, changes the given board
    //if the move is invalid, do nothing
    //Note that the move is according to the blank, i.e. the blank moves UP or DOWN and so on
    public static void move(byte[] board, MOVES toMove){
        int blank = getBlankIndex(board);
        if(blank == -1) return;  //impossible, but just to be sure
        switch(toMove){
            case UP:
                if(blank/3 != 0) swap(board, blank, blank-3);
                break;
            case DOWN:
                if(blank/3 != 2) swap(board, blank, blank+3);
                break;
            case RIGHT:
                if(blank%3 != 2) swap(board, blank, blank+1);
                break;
            case LEFT:
                if(blank%3 != 0) swap(board, blank, blank-1);
                break;
        }
    }
    
    public boolean isSolved(){
        return Arrays.equals(this.current, this.GOAL);
    }
    
    //Mengatur ulang Papan Puzzle
    public void resetPuzzle(){
//        for(int i = 0 ; i < current.length-1 ; ++i) current[i] = (byte)(i+1);
        current[0] = (byte)(7);
        current[1] = (byte)(2);
        current[2] = (byte)(4);
        current[3] = (byte)(5);
        current[4] = (byte)(0);
        current[5] = (byte)(6);
        current[6] = (byte)(8);
        current[7] = (byte)(3);
        current[8] = (byte)(1);

//        current[current.length - 1] = 0;
    }
    
    //generates a random, solvable board and makes it the current board
    public void randomizeBoard(){
        byte board[];
        while(!isSolvable(board = getRandomBoard()));
        current = board;
    }
    
    //makes a state at random, not necessarly solvable
    private byte[] getRandomBoard(){
        boolean f[] = new boolean[current.length];
        byte board[] = new byte[current.length];
        Random rand = new Random();
        
        //randomizes each element and make sure no element is repeated
        for(int i = 0 ; i < current.length ; ++i){
            byte t;
            while(f[t = (byte)rand.nextInt(9)]);
            f[t] = true;
            board[i] = t;
        }
        return board;
    }
    
    //checks if the given state is solvable or not
    private boolean isSolvable(byte board[]){
        //inversion counter
        int inv = 0;
        for(int i = 0 ; i < board.length ; ++i){
            if(board[i] == 0) continue;
            for(int j = i+1 ; j < board.length ; ++j){
                //wrong precedence, count an inversion
                //salah didahulukan, hitung inversi
                if(board[j] != 0 && board[i] > board[j]){
                    ++inv;
                }
            }
        }
        
        //the board is solvable if the number of inversions is even
        //papan dipecahkan jika jumlah inversinya genap
        return (inv % 2 == 0);
    }
    
    //mengembalikan indeks elemen kosong di papan yang diberikan, -1 jika tidak ditemukan (case yang mustahil)
    public static int getBlankIndex(byte[] board){
        for(int i = 0 ; i < board.length ; ++i) if(board[i] == 0) return i;
        return -1;
    }

    //menukar 2 elemen di papan yang diberikan
    //jika swap tidak memungkinkan (indeks di luar kisaran), jangan lakukan apa pun
    public static void swap(byte[] board, int i, int j){
        try{
            byte iv = board[i];
            byte jv = board[j];
            board[i] = jv;
            board[j] = iv;
        }catch(ArrayIndexOutOfBoundsException ex){
            //if i or j is out of range, do nothing
        }
    }
    
    //Digunakan untuk Debugging Test
    public static void print(byte[] b, Stack<byte[]> stk){
        for(int i = 0 ; i < b.length ; ++i) {
                System.out.print(b[i] + " ");
        }
        
        System.out.println("");
        System.out.println("ll");
        System.out.println("ll");
        System.out.println("V");
    }
    
    public void solve(GUI gui, Solvers.SOLVE_METHOD method){
        System.out.println("Puzzle Start State = "+Arrays.toString(current)+" ");
        Map<String, byte[]> parent = null;

        this.solving = true;
        
        long time = System.nanoTime();
        switch(method){
            case A_STAR:
                parent = Solvers.aStar(getCurrentBoard().clone());
                break;
        }
        
        time = (System.nanoTime() - time) / 1000000;
        
        //gunakan teknik backtracking seperti untuk mendapatkan gerakan yang harus dilakukan
        //status solusi (tidak bergerak) disimpan ke dalam tumpukan untuk dieksekusi
        Stack<byte[]> nextBoard = new Stack<>();
        nextBoard.add(GOAL.clone());
        while(!Arrays.equals(nextBoard.peek(), this.current)){
            nextBoard.add(parent.get(make(nextBoard.peek()))); 
//            System.out.println("H = "+parent.);
        }
        nextBoard.pop();
        
        String status = String.format("<html>%d ms<br/>%d moves<br/>%d expanded nodes</html>", time, nextBoard.size(), Solvers.times);
        gui.setStatus(status);
        
        int sizes = nextBoard.size();
        //Cetak status Waktu, Banyak langkah, dan banyak node yang didapatkan;
//        System.out.println("Time = "+time);
//        System.out.println("Steps = "+nextBoard.size());
//        System.out.println("Banyak Node = "+Solvers.times);
//        System.out.println(Arrays.toString(nextBoard.pop()));
        
        //mulai timer untuk bergerak setiap saat sampai papan diselesaikan
        new Timer(this.timerSpeed, new ActionListener(){
            private Stack<byte[]> boards;
            public PuzzleControl pc;

            //memberikan timer tumpukan dari states, gui dan pengontrol papan 
            //dan menonaktifkan seluruh GUI sampai selesai
            public ActionListener me(Stack<byte[]> stk, PuzzleControl _pc){
                this.boards = stk;
                this.pc = _pc;
                return this;
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //jika tumpukan kosong, tutup aktifkan GUI dan hentikan timer
                if(boards.empty() || isSolved()){
                    PuzzleControl.this.solving = false;
                    ((Timer)e.getSource()).stop();
                    return;
                }
                
                //atur papan saat ini ke status yang diberikan dan perbarui GUI
                pc.setCurrentBoard(boards.pop());
//                System.out.println("Step= "+Arrays.toString(bc.getCurrentBoard()));
//                bc.print(bc.getCurrentBoard(), boards);
                gui.drawBoardPuzzle();
                int steps = Math.abs(boards.size()-sizes);
                System.out.println("Step "+steps+" = "+Arrays.toString(pc.getCurrentBoard()));
            }
        }.me(nextBoard, this)).start();    //start the timer right away
    }
    
    // mengambil array byte dan membuatnya menjadi string dan mengembalikan string
    // digunakan untuk hashing, JANGAN PERNAH MENGHANCURKAN ARRAY DI JAVA   
    private String make(byte[] arr){
        String str = "";
        for(int i = 0 ; i < arr.length ; ++i){
            str += String.valueOf(arr[i]);
        }
        return str;
    }
    
}
