package com.mohammad.relief.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "trigger_id")
    private Trigger trigger;
    @OneToMany(mappedBy = "solution", cascade = CascadeType.ALL)
    private List<SolutionFeedback> feedbacks = new ArrayList<>();

    @Transient
    public double getEfficiencyRate() {
        if (feedbacks == null || feedbacks.isEmpty()) return 0.0;
        long positives = feedbacks.stream().filter(SolutionFeedback::isEfficient).count();
        return (double) positives / feedbacks.size();
    }

}
