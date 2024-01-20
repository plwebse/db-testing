package se.plweb;

import com.github.javafaker.Faker;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class DbCallable implements Callable<Integer> {

    final DBPopulate dbPopulate;
    private final int numberOfRowsToBeInserted;
    private final int executeBatchEveryRowNumber;

    private final Faker faker = new Faker();
    private final int threadNumber;

    public static DbCallable create(int numberOfRowsToBeInserted, int threadNumber, int executeBatchEveryRowNumber) {
        return new DbCallable(numberOfRowsToBeInserted, threadNumber, executeBatchEveryRowNumber);
    }

    private DbCallable(int numberOfRowsToBeInserted, int threadNumber, int executeBatchEveryRowNumber) {
        this.numberOfRowsToBeInserted = numberOfRowsToBeInserted;
        this.threadNumber = threadNumber;
        this.executeBatchEveryRowNumber = executeBatchEveryRowNumber;
        dbPopulate = new DBPopulate();
    }

    @Override
    public Integer call() {
        AtomicInteger count = new AtomicInteger();
        IntStream.range(0, numberOfRowsToBeInserted).forEach((i) -> {
            if (dbPopulate.addDataData(faker.name().fullName(), faker.number().numberBetween(0, 100))) {
                count.getAndIncrement();
            }
            if (i % executeBatchEveryRowNumber == 0) {
                dbPopulate.executeBatch();
                System.out.println(threadNumber + " -> " + i);
            }
        });
        dbPopulate.executeBatch();

        return count.get();
    }
}
