package org.acme;

import io.quarkus.runtime.Startup;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.acme.security.Decrypter;
import org.acme.security.Encrypter;
import org.jboss.resteasy.reactive.RestQuery;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/")
public class QuestionsResource {

    @Inject
    QuestionsImporter questionsImporter;

    @Inject
    VectorCalculator vectorCalculator;

    @Startup
    public void importQuestions() throws IOException {
        questionsImporter.importQuestions();
    }

    @GET
    public List<QuestionPojo> suggestQuestions(@RestQuery String query) {
        float[] queryVector = vectorCalculator.generateQueryVector(query);

        return Question.suggestQuestions(queryVector)
                .stream()
                .map(this::transform)
                .toList();
    }

    @Inject
    Encrypter encrypter;

    @GET
    @Path("/round")
    public List<QuestionPojo> suggestQuestions(@RestQuery String query,
                                               @RestQuery String round) throws ExecutionException, InterruptedException {
        float[] queryVector = vectorCalculator.generateQueryVector(query);
        String encryptedRound = encrypter.deterministic(round);

        return Question.suggestQuestions(queryVector, encryptedRound)
                .stream()
                .map(this::transform)
                .toList();
    }

    private QuestionPojo transform(Question q) {

        return new QuestionPojo(q.question, q.round);

    }
}
