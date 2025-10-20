package org.acme;

import dev.langchain4j.model.embedding.EmbeddingModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.security.Encrypter;
import org.jboss.logging.Logger;

import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class VectorCalculator {

    @Inject
    Logger logger;

    @Inject
    EmbeddingModel embeddingModel;

    @Inject
    Encrypter encrypter;

    float[] generateQueryVector(String query) {
        float[] vector = embeddingModel.embed(query)
                                        .content()
                                        .vector();

        try {
            return encrypter.generateQueryVector(vector);
        } catch (ExecutionException | InterruptedException e) {
            logger.infof("Couldn't generate query vector", e);
        }

        return new float[384];
    }

    Question calculate(Question q) {

        float[] embed = embeddingModel
                            .embed(q.question)
                                .content()
                                .vector();

        try {
            q.embedding = encrypter.vector(embed);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Couldn't encrypt a vector", e);
        }

        return q;
    }

}
