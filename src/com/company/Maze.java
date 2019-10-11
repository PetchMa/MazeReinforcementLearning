package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Maze {
    private char[][] map = new char[7][11];
    private boolean[][] visited = new boolean[7][11];
    Place Start = new Place(0,0);
    Place Exit = new Place(1,9);
    Place Cheese;
    public Maze()throws IOException {
        map = read(map);

        this.Cheese = findTarget(map, 'C');
        this.Exit = findTarget(map, 'X');

    }
    public static char[][] read(char[][] map) throws IOException {
        String file = "D://software_dev/MazeRL/Maze.txt";
        FileReader in = new FileReader(file);
        BufferedReader br = new BufferedReader(in);
        int count =0;
        br.readLine();
        String msg;
        while (br.readLine() != null) {
            msg = br.readLine();
            for(int i=0; i<11;i++){
                map[count][i] = msg.charAt(i);
            }
            count++;
        }
        in.close();
        return map;
    }
    public Place getCheese(){
        return Cheese;
    }
    public int getHeight() {
        return map.length;
    }
    public int getWidth() {
        return map[0].length;
    }
    public Place getStart(){
        return Start;
    }
    public void printStart(){
        System.out.println(Start.getX() +"-"+Start.getY());
    }
    public Place getExit(){
        return Exit;
    }
    public boolean isWall(int x, int y){
        if (map[x][y] == 'B') {
            return true;
        }
        return false;
    }
    public void setVisited(int x, int y, boolean flag){
        visited[x][y]=flag;
    }
    public boolean isExplored(int x, int y){
        return visited[x][y];
    }
    public boolean isExit(int x, int y){
        if(x==Exit.getX() && y==Exit.getY()){
            return true;
        }return false;
    }
    public boolean isCheese(int x, int y){
        if(x==Cheese.getX() && y==Cheese.getY()){
            return true;
        }
        return false;
    }
    public boolean isValidLocation(int row, int col) {
        if(row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
            return false;
        }
        return true;
    }
    public Place findTarget(char[][] map, char target){
        Place Start = new Place(0,0);
        for(int i=0;i<map.length;i++){
            for(int k=0;k<map[0].length;k++){
                if(map[i][k]==target){
                    Start.setX(i);
                    Start.setY(k);
                    return Start;
                }
            }
        }
        return Start;
    }
    public void printMap(){
        for(int i=0;i<getHeight(); i++){
            for(int k=0;k<getWidth();k++){
                System.out.print(map[i][k]);
            }
            System.out.println();
        }
    }
    public void resetExplore(){
        for(int t=0;t<7;t++){
            for(int k=0; k<11;k++){
                this.visited[t][k]=false;
            }
        }
    }
    public void setStart(Place start){
        this.Start = start;
    }
    public double getReward(int x, int y){
        if(map[x][y]=='B'){
            return -10000000;
        }
        else if(map[x][y]=='.'){
            return -1;
        }
        else if(map[x][y]=='C'){
            return 1000000000;
        }
        return -1;
    }
}
