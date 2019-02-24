package ka.masato.wordcloud.wordcloudcreator.controller.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class CreateRequest implements Serializable {

    private Integer imageId;

    @NotNull
    @Pattern(regexp = "^https?://[a-zA-Z0-9/:%#&~=_!'\\$\\?\\(\\)\\.\\+\\*\\-]+$", message = "{validation.url.message}")
    private String targetUrl;

    @NotNull
    @Min(100)
    @Max(1024)
    private Integer width;

    @NotNull
    @Min(100)
    @Max(768)
    private Integer height;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
