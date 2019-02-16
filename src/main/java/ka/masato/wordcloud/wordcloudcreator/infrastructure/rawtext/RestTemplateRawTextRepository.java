package ka.masato.wordcloud.wordcloudcreator.infrastructure.rawtext;

import ka.masato.wordcloud.wordcloudcreator.domain.repository.RawTextRepository;
import ka.masato.wordcloud.wordcloudcreator.exception.KatariCloudFailedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

@Repository
public class RestTemplateRawTextRepository implements RawTextRepository {

    private RestTemplate restTemplate;


    public RestTemplateRawTextRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<String> getRawText(String url) {
        try {
            URL urlPath = new URL(url);
            URI uri = new URI(url);
        } catch (MalformedURLException e) {
            throw new KatariCloudFailedException("Malformed URL path. : " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new KatariCloudFailedException("Malformed URI path. : " + e.getMessage());
        }

        String result = restTemplate.getForObject(url, String.class);

        return Optional.ofNullable(result);
    }


}
