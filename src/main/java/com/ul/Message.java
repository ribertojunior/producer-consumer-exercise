/*
 * (c) 2014 UL TS BV
 */
package com.ul;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message implements Comparable<Message> {

  public enum Priority {
    HIGH,
    MEDIUM,
    LOW
  }

  private long timestamp;
  private Priority priority;
  private String text;

  @Override
  public int compareTo(Message otherMessage) {
    if (otherMessage.getPriority() == this.getPriority()) {
      return 0;
    }
    if (getPriority() == Priority.LOW) {
      return -1;
    }
    if (getPriority() == Priority.MEDIUM) {
      if (otherMessage.getPriority() == Priority.LOW) {
        return 1;
      } else {
        return -1;
      }
    }
    return 1;
  }
}
