package engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    ObjectMapper objectMapper = new ObjectMapper();

    List<Quiz> quizList = new ArrayList<>();

    public String saveQuiz(String title, String text, String [] options, Optional<Integer[]> answer) {
        int id = 1;
        if (quizList.size() > 0) {
            id = quizList.stream().map(Quiz::getId).max(Integer::compare).get() + 1;
        }

        quizList.add(new Quiz(id, title, text, options, answer));

        int idForLambda = id;
        Quiz returnQuiz = quizList.stream()
                .filter(a -> a.getId() == idForLambda)
                .findFirst().get();
        try {
            return objectMapper.writeValueAsString(returnQuiz);
        } catch (Exception e) {
            return "Quiz not found";
        }
    }

    public String getQuiz(int id) {
        Optional<Quiz> returnQuiz = quizList.stream().filter(a -> a.getId() == id).findFirst();
        try {
            return objectMapper.writeValueAsString(returnQuiz.get());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public String checkAnswer(int id, Quiz answer) {

        List<Optional<Integer[]>> listCorrectAnswerOpt = quizList.stream()
                .filter(a -> a.getId() == id)
                .map(Quiz::getAnswer)
                .collect(Collectors.toList());

        Optional<Integer[]> correctAnswerOpt = Optional.ofNullable(listCorrectAnswerOpt.get(0))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        String correct = "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
        String wrong = "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";

        if (correctAnswerOpt.isEmpty()) {
            if (answer.getAnswer().isEmpty()
                    || Arrays.toString(answer.getAnswer().get()) == "[]") {
                return correct;
            } else {
                return wrong;
            }
        }

        Integer[] userAnswer = answer.getAnswer().get();
        Integer[] correctAnswer = correctAnswerOpt.get();

        if (Arrays.equals(correctAnswer, userAnswer)) {
            return correct;
        } else {
            return wrong;
        }
    }

    public String getAllQuizzes() {
        String text = "[\n ";
        for (Quiz quiz : quizList) {
            try {
                text += objectMapper.writeValueAsString(quiz);
            } catch (Exception e) {
                text += "";
            }
        }
        text = text.replace("}{", "},\n {");
        text += "\n]";

        return text;
    }

}
