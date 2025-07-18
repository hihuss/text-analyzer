package dedalus.com.text_analyzer;

import dedalus.com.text_analyzer.entity.AnalysisResult;
import dedalus.com.text_analyzer.service.impl.TextAnalyzerService;
import dedalus.com.text_analyzer.utils.AnalysisMode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TextAnalyzerServiceTest {

    @InjectMocks
    TextAnalyzerService textAnalyzerService;

    @Test
    @DisplayName("Should return 0 when text is empty")
    public void testEmptyText() {
        AnalysisResult result = textAnalyzerService.analyzeText("", AnalysisMode.Vowels);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should return 0 when text is null")
    public void testTextNull() {
        AnalysisResult result = textAnalyzerService.analyzeText(null, AnalysisMode.Vowels);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should return 0 when text is only whitespaces")
    public void testTextWhitespaces() {
        AnalysisResult result = textAnalyzerService.analyzeText("              ", AnalysisMode.Vowels);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should return 0 when counting vowels in all consonants text")
    public void testOnlyConsonants() {
        Map<String, Long> result = textAnalyzerService.findMatches("bcdfg", AnalysisMode.Vowels);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return 0 when counting consonants in all vowels text")
    public void testOnlyVowels() {
        Map<String, Long> result = textAnalyzerService.findMatches("aeiou", AnalysisMode.Consonants);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return 0 when analyzing only numbers and characters text")
    public void testOnlyCharacters() {
        String text = "12345!@#$$%^&*()";

        assertThat(textAnalyzerService.findMatches(text, AnalysisMode.Consonants)).isEmpty();
        assertThat(textAnalyzerService.findMatches(text, AnalysisMode.Vowels)).isEmpty();
    }

    @Test
    @DisplayName("Should return correct results when analyzing text for consonants")
    public void testConsonants() {
        String text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

        Map<String, Long> result = textAnalyzerService.findMatches(text, AnalysisMode.Consonants);
        assertThat(result.size()).isEqualTo(18);
        assertThat(result.values().stream().reduce(0L, Long::sum)).isEqualTo(299);
    }

    @Test
    @DisplayName("Should return correct results when analyzing text for vowels")
    public void testVowels() {
        String text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

        Map<String, Long> result = textAnalyzerService.findMatches(text, AnalysisMode.Vowels);
        assertThat(result.size()).isEqualTo(5);
        assertThat(result.values().stream().reduce(0L, Long::sum)).isEqualTo(168);
    }

    @Test
    @DisplayName("Should return correct result when very long string")
    public void testVeryLongString() {
        String text = "a".repeat(1000000);
        Map<String, Long> result = textAnalyzerService.findMatches(text, AnalysisMode.Vowels);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get("a")).isEqualTo(1000000);
    }

}
