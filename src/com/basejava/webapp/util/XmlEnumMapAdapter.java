package com.basejava.webapp.util;

import com.basejava.webapp.model.EntryXml;
import com.basejava.webapp.model.IElement;
import com.basejava.webapp.model.ListOfEntryXml;
import com.basejava.webapp.model.SectionType;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class XmlEnumMapAdapter extends XmlAdapter<ListOfEntryXml, EnumMap<SectionType, List<IElement>>> {
    @Override
    public EnumMap<SectionType, List<IElement>> unmarshal(ListOfEntryXml listOfEntry) throws Exception {
        EnumMap<SectionType, List<IElement>> map = new EnumMap<>(SectionType.class);
        for(EntryXml entry : listOfEntry.getList() ) {
            map.put(entry.getKey(), entry.getList() );
        }
        return map;
    }

    @Override
    public ListOfEntryXml marshal(EnumMap<SectionType, List<IElement>> sectionTypeListEnumMap) throws Exception {
        ListOfEntryXml loe = new ListOfEntryXml();
        for(Map.Entry<SectionType, List<IElement>> mapEntry : sectionTypeListEnumMap.entrySet()) {
            EntryXml entry = new EntryXml();
            entry.setKey( mapEntry.getKey() );
            entry.getList().addAll( mapEntry.getValue() );
            loe.getList().add(entry);
        }
        return loe;
    }
}
