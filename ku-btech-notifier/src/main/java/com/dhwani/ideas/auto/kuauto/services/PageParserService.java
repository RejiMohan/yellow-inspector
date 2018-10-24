package com.dhwani.ideas.auto.kuauto.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector.SelectorParseException;
import com.dhwani.ideas.auto.kuauto.datum.AppConfigDatum;
import com.dhwani.ideas.auto.kuauto.datum.KUUpdate;
import com.dhwani.ideas.auto.kuauto.utils.AppConfigInterface;
import com.dhwani.ideas.auto.kuauto.utils.Category;
import com.dhwani.ideas.auto.kuauto.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageParserService {

  private int processedHeaderRows = 0;
  private int maxHeaderRows = 2;
  private String currentTableTitle;
  private List<KUUpdate> kuUpdatesList = new ArrayList<>();

  public List<KUUpdate> parsePagesAndgetUpdates() {

    AppConfigInterface.getAppConfigData().ifPresent(cfg -> {
      this.maxHeaderRows = cfg.getMaxTableBlocks();
      parseNotificationsPage(cfg);
      parseTimeTablesPage(cfg);
      parseResultsPage(cfg);
      parseExamsHomeUpdates(cfg);
      parseLatestUpdates(cfg);
    });

    return kuUpdatesList;
  }

  private void parseNotificationsPage(AppConfigDatum appCfg) {
    try {
      Document doc = Jsoup.connect(appCfg.getKuExamsNotifPage()).get();
      Elements tableRowElements = doc.select(Constants.TABLE_ROW_SELECTOR);
      processRowsInSelectedTable(tableRowElements, Category.NOTIFS);

      log.info("Updates count after notificatons page : {}", kuUpdatesList.size());

    } catch (IOException ex) {
      log.error("Failed to parse NOTIFICATIONS Page.", ex);
    }
  }

  private void processRowsInSelectedTable(Elements tableRowElements, Category category) {
    if (CollectionUtils.isNotEmpty(tableRowElements)) {
      this.currentTableTitle = "";
      this.processedHeaderRows = 0;
      tableRowElements.stream().skip(1) // Remove the page number row
          .forEach(row -> {
            if (row.hasClass("tableHeading")) {
              if (StringUtils.containsIgnoreCase(row.text(), Constants.PUBLISHED_ON)) {
                ++this.processedHeaderRows;
                this.currentTableTitle = this.processedHeaderRows > this.maxHeaderRows ? "" : StringUtils.trim(StringUtils.remove(row.text(), Constants.PUBLISHED_ON));
              }
            } else if (row.hasClass("displayList")
                && StringUtils.isNotBlank(this.currentTableTitle)) {
              buildRecordFromRowData(row, category).ifPresent(this.kuUpdatesList::add);
            }
          });
    }

  }

  private Optional<KUUpdate> buildRecordFromRowData(Element row, Category category) {

    if (null != row && Constants.BTECH_PATTERN.matcher(row.text()).find()) {
      KUUpdate update = new KUUpdate();
      update.setPublishedOn(this.currentTableTitle);
      update.setCategory(category);
      update.setDescription(StringUtils.trim(row.text()));

      Element anchor = row.getElementsByTag(Constants.TAG_ANCHOR).first();
      if (null != anchor)
        update.setLink(StringUtils.trim(anchor.attr(Constants.ATTR_HREF)));

      return Optional.of(update);
    }

    return Optional.empty();
  }

  private void parseTimeTablesPage(AppConfigDatum appCfg) {
    try {
      Document doc = Jsoup.connect(appCfg.getKuExamsTimeTablepage()).get();
      Elements tableRowElements = doc.select(Constants.TABLE_ROW_SELECTOR);
      processRowsInSelectedTable(tableRowElements, Category.TIMETABS);

      log.info("Updates count after time tables page : {}", kuUpdatesList.size());

    } catch (SelectorParseException | IOException ex) {
      log.error("Failed to parse TIMETABLES Page.", ex);
    }
  }

  private void parseResultsPage(AppConfigDatum appCfg) {
    try {
      Document doc = Jsoup.connect(appCfg.getKuExamsResultsPage()).get();
      Elements tableRowElements = doc.select(Constants.TABLE_ROW_SELECTOR);
      processRowsInSelectedTable(tableRowElements, Category.RESULTS);

      log.info("Updates count after results page : {}", kuUpdatesList.size());

    } catch (SelectorParseException | IOException ex) {
      log.error("Failed to parse RESULTS Page.", ex);
    }
  }

  private void parseLatestUpdates(AppConfigDatum appCfg) {
    try {
      Document doc = Jsoup.connect(appCfg.getKuHomepage()).get();
      Elements updatesListItems = doc.select(Constants.KU_HOME_LIST_ITEMS);
      updatesListItems.forEach(li -> {
        if (null != li && Constants.BTECH_PATTERN.matcher(li.text()).find()) {
          Element textContainer = li.selectFirst("div.common_text");
          Element navLink = li.selectFirst(Constants.TAG_ANCHOR);
          if (null != textContainer) {
            KUUpdate update = new KUUpdate();
            update.setCategory(Category.KU_HOME);
            update.setDescription(StringUtils.trim(textContainer.text()));

            if (null != navLink && StringUtils.isNotBlank(navLink.attr(Constants.ATTR_HREF)))
              update.setLink(StringUtils.appendIfMissingIgnoreCase(
                  navLink.attr(Constants.ATTR_HREF), appCfg.getKuHomepage()));

            if (StringUtils.isBlank(update.getLink()))
              update.setLink(appCfg.getKuHomepage());

            kuUpdatesList.add(update);
          }
        }
      });

      log.info("Updates count after KU Home page : {}", kuUpdatesList.size());

    } catch (SelectorParseException | IOException ex) {
      log.error("Failed to parse Latest Updates Scroller.", ex);
    }
  }

  private void parseExamsHomeUpdates(AppConfigDatum appCfg) {
    try {
      Document doc = Jsoup.connect(appCfg.getKuExamsHomepage()).get();
      Elements marqueeLinks = doc.select(Constants.EXAMS_HOME_MARQUEE_LINKS);

      marqueeLinks.forEach(link -> {
        if (null != link && Constants.BTECH_PATTERN.matcher(link.text()).find()) {
          KUUpdate update = new KUUpdate();
          update.setCategory(Category.EXAM_HOME);
          update.setDescription(StringUtils.trim(link.text()));
          update.setLink(link.attr(Constants.ATTR_HREF));
          kuUpdatesList.add(update);
        }
      });

      log.info("Updates count after Exams Home page : {}", kuUpdatesList.size());

    } catch (SelectorParseException | IOException ex) {
      log.error("Failed to parse Exam Page Updates Scroller.", ex);
    }
  }

}
