package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.hogwarts.school.configuration.InfoService;

import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Component
public class InfoServiceImpl implements InfoService {
    private final Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);
    @Value("${server.port}")
    private Integer port;

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public void calculateMillions() {
        int limit = 10_000_000;
        doingSomethingBefore(limit);
        doingSomethingAfterWithLongStream(limit);
        doingSomethingAfterWithLongStreamAndParallel(limit);
        doingSomethingAfterWithDoubleStream(limit);
        long l = doingSomethingAfterWithLongAndFormulaOfArithmeticProgression(limit);
        long l1 = doingSomethingAfterWithLongStreamThroIterate(limit);
        if (l == l1) {
            logger.info("try");
            logger.info("doingSomethingAfterWithLongAndFormulaOfArithmeticProgression - {}", l);
            logger.info("doingSomethingAfterWithLongStreamThroIterate - {}", l1);
        }
    }

    public void doingSomethingBefore(int limit) {
        long start = System.currentTimeMillis();
        int sum = Stream
                .iterate(1, a -> a + 1)
                .limit(limit)
                .reduce(0, (a, b) -> a + b);
        long duration = System.currentTimeMillis() - start;
        logger.info("⏱ calling (parent) method executed in {} ms", duration);
    }

    public void doingSomethingAfterWithLongStreamAndParallel(int limit) {
        long start = System.currentTimeMillis();
        long sum = LongStream
                .range(1, limit)
                .parallel()
                .sum();
        long duration = System.currentTimeMillis() - start;
        logger.info("⏱ calling (parent) method executed in {} ms", duration);

    }

    public void doingSomethingAfterWithLongStream(int limit) {
        long start = System.currentTimeMillis();
        long sum = LongStream
                .range(1, limit)
                .sum();
        long duration = System.currentTimeMillis() - start;
        logger.info("⏱ calling (parent) method executed in {} ms", duration);
    }

    public void doingSomethingAfterWithDoubleStream(int limit) {
        long start = System.currentTimeMillis();
        double sum = DoubleStream
                .iterate(1, a -> a + 1.0)
                .limit(limit)
                .reduce(0, Double::sum);
        long duration = System.currentTimeMillis() - start;
        logger.info("⏱ calling (parent) method executed in {} ms", duration);
    }

    public long doingSomethingAfterWithLongAndFormulaOfArithmeticProgression(int limit) {
        long start = System.currentTimeMillis();
        long sum = (long) limit * (limit + 1) / 2;
        long duration = System.currentTimeMillis() - start;
        logger.info("⏱ calling (parent) method executed in {} ms", duration);
        return sum;
    }

    public long doingSomethingAfterWithLongStreamThroIterate(int limit) {
        long start = System.currentTimeMillis();
        long sum = LongStream
                .iterate(0, a -> a + 1)
                .limit(limit + 1) // От 0 до limit включительно
                .sum();
        long duration = System.currentTimeMillis() - start;
        logger.info("⏱ calling (parent) method executed in {} ms", duration);
        return sum;
    }

}
