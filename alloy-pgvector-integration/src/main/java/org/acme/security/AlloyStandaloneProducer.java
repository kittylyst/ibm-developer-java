package org.acme.security;

import com.ironcorelabs.ironcore_alloy_java.AlloyException;
import com.ironcorelabs.ironcore_alloy_java.RotatableSecret;
import com.ironcorelabs.ironcore_alloy_java.Secret;
import com.ironcorelabs.ironcore_alloy_java.SecretPath;
import com.ironcorelabs.ironcore_alloy_java.Standalone;
import com.ironcorelabs.ironcore_alloy_java.StandaloneConfiguration;
import com.ironcorelabs.ironcore_alloy_java.StandaloneSecret;
import com.ironcorelabs.ironcore_alloy_java.StandardSecrets;
import com.ironcorelabs.ironcore_alloy_java.VectorSecret;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@ApplicationScoped
public class AlloyStandaloneProducer {

    public static final String SECRET_PATH = "jeopardy";

    @ConfigProperty(name = "alloy.key")
    String key;

    @ConfigProperty(name = "alloy.approximation-factor")
    Float approximationFactor;

    @Produces
    VectorSecret createsVectorSecret() throws AlloyException {
        StandaloneSecret standaloneSecret =
                new StandaloneSecret(1, new Secret(key.getBytes()));

        return
                VectorSecret.newWithScalingFactor(approximationFactor, new RotatableSecret(standaloneSecret, null));
    }

    @Produces
    StandardSecrets createStandardSecrets() throws AlloyException {
        return new StandardSecrets(null, new ArrayList<>());
    }

    @Produces
    RotatableSecret createDeterministicSecret() throws AlloyException {
        StandaloneSecret standaloneSecret =
                new StandaloneSecret(1, new Secret(key.getBytes()));
        return new RotatableSecret(standaloneSecret, null);
    }

    @Produces
    Standalone createStandalone(VectorSecret vector, StandardSecrets standard, RotatableSecret deterministic) {

        Map<SecretPath, VectorSecret> vectorSecrets =
                Collections.singletonMap(new SecretPath(SECRET_PATH), vector);

        Map<SecretPath, RotatableSecret> deterministicSecrets =
                Collections.singletonMap(new SecretPath(SECRET_PATH), deterministic);

        StandaloneConfiguration config =
                new StandaloneConfiguration(standard, deterministicSecrets, vectorSecrets);

        return new Standalone(config);

    }

}
