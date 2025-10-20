package org.acme;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.io.IOException;

import java.net.URI;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class QuestionsParser {

    @Inject
    Logger logger;

    @ConfigProperty(name = "questions.number", defaultValue = "100")
    int numberOfQuestions;

    @ConfigProperty(name = "questions.url")
    URI questionsUri;

    List<QuestionPojo> parseQuestions() throws IOException {
        List<QuestionPojo> questions = new ArrayList<>();

        logger.infof("Starting importing questions from %s", questionsUri);

        JsonFactory factory = new JsonFactory();
        try (JsonParser parser = factory.createParser(questionsUri.toURL().openStream())) {

            // Expect START_ARRAY
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalStateException("Expected an array");
            }

            int currentQuestion = 0;
            // Iterate over objects inside array
            while (parser.nextToken() == JsonToken.START_OBJECT
                    && currentQuestion < numberOfQuestions) {

                String round = null;
                String question = null;

                while (parser.nextToken() != JsonToken.END_OBJECT) {
                    String fieldName = parser.currentName();
                    parser.nextToken(); // move to value token
                    switch (fieldName) {
                        case "Round" -> round = parser.getValueAsString();
                        case "Question" -> question = parser.getValueAsString();
                        default -> parser.skipChildren();
                    }
                }
                currentQuestion++;
                questions.add(new QuestionPojo(question, round));
            }
        }

        logger.infof("Imported %d questions", questions.size());
        return questions;
    }

}
