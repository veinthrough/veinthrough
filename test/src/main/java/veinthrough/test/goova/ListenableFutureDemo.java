package veinthrough.test.goova;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.JdkFutureAdapters;
import com.google.common.util.concurrent.ListenableFuture;

public class ListenableFutureDemo {
    private static int counter= 6;
    private static int i,j;
    private static boolean ASYNC= false, SYNC=true;
    private final static ExecutorService pool= Executors.newCachedThreadPool();

    public void test() {
        System.out.println("CANCEL ASYNC TEST----------------------------------------------");
        i=0;cancelTest( counter/2, ASYNC);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nCANCEL SYNC TEST---------------------------------------------");
        i=0;cancelTest( counter/2, SYNC);


        System.out.println("\nEXCEPTION ASYNC TEST----------------------------------------------");
        i=0;exceptionTest( 2, 0, ASYNC);

        System.out.println("\nEXCEPTION SYNC TEST-----------------------------------------------");
        i=0;exceptionTest( 2, 0, SYNC);
    }

    private void cancelTest( int counter, boolean isSync) {
        ListenableFuture<Integer> future= isSync ? countSync() : countAsync();
        Futures.addCallback( future, new FutureCallback<Integer>() {
            @Override
            public void onFailure(Throwable arg0) {
                System.out.println("Failed with "+ j+ " for "+ arg0.getClass().getSimpleName());
            }

            @Override
            public void onSuccess(Integer arg0) {
                System.out.println("Succeeded with "+ arg0);
            }
        });
        for( ; i<counter; i++){
            System.out.println("In main thread:"+ i);
            try {
                Thread.sleep( 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        future.cancel( true);
    }
    private void exceptionTest( int a, int b, boolean isSync) {
        ListenableFuture<Integer> future= isSync ? divideSync( a, b) : divideAsync( a, b);
        Futures.addCallback( future, new FutureCallback<Integer>() {

            @Override
            public void onSuccess(Integer result) {
                System.out.println("Succeeded with "+ result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Failed for "+ t.getClass().getSimpleName());
            }

        });
    }

    public Integer count() throws InterruptedException {
        for( j=0; j< counter; j++) {
            System.out.println("j: "+ j);
            Thread.sleep( 1000);
        }
        return j;
    }

    private ListenableFuture<Integer> countSync() {
        try {
            return Futures.immediateFuture( count());
        } catch( Exception e) {
            return Futures.immediateFailedFuture( e);
        }
    }

    private ListenableFuture<Integer> countAsync() {
        return JdkFutureAdapters.listenInPoolThread(
                pool.submit( this::count));
    }

    private int divide( int a, int b) throws Exception {
        if( 0== b) {
            throw new IllegalArgumentException("the divisor can't be 0");
        } else {
            return a/b;
        }
    }

    public ListenableFuture<Integer> divideSync( int a, int b) {
        try {
            return Futures.immediateFuture( divide( a,b));
        } catch( Exception e) {
            return Futures.immediateFailedFuture( e);
        }
    }

    public ListenableFuture<Integer> divideAsync( int a, int b) {
        return JdkFutureAdapters.listenInPoolThread(
            pool.submit(() -> divide(a, b)));
    }

    public static void main(String[] args) {
        new ListenableFutureDemo().test();
    }

}
