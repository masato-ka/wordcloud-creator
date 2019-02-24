package ka.masato.wordcloud.wordcloudcreator.controller;

import ka.masato.wordcloud.wordcloudcreator.controller.model.CreateRequest;
import ka.masato.wordcloud.wordcloudcreator.controller.model.ImageResult;
import ka.masato.wordcloud.wordcloudcreator.domain.service.WordCloudImageService;
import org.springframework.context.MessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordCloudImageController {

    private MessageSource messageSource;
    private WordCloudImageService wordCloudImageService;

    public WordCloudImageController(MessageSource messageSource, WordCloudImageService wordCloudImageService) {
        this.messageSource = messageSource;
        this.wordCloudImageService = wordCloudImageService;
    }

    @PostMapping("/api/v1/images")
    public ImageResult create(@RequestBody @Validated CreateRequest createRequest) {

        ImageResult imageResult = new ImageResult();

        String path = wordCloudImageService.createImage(createRequest.getTargetUrl(),
                createRequest.getWidth(), createRequest.getHeight());

        imageResult.setImagePath(path);

        return imageResult;

    }


}
