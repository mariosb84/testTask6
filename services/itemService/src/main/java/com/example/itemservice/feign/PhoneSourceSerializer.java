package com.example.itemservice.feign;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PhoneSourceSerializer extends JsonSerializer<PhoneSource> {

    @Override
    public void serialize(PhoneSource phoneSource, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        /* Начинаем массив*/
        jsonGenerator.writeStartArray();
        /* Перебираем список и записываем каждый элемент*/
        for (String phone : phoneSource.getSource()) {
            jsonGenerator.writeString(phone);
        }
        /* Закрываем массив*/
        jsonGenerator.writeEndArray();
    }

}
