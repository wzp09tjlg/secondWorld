package com.example.wuzp.secondworld.view.search.asynctask;

/**
 * 可取消的任务
 *
 * @author Tsimle
 */
public interface ICancelable {
    boolean cancel(boolean mayInterruptIfRunning);

    boolean isCancelled();
}
