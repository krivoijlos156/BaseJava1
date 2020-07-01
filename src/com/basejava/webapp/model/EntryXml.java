package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class EntryXml {
    @XmlElement
    private SectionType key;
    @XmlElement
    private List<IElement> list = new ArrayList<>();
    public SectionType getKey(){ return key; }
    public void setKey( SectionType value ){ key = value; }
    public List<IElement> getList(){ return list; }
}
