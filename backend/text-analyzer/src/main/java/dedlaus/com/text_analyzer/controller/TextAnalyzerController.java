package dedlaus.com.text_analyzer.controller;

import dedlaus.com.text_analyzer.service.impl.TextAnalyzerService;
import dedlaus.com.text_analyzer.utils.AnalysisMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextAnalyzerController {

    TextAnalyzerService textAnalyzerService;

    @Autowired
    public TextAnalyzerController(TextAnalyzerService textAnalyzerService) {
        this.textAnalyzerService = textAnalyzerService;
    }

    @PostMapping(value = "/analyze-text")
    public ResponseEntity<Integer> analyzeText(@RequestBody String text, @RequestBody AnalysisMode analysisMode) {
        Integer result = textAnalyzerService.analyzeText(text, analysisMode);

        return ResponseEntity.ok().body(result);
    }
}
