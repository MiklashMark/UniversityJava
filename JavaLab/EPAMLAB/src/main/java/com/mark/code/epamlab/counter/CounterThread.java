//package com.dmitry.code.epamlab.counter;
//
//public class CounterThread extends Thread{
//
//    @Override
//    public void start(){
//        Counter.increment();
//    }
//
//}
package com.mark.code.epamlab.counter;

public class CounterThread extends Thread{

    @Override
    public void run(){
        Counter.increment();
    }

}
