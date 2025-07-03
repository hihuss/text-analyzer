package dedalus.com.text_analyzer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dedalus.com.text_analyzer.entity.AnalysisResult;
import dedalus.com.text_analyzer.repository.AnalysisResultRepository;
import dedalus.com.text_analyzer.service.ITextAnalyzerService;
import dedalus.com.text_analyzer.utils.AnalysisMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TextAnalyzerService implements ITextAnalyzerService {

    protected final AnalysisResultRepository analysisResultRepository;
    protected final ObjectMapper objectMapper;

    @Autowired
    public TextAnalyzerService(AnalysisResultRepository analysisResultRepository, ObjectMapper objectMapper) {
        this.analysisResultRepository = analysisResultRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public AnalysisResult analyzeText(String text, AnalysisMode analysisMode) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        Map<String, Long> analysisResult = findMatches(text, analysisMode);

        try {
            String jsonResult = objectMapper.writeValueAsString(analysisResult);
            return saveAnalysisResult(text, analysisMode, jsonResult);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // TODO: throw custom exception (i.e. global exception handling)
        }
    }

    public Map<String, Long> findMatches(String text, AnalysisMode analysisMode) {
        String vowelRegex = "[aeiouAEIOU]";
        String consonantRegex = "[bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ]";
        String regexToUse = (AnalysisMode.Vowels == analysisMode) ? vowelRegex : consonantRegex;

        Pattern pattern = Pattern.compile(regexToUse, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        return matcher.results()
                .map(matchResult -> matchResult.group().toLowerCase())
                .collect(Collectors.groupingBy(letter -> letter, Collectors.counting()));
    }

    private AnalysisResult saveAnalysisResult(String text, AnalysisMode analysisMode, String jsonResult) {
        AnalysisResult analysisResult = new AnalysisResult(text, analysisMode, jsonResult);
        return analysisResultRepository.save(analysisResult);
    }

    @Override
    public List<AnalysisResult> getPreviousResults() {
        return analysisResultRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
