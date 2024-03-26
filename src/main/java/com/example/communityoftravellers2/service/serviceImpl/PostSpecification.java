package com.example.communityoftravellers2.service.serviceImpl;

import com.example.communityoftravellers2.enums.HistoricalPeriod;
import com.example.communityoftravellers2.model.Post;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PostSpecification {

    public static Specification<Post> nameContains(String keyword){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("keyword"), "%" + keyword + "%"));
    }

    public static Specification<Post> topicEquals(String topic){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("topic"), topic));
    }

    public static Specification<Post> historicalPeriodBetween(HistoricalPeriod historicalPeriod) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("historicalPeriod"), historicalPeriod);
    }
}
