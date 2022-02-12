package com.seg.view.concurrent.service.blueprint;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public interface IService {

    default Executor createExecutor(){
        final Executor executor = Executors.newCachedThreadPool((runnable) -> {
            final Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });
        return executor;
    }

    
}
