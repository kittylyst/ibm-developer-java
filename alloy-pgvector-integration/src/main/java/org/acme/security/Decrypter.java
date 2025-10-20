package org.acme.security;

import com.ironcorelabs.ironcore_alloy_java.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Base64;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class Decrypter {

    @Inject
    Standalone sdk;

    @ConfigProperty(name = "alloy.tenant", defaultValue = "tenant-1")
    AlloyMetadata alloyMetadata;

    public String deterministic(String content) throws ExecutionException, InterruptedException {
        byte[] decode = Base64.getDecoder().decode(content);
        EncryptedBytes encryptedBytes = new EncryptedBytes(decode);
        EncryptedField encryptedField = new EncryptedField(encryptedBytes,
                new SecretPath(AlloyStandaloneProducer.SECRET_PATH),
                new DerivationPath(Encrypter.DERIVATION_PATH));
        PlaintextField plaintextField = sdk.deterministic().decrypt(encryptedField, alloyMetadata).get();

        return new String(plaintextField.plaintextField().value());
    }

}
