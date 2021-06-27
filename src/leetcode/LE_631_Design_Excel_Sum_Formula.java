package leetcode;

import java.util.HashMap;
import java.util.HashSet;

public class LE_631_Design_Excel_Sum_Formula {
    /**
     * Design the basic function of Excel and implement the function of the sum formula.
     *
     * Implement the Excel class:
     *
     * Excel(int height, char width) Initializes the object with the height and the width of the sheet. The sheet is an
     * integer matrix mat of size height x width with the row index in the range [1, height] and the column index in the
     * range ['A', width]. All the values should be zero initially.
     * void set(int row, char column, int val) Changes the value at mat[row][column] to be val.
     * int get(int row, char column) Returns the value at mat[row][column].
     * int sum(int row, char column, List<String> numbers) Sets the value at mat[row][column] to be the sum of cells
     * represented by numbers and returns the value at mat[row][column]. This sum formula should exist until this cell
     * is overlapped by another value or another sum formula. numbers[i] could be on the format:
     * "ColRow" that represents a single cell.
     * For example, "F7" represents the cell mat[7]['F'].
     * "ColRow1:ColRow2" that represents a range of cells. The range will always be a rectangle where "ColRow1" represent
     * the position of the top-left cell, and "ColRow2" represents the position of the bottom-right cell.
     * For example, "B3:F7" represents the cells mat[i][j] for 3 <= i <= 7 and 'B' <= j <= 'F'.
     * Note: You could assume that there will not be any circular sum reference.
     *
     * For example, mat[1]['A'] == sum(1, "B") and mat[1]['B'] == sum(1, "A").
     *
     * Example 1:
     * Input
     * ["Excel", "set", "sum", "set", "get"]
     * [[3, "C"], [1, "A", 2], [3, "C", ["A1", "A1:B2"]], [2, "B", 2], [3, "C"]]
     * Output
     * [null, null, 4, null, 6]
     *
     * Explanation
     * Excel excel = new Excel(3, "C");
     *  // construct a 3*3 2D array with all zero.
     *  //   A B C
     *  // 1 0 0 0
     *  // 2 0 0 0
     *  // 3 0 0 0
     * excel.set(1, "A", 2);
     *  // set mat[2]["B"] to be 2.
     *  //   A B C
     *  // 1 2 0 0
     *  // 2 0 0 0
     *  // 3 0 0 0
     * excel.sum(3, "C", ["A1", "A1:B2"]); // return 4
     *  // set mat[3]["C"] to be the sum of value at mat[1]["A"] and the values sum of the rectangle range whose top-left cell is mat[1]["A"] and bottom-right cell is mat[2]["B"].
     *  //   A B C
     *  // 1 2 0 0
     *  // 2 0 0 0
     *  // 3 0 0 4
     * excel.set(2, "B", 2);
     *  // set mat[2]["B"] to be 2. Note mat[3]["C"] should also be changed.
     *  //   A B C
     *  // 1 2 0 0
     *  // 2 0 2 0
     *  // 3 0 0 6
     * excel.get(3, "C"); // return 6
     *
     *
     * Constraints:
     * 1 <= height <= 26
     * 'A' <= width <= 'Z'
     * 1 <= row <= height
     * 'A' <= column <= width
     * -100 <= val <= 100
     * 1 <= numbers.length <= 5
     * numbers[i] has the format "ColRow" or "ColRow1:ColRow2".
     * At most 100 calls will be made to set, get, and sum.
     *
     * Hard
     */

    /**
     * O(1) set() operation, only do the traverse in get()/sum() operation, 103ms.
     * Good for set() heavy system.
     *
     * Key insights:
     * From given example, one important observation is that one cell can appear multiple times in a formula:
     *  excel.sum(3, "C", ["A1", "A1:B2"]), A1 cell appears twice. So its value will be added twice when calculating sum.
     *
     * Therefore, it's not enough just to remember what cells are in the current formula, we also need to know how many
     * times (count) of a given cell appears in the formula. That's why we use HashMap<Cell, Integer> in Cell class, it
     * is basically the frequency map for a given cell.
     *
     */
    class Excel {
        Cell[][] table;

        public Excel(int height, char width) {
            /**
             * !!!
             * " + 1"
             */
            table = new Cell[height + 1][width - 'A' + 1];
        }

        public void set(int row, char column, int val) {
            if (table[row][column - 'A'] == null) {
                table[row][column - 'A'] = new Cell(val);
            } else {
                table[row][column - 'A'].setVal(val);
            }
        }

        public int get(int row, char column) {
            if (table[row][column - 'A'] == null) {
                return 0;
            }
            return table[row][column - 'A'].getVal();
        }

        public int sum(int row, char column, String[] numbers) {
            if (table[row][column - 'A'] == null) {
                table[row][column - 'A'] = new Cell(numbers);
            } else {
                table[row][column - 'A'].setFormula(numbers);
            }

            return table[row][column - 'A'].getVal();
        }

        class Cell {
            /**
             * only HashMap has "clear()" method, "Map" does not have it.
             */
            HashMap<Cell, Integer> formula = new HashMap<>();
            int val = 0;

            public Cell(int val) {
                setVal(val);
            }

            public Cell(String[] formulaStr) {
                setFormula(formulaStr);
            }

            public void setVal(int val) {
                /**
                 * !!!
                 * once value is set, its previous formula should be removed.
                 */
                formula.clear();
                this.val = val;
            }

            public int getVal() {
                if (this.formula.isEmpty()) return this.val;

                int sum = 0;
                for (Cell c : formula.keySet()) {
                    /**
                     * since c here may also have a formula, so it is recursive in nature.
                     */
                    sum += c.getVal() * formula.get(c);
                }
                return sum;
            }

            public void setFormula(String[] formulaStr) {
                /**
                 * !!!
                 * Don't forget to clear existing formula before we set a new one.
                 */
                formula.clear();
                for (String s : formulaStr) {
                    if (s.indexOf(":") < 0) {
                        int[] pos = getPos(s);
                        addFormulaCell(pos[0], pos[1]);
                    } else {
                        String[] pos = s.split(":");
                        int[] startPos = getPos(pos[0]);
                        int[] endPos = getPos(pos[1]);

                        for (int r = startPos[0]; r <= endPos[0]; r++) {
                            for (int c = startPos[1]; c <= endPos[1]; c++) {
                                addFormulaCell(r, c);
                            }
                        }
                    }
                }
            }

            private int[] getPos(String s) {
                int[] pos = new int[2];
                /**
                 * !!!
                 * use s.substring(1), row value may have multiple digits, such as "A:77"
                 */
                pos[0] = Integer.parseInt(s.substring(1));
                pos[1] = s.charAt(0) - 'A';

                return pos;
            }

            private void addFormulaCell(int r, int c) {
                if (table[r][c] == null) table[r][c] = new Cell(0);
                Cell rangeCell = table[r][c];
                formula.put(rangeCell, formula.getOrDefault(rangeCell, 0) + 1);
            }
        }
    }

    /**
     * All cells' values are always up to date after each operation, O(1) get() operation, 110ms.
     *
     * Good for get()/sum() heavy system
     *
     * Tricky part is how to update cells since there are dependence between cells.
     * So we use "HashSet<Cell> dependentCells" in Cell class to remember all other cells that depend on "me".
     * "HashMap<Cell, Integer> formula" remembers what cells "I" depend on.
     *
     * This is like a directed graph, those 2 data structures in a cell (a node) act as in/out relationship with other nodes.
     */
    public class Excel2 {
        Cell[][] table;

        public Excel2(int H, char W) {
            table = new Cell[H + 1][W - 'A' + 1];
        }

        public void set(int r, char c, int v) {
            if (table[r][c - 'A'] == null) table[r][c - 'A'] = new Cell(v);
            else table[r][c - 'A'].setValue(v);
        }

        public int get(int r, char c) {
            if (table[r][c - 'A'] == null) return 0;
            else return table[r][c - 'A'].val;
        }

        public int sum(int r, char c, String[] strs) {
            if (table[r][c - 'A'] == null) table[r][c - 'A'] = new Cell(strs);
            else {
                table[r][c - 'A'].setFormula(strs);
            }
            return table[r][c - 'A'].val;
        }

        private class Cell {
            int val = 0;
            HashMap<Cell, Integer> formula = new HashMap<>();
            HashSet<Cell> dependentCells = new HashSet<>();

            public Cell(int val) {
                setValue(val);
            }

            public Cell(String[] formulaStr) {
                setFormula(formulaStr);
            }

            /**
             * all values updated in set action
             */
            public void setValue(int val) {
                /**
                 * !!!
                 * set value, so we need to remove formula, I no longer depend on those rangeCells, remove myself
                 */
                removeFormulaCells();
                formula.clear();

                /**
                 * !!!
                 */
                updateDependentCells(val);

                this.val = val;
            }

            public void setFormula(String[] formulaStr) {
                /**
                 * !!!
                 * set new formula, so we need to remove existing formula, I no longer depend on those rangeCells, remove myself
                 */
                removeFormulaCells();
                formula.clear();

                /**
                 * Always update newVal, so it can be retrieved in O(1)
                 */
                int newVal = 0;
                for (String str : formulaStr) {
                    if (str.indexOf(":") < 0) {
                        int[] pos = getPos(str);
                        /***
                         * !!!
                         */
                        newVal += addFormulaCell(pos[0], pos[1]);
                    } else {
                        String[] pos = str.split(":");
                        int[] startPos = getPos(pos[0]);
                        int[] endPos = getPos(pos[1]);
                        for (int r = startPos[0]; r <= endPos[0]; r++) {
                            for (int c = startPos[1]; c <= endPos[1]; c++) {
                                newVal += addFormulaCell(r, c);
                            }
                        }
                    }
                }

                /**
                 * !!!
                 */
                updateDependentCells(newVal);

                this.val = newVal;
            }

            private int[] getPos(String str) {
                int[] pos = new int[2];
                pos[1] = str.charAt(0) - 'A';
                pos[0] = Integer.parseInt(str.substring(1));
                return pos;
            }

            private int addFormulaCell(int r, int c) {
                if (table[r][c] == null) table[r][c] = new Cell(0);
                Cell rangeCell = table[r][c];
                formula.put(rangeCell, formula.getOrDefault(rangeCell, 0) + 1);

                /**
                 * !!!
                 * I(this cell) depends on the value of rangeCell
                 */
                rangeCell.dependentCells.add(this);

                return rangeCell.val;
            }

            /**
             * Remove myself from the cell that depends on me
             */
            private void removeFormulaCells() {
                if (!this.formula.isEmpty()) {
                    for (Cell rangeCell : this.formula.keySet()) {
                        rangeCell.dependentCells.remove(this);
                    }
                }
            }

            /**
             * recursively update values
             */
            private void updateDependentCells(int newVal) {
                int delta = newVal - this.val;

                /**
                 * Iterate through my dependence, since my value is changed, I need to update them.
                 */
                for (Cell cell : dependentCells) {
                    int cellNewVal = cell.val + delta * cell.formula.get(this);
                    /**
                     * recursion
                     */
                    cell.updateDependentCells(cellNewVal);
                }
                this.val = newVal;
            }
        }
    }
}
