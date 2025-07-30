import java.util.Scanner;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

class Main{
     void menu(char board[][]){
         System.out.println("+-----------------+");
         System.out.println("|  TIC  TAC  TOE  |");
         System.out.println("+-----------------+\n");
         //display menu
         System.out.println("    "+board[0][0]+"  |  "+board[0][1]+"  |  "+board[0][2]+" ");
         System.out.println("  -----------------");
         System.out.println("    "+board[1][0]+"  |  "+board[1][1]+"  |  "+board[1][2]+" ");
         System.out.println("  -----------------");
         System.out.println("    "+board[2][0]+"  |  "+board[2][1]+"  |  "+board[2][2]+" \n");
     }
     public static void main(String[] args) {
     Main m = new Main();
     char[][] board = new char[3][3];
     for(int i=0; i<3; i++){
         for(int j=0; j<3; j++){
             board[i][j] = '-';
         }
     }
     m.menu(board);
 
    //Create a Scanner for asking the players for their names
    Scanner in = new Scanner(System.in);
    System.out.print("What is your name? player 1: ");
    String p1 = in.nextLine();
    System.out.print("What is your name? player 2: ");
    String p2 = in.nextLine();
    int p1_score = 0;
    int p2_score = 0;
    //Create a player1 boolean that is true if it is player 1's turn and false if it is player 2's turn
    boolean player1 = true;
    char opt='y';
    //Create a gameEnded boolean and use it as the condition in the while loop
    boolean gameEnded = true;
    
    while(gameEnded){
        if(player1)
        System.out.println("\n"+p1 + "'s Turn(X): ");
        else
        System.out.println(p2 + "'s Turn(O): ");
     
        char c = '-';
        if(player1)
        c = 'X';
        else
        c = 'O';
        int row = 0;
        int col = 0;
 
     
        while(true){
            System.out.print("Enter a row number: ");
            row = in.nextInt();
            System.out.print("Enter a column number: ");
            col = in.nextInt();
        
            //Check if the row and col are outside of the board
            if(row < 0 || col < 0 || row >= 3 || col >= 3)
            System.out.println("This position is off the bounds of the board!");
            else if(board[row][col] != '-')
            System.out.println("Someone has already made a move at this position!");
            else 
            break;
        }
 
        board[row][col] = c;
 
        //Check to see if either player has won
        if(playerHasWon(board) == 'X') {
        System.out.println("\n---------------------\n"+p1 + " has won!\n---------------------");
        p1_score++;
        playwinSound("winSound.wav");
        System.out.println("Scores are: \n"+p1+" = "+p1_score+"\n"+p2+" = "+p2_score);
        drawBoard(board);
        System.out.println("\nDO YOU WANT TO PLAY AGAIN?(y/n): ");
        opt = in.next().charAt(0);
        if(opt=='n' || opt=='N')
            System.exit(0);
        else{
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++)
                    board[i][j] = '-';
            }
        }
        }
        else if(playerHasWon(board) == 'O') {
        System.out.println("\n--------------------\n"+p2 + " has won!\n---------------------");
        p2_score++;
        playwinSound("winSound.wav");
        System.out.println("Scores are: \n"+p1+" = "+p1_score+"\n"+p2+" = "+p2_score);
        drawBoard(board);
        System.out.println("\nDO YOU WANT TO PLAY AGAIN?(y/n): ");
        opt = in.next().charAt(0);
        if(opt=='n' || opt=='N')
            System.exit(0);
        else{
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++)
                    board[i][j] = '-';
            }
        }
        }
        else{
            if(boardIsFull(board)){
            System.out.println("\n---------------------\nIt's a tie!\n---------------------");
            playwinSound("winSound.wav");
            System.out.println("Scores are: \n"+p1+" = "+p1_score+"\n"+p2+" = "+p2_score);
            drawBoard(board);
            System.out.println("\nDO YOU WANT TO PLAY AGAIN?(y/n): ");
            opt = in.next().charAt(0);
            if(opt=='n' || opt=='N')
                System.exit(0);
            else{
                for(int i=0; i<3; i++){
                    for(int j=0; j<3; j++)
                        board[i][j] = '-';
                }
            }
            } 
            else
            player1 = !player1;
        }
        drawBoard(board);
    }

    drawBoard(board);
 }
 public static void playwinSound(String filePath) {
    try {
        File soundFile = new File(filePath);
        if(soundFile.exists()){
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        else{
            System.out.print("Can't open");
        }
    }
    catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){}
}
 public static void playSound(String filePath) {
    try {
        File soundFile = new File(filePath);
        if(soundFile.exists()){
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        else{
            System.out.print("Can't open");
        }
    }
    catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){}   
}
 //displaying board
 static void drawBoard(char[][] board){
    playSound("game.wav");
    
    System.out.println("\n    "+board[0][0]+"  |  "+board[0][1]+"  |  "+board[0][2]+" ");
    System.out.println("  -----------------");
    System.out.println("    "+board[1][0]+"  |  "+board[1][1]+"  |  "+board[1][2]+" ");
    System.out.println("  -----------------");
    System.out.println("    "+board[2][0]+"  |  "+board[2][1]+"  |  "+board[2][2]+" \n");
 }
 
 //Make a function to see if someone has won and return the winning char
public static char playerHasWon(char[][] board) {
    for(int i = 0; i < board.length; i++) {
    boolean inARow = true;
    char value = board[i][0];

    if(value == '-')
        inARow = false;
    else{
        for(int j = 1; j < board[i].length; j++) {
            if(board[i][j] != value) {
            inARow = false;
            break;
            }
        }
    }
    if(inARow)
    return value;
    
    }
    //column
    for(int j = 0; j < board[0].length; j++) {
    boolean inACol = true;
    char value = board[0][j];

    if(value == '-')
    inACol = false;
    else{
        for(int i = 1; i < board.length; i++) {
            if(board[i][j] != value) {
            inACol = false;
            break;
            }
        }
    }
    if(inACol)
    return value;

    }
    boolean inADiag1 = true;
    char value1 = board[0][0];
    if(value1 == '-') {
    inADiag1 = false;
    }
    else{
    for(int i = 1; i < board.length; i++) {
        if(board[i][i] != value1) {
        inADiag1 = false;
        break;
        }
    }
    }
 
    if(inADiag1) {
    return value1;
    }
 
    // Check the diagonal going from upper right to bottom left: [0][n-1], [1][n-2], [2][n-3]...
    boolean inADiag2 = true;
    char value2 = board[0][board.length-1];
    
    if(value2 == '-') {
    inADiag2 = false;
    }
    else {
        for(int i = 1; i < board.length; i++) {
            if(board[i][board.length-1-i] != value2) {
            inADiag2 = false;
            break;
            }
        }
    }
    if(inADiag2) {
    return value2;
    }
    return ' ';
    }

    public static boolean boardIsFull(char[][] board) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == '-')
                return false;
            }
        }
        return true;
    }
}