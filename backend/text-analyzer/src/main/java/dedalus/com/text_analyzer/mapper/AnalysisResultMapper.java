package dedalus.com.text_analyzer.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dedalus.com.text_analyzer.dto.AnalysisResultDTO;
import dedalus.com.text_analyzer.entity.AnalysisResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class AnalysisResultMapper {

    @Autowired
    protected ObjectMapper objectMapper;


    @Mapping(target = "results", expression = "java(parseResultJson(entity.getResultJson()))")
    public abstract AnalysisResultDTO mapToDto(AnalysisResult entity);

    protected Map<String, Long> parseResultJson(String json) {
        if (json == null || json.isBlank()) {
            return Map.of();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse result JSON", e); // TODO: throw custom exception
        }
    }

    public List<AnalysisResultDTO> mapToDToList(List<AnalysisResult> analysisResult) {
        return analysisResult.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
