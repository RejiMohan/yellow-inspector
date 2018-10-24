package com.dhwani.ideas.auto.kuauto.utils;

import lombok.Getter;

public enum Category {

  KU_HOME("KU Home Updates"), EXAM_HOME("Exams Home Updates"), RESULTS("Results"), TIMETABS(
      "Timetables"), NOTIFS("Notifications");

  @Getter
  private final String label;

  Category(String label) {
    this.label = label;
  }

}
