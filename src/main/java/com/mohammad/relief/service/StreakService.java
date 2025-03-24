package com.mohammad.relief.service;

import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Streak;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.repository.StreakRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StreakService {
    private StreakRepository repository;
    public StreakService(StreakRepository repository) {
        this.repository = repository;
    }

    public void setStreak(Streak streak) {
        repository.save(streak);
    }

    public Streak getStreakByUserIdAndAddiction(UUID userId, Integer addictionsId) throws ReliefApplicationException {
        Optional<Streak> optionalStreak = repository.findByUserIdAndAddictionsId(userId,addictionsId);
        return optionalStreak.orElseThrow(() -> new ReliefApplicationException("The streak does not exist"));
    }


    public List<Streak> getAllStreaks() {
        return repository.findAll();
    }
}
