package com.mark.code.epamlab.service;

import com.mark.code.epamlab.model.Fibonachi;
import com.mark.code.epamlab.repositories.FibonachiRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FibonachiService {

    private final FibonachiRepository fibonachiRepository;

    public FibonachiService(FibonachiRepository fibonachiRepository) {
        this.fibonachiRepository = fibonachiRepository;
    }

    @Transactional
    public void save(Fibonachi fibonachi){
        fibonachiRepository.save(fibonachi);
    }

    @Transactional
    public Fibonachi findOne(int id){
        Optional<Fibonachi> foundFibonachi = fibonachiRepository.findById(id);
        return foundFibonachi.orElse(null);

    }
}