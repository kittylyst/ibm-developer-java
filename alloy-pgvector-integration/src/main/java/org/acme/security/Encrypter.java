package org.acme.security;

import com.ironcorelabs.ironcore_alloy_java.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class Encrypter {

    public static final String DERIVATION_PATH = "sentence";

    @Inject
    Standalone sdk;

    @ConfigProperty(name = "alloy.tenant", defaultValue = "tenant-1")
    AlloyMetadata alloyMetadata;

    public String deterministic(String content) throws ExecutionException, InterruptedException {
        PlaintextBytes plaintextBytes = new PlaintextBytes(content.getBytes());

        PlaintextField plaintextField = new PlaintextField(plaintextBytes,
                new SecretPath(AlloyStandaloneProducer.SECRET_PATH),
                new DerivationPath(DERIVATION_PATH));

        CompletableFuture<EncryptedField> encryptedFieldCompletableFuture = sdk.deterministic().encrypt(plaintextField, alloyMetadata);
        EncryptedField encryptedField = encryptedFieldCompletableFuture.get();
        byte[] field = encryptedField.encryptedField().value();

        return Base64.getEncoder().encodeToString(field);
    }

    public float[] generateQueryVector(float[] embeddingQuery) throws ExecutionException, InterruptedException {
        PlaintextVector pv = new PlaintextVector(getFloats(embeddingQuery),
                new SecretPath(AlloyStandaloneProducer.SECRET_PATH),
                new DerivationPath(DERIVATION_PATH));
        Map<VectorId, PlaintextVector> vectors = Map.of(new VectorId("vec-1"), pv);
        PlaintextVectors pvs = new PlaintextVectors(vectors);

        GenerateVectorQueryResult generateVectorQueryResult = sdk.vector().generateQueryVectors(pvs, alloyMetadata).get();

        // No rotation key used so always the same element
        EncryptedVector encryptedVector = generateVectorQueryResult.value()
                .get(new VectorId("vec-1")).getFirst();

        return getEncryptedVectorArray(encryptedVector.encryptedVector());

    }

    public float[] vector(float[] vector) throws ExecutionException, InterruptedException {

        List<Float> encryptVector = getFloats(vector);

        PlaintextVector pv = new PlaintextVector(encryptVector,
                new SecretPath(AlloyStandaloneProducer.SECRET_PATH),
                new DerivationPath(DERIVATION_PATH));

        List<Float> encryptedVector = sdk.vector().encrypt(pv, alloyMetadata).get().encryptedVector();

        return getEncryptedVectorArray(encryptedVector);
    }

    private static float[] getEncryptedVectorArray(List<Float> encryptedVector) {
        float[] encryptedVectorArray = new float[encryptedVector.size()];

        for (int i = 0; i < encryptedVector.size(); i++) {
            encryptedVectorArray[i] = encryptedVector.get(i);
        }
        return encryptedVectorArray;
    }

    private static List<Float> getFloats(float[] vector) {
        List<Float> encryptVector = new ArrayList<>(vector.length);

        for(float v : vector) {
            encryptVector.add(v);
        }
        return encryptVector;
    }

}
