/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package com.enel.workbeat.kie.kieserver.json;

import java.io.IOException;
import java.time.LocalDateTime;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.kie.server.api.marshalling.json.JSONMarshaller;
import org.kie.server.api.marshalling.json.JSONMarshallerExtension;

public class JSONMarshallerExtensionLocalDateTime implements JSONMarshallerExtension {

    private static final LocalDateTimeDeserializer DESERIALIZER = new LocalDateTimeDeserializer();
    private static final LocalDateTimeSerializer SERIALIZER = new LocalDateTimeSerializer();

    @Override
    public void extend(JSONMarshaller marshaller, ObjectMapper serializer, ObjectMapper deserializer) {
        registerModule(serializer);
        registerModule(deserializer);
    }

    private void registerModule(ObjectMapper objectMapper) {
        SimpleModule gregorianCalendarModule = new SimpleModule("localdatetime-module", Version.unknownVersion());
        gregorianCalendarModule.addDeserializer(LocalDateTime.class, DESERIALIZER);
        gregorianCalendarModule.addSerializer(LocalDateTime.class, SERIALIZER);
        objectMapper.registerModule(gregorianCalendarModule);
    }

    private static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime>{

        @Override
        public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            String text = jp.getText();
            LocalDateTime parse = LocalDateTime.parse(text);
            return parse;
        }

    }

    private static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime>{

        @Override
        public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException, JsonProcessingException {
            String serialized = value.toString();
            jgen.writeString(serialized);
        }

    }
}
