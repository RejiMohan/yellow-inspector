package com.dhwani.ideas.auto.kuauto.utils;

import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import org.apache.commons.lang3.StringUtils;
import com.dhwani.ideas.auto.kuauto.datum.AppConfigDatum;

public interface AppConfigInterface {

  static Optional<AppConfigDatum> getAppConfigData() {

    ResourceBundle appProps = ResourceBundle.getBundle("app-ref");

    String examsHomepage = appProps.getString("ku.exams.homepage");
    String kuHomepage = appProps.getString("ku.homepage");
    String examsTimeTablepage = appProps.getString("ku.exams.timetablepage");
    String examsNotifPage = appProps.getString("ku.exams.notificationspage");
    String examsResultsPage = appProps.getString("ku.exams.resultspage");
    String maxTableBlocks = appProps.getString("app.maxTableBlocks");
    String recipients = appProps.getString("app.recipients");

    if (StringUtils.isAnyBlank(examsHomepage, kuHomepage, examsTimeTablepage, examsNotifPage,
        examsResultsPage, recipients))
      return Optional.empty();

    AppConfigDatum appCfg = new AppConfigDatum();
    appCfg.setKuExamsHomepage(examsHomepage);
    appCfg.setKuHomepage(kuHomepage);
    appCfg.setKuExamsTimeTablepage(examsTimeTablepage);
    appCfg.setKuExamsNotifPage(examsNotifPage);
    appCfg.setKuExamsResultsPage(examsResultsPage);
    appCfg.setMaxTableBlocks(Integer.parseInt(maxTableBlocks));
    appCfg.setRecepients(Arrays.asList(StringUtils.split(recipients, ",")));

    return Optional.of(appCfg);
  }
}
