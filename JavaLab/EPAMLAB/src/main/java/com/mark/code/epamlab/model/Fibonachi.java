package com.mark.code.epamlab.model;

import com.mark.code.epamlab.exception.InternalServerError;
import com.mark.code.epamlab.exception.InvalidURLException;
import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Table(name = "fibonachi")
public class Fibonachi {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "number")
    private int number;
    @Column(name = "result")
    private int result;

    @Transient
    private static Logger logger;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        Fibonachi.logger = logger;
    }

    public Fibonachi() {
    }

    public Fibonachi(int number) {
        this.number = number;
    }



    public int calculate(int number) {
        int first = 1;
        int second = 1;
        int third = 0;

        for (int i = 2; i <= number; i++) {
            third = first + second;
            first = second;
            second = third;
        }

        return third;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public void validate() throws InternalServerError, InvalidURLException {
        logger = LogManager.getLogger();

        if (number < 0) {
            logger.error("<0");
            throw new InvalidURLException("<0");
        } else if (number >= 1000000) {
            logger.error(">1000000");
            throw new InternalServerError(">100000");
        }
    }

}
