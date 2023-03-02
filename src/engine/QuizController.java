package engine;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@RestController
@Validated
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping (path = "/api/quizzes/{id}")
    public String getQuiz(@PathVariable int id) throws JsonProcessingException {
        return quizService.getQuiz(id);
    }

    @GetMapping (path = "/api/quizzes")
    public String getAllQuizzes() throws JsonProcessingException {
        return quizService.getAllQuizzes();
    }

    @PostMapping (path = "/api/quizzes/{id}/solve")
    public String answerQuiz(@PathVariable int id, @RequestBody Quiz answer) {
        return quizService.checkAnswer(id, answer);
    }

    @PostMapping (path = "/api/quizzes")
    public String createQuiz(@Valid @RequestBody Quiz newQuiz) {

            return quizService.saveQuiz(newQuiz.getTitle(),
                    newQuiz.getText(), newQuiz.getOptions(), newQuiz.getAnswer());
    }



}
