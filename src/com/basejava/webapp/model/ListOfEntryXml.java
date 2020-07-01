package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListOfEntryXml {
    @XmlElement
    private List<EntryXml> list = new ArrayList<>();
    public List<EntryXml> getList(){ return list; }
}
