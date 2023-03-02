package engine;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

public class Quiz {
    int id;

    @NotBlank
    String title;
    @NotBlank
    String text;

    @Size(min = 2)
    @NotNull
    String [] options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Optional<Integer[]> answer = Optional.empty();



    public Quiz() {}
    public Quiz(Optional<Integer[]> answer){this.answer = answer;}

    public Quiz(int id, String title, String text, String[] options, Optional<Integer[]> answer) {

        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
        this.id = id;
    }

    public Optional<Integer[]> getAnswer() {
        return answer;
    }

    public void setAnswer(Optional<Integer[]> answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

}
