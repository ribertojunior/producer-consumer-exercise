/*
 * (c) 2014 UL TS BV
 */
package com.ul;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;


public class Consumer {

    private static final Logger logger = LogManager.getLogger("Consumer");
    private final BlockingQueue<Message> queue;
    private Thread consumerThread = null;

    public Consumer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    public void startConsuming() {
        consumerThread = new Thread(() -> {
            while (true) {
                try {
                    Message message = queue.take();
                    logger.info(message);
                } catch (InterruptedException e) {
                    // executing thread has been interrupted, exit loop
                    break;
                }
            }
        });
        consumerThread.start();
    }

    public void stopConsuming() {
        consumerThread.interrupt();
    }
}
