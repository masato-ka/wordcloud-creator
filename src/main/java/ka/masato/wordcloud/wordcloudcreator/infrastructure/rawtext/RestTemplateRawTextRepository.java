package ka.masato.wordcloud.wordcloudcreator.infrastructure.rawtext;

import ka.masato.wordcloud.wordcloudcreator.domain.repository.RawTextRepository;
import ka.masato.wordcloud.wordcloudcreator.exception.FailedGetRawTextException;
import ka.masato.wordcloud.wordcloudcreator.exception.KatariCloudFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Repository
public class RestTemplateRawTextRepository implements RawTextRepository {

    private Logger logger = LoggerFactory.getLogger(RestTemplateRawTextRepository.class.getSimpleName());
    private RestTemplate restTemplate;


    public RestTemplateRawTextRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<String> getRawText(String url) {
        try {
            URL urlPath = new URL(url);
        } catch (MalformedURLException e) {
            logger.error("Malformed URL path : " + e.getMessage());
            throw new KatariCloudFailedException("Malformed URL path : " + e.getMessage());
        }
        String result = "";
        try {
            result = restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException ex) {
            throw new FailedGetRawTextException(ex.getMessage());
        }

        return Optional.ofNullable(result);
    }


}
