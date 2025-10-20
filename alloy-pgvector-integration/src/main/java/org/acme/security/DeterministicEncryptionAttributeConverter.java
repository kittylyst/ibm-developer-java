package org.acme.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Converter
public class DeterministicEncryptionAttributeConverter implements AttributeConverter<String, String> {

    @Inject
    Encrypter encrypter;

    @Inject
    Decrypter decrypter;

    @Override
    public String convertToDatabaseColumn(String s) {
        try {
            return encrypter.deterministic(s);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String s) {
        try {
            return decrypter.deterministic(s);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
