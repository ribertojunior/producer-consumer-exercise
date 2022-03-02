/*
 * (c) 2014 UL TS BV
 */
package com.ul;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.BlockingQueue;

@RequiredArgsConstructor
public class Producer {

  @NonNull private final BlockingQueue<Message> queue;
  private int messageCounter = 0;

  public void startProducing() {
    new Thread(
            () -> {
              try {
                while (messageCounter != 100) {
                  Message message = MessageFactory.generateMessage(messageCounter);
                  queue.add(message);
                  messageCounter++;
                }
              } catch (InterruptedException e) {
                // exit loop quietly
              }
            })
        .start();
  }

  public void stopProducing() {
    // set message counter to maximum number of messages to stop loop, allowing thread to exit
    messageCounter = 100;
  }
}
