/**
 *
 */
package veinthrough.test.multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>
 * skeleton:
 * <p>
 * 1. use lock, lock is sharable
 * <p>
 * 2. use condition, one lock may have one or more conditions
 * <p>
 * 3. thread and interrupt
 */
public class SyncBankTest extends AbstractUnitTester {
    public static final int NACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        int i;
        for (i = 0; i < NACCOUNTS; i++)
        {
            TransferRunnable r = new TransferRunnable(b, i, INITIAL_BALANCE);
            Thread t = new Thread(r);
            t.start();
        }
    }

    public static void main(String[] args) {
        new SyncBankTest().test();
    }


    class TransferRunnable implements Runnable
    {
        private Bank bank;
        private int fromAccount;
        private double maxAmount;
        private int DELAY = 10;

        /**
        * Constructs a transfer runnable.
        * @param b the bank between whose account money is transferred
        * @param from the account to transfer money from
        * @param max the maximum amount of money in each transfer
        */
        public TransferRunnable(Bank b, int from, double max)
        {
            bank = b;
            fromAccount = from;
            maxAmount = max;
        }

        public void run()
        {
            try
            {
                // No need to use Thread.currentThread() as sleep is called in the loop,
                // The thread can't check the interrupted state when blocked(sleep/wait):
                // 1. if interrupted is set then sleep, sleep() will clear the state then throw InterruptedException
                // 2. interrup on sleep will throw InterruptedException
                //while(!Thread.currentThread()) {
                while (true)
                {
                    int toAccount = (int) (bank.size() * Math.random());
                    double amount = maxAmount * Math.random();
                    bank.transfer(fromAccount, toAccount, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            }
            catch (InterruptedException e)
            {
            }
        }
    }
    class Bank
    {
        private final double[] accounts;
        private Lock bankLock;
        private Condition sufficientFunds;

        /**
        * Constructs the bank.
        * @param n the number of accounts
        * @param initialBalance the initial balance for each account
        */
        public Bank(int n, double initialBalance)
        {
            accounts = new double[n];
            for (int i = 0; i < accounts.length; i++)
                accounts[i] = initialBalance;

            // reentrant lock
            bankLock = new ReentrantLock();
            // one condition of lock
            sufficientFunds = bankLock.newCondition();
        }

        /**
        * Transfers money from one account to another.
        * @param from the account to transfer from
        * @param to the account to transfer to
        * @param amount the amount to transfer
        * @throws InterruptedException
        */
        public void transfer(int from, int to, double amount) throws InterruptedException
        {
            bankLock.lock();

            try {
                // (1) unsufficient funds
                // await may throw InterruptedException
                while(accounts[from] < amount) {
                    sufficientFunds.await();
                }
                // (2) sufficient funds
                System.out.print(Thread.currentThread());
                accounts[from] -= amount;
                System.out.printf(" %10.2f from %d to %d", amount, from, to);
                accounts[to] += amount;
                System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
                // (3) signal all as it can make others to be with sufficient funds
                sufficientFunds.signalAll();
            }
            finally {
                bankLock.unlock();
            }
        }

        /**
        * Gets the sum of all account balances.
        * @return the total balance
        */
        public double getTotalBalance()
        {
            // the bankLock is sharable
            bankLock.lock();

            try {
                double sum = 0;
                for (double a : accounts)
                    sum += a;
                return sum;
            }
            finally {
                bankLock.unlock();
            }
        }

        /**
        * Gets the number of accounts in the bank.
        * @return the number of accounts
        */
        public int size()
        {
            return accounts.length;
        }
    }
}
