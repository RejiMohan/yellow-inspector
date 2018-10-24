package com.dhwani.ideas.auto.kuauto;

import java.time.Instant;
import com.dhwani.ideas.auto.kuauto.services.EmailSender;
import com.dhwani.ideas.auto.kuauto.services.PageParserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutoAppBase {

  public static void main(String... args) {
    log.info("Starting automation App - [{}]", Instant.now());
    PageParserService parserService = new PageParserService();
    EmailSender.sendEmailNotification(parserService.parsePagesAndgetUpdates());
    log.info("Process completed -- [{}]", Instant.now());

  }

}
