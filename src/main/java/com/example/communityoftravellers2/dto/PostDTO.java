package com.example.communityoftravellers2.dto;

import com.example.communityoftravellers2.enums.HistoricalPeriod;
import lombok.Data;

@Data
public class PostDTO {
    private String topic;
    private String description;
    private String encounteredFigure;
    private String futurePredictions;
    private HistoricalPeriod historicalPeriod;
}
