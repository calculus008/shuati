package Interviews.Paypal;

import java.util.Stack;

/**
 * https://github.com/dimitrovvlado/misc/blob/master/src/main/java/com/vlado/stacks/TransactionalStack.java
 *
 * https://leetcode.com/discuss/interview-question/143371/forusall-transactional-stack
 *
 */

public class TransactionalStack {
    private Stack<Integer> content;

    /**
     * transaction lives in this stack after begin() and before commit() or rollback()
     */
    private Stack<TransactionalStack> transactions;

    public TransactionalStack() {
        this(false);
    }

    private TransactionalStack(boolean internal) {
        content = new Stack<Integer>();
        if (!internal) {
            transactions = new Stack<TransactionalStack>();
            /**
             * The first element in transactions, used later
             * to tell if there's still transactions left.
             */
            transactions.push(this);
        }
    }

    public void push(int value) {
        transactions.peek().content.push(value);
    }

    public int top() {
        Stack<Integer> cn = transactions.peek().content;
        return cn.empty() ? 0 : cn.peek();
    }

    public int pop() {
        Stack<Integer> cn = transactions.peek().content;
        return cn.empty() ? 0 : cn.pop();
    }

    /**
     * So the transaction begin means to make a SNAPSHOT of the current
     * internal stack and put into a new TransactionStack object,
     * push to transactions stack.
     *
     *
     */
    public void begin() {
        TransactionalStack newTransaction = new TransactionalStack();
        newTransaction.content = (Stack<Integer>) content.clone();
        transactions.add(newTransaction);
    }

    public boolean rollback() {
        TransactionalStack lastTransaction = transactions.peek();
        /**
         * if lastTransaction == this), means there's no more transactions for rollback
         */
        if (lastTransaction != this) {
            transactions.pop();
            return true;
        }
        return false;
    }

    /**
     * pop the top of transactions, put its content into the current top transaction
     */
    public boolean commit() {
        TransactionalStack lastTransaction = transactions.peek();
        /**
         * if lastTransaction == this, means no more transaction left to be committed
         */
        if (lastTransaction != this) {
            lastTransaction = transactions.pop();
            transactions.peek().content = lastTransaction.content;
            return true;
        }
        return false;
    }

    public static void main(String[] as) {
        TransactionalStack sol = new TransactionalStack();
        sol.push(4);
        sol.begin();                    // start transaction 1
        sol.push(7);                    // stack: [4,7]
        sol.begin();                    // start transaction 2
        sol.push(2);                    // stack: [4,7,2]
        System.out.println(sol.rollback());// == true;  // rollback transaction 2
        System.out.println(sol.top());// == 7;          // stack: [4,7]
        sol.begin();                    // start transaction 3
        sol.push(10);                   // stack: [4,7,10]
        System.out.println(sol.commit());// == true;    // transaction 3 is committed
        System.out.println(sol.top()); //== 10;
        System.out.println(sol.rollback());// == true;  // rollback transaction 1
        System.out.println(sol.top());// == 4;          // stack: [4]
        System.out.println(sol.commit());// == false;   // there is no open transaction
    }
}
