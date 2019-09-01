package com.phauer.modernunittesting;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FuturePlayground {

    public void bla() throws ExecutionException, InterruptedException {
        CompletableFuture<String> usFuture = CompletableFuture.supplyAsync(() -> doBusinessLogic(Locale.US));
        CompletableFuture<String> germanyFuture = CompletableFuture.supplyAsync(() -> doBusinessLogic(Locale.GERMANY));
        String usResult = usFuture.get();
        String germanyResult = germanyFuture.get();
    }

    @Scheduled
    public void start() {

    }

    String doBusinessLogic(Locale locale) {
        return "asdf";
    }
}


