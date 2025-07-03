package dedlaus.com.text_analyzer.controller;

import dedlaus.com.text_analyzer.dto.AnalysisResultDTO;
import dedlaus.com.text_analyzer.entity.AnalysisResult;
import dedlaus.com.text_analyzer.mapper.AnalysisResultMapper;
import dedlaus.com.text_analyzer.service.impl.TextAnalyzerService;
import dedlaus.com.text_analyzer.utils.AnalysisMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class TextAnalyzerController {

    TextAnalyzerService textAnalyzerService;
    AnalysisResultMapper analysisResultMapper;

    @Autowired
    public TextAnalyzerController(TextAnalyzerService textAnalyzerService, AnalysisResultMapper analysisResultMapper) {
        this.textAnalyzerService = textAnalyzerService;
        this.analysisResultMapper = analysisResultMapper;
    }

    @PostMapping(value = "/analyze-text/{analysisMode}")
    public ResponseEntity<AnalysisResultDTO> analyzeText(@RequestBody String text, @PathVariable AnalysisMode analysisMode) {
        AnalysisResult result = textAnalyzerService.analyzeText(text, analysisMode);
        return ResponseEntity.ok().body(analysisResultMapper.mapToDto(result));
    }

    @GetMapping(value = "/history-data")
    public ResponseEntity<List<AnalysisResultDTO>> getHistoryData() {
        List<AnalysisResult> previousResults = this.textAnalyzerService.getPreviousResults();

        return ResponseEntity.ok().body(analysisResultMapper.mapToDToList(previousResults));
    }
}
