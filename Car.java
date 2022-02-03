
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier cyclicBarrier;
    private CountDownLatch cdl1;
    private CountDownLatch cdl2;
    private ReentrantLock lock;


    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CyclicBarrier cyclicBarrier, CountDownLatch cdl1, CountDownLatch cdl2, ReentrantLock lock) {
        this.race = race;
        this.speed = speed;
        this.cyclicBarrier = cyclicBarrier;
        this.cdl1 = cdl1;
        this.cdl2 = cdl2;
        this.lock = lock;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;

    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            cdl1.countDown();
            cyclicBarrier.await();

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        cdl2.countDown();

        if(lock.tryLock()) {
            System.out.println(this.name + " Win!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
        }


    }

}
