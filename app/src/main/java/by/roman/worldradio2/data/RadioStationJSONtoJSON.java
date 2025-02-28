// Converter.java

// To use this code, add the following Maven dependency to your project:
//
//     org.projectlombok : lombok : 1.18.2
//     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
//     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
//
// Import this package:
//
//     import io.quicktype.Converter;
//
// Then you can deserialize a JSON string with
//
//     RadioStationModel data = Converter.fromJsonString(jsonString);

package by.roman.worldradio2.data;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

class Converter {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    public static RadioStationJSONtoJSON fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(RadioStationJSONtoJSON obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return Converter.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(RadioStationJSONtoJSON.class);
        writer = mapper.writerFor(RadioStationJSONtoJSON.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) instantiateMapper();
        return writer;
    }
}

// RadioStationModel.java

@lombok.Data
public class RadioStationJSONtoJSON {
    @lombok.Getter(onMethod_ = {@JsonProperty("id")})
    @lombok.Setter(onMethod_ = {@JsonProperty("id")})
    private UUID id;
    @lombok.Getter(onMethod_ = {@JsonProperty("name")})
    @lombok.Setter(onMethod_ = {@JsonProperty("name")})
    private String name;
    @lombok.Getter(onMethod_ = {@JsonProperty("request")})
    @lombok.Setter(onMethod_ = {@JsonProperty("request")})
    private Request request;
    @lombok.Getter(onMethod_ = {@JsonProperty("response")})
    @lombok.Setter(onMethod_ = {@JsonProperty("response")})
    private Response response;
    @lombok.Getter(onMethod_ = {@JsonProperty("uuid")})
    @lombok.Setter(onMethod_ = {@JsonProperty("uuid")})
    private UUID uuid;
    @lombok.Getter(onMethod_ = {@JsonProperty("persistent")})
    @lombok.Setter(onMethod_ = {@JsonProperty("persistent")})
    private boolean persistent;
    @lombok.Getter(onMethod_ = {@JsonProperty("insertionIndex")})
    @lombok.Setter(onMethod_ = {@JsonProperty("insertionIndex")})
    private long insertionIndex;
}

// Request.java
@lombok.Data
class Request {
    @lombok.Getter(onMethod_ = {@JsonProperty("url")})
    @lombok.Setter(onMethod_ = {@JsonProperty("url")})
    private String url;
    @lombok.Getter(onMethod_ = {@JsonProperty("method")})
    @lombok.Setter(onMethod_ = {@JsonProperty("method")})
    private String method;
    @lombok.Getter(onMethod_ = {@JsonProperty("bodyPatterns")})
    @lombok.Setter(onMethod_ = {@JsonProperty("bodyPatterns")})
    private List<BodyPattern> bodyPatterns;
}

// BodyPattern.java


@lombok.Data
class BodyPattern {
    @lombok.Getter(onMethod_ = {@JsonProperty("equalTo")})
    @lombok.Setter(onMethod_ = {@JsonProperty("equalTo")})
    private String equalTo;
    @lombok.Getter(onMethod_ = {@JsonProperty("caseInsensitive")})
    @lombok.Setter(onMethod_ = {@JsonProperty("caseInsensitive")})
    private boolean caseInsensitive;
}

// Response.java

@lombok.Data
class Response {
    @lombok.Getter(onMethod_ = {@JsonProperty("status")})
    @lombok.Setter(onMethod_ = {@JsonProperty("status")})
    private long status;
    @lombok.Getter(onMethod_ = {@JsonProperty("body")})
    @lombok.Setter(onMethod_ = {@JsonProperty("body")})
    private String body;
    @lombok.Getter(onMethod_ = {@JsonProperty("headers")})
    @lombok.Setter(onMethod_ = {@JsonProperty("headers")})
    private Headers headers;
}

// Headers.java

@lombok.Data
class Headers {
    @lombok.Getter(onMethod_ = {@JsonProperty("Server")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Server")})
    private String server;
    @lombok.Getter(onMethod_ = {@JsonProperty("Access-Control-Allow-Origin")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Access-Control-Allow-Origin")})
    private String accessControlAllowOrigin;
    @lombok.Getter(onMethod_ = {@JsonProperty("Access-Control-Allow-Methods")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Access-Control-Allow-Methods")})
    private String accessControlAllowMethods;
    @lombok.Getter(onMethod_ = {@JsonProperty("Access-Control-Allow-Headers")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Access-Control-Allow-Headers")})
    private String accessControlAllowHeaders;
    @lombok.Getter(onMethod_ = {@JsonProperty("Date")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Date")})
    private String date;
    @lombok.Getter(onMethod_ = {@JsonProperty("Content-Type")})
    @lombok.Setter(onMethod_ = {@JsonProperty("Content-Type")})
    private String contentType;
}
