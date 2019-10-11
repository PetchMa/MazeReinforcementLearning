package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long startTime = System.currentTimeMillis();
        Mouse mouse = new Mouse();
        Maze maze = new Maze();
        boolean flag = true;
        System.out.println("WELCOME TO THE GAME!!!");
        System.out.println("Here is your board");
        maze.printMap();
        System.out.println("Where do you wan the mouse to start?");
        while(flag)
        try{
            System.out.println("Type the ROW value");
            int x = Integer.parseInt(br.readLine());
            System.out.println("Type the COLUMN value");
            int y = Integer.parseInt(br.readLine());
            Place start = new Place(x,y);
            if(!maze.isWall(x,y)
                    && !maze.isCheese(x,y)
                    && !maze.isExit(x,y)
                    && maze.isValidLocation(x,y)){
                maze.setStart(start);
                flag = false;
            }

        }catch(Exception e){
            System.out.println("INVALID POSITION");
            continue;
        }
        mouse.solutionCheese(maze);
        mouse.getBrainMap();
        mouse.movesMaking(maze.getStart().getX(), maze.getStart().getY());
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

    }
    public static void printMap(char[][]map){
        for(int i=0;i>map.length;i++){
            for(int k=0;k<map[0].length;k++){
                System.out.print(map[i][k]);
            }
        }
    }
    public static void printSol(List<Place> sol){
        for(Place i:sol){
            i.print();
        }
    }
}
