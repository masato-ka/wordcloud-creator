package ka.masato.wordcloud.wordcloudcreator.controller.model;

public class CreateRequest {

    private Integer imageId;
    private String targerUrl;
    private Integer width;
    private Integer height;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getTargerUrl() {
        return targerUrl;
    }

    public void setTargerUrl(String targerUrl) {
        this.targerUrl = targerUrl;
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
