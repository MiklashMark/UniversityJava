//package com.dmitry.code.epamlab.controllers;
//
//import com.dmitry.code.epamlab.cache.Cache;
//import com.dmitry.code.epamlab.counter.Counter;
//import com.dmitry.code.epamlab.counter.CounterThread;
//import com.dmitry.code.epamlab.exception.InternalServerError;
//import com.dmitry.code.epamlab.exception.InvalidURLException;
//import com.dmitry.code.epamlab.model.Fibonachi;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RestController
//public class FibonController {
//    private final Cache<Integer, Integer> cache;
//
//    private static Logger logger = LogManager.getLogger(FibonController.class);
//
//    private Fibonachi fibonachi;
//
//    @Autowired
//    public FibonController(Cache<Integer,Integer> cache) {
//        this.cache = cache;
//    }
//
//    @GetMapping("/fibonachi")
//    public ResponseEntity<?> fibon(@RequestParam("number") int number) {
//
//        CounterThread counterThread = new CounterThread();
//        counterThread.start();
//
//        logger.info("Connect to localhost:8080/fibonachi?number=?");
//
//        fibonachi = new Fibonachi();
//
//        fibonachi.setNumber(number);
//
//
//        try {
//            fibonachi.validate();
//        } catch (InternalServerError e) {
//            return new ResponseEntity<>("Iternal_Server_Error", HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (InvalidURLException e) {
//            return new ResponseEntity<>("Bad_Request", HttpStatus.BAD_REQUEST);
//        }
//
//        int answer;
//
//
//        if (cache.isContains(fibonachi.getNumber())) {
//            logger.info("get from cache");
//            answer = cache.get(fibonachi.getNumber());
//        } else {
//            logger.info("calculate");
//            answer = fibonachi.calculate();
//            logger.info("push into cache");
//            cache.push(fibonachi.getNumber(), answer);
//        }
//
//        return ResponseEntity.ok("Result: " + Counter.getCounter() + ", " +answer);
//    }
//
//
//}


package com.mark.code.epamlab.controllers;

import com.mark.code.epamlab.async.FibonachiAsync;
import com.mark.code.epamlab.cache.Cache;
import com.mark.code.epamlab.calculations.FibonachiCalculation;
import com.mark.code.epamlab.counter.Counter;
import com.mark.code.epamlab.counter.CounterThread;
import com.mark.code.epamlab.exception.InternalServerError;
import com.mark.code.epamlab.exception.InvalidURLException;
import com.mark.code.epamlab.model.Fibonachi;
import com.mark.code.epamlab.service.FibonachiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class FibonController {
    private final Cache<Integer, Integer> cache;

    private static Logger logger = LogManager.getLogger(FibonController.class);

    private Fibonachi fibonachi;
    private final FibonachiCalculation fibonachiCalculation;
    private final FibonachiService fibonachiService;

    private final FibonachiAsync fibonachiAsync;
    @Autowired
    public FibonController(Cache<Integer, Integer> cache, FibonachiCalculation fibonachiCalculation,
                           FibonachiService fibonachiService, FibonachiAsync fibonachiAsync) {
        this.cache = cache;
        this.fibonachiCalculation = fibonachiCalculation;
        this.fibonachiService = fibonachiService;
        this.fibonachiAsync = fibonachiAsync;

    }



    @GetMapping("/fibonachi")
    public ResponseEntity<?> fibon(@RequestParam("number") int number) {

        CounterThread counterThread = new CounterThread();
        counterThread.start();

        logger.info("Connect to localhost:8080/fibonachi?number=?");

        fibonachi = new Fibonachi();

        fibonachi.setNumber(number);


        try {
            fibonachi.validate();
        } catch (InternalServerError e) {
            return new ResponseEntity<>("Iternal_Server_Error", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidURLException e) {
            return new ResponseEntity<>("Bad_Request", HttpStatus.BAD_REQUEST);
        }

        int answer;


        if (cache.isContains(fibonachi.getNumber())) {
            logger.info("get from cache");
            answer = cache.get(fibonachi.getNumber());
        } else {
            logger.info("calculate");
            answer = fibonachi.calculate(number);
            logger.info("push into cache");
            cache.push(fibonachi.getNumber(), answer);
        }

        fibonachi.setResult(answer);
        fibonachiService.save(fibonachi);

        return ResponseEntity.ok("Result: " + Counter.getCounter() + ", " + answer);
    }

    @GetMapping("/getResult")
    public ResponseEntity<?> getResult(){
        return ResponseEntity.ok("Result: " + Counter.getCounter());
    }

    @PostMapping("/fibon")
    public ResponseEntity<?> listOfNumbersToFibon(@RequestBody List<Integer> listOfNumbers) {
        List<Integer> responseList = listOfNumbers.stream().map(fibonachi::calculate).toList();

        int sum = fibonachiCalculation.findSum(listOfNumbers);
        int min = fibonachiCalculation.findMin(listOfNumbers);
        int max = fibonachiCalculation.findMax(listOfNumbers);
        return new ResponseEntity<>("Result: " + responseList + "\nsum: " + sum +
                "\nmin: " + min +
                "\nmax: " + max, HttpStatus.OK);
    }

    @PostMapping("/async")
    public int async(@RequestBody Fibonachi fibonachi) {

        int id = fibonachiAsync.createHalfEmptyModelInDatabase(fibonachi);

        fibonachiAsync.computeAsync(id);

        return id;

    }

    @GetMapping("/result/{id}")
    public Fibonachi result(@PathVariable("id") int id) {
        return fibonachiService.findOne(id);
    }
}
