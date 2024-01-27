package ai.lazero.lazero;

import android.util.Log;

class MyThread extends Thread {
    // int i=0; // 1
    // never mind.
    HttpPostBytes httpPostBytes_private;
    ByteClass byteClass;
    String name;
    String value;
    MyThread(HttpPostBytes httpPostBytes,ByteClass byteClass,String name,String value){
        this.byteClass=byteClass;
        this.httpPostBytes_private=httpPostBytes;
        this.name=name;
        this.value=value;
    }
    //        @Override
    // as complex as shit.
    public void run() {
        // do this in another thread.
        // sleeping and checking if lock has been released.
        // you shall enable the lock.
        while (!Thread.currentThread().isInterrupted()){try{
            Thread.sleep(1000);
        }catch (Exception e){Log.e("Thread interrupted",e.toString()); break;}
        if (byteClass.screenshot_update){
        try{
//            httpPostBytes_private.testPostBytes();
            boolean htp=httpPostBytes_private.testPostBytes(name,value);
            // here's the thing. close the thing!
            if (htp){System.out.println("SUCCESS POST >>");
                byteClass.screenshot_update=false;
            }else{System.out.println("FAIL POST >>");}
        }
        catch(Exception e){
                                Log.d("lhm", e.toString());
//            System.out.println);
        }}
        }
        // use another thing. separate thread.
//            String name = Thread.currentThread().getName();
//            String inf = Thread.currentThread().toString();
//            long idnum = Thread.currentThread().getId();
//            for ( int i = 0 ;i < 10 ;i ++ ){ // 不管是新建一个对象，还是两个对象， // 2，都是打印20个数据
//// for(;i<10;i++){ // 新建一个对象的时候，打印11个左右的数据 ,新建两个对象的时候， // 2，会打印20个数据。 // 1
//                System.out.println( " i---------- " + i + " ,thread name== " + name
//                        + " ,threadid== " + idnum + " ,thread inf== " + inf);
    }
}