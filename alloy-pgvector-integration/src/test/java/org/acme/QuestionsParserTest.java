package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class QuestionsParserTest {

    @Inject
    QuestionsParser questionsParser;

    @Test
    void shouldParseQuestions() throws IOException {

        List<QuestionPojo> questionPojos = questionsParser.parseQuestions();
        assertThat(questionPojos).hasSize(100);

    }

}
