package real.al.knowles.ratpack.learning.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class JacksonModule extends AbstractModule {


    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .enable(INDENT_OUTPUT)
                .disable(FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                .setSerializationInclusion(NON_EMPTY)
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }

}
