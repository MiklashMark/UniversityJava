package com.mark.code.epamlab.async;


import com.mark.code.epamlab.calculations.FibonachiCalculation;
import com.mark.code.epamlab.model.Fibonachi;
import com.mark.code.epamlab.service.FibonachiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class FibonachiAsync {

    private final FibonachiService fibonachiService;
    private final FibonachiCalculation fibonachiCalculation;
    @Autowired
    public FibonachiAsync(FibonachiService fibonachiService, FibonachiCalculation fibonachiCalculation) {
        this.fibonachiService = fibonachiService;
        this.fibonachiCalculation = fibonachiCalculation;
    }

    public int createHalfEmptyModelInDatabase(Fibonachi fibonachi) {
        Fibonachi fibonachi1 = new Fibonachi();

        fibonachi1.setNumber(fibonachi.getNumber());

        fibonachiService.save(fibonachi1);

        return fibonachi1.getId();
    }

    public CompletableFuture<Integer> computeAsync(int id) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Fibonachi result = fibonachiService.findOne(id);
                Thread.sleep(15000);
                result.setResult(fibonachiCalculation.calculate(result.getNumber()));
                fibonachiService.save(result);

                return result.getId();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
