package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class QuestionsImporterTest {

    @Inject
    QuestionsImporter questionsImporter;

    @Test
    void shouldImportQuestions() throws IOException {

        questionsImporter.importQuestions();
        Question q = Question.findAll().firstResult();
        assertThat(q.embedding).isNotEmpty();
        assertThat(q.embedding[0]).isGreaterThan(1f);

    }


}
