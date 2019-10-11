package com.company;

public class action {
    double up;
    double down;
    double right;
    double left;

    public action() {
        this.up = 0;
        this.down = 0;
        this.right = 0;
        this.left = 0;
    }

    public double getUp() {
        return up;
    }

    public double getDown() {
        return down;
    }

    public double getRight() {
        return right;
    }

    public double getLeft() {
        return left;
    }

    public void setUp(double up) {
        this.up = up;
    }

    public void setDown(double down) {
        this.down = down;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public void setLeft(double left) {
        this.left = left;
    }
    public void setAll(double up, double down, double right, double left) {
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
    }
    public String argMax(){
        if(up>down&&up>right&&up>left){
            return "up";
        }
        else if(down>up&&down>right&&down>left){
            return "down";
        }
        else if (right>up&&right>down&&right>left){
            return "right";
        }
        else{
            return "left";
        }
    }
    public double argMaxVal(){
        if(up>down&&up>right&&up>left){
            return up;
        }
        else if(down>up&&down>right&&down>left){
            return down;
        }
        else if (right>up&&right>down&&right>left){
            return right;
        }
        else{
            return left;
        }
    }
}
