package com.focasoft.focaworld.task;

import com.focasoft.focaworld.utils.ThreadUtils;

import java.util.LinkedList;

public class AsyncWorker implements Runnable {
  private final LinkedList<Runnable> QUEUE = new LinkedList<>();
  private final Thread THREAD;
  private volatile boolean working = true;

  public AsyncWorker() {
    THREAD = new Thread(this, "Async Worker");
  }

  @Override
  public void run() {
    while (working) {
      if (QUEUE.isEmpty()) {
        ThreadUtils.sleep(5);
      }

      try {
        Runnable run = QUEUE.poll();

        if (run != null) {
          run.run();
        }
      } catch (Throwable t) {
        System.out.println("Erro: " + t.getMessage());
      }
    }
  }

  public void addTask(Runnable run) {
    if (!working)
      return;

    QUEUE.addLast(run);
  }

  public synchronized void start() {
    THREAD.start();
  }

  public synchronized void kill() {
    working = false;

    try {
      THREAD.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void flushAndKill() {
    kill();
    QUEUE.forEach(Runnable::run);
  }
}
