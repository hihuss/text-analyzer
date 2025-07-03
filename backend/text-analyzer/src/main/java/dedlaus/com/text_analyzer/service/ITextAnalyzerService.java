package dedlaus.com.text_analyzer.service;

import dedlaus.com.text_analyzer.entity.AnalysisResult;
import dedlaus.com.text_analyzer.utils.AnalysisMode;

import java.util.List;

public interface ITextAnalyzerService {

    AnalysisResult analyzeText(String text, AnalysisMode analysisMode);

    List<AnalysisResult> getPreviousResults();
}
