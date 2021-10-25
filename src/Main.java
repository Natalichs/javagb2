public class Main {
    static final int size = 10000000;
    static final int HALF = size / 2;



    public static void main(String[] args) {
        firstMethod();
        secondMethod();
    }

    //Первый метод
    public static void firstMethod(){
        float[] arr = new float[size];
        for(float o: arr) o=1.0f;
        long startTime=System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++)
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

        long finishTime = System.currentTimeMillis();

        System.out.println("One thread time: " + (finishTime-startTime)+"ms");
    }

    //Второй метод
    public static void secondMethod() {
        float[] arr = new float[size];
        for(float o: arr) o=1.0f;
        long startTime=System.currentTimeMillis();

        float[] finishArr = new float[size];

        //первый поток
        Thread thread1 = new Thread(()-> {
            float[] leftArr = new float[HALF];
            System.arraycopy(arr,0,leftArr,0,HALF);
            for(int i = 0; i<leftArr.length;i++)
                leftArr[i] = (float) (leftArr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

            System.arraycopy(leftArr,0,finishArr,0,HALF);
        });

        //второй поток
        Thread thread2 = new Thread(()-> {
            float[] rightArr= new float[HALF];
            System.arraycopy(arr,HALF,rightArr,0,HALF);
            for(int i = 0; i<rightArr.length;i++)
                rightArr[i] = (float) (rightArr[i] * Math.sin(0.2f + (i+HALF) / 5) * Math.cos(0.2f + (i+HALF) / 5) * Math.cos(0.4f + (i+HALF) / 2));

            System.arraycopy(rightArr,0,finishArr,HALF,HALF);
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        long finishTime = System.currentTimeMillis();

        System.out.println("Two thread time: " + (finishTime-startTime)+"ms");

    }
}
