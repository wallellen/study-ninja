package edu.alvin.ninja.async.controllers;

import com.google.inject.Injector;
import edu.alvin.ninja.async.services.AsyncService;

import javax.inject.Inject;

public class AsyncRunner implements Runnable {

    private AsyncService asyncService;
    private Integer asyncId;
    private Injector injector;

    public AsyncRunner(Integer asyncId,
                       Injector injector) {
        this.asyncId = asyncId;
        this.injector = injector;
    }

    @Override
    public void run() {
        injector.injectMembers(this);
        try {
            while (asyncService.process(asyncId)) {
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
        }
    }

    @Inject
    public void setAsyncService(AsyncService asyncService) {
        this.asyncService = asyncService;
    }
}
