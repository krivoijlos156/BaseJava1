package com.basejava.webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonInheritanceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T>{

    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE  = "INSTANCE";

    @Override
    public JsonElement serialize(T src, Type typeOfSrc,
                                 JsonSerializationContext context) {

        JsonObject retValue = new JsonObject();
        JsonElement elem = context.serialize(src);
        retValue.add(INSTANCE, elem);
        String className = src.getClass().getName();
        retValue.addProperty(CLASSNAME, className);
        return retValue;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT,
                               JsonDeserializationContext context) throws JsonParseException  {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();

        Class<?> klass = null;
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return context.deserialize(jsonObject.get(INSTANCE), klass);
    }

}