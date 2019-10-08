/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project4;

import java.util.*;
import java.util.TreeSet;

public class maze {

    private static final int right = 0;
    private static final int down = 1;
    private static final int left = 2;
    private static final int up = 3;
    private static Random randomGenerator;  // for random numbers
    // A board is an SizexSize array whose values are Points                                                                                                           
    public static Point[][] board;
    // A graph is simply a set of edges: graph[i][d] is the edge 
    // where i is the index for a Point and d is the direction 
    public static Edge[][] graph;
    public static int N;   // number of points in the graph
    public static int Size;    
    
    public static class Point {  // a Point is a position in the maze

        public int x, y;
        
        // Constructor
        public Point(int x, int y) {
            this.x = x;
	       this.y = y;
        }

        public void copy(Point p) {
            this.x = p.x;
            this.y = p.y;
        }
    }
    
    public static class Edge { 
	   // an Edge links two neighboring Points: 
	   // For the grid graph, an edge can be represented by a point and a direction.
	   Point point;
	   int direction;    // one of right, down, left, up
	   boolean used;     // for maze creation
	   boolean deleted;  // for maze creation
	
	   // Constructor
	   public Edge(Point p, int d) {
           this.point = p;
	       this.direction = d;
	       this.used = false;
	       this.deleted = false;
       }
    }

    public static class disjointSetByRank {
    private int[] linkArray;
    private int[] rankArray;
    private int sizeOfMaze;
    public disjointSetByRank(int nElements){
        linkArray = new int[nElements];
        rankArray = new int[nElements];
        sizeOfMaze = nElements;
        //System.out.println(sizeOfMaze);
        for (int i = 0; i < linkArray.length; i++){
            linkArray[i] = -1;
            rankArray[i] = 1;
        }
    }
    
    public int find(int element){
        int parentNode,childNode;        
        childNode = -1;
        
        //Find the root of the tree. Along the way, set the parents' links to the children.
        while (linkArray[element] != -1){
            parentNode = linkArray[element];
            linkArray[element] = childNode;
            childNode = element;
            element = parentNode;
        }
        //Travel back to the starting element, setting all links to the root.
        parentNode = element;
        element = childNode;
        while (element != -1){
            childNode = linkArray[element];
            linkArray[element] = parentNode;
            element = childNode;
        }
        return parentNode;
    }
    public int union(int setOne, int setTwo){
        int parentNode,childNode;
        

        if (rankArray[setOne] > rankArray[setTwo]){
            parentNode = setOne;
            childNode = setTwo;
        }
        else{
            parentNode = setTwo;
            childNode = setOne;
        }
        
        linkArray[childNode] = parentNode;
        if (rankArray[setOne] == rankArray[setTwo]){
            rankArray[parentNode]++;
        }
        
        return parentNode;
    }
    public boolean allInOne(){
        //this.printArrays();
        //System.out.println("-----");
        int counter = 0;
        for (int i = 0; i < linkArray.length; i++){
            if (linkArray[i] == -1){
                counter++;
            }
        }
        
        if (counter > 1)
        {return false;}
        else
        {return true;}
    }
    public void printArrays(){
        System.out.println(Arrays.toString(linkArray));
        System.out.println(Arrays.toString(rankArray));
    }
    ////////////////////////////////////////////////////////////////
}
    
    public static class playerObj {

    private char[][] playerMoves;
    private int indexAt;
    public playerObj(int Size)
    {   
        playerMoves = new char[Size][Size];
                for (int i = 0; i < Size; i++){
                    for (int j = 0; j < Size; j++){
                        playerMoves[i][j] = '.';
                    }
                }
                indexAt = 0;
    }

    public void playerMovementStart(int i, int j)
    {
        
        int pindex = (i*Size) + j;
         System.out.println("-------------------------");
        ///Printing the last move
        for (int k = 0; k < Size; k++) {
            for (int l = 0; l < Size; l++) {
                System.out.print(playerMoves[k][l] + " ");
            }
            System.out.println();
        }
        
        
        //Moving the player one space left
        //System.out.println("I,J = " + i + " " + j);
        if ((j > 0) && (graph[indexAt][up].deleted == true) && (playerMoves[i][j-1] == ('.')))
        {
            System.out.println("IndexAt: " + indexAt + " indexMovingTo: " + (indexAt) + "goingUp");
            playerMoves[i][j] = 'X';
            playerMovement(i, j-1);
        }
        //Moving the player one space right
        else if ((j < Size-1) && (graph[indexAt][down].deleted == true) && (playerMoves[i][j+1] == ('.')))
        {
            System.out.println("IndexAt: " + indexAt + " indexMovingTo: " + (indexAt) + "goingDown");
            playerMoves[i][j] = 'X';
            playerMovement(i, j+1);
        }
        //Moving the player one space up
        else if ((i > 0) && (graph[indexAt][left].deleted == true) && (playerMoves[i-1][j] == ('.')))
        {
            System.out.println("IndexAt: " + indexAt + " indexMovingTo: " + (indexAt) + "goingleft");
            playerMoves[i][j] = 'X';
            playerMovement(i-1, j);
        }
        //Moving the player one space down
        else if ((i < Size-1) && (graph[indexAt][right].deleted == true) && (playerMoves[i+1][j] == ('.')))
        {
            System.out.println("IndexAt: " + indexAt + " indexMovingTo: " + (indexAt) + "goingRight");
            playerMoves[i][j] = 'X';
            playerMovement(i+1, j);
        }
        else if ((i == Size-1) && (j == Size-1))//(i == 0 || j == 0 || i == Size-1 || j == Size-1)
        {
            playerMoves[i][j] = 'F';
        }
        else
        {
            playerMoves[i][j] = 'O';
            playerBackTrack(i,j);
        }

    }
     public void playerMovement(int i, int j)
    {
        
        int pindex = (i*Size) + j;
         System.out.println("-------------------------");
        ///Printing the last move
        for (int k = 0; k < Size; k++) {
            for (int l = 0; l < Size; l++) {
                System.out.print(playerMoves[k][l] + " ");
            }
            System.out.println();
        }
        
        
        //Moving the player one space left
        //System.out.println("I,J = " + i + " " + j);
        if ((j > 0) && (graph[indexAt][up].deleted == true) && (playerMoves[i][j-1] == ('.')))
        {
            System.out.println("IndexAt: " + indexAt + " indexMovingTo: " + (indexAt-1) + "goingLeft");
            indexAt = indexAt-1;
            playerMoves[i][j] = 'X';
            playerMovement(i, j-1);
        }
        //Moving the player one space right
        else if ((j < Size-1) && (graph[indexAt][down].deleted == true) && (playerMoves[i][j+1] == ('.')))
        {
            System.out.println("IndexAt: " + indexAt + " indexMovingTo: " + (indexAt+1) + "goingRight");
            indexAt = indexAt + 1;
            playerMoves[i][j] = 'X';
            playerMovement(i, j+1);
        }
        //Moving the player one space up
        else if ((i > 0) && (graph[indexAt][left].deleted == true) && (playerMoves[i-1][j] == ('.')))
        {
            System.out.println("IndexAt: " + indexAt + " indexMovingTo: " + (indexAt-Size) + "goingUp");
            indexAt = indexAt-Size;
            playerMoves[i][j] = 'X';
            playerMovement(i-1, j);
        }
        //Moving the player one space down
        else if ((i < Size-1) && (graph[indexAt][right].deleted == true) && (playerMoves[i+1][j] == ('.')))
        {
            System.out.println("IndexAt: " + indexAt + " indexMovingTo: " + (indexAt+Size) + "goingDown");
            indexAt = indexAt+Size;
            playerMoves[i][j] = 'X';
            playerMovement(i+1, j);
        }
        else if ((i == Size-1) && (j == Size-1))//(i == 0 || j == 0 || i == Size-1 || j == Size-1)
        {
            playerMoves[i][j] = 'F';
        }
        else
        {
            playerMoves[i][j] = 'O';
            playerBackTrack(i,j);
        }

    }

    public void playerBackTrack(int i, int j)
    {
        //Moving the player back Right
        if ((j < Size-1) && playerMoves[i][j+1] == 'X')
        {
            System.out.println("IndexAt: " + indexAt + " indexMovingTo: " + (indexAt+1) + "goingRight");
            indexAt = indexAt+1;
            playerMoves[i][j] = 'O';
            playerMovement(i,j+1);
        }
        //Moving the player one space left
        else if ((j > 0) && (playerMoves[i][j-1] == ('X')))
        {
            System.out.println("IndexAt: " + indexAt + " indexMovingTo: " + (indexAt-1) + "goingleft");
            indexAt = indexAt - 1;
            playerMoves[i][j] = 'O';
            playerMovement(i, j-1);
        }
        //Moving the player one space up
        else if ((i > 0) && (playerMoves[i-1][j] == ('X')))
        {
            System.out.println("IndexAt: " + indexAt + " indexMovingTo: " + (indexAt-Size) + "goingup");
            indexAt = indexAt-Size;
            playerMoves[i][j] = 'O';
            playerMovement(i-1, j);
        }
        //Moving the player one space down
        else if ((i < Size-1) && (playerMoves[i+1][j] == ('X')))
        {
            System.out.println("IndexAt: " + indexAt + " indexMovingTo: " + (indexAt+Size) + "goingdown");
            indexAt = indexAt+Size;
            playerMoves[i][j] = 'O';
            playerMovement(i+1, j);
        }

    }
    
    public void printMoves(){
        System.out.println("Finished. Moves Are:");
        for (int i = 0; i < Size; i++)
        {
            for (int j = 0; j < Size; j++){
                char charAt = playerMoves[i][j];
                System.out.print(charAt);
            }
            System.out.println();
        }
    }


}

    public static void createEmptyMap(){
        // Create one dummy edge for all boundary edges.
	    Edge dummy = new Edge(new Point(0, 0), 0);
	    dummy.used = true;
	    	     
	    // Create board and graph.
	    board = new Point[Size][Size];
	    N = Size*Size;  // number of points
	    graph = new Edge[N][4];         
	     
	    for (int i = 0; i < Size; ++i) 
		  for (int j = 0; j < Size; ++j) {
		    Point p = new Point(i, j);
		    int pindex = i*Size+j;   // Point(i, j)'s index is i*Size + j
		     
		    board[i][j] = p;
		     
		    graph[pindex][right] = (j < Size)? new Edge(p, right): dummy;
		    graph[pindex][down] = (i < Size)? new Edge(p, down) : dummy;        
		    graph[pindex][left] = (j > 0)? graph[pindex-1][right] : dummy;         
		    graph[pindex][up] = (i > 0)? graph[pindex-Size][down] : dummy;

		}
    }
    public static void createRandomMaze(){
        createEmptyMap();
        int numbOfSquares = Size*Size;
        disjointSetByRank builtMaze = new disjointSetByRank(numbOfSquares);
        boolean allInOneTree = builtMaze.allInOne();
        randomGenerator = new Random();
        Point[] usefulEdges = collectAllEdges();
        Point[] keptEdges = new Point[usefulEdges.length];
        boolean[] alreadyChosen = new boolean[usefulEdges.length];
        //System.out.println("Length:" + usefulEdges.length);
        builtMaze.union(0,1);
        builtMaze.union(numbOfSquares-1,numbOfSquares-2);
        //builtMaze.printArrays();
        graph[0][right].deleted = true;
        graph[((Size*Size)-2)][right].deleted = true;
        while (allInOneTree == false)
        {
            int randArrayVal = randomGenerator.nextInt(usefulEdges.length);
            if (alreadyChosen[randArrayVal] == false)
            {
                Point randomEdge = usefulEdges[randArrayVal];
                alreadyChosen[randArrayVal] = true;
                int u = builtMaze.find(randomEdge.x);
                int v = builtMaze.find(randomEdge.y);
                if (u != v)
                {
                    builtMaze.union(u,v);
                    deleteEdge(randomEdge.x,randomEdge.y);
                    //System.out.println("Delete Edge Between " + randomEdge.x + " and " + randomEdge.y);
                                  
                }
                else
                {
                    keptEdges[randArrayVal] = randomEdge;
                    //System.out.println("Keep Edge Between " + randomEdge.x + " and " + randomEdge.y);
                }
            }
           allInOneTree = builtMaze.allInOne();
        }
        displayBoard();
        //builtMaze.printArrays();
 
    }
    public static void deleteEdge(int pIndex1, int pIndex2){
        if(pIndex1 == pIndex2-1)
        {
            //Shared Edge is pI1->Right, pI2->Left
            //Case 1
            graph[pIndex1][right].deleted = true;
            graph[pIndex2][left].deleted = true;
            
        }
        if (pIndex1 == pIndex2+1)
        {
            //Shared Edge is pI1->Left, pI2->Right
            //Case 2
            graph[pIndex1][left].deleted = true;
            graph[pIndex2][right].deleted = true;
        }
        if (pIndex1 == pIndex2-Size)
        {
            //Shared Edge is pI1->Down, pI2->Up
            //Case 3
            graph[pIndex1][down].deleted = true;
            graph[pIndex2][up].deleted = true;    
        }
        if (pIndex1 == pIndex2+Size)
        {
            //Shared Edge is pI1->Up, pI2->Down
            //Case 4
            graph[pIndex1][up].deleted = true;
            graph[pIndex2][down].deleted = true;
        }
    }
    public static void displayInitBoard() {
        System.out.println("\nInitial Configuration:");

        for (int i = 0; i < Size; ++i) {
            System.out.print("    -");
            for (int j = 0; j < Size; ++j) System.out.print("----");
            System.out.println();
            if (i == 0) System.out.print("Start");
            else System.out.print("    |");
            for (int j = 0; j < Size; ++j) {
                if (i == Size-1 && j == Size-1)
		    System.out.print("    End");
                else System.out.print("   |");
            }
            System.out.println();
        }
        System.out.print("    -");
        for (int j = 0; j < Size; ++j) System.out.print("----");
        System.out.println();
    }
    public static void displayBoard() {

        for (int i = 0; i < Size; ++i) {
            //Top Right Corner
            System.out.print("    -");
            //Bottom Edge for each Row
            for (int j = 0; j < Size; ++j) 
            {
                if (i ==0){
                    System.out.print("----");
                }
                else
                {
                    int pindex = (i-1)*Size + j;
                        if ((graph[pindex][down].deleted == false))
                        {System.out.print("----");}
                        else
                        {System.out.print("    ");}
                                               
                }
            }
            System.out.println();
            //Left Edge 
            if (i == 0) System.out.print("Start");
            else System.out.print("    |");
            
            for (int j = 0; j < Size; ++j) {
                //Right Edge
                if (i == Size-1 && j == Size-1)
		    System.out.print("    End");
                else if (i != Size-1 && j == Size-1)
                        System.out.print("   |");
                
                
                else 
                {
                    int pindex = i*Size + j;
                    if ((graph[pindex][right].deleted == false))
                    {System.out.print("   |");}
                    else
                    {System.out.print("    ");}
                }
            }
            System.out.println();
        }
        //Bottom Edge
        System.out.print("    -");
        for (int j = 0; j < Size; ++j) System.out.print("----");
        System.out.println();
    }
    public static Point[] collectAllEdges(){
        int newSize = 2*(Size*(Size-1)) - 2;
        Point[] usefulEdges = new Point[newSize];
        int counter = 0;
        for (int q = 0; q < Size; q++){
        int offset = q*Size;
            for (int i = 0; i < Size; i++){
                    if (i != Size-1){
                        int j = i+1;
                        boolean skipThese = skipEdges(i+offset, j+offset);
                        if (skipThese == false)
                        {
                        Point p = new Point(i+offset, j+offset);
                        usefulEdges[counter] = p;
                        counter++;
                        //System.out.println("I: " + (i+offset) + "| J = " + (j+offset));
                        }
                    }

            }   
        }
        //System.out.println("------------------------------");
        for (int w = 0; w < Size-1; w++){
        int offset = w*Size;
            for (int i = 0; i < Size; i++){
                    
                        int j = i+Size;
                        Point p = new Point(i+offset, j+offset);
                        usefulEdges[counter] = p;
                        counter++;
                        //System.out.println("I: " + (i+offset) + "| J = " + (j+offset));
            }   
        }

        return usefulEdges;
        
    }
    public static boolean skipEdges(int x, int y){
        boolean skipThese = false;
        
        if ((x == 0) && (y == 1)){
            skipThese = true;
        }
        if ((x == (Size*Size)-2) && (y == (Size*Size)-1)){
            skipThese = true;
        }
        return skipThese;
    }
    public static void solveMaze(){
        playerObj mazeSolver = new playerObj(Size);
        mazeSolver.playerMovementStart(0,0);
        mazeSolver.printMoves();
        
    }


        /*
    public static boolean checkForRepeats(int pIndex1, int pIndex2){
        boolean checked = false;
        for (int i = 0; i < 4; i++)
        {
            if ((graph[pIndex1][i].deleted == true) || (graph[pIndex1][i].used == true)){
                if ((graph[pIndex2][i].deleted == true) || (graph[pIndex2][i].used == true)){
                checked = true;
            }
            }
            
        }
        return checked;
    }
    */
    
   
    public static void main(String[] args) {
         

	    Scanner scan = new Scanner(System.in);         
	    try {	     
	        System.out.println("What's the size of your maze? ");
	        Size = scan.nextInt();
	    }
	    catch(Exception ex){
	        ex.printStackTrace();
	    }
	    scan.close();
        

    createRandomMaze();
    solveMaze();
    
	    //displayInitBoard();
         

    }
    
    
}
