package ka.masato.wordcloud.wordcloudcreator.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApiErrorMessage implements Serializable {

    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Detail> details = new ArrayList<>();

    public static class Detail implements Serializable {
        private final String target;
        private final String message;

        public String getTarget() {
            return target;
        }

        public String getMessage() {
            return message;
        }

        public Detail(String target, String message) {
            this.target = target;
            this.message = message;
        }
    }

    public ApiErrorMessage() {
    }

    public ApiErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(String target, String message) {
        this.details.add(new Detail(target, message));
    }
}
