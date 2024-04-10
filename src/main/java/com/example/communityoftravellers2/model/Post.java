package com.example.communityoftravellers2.model;

import com.example.communityoftravellers2.enums.HistoricalPeriod;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String topic;
    @Enumerated(EnumType.STRING)
    private HistoricalPeriod historicalPeriod;
    @Column(nullable = false)
    private String description;
    private String encounteredFigure;
    private String futurePredictions;
    private long upVotes = 0;
    private long downVotes = 0;

    @ElementCollection
    private Set<Long> upVotedUsers = new HashSet<>();

    @ElementCollection
    private Set<Long> downVotedUsers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
