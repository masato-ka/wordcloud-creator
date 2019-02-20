package ka.masato.wordcloud.wordcloudcreator.domain.service;

import ka.masato.wordcloud.wordcloudcreator.domain.repository.RawTextRepository;
import ka.masato.wordcloud.wordcloudcreator.domain.repository.WordCloudImageRepository;
import ka.masato.wordcloud.wordcloudcreator.util.WordCloudBuilder;
import ka.masato.wordcloud.wordcloudcreator.util.WordCounter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WordCloudImageService {

    private RawTextRepository rawTextRepository;
    private WordCloudImageRepository wordCloudImageRepository;
    private WordCloudBuilder wordCloudBuilder;
    private WordCounter wordCounter;

    public WordCloudImageService(RawTextRepository rawTextRepository,
                                 WordCloudImageRepository wordCloudImageRepository,
                                 WordCloudBuilder wordCloudBuilder, WordCounter wordCounter) {
        this.rawTextRepository = rawTextRepository;
        this.wordCloudImageRepository = wordCloudImageRepository;
        this.wordCloudBuilder = wordCloudBuilder;
        this.wordCounter = wordCounter;
    }

    public String createImage(String url, int width, int height) {
        Optional<String> result = rawTextRepository.getRawText(url)
                .map((rawText -> wordCounter.countToken(rawText)))
                .map(dataSet -> wordCloudBuilder.setWidth(width).setHeight(height).setDataSet(dataSet).build())
                .map(resultImage -> wordCloudImageRepository.saveImage(resultImage));
        return result.get(); //TODO Must be add Error handling.
    }

}
