package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQuery;
import org.acme.security.DeterministicEncryptionAttributeConverter;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Entity
@NamedQuery(name = "suggestQuestions",
        query = "FROM Question q ORDER BY cosine_distance(q.embedding, :vector) LIMIT 5")
@NamedNativeQuery(name = "suggestQuestionsByRound", resultClass = Question.class,
        query = "SELECT * FROM Question WHERE round = :round ORDER BY embedding <=> cast(:vector as vector) LIMIT 5;")
public class Question extends PanacheEntity {

    @Column(length = 65_535)
    public String question;

    @Column(length = 256)
    @Convert(converter = DeterministicEncryptionAttributeConverter.class)
    public String round;

    @Column
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 384)
    public float[] embedding;

    public Question() { }

    public Question(String question, String round) {
        this.question = question;
        this.round = round;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Question.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("question='" + question.substring(0, Math.min(question.length(), 20)) + "...'")
                .add("round='" + round + "'")
                .add("embedding='" + Arrays.toString(Arrays.copyOfRange(embedding, 0, 8)) + "...'")
                .toString();
    }

    // Round must be encrypted to work as the field in the database is encrypted too
    public static List<Question> suggestQuestions(float[] vector, String round) {
        return getEntityManager()
                .createNamedQuery("suggestQuestionsByRound", Question.class)
                .setParameter("vector", vector)
                .setParameter("round", round)
                .getResultList();
    }

    public static List<Question> suggestQuestions(float[] vector) {
        return find("#suggestQuestions",
                    Parameters.with("vector", vector).map())
                .list();

    }

}
