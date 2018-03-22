package assignment4;

public class Critter3 extends Critter {

    final int RIGHT = 0;
    final int UP = 2;
    final int LEFT = 4;
    final int DOWN = 6;
    private int right = 4;
    private int down = 4;
    private int left = 4;
    private int up = 4;

    private int rightMoves;
    private int downMoves;
    private int leftMoves;
    private int upMoves;

    public Critter3() {
        rightMoves = 4;
        downMoves = 4;
        leftMoves = 4;
        upMoves = 4;
    }

    public Critter3(int right, int down, int left, int up){
        this.right = right;
        this.left = left;
        this.up = up;
        this.down = down;
        rightMoves = right;
        leftMoves = left;
        upMoves = up;
        downMoves = down;
    }

    @Override
    public String toString() {
        return "3";
    }

    @Override
    // Do they move down?
    public void doTimeStep() {
        if(rightMoves > 0) {
            rightMoves--;
            walk(RIGHT);
        }
        else if(downMoves > 0) {
            if(downMoves == down) {
                Critter3 offspring  = new Critter3(right, down+1, left, up+1);
                reproduce(offspring, RIGHT);
            }
            downMoves--;
            walk(DOWN);
        }
        else if(leftMoves > 0) {
            leftMoves--;
            walk(LEFT);
        }
        else if(upMoves > 0) {
            upMoves--;
            walk(UP);
        }
        else {
            rightMoves = this.right-1;
            downMoves = this.down;
            leftMoves = this.left;
            upMoves = this.up;
            walk(RIGHT);
        }
    }

    @Override
    public boolean fight(String opponent) {
        return true;
    }
}
