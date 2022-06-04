package com.unitests.spring.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Junit 5 parameterized testing resolver, which support converting Json file to java bean
 */
public class JsonFileArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<JsonFileSource> {

    private JsonFileSource source;
    private final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();


    @Override
    public void accept(JsonFileSource jsonFileSource) {
        Preconditions.condition(!"".equals(jsonFileSource.value()), () -> "You should give the file path as value");
        this.source = jsonFileSource;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Class<?>[] parameterTypes = extensionContext.getTestMethod().map(Method::getParameterTypes).orElseThrow();
        try (InputStream file = this.getClass().getClassLoader().getResourceAsStream(source.value())) {
//            Object result = objectMapper.readValue(file, parameterTypes[0]);
            String result = new BufferedReader(new InputStreamReader(file))
                    .lines().collect(Collectors.joining("\n"));
            return Stream.of(result).map(Arguments::of);
        }
    }
}
