package se.plweb;

import com.github.javafaker.Faker;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class DbCallable implements Callable<Integer> {

    final DBPopulate dbPopulate;
    private final int numberOfRowsToBeInserted;

    private final Faker faker = new Faker();
    private final int threadNumber;

    public static DbCallable create(int numberOfRowsToBeInserted, int threadNumber) {
        return new DbCallable(numberOfRowsToBeInserted, threadNumber);
    }

    private DbCallable(int numberOfRowsToBeInserted, int threadNumber) {
        this.numberOfRowsToBeInserted = numberOfRowsToBeInserted;
        this.threadNumber = threadNumber;
        dbPopulate = new DBPopulate();
    }

    @Override
    public Integer call() {
        AtomicInteger count = new AtomicInteger();
        IntStream.range(0, numberOfRowsToBeInserted).forEach((i) -> {
            if (dbPopulate.addDataData(faker.name().fullName(), faker.number().numberBetween(0, 100))) {
                count.getAndIncrement();
            }
            if (i % 100 == 0) {
                System.out.println(threadNumber + " -> " + i);
            }
        });

        return count.get();
    }
}
