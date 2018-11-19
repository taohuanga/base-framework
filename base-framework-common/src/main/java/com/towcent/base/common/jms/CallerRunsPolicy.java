package com.towcent.base.common.jms;

import org.apache.log4j.Logger;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class CallerRunsPolicy implements RejectedExecutionHandler {

	private static Logger logger = Logger
			.getLogger(CallerRunsPolicy.class.getName());
	/**
     * Creates a <tt>CallerRunsPolicy</tt>.
     */
    public CallerRunsPolicy() { }

    /**
     * Executes task r in the caller's thread, unless the executor
     * has been shut down, in which case the task is discarded.
     * @param r the runnable task requested to be executed
     * @param e the executor attempting to execute this task
     */
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            r.run();
            logger.warn(" =========== Warning Policy ==== Pls Ajust ThreadPool Size ==== ");
        }
    }
}
