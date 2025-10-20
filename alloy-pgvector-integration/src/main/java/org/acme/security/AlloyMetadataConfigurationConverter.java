package org.acme.security;

import com.ironcorelabs.ironcore_alloy_java.AlloyMetadata;
import com.ironcorelabs.ironcore_alloy_java.TenantId;
import org.eclipse.microprofile.config.spi.Converter;

public class AlloyMetadataConfigurationConverter implements Converter<AlloyMetadata> {
    @Override
    public AlloyMetadata convert(String value) throws IllegalArgumentException, NullPointerException {
        return  AlloyMetadata.newSimple(new TenantId(value));
    }
}
