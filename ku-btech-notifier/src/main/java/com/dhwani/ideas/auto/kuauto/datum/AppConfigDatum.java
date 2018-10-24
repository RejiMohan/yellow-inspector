package com.dhwani.ideas.auto.kuauto.datum;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppConfigDatum {

  private String kuHomepage;
  private String kuExamsHomepage;
  private String kuExamsTimeTablepage;
  private String kuExamsNotifPage;
  private String kuExamsResultsPage;
  
  private Integer maxTableBlocks;
  private List<String> recepients;

}
