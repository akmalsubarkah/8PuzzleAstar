package tugaspuzzleastar;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Menjelaskan status papan Puzzle, digunakan hanya dalam pencarian A*
 * mengimplementasikan pembanding yang membandingkan total weight = f(n) dari status (cost + hurestic)
 */
public class State implements Comparable<State>{
    
    private final byte board[];     //Nilai dari papan Puzzle
    private final int cost;         //biaya/cost yang dibutuhkan untuk mencapai keadaan saat ini- g(n)
    private final int weight;       //Biaya Evaluasi dari keadaan- f(n)
    
    //Konstruktor kelas State
    public State(byte b[], int _cost){
        this.board = b; //Menginisialisasi nilai papan Puzzle berdasarkan parameter b 
        cost = _cost; //Menginisialisasi nilai cost berdasarkan parameter _cost
        weight = cost + hurestic(); //Menginisialisasi nilai Weight (Biaya Evaluasi)-f(n) dari penjumlahan nilai cost dan heuristic
    }
    
    //Method GETTER untuk mengembalikan nilai dari papan Puzzle
    public byte[] getBoard(){
        return this.board;
    }
    
    //Method GETTER untuk mengembalikan biaya/cost yang dibutuhkan untuk mencapai keadaan saat ini- g(n)
    public int getCost(){
        return this.cost;
    }

    //Method GETTER untuk mengembalikan Biaya Evaluasi dari keadaan- f(n)
    public int getWeight() {
        return weight;
    }
    
    //Method yang digunakan untuk menghitung nilai heuristik papan Puzzle menggunakan jarak manhattan- h(n)
    public int hurestic(){
        int h = 0;
        for(int i = 0 ; i < board.length ; ++i){
            if(board[i] == 0) continue;
            int dr = Math.abs(i/3 - (board[i]-1)/3);
            int dc = Math.abs(i%3 - (board[i]-1)%3);
            h += dr + dc;
        }
        return h;
    }
    
    //Method ini untuk menghasilkan dan mengembalikan semua status yang mungkin dari status saat ini
    public ArrayList<State> getNextStates(){
        ArrayList<State> states = new ArrayList<>();

        //perulangan untuk mencoba setiap gerakan, jika suatu gerakan mengubah papan, maka itu adalah langkah yang valid
        for(PuzzleControl.MOVES move : PuzzleControl.MOVES.values()){
            byte newBoard[] = this.board.clone();
            PuzzleControl.move(newBoard, move);
            if(!Arrays.equals(this.board, newBoard)){
                states.add(new State(newBoard, this.cost + 1));
            }
        }
        return states;
    }

    //Method Override dari implementasi kelas Interface Comparable<T extends Object>
    //membandingkan antara setiap State Puzzle dengan menggunakan nilai total Weight (Biaya Evaluasi)- f(n)
    @Override
    public int compareTo(State o) {
        return this.weight - o.weight;
    }
    
}
