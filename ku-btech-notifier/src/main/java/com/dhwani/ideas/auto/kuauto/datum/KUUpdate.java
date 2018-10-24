package com.dhwani.ideas.auto.kuauto.datum;

import org.apache.commons.lang3.StringUtils;
import com.dhwani.ideas.auto.kuauto.utils.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KUUpdate {
  private Category category;
  private String description;
  private String publishedOn;
  private String link;

  @Override
  public boolean equals(Object obj) {

    if (obj instanceof KUUpdate) {
      KUUpdate update = (KUUpdate) obj;

      if (StringUtils.isNotBlank(this.description) && StringUtils.isNotBlank(update.description))
        return this.description.trim().equalsIgnoreCase(update.description.trim());
    }

    return super.equals(obj);
  }
}
