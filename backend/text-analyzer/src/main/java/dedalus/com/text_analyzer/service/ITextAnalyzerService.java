package dedalus.com.text_analyzer.service;

import dedalus.com.text_analyzer.entity.AnalysisResult;
import dedalus.com.text_analyzer.utils.AnalysisMode;

import java.util.List;

public interface ITextAnalyzerService {

    AnalysisResult analyzeText(String text, AnalysisMode analysisMode);

    List<AnalysisResult> getPreviousResults();
}
