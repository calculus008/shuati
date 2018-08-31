package lintcode;

/**
 * Created by yuank on 8/28/18.
 */
public class LI_224_Implement_Three_Stacks_By_Single_Array {
    /**
         Implement three stacks by single array.

         You can assume the three stacks has the same size and big enough,
         you don't need to care about how to extend it if one of the stack is full.
     */

    /**
     * Solution 1
     * Divide array into 3 section, each section is for one stack
     */
    public class ThreeStacks1 {
        /*
        * @param size: An integer
        */
        int[] arr;
        int[] pos = new int[3];
        int size;

        public ThreeStacks1(int size) {
            // do intialization if necessary
            arr = new int[size * 3];
            pos[0] = 0;
            pos[1] = size;
            pos[2] = 2 * size;
            this.size = size;
        }

        /*
         * @param stackNum: An integer
         * @param value: An integer
         * @return: nothing
         */
        public void push(int stackNum, int value) {
            // Push value into stackNum stack
            arr[pos[stackNum]++] = value;
        }

        /*
         * @param stackNum: An integer
         * @return: the top element
         */
        public int pop(int stackNum) {
            // Pop and return the top element from stackNum stack
            return arr[--pos[stackNum]];
        }

        /*
         * @param stackNum: An integer
         * @return: the top element
         */
        public int peek(int stackNum) {
            // Return the top element
            return arr[pos[stackNum] - 1];
        }

        /*
         * @param stackNum: An integer
         * @return: true if the stack is empty else false
         */
        public boolean isEmpty(int stackNum) {
            // write your code here
            return pos[stackNum] == (size * stackNum);
        }
    }

    /**
     * Solution 2
     * Each stack grows dynamically based on input
     */

    public class ThreeStacks2 {
        public int stackSize;
        public int indexUsed;
        public int[] stackPointer;
        public StackNode[] buffer;

        public ThreeStacks2(int size) {
            // do intialization if necessary
            stackSize = size;
            stackPointer = new int[3];
            for (int i = 0; i < 3; ++i)
                stackPointer[i] = -1;
            indexUsed = 0;
            buffer = new StackNode[stackSize * 3];
        }

        public void push(int stackNum, int value) {
            // Write your code here
            // Push value into stackNum stack
            int lastIndex = stackPointer[stackNum];
            stackPointer[stackNum] = indexUsed;
            indexUsed++;
            buffer[stackPointer[stackNum]] = new StackNode(lastIndex, value, -1);
        }

        /**
           pop：pop的操作较为复杂，因为有三个栈，所以栈顶不一定在数组尾端，pop掉栈顶之后，数组中可能存在空洞。而这个空洞又很难push入元素。
           所以，解决方法是，当要pop的元素不在数组尾端（即indexUsed-1）时，交换这两个元素。不过一定要注意，交换的时候，要注意修改这两个元素之前、
           之后结点的prev和next指针，使得链表仍然是正确的，事实上这就是结点中next的作用——为了找到之后结点并修改它的prev。在交换时，
           一种很特殊的情况是栈顶节点刚好是数组尾端元素的后继节点，这时需要做特殊处理。在交换完成后，就可以删掉数组尾端元素，并修改相应的stackPointer、
           indexUsed和新栈顶的next
         */
        public int pop(int stackNum) {
            // Write your code here
            // Pop and return the top element from stackNum stack
            int value = buffer[stackPointer[stackNum]].value;
            int lastIndex = stackPointer[stackNum];
            if (lastIndex != indexUsed - 1)
                swap(lastIndex, indexUsed - 1, stackNum);

            stackPointer[stackNum] = buffer[stackPointer[stackNum]].prev;
            if (stackPointer[stackNum] != -1)
                buffer[stackPointer[stackNum]].next = -1;

            buffer[indexUsed-1] = null;
            indexUsed --;
            return value;
        }

        public int peek(int stackNum) {
            // Write your code here
            // Return the top element
            return buffer[stackPointer[stackNum]].value;
        }

        public boolean isEmpty(int stackNum) {
            // Write your code here
            return stackPointer[stackNum] == -1;
        }

        public void swap(int lastIndex, int topIndex, int stackNum) {
            if (buffer[lastIndex].prev == topIndex) {
                int tmp = buffer[lastIndex].value;
                buffer[lastIndex].value = buffer[topIndex].value;
                buffer[topIndex].value = tmp;
                int tp = buffer[topIndex].prev;
                if (tp != -1) {
                    buffer[tp].next = lastIndex;
                }
                buffer[lastIndex].prev = tp;
                buffer[lastIndex].next = topIndex;
                buffer[topIndex].prev = lastIndex;
                buffer[topIndex].next = -1;
                stackPointer[stackNum] = topIndex;
                return;
            }

            int lp = buffer[lastIndex].prev;
            if (lp != -1)
                buffer[lp].next = topIndex;

            int tp = buffer[topIndex].prev;
            if (tp != -1)
                buffer[tp].next = lastIndex;

            int tn = buffer[topIndex].next;
            if (tn != -1)
                buffer[tn].prev = lastIndex;
            else {
                for (int i = 0; i < 3; ++i)
                    if (stackPointer[i] == topIndex)
                        stackPointer[i] = lastIndex;
            }

            StackNode tmp = buffer[lastIndex];
            buffer[lastIndex] = buffer[topIndex];
            buffer[topIndex] = tmp;
            stackPointer[stackNum] = topIndex;
        }
    }

    class StackNode {
        public int prev, next;
        public int value;
        public StackNode(int p, int v, int n) {
            value = v;
            prev = p;
            next = n;
        }
    }
}
