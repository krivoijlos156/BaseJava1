package com.basejava.webapp.model;

import java.io.Serializable;

public interface IElement extends Serializable {

    String getDescription();

    void setDescription(String description);

    String getTitle();

    void setTitle(String title);
}
