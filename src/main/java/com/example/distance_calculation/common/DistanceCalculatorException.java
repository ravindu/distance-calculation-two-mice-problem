package com.example.distance_calculation.common;

/**
 * Distance calculator custom exception
 * 
 * @author rsirimanna
 *
 */
public class DistanceCalculatorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DistanceCalculatorException(final String message) {
        super(message);
    }

    public DistanceCalculatorException(final Throwable throwable) {
        super(throwable);
    }

    public DistanceCalculatorException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}