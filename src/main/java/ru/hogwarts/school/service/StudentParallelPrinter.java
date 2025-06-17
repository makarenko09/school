package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class StudentParallelPrinter {
    private final StudentService studentService;
    private final ThreadPoolTaskExecutor studentPrintExecutor;
    public List<String> printParallel(int firstPage, int size) {
        List<String> result = new ArrayList<>();


        result.addAll( printPage(firstPage, size) );


        CompletableFuture<List<String>> task1 = CompletableFuture.supplyAsync(
                () -> printPage(firstPage + 1, size), studentPrintExecutor);

        CompletableFuture<List<String>> task2 = CompletableFuture.supplyAsync(
                () -> printPage(firstPage + 2, size), studentPrintExecutor);

        result.addAll(task1.join());
        result.addAll(task2.join());

        return result;
    }
    private List<String> printPage(int page, int size) {
        List<String> names = studentService.getNamesByPage(page, size);
        names.forEach(System.out::println);
        return names;

    }
}
