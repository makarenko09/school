package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
public class StudentParallelPrinter {
    private final StudentService studentService;
    private final ThreadPoolTaskExecutor studentPrintExecutor;
    public List<String> printParallel(int startPage, int size, int pageCount) {
        Executor executor = buildExecutor(pageCount);

        List<CompletableFuture<List<String>>> futures = new ArrayList<>();
        List<String> result = new ArrayList<>();

        for (int i = 0; i < pageCount; i++) {
            int page = startPage + i;
            futures.add(CompletableFuture.supplyAsync(() -> printPage(page, size), executor));
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
