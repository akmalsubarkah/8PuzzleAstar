package tugaspuzzleastar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

/**
 * Kelas ini berisi implementasi metode yang diperlukan untuk menyelesaikan keadaan saat ini
 * metode A* yang diterapkan disini mengembalikan hash Map yang berisi status keadaan induk/parent
 * masing-masing keadaan Puzzle sedang berjalan untuk memecahkan keadaan saat ini
 */
public class Solvers {
    
    public static enum SOLVE_METHOD{A_STAR};
    
    //untuk menghitung jumlah node yang diperluas
    public static long times;
    
    //solve the current position with A* search
    public static Map<String, byte[]> aStar(byte[] current){
        PriorityQueue<State> q = new PriorityQueue<>();
        Map<String, Integer> dist = new HashMap<>();
        Map<String, byte[]> parent = new HashMap<>();
        State stt = new State(current, 0);
        
        times = 0;
        
        //inisialisasi jarak dari kondisi saat ini menjadi 0
        dist.put(stringify(current), 0);

        //tambahkan status saat ini ke bagian depan antrian status
//        q.add(new State(startState, 0));
        q.add(stt);
        
        //Algoritme A*
        while(!q.isEmpty()){
            State crnt = q.poll();

            times++;
            if(Arrays.equals(crnt.getBoard(), PuzzleControl.GOAL)) break;            
            for(State child : crnt.getNextStates()){
//                System.out.println("Child GetBoard = "+stringify(child.getBoard()));
//                System.out.println("Child GetCost = "+child.getCost());
//                System.out.println("Child Heuristic = "+child.hurestic());
//                System.out.println("Child GetWeight (Cost + H) = "+child.getWeight());
                if(dist.getOrDefault(stringify(child.getBoard()), Integer.MAX_VALUE) > child.getCost()){                    
                    parent.put(stringify(child.getBoard()), crnt.getBoard());
                    dist.put(stringify(child.getBoard()), child.getCost());
                    q.add(child);
                }               
            }
        }
        return parent;
    }
    
    //mengambil array byte dan mengembalikannya sebagai string untuk hash peta
    //jangan pernah menggunakan array hash di java, karena hampir selalu mengembalikan nilai hash yang berbeda
    public static String stringify(byte[] arr){
        String str = "";
        for(int i = 0 ; i < arr.length ; ++i){
            str += String.valueOf(arr[i]);
        }
        return str;
    }
    
}
