package dedlaus.com.text_analyzer.repository;

import dedlaus.com.text_analyzer.entity.AnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, Integer> {

}
