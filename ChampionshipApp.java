import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class ChampionshipApp {
    public static final int CARS_COUNT = 4;
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(CARS_COUNT + 1);
        final CountDownLatch cdl1 = new CountDownLatch(CARS_COUNT);
        final CountDownLatch cdl2 = new CountDownLatch(CARS_COUNT);
        final ReentrantLock lock = new ReentrantLock();


        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cyclicBarrier, cdl1, cdl2, lock);
            System.out.println(cars[i].getSpeed());
        }


        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }



        cdl1.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        cyclicBarrier.await();

        cdl2.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");


    }

}
