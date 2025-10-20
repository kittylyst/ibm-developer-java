package org.acme.security;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class EncrypterTest {

    @Inject
    Encrypter encrypter;

    @Test
    void shouldEncryptVector() throws ExecutionException, InterruptedException {

        float[] v = {1f, 2f, 3f};
        float[] encrypted = encrypter.vector(v);

        assertThat(encrypted).isNotEmpty().isNotEqualTo(v);
    }

    @Test
    void shouldDeterministicEncrypt() throws ExecutionException, InterruptedException {

        String deterministic = encrypter.deterministic("alex");
        assertThat(deterministic).isBase64().isNotEqualTo("alex");

    }

    @Test
    public void shouldGenerateTheQueryVector() throws ExecutionException, InterruptedException {
        float[] floats = encrypter.generateQueryVector(new float[]{1f, 2f, 3f});
    }

}
