package com.basejava.webapp.util;

import com.basejava.webapp.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.EnumMap;

public class JsonParser {
    private static Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(IElement.class, new JsonInheritanceAdapter<IElement>())
            .registerTypeAdapter(
                    new TypeToken<EnumMap<ContactType, String>>() {
                    }.getType(),
                    new JsonEnumMapInstanceCreator<ContactType, String>(ContactType.class))
            .registerTypeAdapter(
                    new TypeToken<EnumMap<SectionType, ArrayList<IElement>>>() {
                    }.getType(),
                    new JsonEnumMapInstanceCreator<SectionType, ArrayList<IElement>>(SectionType.class))
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

}
