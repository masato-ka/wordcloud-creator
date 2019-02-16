package ka.masato.wordcloud.wordcloudcreator.domain.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RawTextRepository {
    public Optional<String> getRawText(String url);
}
