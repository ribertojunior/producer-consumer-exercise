/*
 * (c) 2014 UL TS BV
 */
package com.ul;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;

@RequiredArgsConstructor
public class Consumer {

  @NonNull private final BlockingQueue<Message> queue;
  private Thread consumerThread = null;
  private ArrayList<Message> messageArrayList = new ArrayList<>();
  private final DateTimeFormatter dateFormatter =
      DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss.nnn");


  public void startConsuming() {
    consumerThread =
        new Thread(
            () -> {
              messageArrayList = new ArrayList<>();
              while (true) {
                try {
                  messageArrayList.add(queue.take());
                  if (messageArrayList.size() == 10) {
                    Collections.sort(messageArrayList);
                    for (Message message : messageArrayList) {
                      System.out.println(
                          dateFormatter.format(LocalDateTime.now())
                              + " - "
                              + message.getPriority()
                              + " - "
                              + message.getText());
                    }
                    messageArrayList = new ArrayList<>();
                  }

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
