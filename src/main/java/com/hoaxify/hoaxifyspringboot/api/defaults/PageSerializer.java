package com.hoaxify.hoaxifyspringboot.api.defaults;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageSerializer extends JsonSerializer<Page<?>> {
    @Override
    public void serialize(Page<?> objects, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("content");
        serializerProvider.defaultSerializeValue(objects.getContent(), jsonGenerator);
        jsonGenerator.writeObjectField("pageable", objects.getPageable());
        jsonGenerator.writeObjectField("last", objects.isLast());
        jsonGenerator.writeObjectField("totalPages", objects.getTotalPages());
        jsonGenerator.writeObjectField("totalElements", objects.getTotalElements());
        jsonGenerator.writeObjectField("size", objects.getSize());
        jsonGenerator.writeObjectField("number", objects.getNumber());
        jsonGenerator.writeObjectField("sort", objects.getSort());
        jsonGenerator.writeObjectField("numberOfElements", objects.getNumberOfElements());
        jsonGenerator.writeObjectField("first", objects.isFirst());
        jsonGenerator.writeObjectField("empty", objects.isEmpty());
        jsonGenerator.writeEndObject();
    }
}
