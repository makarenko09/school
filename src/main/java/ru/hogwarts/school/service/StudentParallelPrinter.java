package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.configuration.LoggingAspect;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class StudentParallelPrinter {
    private final StudentService studentService;
    private final ThreadPoolTaskExecutor studentPrintExecutor;

    private final Logger logger = LoggerFactory.getLogger(StudentParallelPrinter.class);

    public List<String> printParallel(int startPage, int size, int pageCount, boolean sync) {
        logger.info("Starting parallel printer with using of synchronized {}", sync);
        Executor executor = buildExecutor(pageCount);

        List<CompletableFuture<List<String>>> futures = new ArrayList<>();
        List<String> result = new ArrayList<>();

        BiFunction<Integer, Integer, List<String>> printMethodWithParamOfSync = sync ?
                this::printPageSynchronized : this::printPage;

        for (int i = 0; i < pageCount; i++) {
            int page = startPage + i;
            futures.add(CompletableFuture.supplyAsync(() -> printMethodWithParamOfSync.apply(page, size), executor));
        }

        for (CompletableFuture<List<String>> f : futures) {
            List<String> names = f.join();
            if (names.isEmpty()) break;
            result.addAll(names);
        }

        return result;
    }

    private List<String> printPage(int page, int size) {
        List<String> names = studentService.getNamesByPage(page, size);

            names.forEach(System.out::println);

        return names;
    }

    private List<String> printPageSynchronized(int page, int size) {
        List<String> names = studentService.getNamesByPage(page, size);
        synchronized (StudentParallelPrinter.class) {
            names.forEach(System.out::println);
        }
        return names;
    }

    private Executor buildExecutor(int threads) {
        ThreadPoolTaskExecutor ex = new ThreadPoolTaskExecutor();
        ex.setCorePoolSize(Math.max(1, threads));
        ex.setMaxPoolSize(Math.max(1, threads));
        ex.setQueueCapacity(0);
        ex.setThreadNamePrefix("StudentPrint-");
        ex.initialize();
        return ex;
    }
}
