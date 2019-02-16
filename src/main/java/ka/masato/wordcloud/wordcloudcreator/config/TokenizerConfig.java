package ka.masato.wordcloud.wordcloudcreator.config;

import com.atilika.kuromoji.ipadic.Tokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenizerConfig {

    @Bean
    public Tokenizer getTokenizer() {
        return new Tokenizer();
    }

}
