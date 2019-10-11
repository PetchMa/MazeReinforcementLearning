package com.company;

import java.io.IOException;
import java.util.*;

public class Mouse {
    private int startX;
    private int startY;
    Maze maze = new Maze();
    action action = new action();
    public action[][] q = new action[7][11];
    double total_episodes = 15000;
    double learning_rate = 0.7;
    int max_steps = 99;
    double gamma = 0.95;
    double  epsilon = 1.0;
    double  max_epsilon = 1.0;
    double min_epsilon = 0.01;
    double decay_rate = 0.005 ;
    public Mouse() throws IOException {
        Maze maze = new Maze();
        Place start = new Place(0, 0);
        start = maze.getStart();
        Place end = new Place(0, 0);
        end = maze.getExit();
        this.startX = start.getX();
        this.startY = start.getY();
        for(int i=0;i<7;i++){
            for(int j =0;j<11; j++){
                q[i][j] = new action();
            }
        }
    }
    public void solutionCheese(Maze maze) {
        brainCheese(maze, maze.getStart().getX(), maze.getStart().getY());
    }
    public List<Place> solutionExit(Maze maze) {
        List<Place> path = new ArrayList<>();
        if (brainExit(maze, maze.getCheese().getX(), maze.getCheese().getY(), path)) {
            System.out.println("Optimize Solution");
            return path;
        }
        return Collections.emptyList();
    }
    private boolean brainExit(Maze maze, int row, int col, List<Place> path) {
        if (!maze.isValidLocation(row, col)
                || maze.isWall(row, col)
                || maze.isExplored(row, col)) {
            return false;
        }
        path.add(new Place(row, col));
        maze.setVisited(row, col, true);
        if (maze.isExit(row, col)) {
            System.out.println("Found a path!");
            return true;
        }
        if (brainExit(maze, row + 1, col, path)) {
            return true;
        }
        if (brainExit(maze, row - 1, col, path)) {
            return true;
        }
        if (brainExit(maze, row, col + 1, path)) {
            return true;
        }
        if (brainExit(maze, row, col - 1, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }
    private void brainCheese(Maze maze, int row, int col) {
        String move="gay";
        Place State = new Place(row, col);
        for(int episode =0; episode<total_episodes; episode++){
            int total_reward = 0;
            row = State.getX();
            col=State.getY();
            for(int step =0; step<max_steps;step++){
                double exp_exp_tradeoff = Math.random();
                int newRow = row;
                int newCol = col;
                if(exp_exp_tradeoff> episode){
                     move = q[row][col].argMax();
                     if(move.equals("up") && maze.isValidLocation(row-1, col)){
//                         System.out.println(move);
                         newRow=row-1;
                     }
                     else if(move.equals("down") && maze.isValidLocation(row+1, col)){
//                         System.out.println(move);
                         newRow=row+1;
                     }
                     else if(move.equals("right") && maze.isValidLocation(row, col+1)){
//                         System.out.println(move);
                         newCol=col+1;
                     }
                     else if(maze.isValidLocation(row, col-1)){
//                         System.out.println(move);
                         newCol=col-1;
                     }
                }
                else{
                    double spin = Math.random();
                    if(spin<0.25 && maze.isValidLocation(row-1, col) ){

                        move = "up";
//                        System.out.println(move);
                        newRow=row-1;
                    }
                    else if(spin<0.50 && spin>=0.25 && maze.isValidLocation(row+1, col)){
                        move = "down";
//                        System.out.println(move);
                        newRow=row+1;
                    }
                    else if(spin<0.75 && spin>=0.50 && maze.isValidLocation(row, col+1)){
                        move = "right";
//                        System.out.println(move);
                        newCol=col+1;
                    }
                    else if(maze.isValidLocation(row, col-1)){
                        move = "left";
//                        System.out.println(move);
                        newCol=col-1;
                    }
                }
                if(maze.isCheese(row, col)){

                    break;
                }
//                New state

                double reward = maze.getReward(newRow, newCol);
                if(move.equals("up")){
                    q[row][col].setUp(q[row][col].getUp()+learning_rate*(reward+gamma*(q[newRow][newCol].argMaxVal()
                    -q[row][col].argMaxVal())));
                }
                else if(move.equals("down")){
                    q[row][col].setDown(q[row][col].getDown()+learning_rate*(reward+gamma*(q[newRow][newCol].argMaxVal()
                            -q[row][col].argMaxVal())));
                }
                else if(move.equals("right")){
                    q[row][col].setRight(q[row][col].getRight()+learning_rate*(reward+gamma*(q[newRow][newCol].argMaxVal()
                            -q[row][col].argMaxVal())));
                }
                else{
                    q[row][col].setLeft(q[row][col].getLeft()+learning_rate*(reward+gamma*(q[newRow][newCol].argMaxVal()
                            -q[row][col].argMaxVal())));
                }
                total_reward+=reward;
                row = newRow;
                col = newCol;
//                System.out.println(newRow + ", "+ newCol);
                epsilon = epsilon*(1-decay_rate*episode);
            }
        }
    }
    public void getBrainMap(){
        for(int i=0;i<7;i++){
            for(int j =0;j<11; j++){
                System.out.print("["+q[i][j].getDown() + " "+ q[i][j].getUp()+" "+ q[i][j].getRight()+ " "+ q[i][j].getLeft()+"]");
            }
            System.out.println();
        }
    }
    public void movesMaking(int row, int col){
        boolean done = false;
        String move;
        int newRow=row;
        int newCol=col;
        int count=0;
        while(!done && count<100){
            count++;
            if(maze.isCheese(row, col)){
                done = true;
            }
            else{
                move = q[row][col].argMax();
                if(move.equals("up") && maze.isValidLocation(row-1, col)){
                    newRow=row-1;
                    System.out.println(newRow + " "+ newCol);
                }
                else if(move.equals("down") && maze.isValidLocation(row+1, col)){
                    newRow=row+1;
                    System.out.println(newRow + " "+ newCol);
                }
                else if(move.equals("right") && maze.isValidLocation(row, col+1)){
                    newCol=col+1;
                    System.out.println(newRow + " "+ newCol);
                }
                else if(maze.isValidLocation(row, col-1)){
                    newCol=col-1;
                    System.out.println(newRow + " "+ newCol);
                }
                row = newRow;
                col = newCol;
            }
        }
    }
}

