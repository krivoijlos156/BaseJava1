package com.basejava.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;

public interface IElement extends Serializable {

    String getDescription();

    void setDescription(String description);

    String getTitle();

    void setTitle(String title);

    LocalDate getStartDate();

    LocalDate getEndDate();

    Link getLink();
}
