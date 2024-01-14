package se.plweb;


import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        final int numberOfThreads = 100;
        final int maxRows = 9900000;
        final int maxRowsPerThread = maxRows / numberOfThreads;

        System.out.println("numberOfThreads:" + numberOfThreads);
        System.out.println("maxRowsPerThread:" + maxRowsPerThread);
        System.out.println("maxRows:" + maxRows);

        Collection<Callable<Integer>> callableCollection = new ArrayList<>();

        IntStream
                .range(0, numberOfThreads)
                .forEach((i) -> callableCollection.add(DbCallable.create(maxRowsPerThread, i)));

        ExecutorService executorService = Executors.newFixedThreadPool(callableCollection.size());
        executorService
                .invokeAll(callableCollection)
                .forEach(f -> {
                    try {
                        System.out.println(f.get());
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
        executorService.shutdown();
    }
}