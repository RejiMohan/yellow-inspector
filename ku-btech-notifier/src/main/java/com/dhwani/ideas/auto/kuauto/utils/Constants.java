package com.dhwani.ideas.auto.kuauto.utils;

import java.util.regex.Pattern;

public class Constants {

  public static final Pattern BTECH_PATTERN =
      Pattern.compile("B.\\s*Tech", Pattern.CASE_INSENSITIVE);
  public static final String PUBLISHED_ON = "Published on";

  public static final String TABLE_ROW_SELECTOR = "#wrapper > table:nth-child(3) > tbody > tr";
  public static final String EXAMS_HOME_MARQUEE_LINKS = "#marquee a";
  public static final String KU_HOME_LIST_ITEMS = "#nt-oneliner li";
  public static final String TAG_ANCHOR = "a";
  public static final String ATTR_HREF = "href";

}
