package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.security.Encrypter;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class QuestionsImporter {

    @Inject
    VectorCalculator vectorCalculator;

    @Inject
    QuestionsParser questionsParser;

    @Inject
    Logger logger;


    @Transactional
    public void importQuestions() throws IOException {

        final List<QuestionPojo> questions = questionsParser.parseQuestions();
        questions.stream()
                .map(this::transform)
                .map(vectorCalculator::calculate)
                .forEach(q -> {
                    q.persist();
                    logger.infof("Imported %s", q);
                });
    }

    private Question transform(QuestionPojo qp) {
        return new Question(qp.question(), qp.round());
    }
}
