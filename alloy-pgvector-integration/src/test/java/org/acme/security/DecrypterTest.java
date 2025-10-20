package org.acme.security;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class DecrypterTest {

    @Inject
    Encrypter encrypter;

    @Inject
    Decrypter decrypter;

    @Test
    public void shouldDecryptData() throws ExecutionException, InterruptedException {
        String alexe = encrypter.deterministic("alex");
        String alex = decrypter.deterministic(alexe);

        assertThat(alex).isEqualTo("alex");

    }

}
