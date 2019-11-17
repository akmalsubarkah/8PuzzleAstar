package tugaspuzzleastar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Tampilan GUI dari Puzzle dan Semua yang memanipulasinya;
 * (Kecuali Timer yang ada di Kelas PuzzleControl)
 */
public class GUI extends javax.swing.JFrame {
    
    public final PuzzleControl puzzleControl;
    private final JButton tiles[];
    
    /**
     * Membuat form GUI baru
     */
    public GUI() {

        //frame Title
        super("Tugas 8 Puzzle - Firnanda Akmal Subarkah");
        
        //mengatur GUI
        initComponents();
        this.setLocationRelativeTo(null);   //pusatkan frame saat layar terbuka
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        
        //Inisialisasi Variabel-variabel Global
        this.tiles = new JButton[]{Tile_1, Tile_2, Tile_3, Tile_4, Tile_5, Tile_6, Tile_7, Tile_8, Tile_0};
        this.puzzleControl = new PuzzleControl();
        
        //inisialisasi ubin/cutton Puzzle, atur font, nonaktifkan fokus dan tambahkan tindakan/aksi
        for(int i = 0 ; i < tiles.length ; ++i){
            
            tiles[i].setFocusable(false);
            tiles[i].setFont(tiles[i].getFont().deriveFont(25.0f));
            
            tiles[i].addActionListener(new ActionListener() {
                
                int num;
                
                ActionListener me(int i){
                    num = i;
                    return this;
                }
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(GUI.this.puzzleControl.isSolving()) return;
                    GUI.this.puzzleControl.tilePressed(num);
                    GUI.this.drawBoardPuzzle();
                }
            }.me(i));
        }
        
        //Aksi untuk button/tombol Reset
        Button_Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.this.puzzleControl.resetPuzzle();
                GUI.this.drawBoardPuzzle();
            }
        });
        
        //Aksi untuk button/tombol Randomize
        Button_Rand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GUI.this.puzzleControl.isSolving()) return;
                GUI.this.puzzleControl.randomizeBoard();
                GUI.this.drawBoardPuzzle();
            }
        });

        //Aksi untuk button/tombol Solve - A*
        Button_Solve_A.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GUI.this.puzzleControl.isSolving()) return;
                GUI.this.puzzleControl.solve(GUI.this, Solvers.SOLVE_METHOD.A_STAR);
                GUI.this.pack();
            }
        });
        
        //Aksi untuk button/tombol Speed = (Slow, Medium, atau Fast)
        Button_Speed.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(GUI.this.puzzleControl.isSolving()) return;
                String crnt = ((JButton)e.getSource()).getText();
                switch(crnt){
                    case "Slow":
                        GUI.this.puzzleControl.setTimerSpeed(PuzzleControl.SPEED.MEDIUM);
                        GUI.this.Button_Speed.setText("Medium");
                        break;
                    case "Medium":
                        GUI.this.puzzleControl.setTimerSpeed(PuzzleControl.SPEED.FAST);
                        GUI.this.Button_Speed.setText("Fast");
                        break;
                    case "Fast":
                        GUI.this.puzzleControl.setTimerSpeed(PuzzleControl.SPEED.SLOW);
                        GUI.this.Button_Speed.setText("Slow");
                        break;
                }
            }
            
        });
        
        this.drawBoardPuzzle();
        this.pack(); //mengubah ukuran frame agar sesuai dengan ukuran tombol yang baru
    }
    
    //Method untuk mengatur gambar Puzzle saat ini di nilai ubin/button Puzzle
    public final void drawBoardPuzzle(){
        final byte[] board = puzzleControl.getCurrentBoard();
        int empty = -1;
        
        //Perulangan untuk memberi label/nilai pada ubin/button Puzzle
        for(int i = 0 ; i < board.length ; ++i){
            if(board[i] == 0) empty = i;
            else tiles[i].setText(String.valueOf(board[i]));
        }
        
        //memperlihatkan semua tombol/ubin Puzzle dan sembunyikan yang bernilai kosong
        for(JButton tile : tiles) tile.setVisible(true);
        tiles[empty].setVisible(false);

        //memberi notifikasi ke panel untuk memperbarui
        Main_Middle.repaint();
        Main_Middle.revalidate();
    }
    
    public void setStatus(String stat){
        this.Label_Status.setText(stat);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        Main_Middle = new javax.swing.JPanel();
        Tile_1 = new javax.swing.JButton();
        Tile_2 = new javax.swing.JButton();
        Tile_3 = new javax.swing.JButton();
        Tile_4 = new javax.swing.JButton();
        Tile_5 = new javax.swing.JButton();
        Tile_6 = new javax.swing.JButton();
        Tile_7 = new javax.swing.JButton();
        Tile_8 = new javax.swing.JButton();
        Tile_0 = new javax.swing.JButton();
        Main_Right = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Label_Status = new javax.swing.JLabel();
        ButtonsPanel = new javax.swing.JPanel();
        Button_Rand = new javax.swing.JButton();
        Button_Reset = new javax.swing.JButton();
        Button_Solve_A = new javax.swing.JButton();
        Button_Speed = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Main_Middle.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Main_Middle.setMaximumSize(new java.awt.Dimension(2147483647, 0));
        Main_Middle.setLayout(new java.awt.GridBagLayout());

        Tile_1.setText("1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 50;
        Main_Middle.add(Tile_1, gridBagConstraints);

        Tile_2.setText("2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 50;
        Main_Middle.add(Tile_2, gridBagConstraints);

        Tile_3.setText("3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 50;
        Main_Middle.add(Tile_3, gridBagConstraints);

        Tile_4.setText("4");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 50;
        Main_Middle.add(Tile_4, gridBagConstraints);

        Tile_5.setText("5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 50;
        Main_Middle.add(Tile_5, gridBagConstraints);

        Tile_6.setText("6");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 50;
        Main_Middle.add(Tile_6, gridBagConstraints);

        Tile_7.setText("7");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 50;
        Main_Middle.add(Tile_7, gridBagConstraints);

        Tile_8.setText("8");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 50;
        Main_Middle.add(Tile_8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 50;
        Main_Middle.add(Tile_0, gridBagConstraints);

        getContentPane().add(Main_Middle, java.awt.BorderLayout.CENTER);

        Main_Right.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        Main_Right.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 1, 1));
        jPanel2.setOpaque(false);
        jPanel2.add(Label_Status);

        Main_Right.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(Main_Right, java.awt.BorderLayout.PAGE_END);

        ButtonsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ButtonsPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ButtonsPanel.setLayout(new java.awt.GridBagLayout());

        Button_Rand.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        Button_Rand.setText("Randomize");
        Button_Rand.setFocusable(false);
        ButtonsPanel.add(Button_Rand, new java.awt.GridBagConstraints());

        Button_Reset.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        Button_Reset.setText("Reset");
        Button_Reset.setFocusable(false);
        ButtonsPanel.add(Button_Reset, new java.awt.GridBagConstraints());

        Button_Solve_A.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        Button_Solve_A.setText("Solve - A*");
        Button_Solve_A.setFocusable(false);
        ButtonsPanel.add(Button_Solve_A, new java.awt.GridBagConstraints());

        Button_Speed.setText("Slow");
        Button_Speed.setFocusable(false);
        ButtonsPanel.add(Button_Speed, new java.awt.GridBagConstraints());

        getContentPane().add(ButtonsPanel, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_Rand;
    private javax.swing.JButton Button_Reset;
    private javax.swing.JButton Button_Solve_A;
    private javax.swing.JButton Button_Speed;
    private javax.swing.JPanel ButtonsPanel;
    private javax.swing.JLabel Label_Status;
    public javax.swing.JPanel Main_Middle;
    private javax.swing.JPanel Main_Right;
    private javax.swing.JButton Tile_0;
    private javax.swing.JButton Tile_1;
    private javax.swing.JButton Tile_2;
    private javax.swing.JButton Tile_3;
    private javax.swing.JButton Tile_4;
    private javax.swing.JButton Tile_5;
    private javax.swing.JButton Tile_6;
    private javax.swing.JButton Tile_7;
    private javax.swing.JButton Tile_8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
