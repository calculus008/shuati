package leetcode;

import java.util.*;

public class LE_353_Design_Snake_Game {
    /**
     * Design a Snake game that is played on a device with screen size = width x height.
     * Play the game online if you are not familiar with the game.
     *
     * The snake is initially positioned at the top left corner (0,0) with length = 1 unit.
     *
     * You are given a list of food's positions in row-column order. When a snake eats the
     * food, its length and the game's score both (!!!) increase by 1.
     *
     * Each food appears one by one on the screen. For example, the second food will not
     * appear until the first food was eaten by the snake.
     *
     * When a food does appear on the screen, it is guaranteed that it will not appear
     * on a block occupied by the snake.
     *
     * Example:
     *
     * Given width = 3, height = 2, and food = [[1,2],[0,1]].
     *
     * Snake snake = new Snake(width, height, food);
     *
     * Initially the snake appears at position (0,0) and the food at (1,2).
     *
     * |S| | |
     * | | |F|
     *
     * snake.move("R"); -> Returns 0
     *
     * | |S| |
     * | | |F|
     *
     * snake.move("D"); -> Returns 0
     *
     * | | | |
     * | |S|F|
     *
     * snake.move("R"); -> Returns 1 (Snake eats the first food and right after that, the second food appears at (0,1) )
     *
     * | |F| |
     * | |S|S|
     *
     * snake.move("U"); -> Returns 1
     *
     * | |F|S|
     * | | |S|
     *
     * snake.move("L"); -> Returns 2 (Snake eats the second food)
     *
     * | |S|S|
     * | | |S|
     *
     * snake.move("U"); -> Returns -1 (Game over because snake collides with border)
     *
     * Medium
     */

    public class SnakeGame1 {
        //2D position info is encoded to 1D and stored as two copies
        Set<Integer> set; // this copy is good for fast loop-up for eating body case
        Deque<Integer> body; // this copy is good for updating tail

        int score;
        int[][] food;
        int foodIndex;
        int width;
        int height;

        public SnakeGame1(int width, int height, int[][] food) {
            this.width = width;
            this.height = height;
            this.food = food;
            set = new HashSet<>();
            set.add(0); //intially at [0][0]
            body = new LinkedList<>();
            body.offerLast(0);
        }


        public int move(String direction) {
            //case 0: game already over: do nothing
            if (score == -1) {
                return -1;
            }

            // compute new head location coordinate
            int rowHead = body.peekFirst() / width;
            int colHead = body.peekFirst() % width;
            switch (direction) {
                case "U" : rowHead--;
                    break;
                case "D" : rowHead++;
                    break;
                case "L" : colHead--;
                    break;
                default :  colHead++;
            }
            int head = rowHead * width + colHead;

            //case 1: out of boundary or eating body
            set.remove(body.peekLast()); // new head is legal to be in old tail's position, remove from set temporarily
            if (rowHead < 0 || rowHead == height || colHead < 0 || colHead == width || set.contains(head)) {
                return score = -1;
            }

            // add head for case2 and case3
            set.add(head);
            body.offerFirst(head);

            //case2: eating food, keep tail, add head
            if (foodIndex < food.length && rowHead == food[foodIndex][0] && colHead == food[foodIndex][1]) {
                set.add(body.peekLast()); // old tail does not change, so add it back to set
                foodIndex++;
                return ++score;
            }

            //case3: normal move, remove tail, add head
            body.pollLast();

            return score;
        }
    }

    class SnakeGame2 {
        Queue<Integer> q;
        Set<Integer> set;

        int[][] foods;
        int foodIndex;
        int width, height;
        int row, col;

        public SnakeGame2(int width, int height, int[][] food) {
            q = new LinkedList();
            q.offer(0);

            set = new HashSet<>();
            set.add(0);

            foods = food;
            foodIndex = 0;
            this.width = width;
            this.height = height;
            row = 0;
            col = 0;
        }

        public int move(String direction) {
            if(direction.equals("U")){
                row --;
            }else if(direction.equals("D")){
                row ++;
            }else if(direction.equals("L")){
                col --;
            }else if(direction.equals("R")){
                col ++;
            }

            int head = row * width + col;
            /**
             * This logic checks if head bumps into his part of his own body.
             *
             * q.peek() is tail's position before current move.
             * If the head position after current move is not a food,
             * length remains the same, so tail need to move, so
             * this position is legal for head. The logic here means
             * that head's next position can not be any part of his own
             * body except his tail's position (which is not possible the
             * location of a food)
             */
//            if (head != q.peek() && q.contains(head)) {
            if (head != q.peek() && set.contains(head)) {
                return -1;
            }

            if (row >= 0 && row < height && col >= 0 && col < width) {
                q.offer(head);//move head
                set.add(head);

                if (foodIndex < foods.length && row == foods[foodIndex][0] && col == foods[foodIndex][1]) {//eat food
                    foodIndex++;
                } else {//no food, length of snake does not increase, so have to remove tail from q (move tail)
//                    q.poll();
                    set.remove(q.poll());
                }
                return foodIndex;
            }

            return -1;
        }
    }
}
