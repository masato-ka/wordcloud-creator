package ka.masato.wordcloud.wordcloudcreator.controller;

import ka.masato.wordcloud.wordcloudcreator.controller.model.CreateRequest;
import ka.masato.wordcloud.wordcloudcreator.controller.model.ImageResult;
import ka.masato.wordcloud.wordcloudcreator.domain.service.WordCloudImageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordCloudImageController {

    private WordCloudImageService wordCloudImageService;

    public WordCloudImageController(WordCloudImageService wordCloudImageService) {
        this.wordCloudImageService = wordCloudImageService;
    }

    @PostMapping("/api/v1/images")
    public ImageResult create(CreateRequest createRequest) {

        ImageResult imageResult = new ImageResult();

        String path = wordCloudImageService.createImage(createRequest.getTargerUrl(),
                createRequest.getWidth(), createRequest.getHeight());

        imageResult.setImagePath(path);

        return imageResult;

    }

}
