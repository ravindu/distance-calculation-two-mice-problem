package com.example.distance_calculation.application;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.example.distance_calculation.common.AdditionalAttributes;
import com.example.distance_calculation.common.DistanceCalculatorException;
import com.example.distance_calculation.common.MessageUtil;
import com.example.distance_calculation.common.MiceType;

/**
 * Exposed a service to find the distance of each mice traveled. here i followed a simple package structure and DDD.
 * 
 * @author rsirimanna
 *
 */
@Service
public class DistanceCalculationService {

    private static final Logger logger = Logger.getLogger(DistanceCalculationService.class.getName());

    private static Map<MiceType, Map<AdditionalAttributes, Double>> miceAndWallSoulutionMap = new HashMap<MiceType, Map<AdditionalAttributes, Double>>();

    private static double totalNumberofDays = 1;

    private static double distanceAcceleratorCount;

    private static double currentDistanceInMeters;

    public DistanceCalculationService() {
        initiateMiceAndWallSoulutionMap();
    }

    public Map<MiceType, Map<AdditionalAttributes, Double>> CalculateNoofDays(int widthOfwallInMeters) {
        try {

            logger.log(Level.INFO, MessageUtil.RECEIVED_LOG_MESSAGE);
            validateInputParameters(widthOfwallInMeters);
            while (currentDistanceInMeters < widthOfwallInMeters) {

                double bigMiceDistance = calculateDistancePerDay(distanceAcceleratorCount);
                double distanceGap = (widthOfwallInMeters - currentDistanceInMeters);
                totalNumberofDays++;
                distanceAcceleratorCount++;
                /*
                 * Here i have made an assumption that the Big Mice can finish the distance gap. So the distance that
                 * small mice travel is very less compare to the Big mice. There are optional comments to understand how
                 * this is implemented.
                 */
                if (bigMiceDistance >= distanceGap) {
                    double remainDistance = getTotalDistanceofMice(MiceType.BIG_MICE) + distanceGap;
                    // Update Big Mice additional attributes with traveled distance and the noofDays (optional comment)
                    miceAndWallSoulutionMap.put(MiceType.BIG_MICE,
                            buildAdditionalSolutionAttributeMap(remainDistance, totalNumberofDays));
                    // Update Small Mice additional attribute with noofDays only (optional comment)
                    miceAndWallSoulutionMap.put(MiceType.SMALL_MICE, buildAdditionalSolutionAttributeMap(
                            getTotalDistanceofMice(MiceType.SMALL_MICE), totalNumberofDays));
                    logger.log(Level.INFO, MessageUtil.COMPLETED_LOG_MESSAGE);
                    break;
                } else {
                    double totalBigMiceDistance = getTotalDistanceofMice(MiceType.BIG_MICE) + bigMiceDistance;
                    double smallMiceDistance = (1 / bigMiceDistance);
                    double totalSmallMiceDistance = getTotalDistanceofMice(MiceType.SMALL_MICE) + smallMiceDistance;
                    // Update both Mice additional attributes with traveled distance and the noofDays (optional comment)
                    buildMiceAndWallSoulutionMap(
                            buildAdditionalSolutionAttributeMap(totalBigMiceDistance, totalNumberofDays),
                            buildAdditionalSolutionAttributeMap(totalSmallMiceDistance, totalNumberofDays));
                }
                refreshTotalDistance();
            }

        } catch (DistanceCalculatorException distanceCalculatorException) {
            /*
             * Here we can throw and exception also. But decided to not to break the flow. Handle a custom exception to
             * customize the exception handing (optional comment)
             */
            logger.log(Level.WARNING, distanceCalculatorException.getMessage());
        } catch (Exception exception) {
            /* Define and Exception block to handle operations if occurred (optional comment). */
            logger.log(Level.WARNING, MessageUtil.PROCESSING_ERROR_LOG_MESSAGE);
        } finally {
            /* Print the result (optional comment). */
            logger.log(Level.INFO, MessageUtil.RESULT_LOG_MESSAGE + miceAndWallSoulutionMap);
        }
        return miceAndWallSoulutionMap;
    }

    /**
     * Validate the width of the wall
     * 
     * @param widthOfWall
     */
    private void validateInputParameters(int widthOfWall) {
        if (widthOfWall <= 0)
            throw new DistanceCalculatorException("Invalid input found. The wall should be wider than zero.");
    }

    /**
     * Initialize the result map for Day 1
     */
    private void initiateMiceAndWallSoulutionMap() {
        Map<AdditionalAttributes, Double> initialAdditionalAttributesMap = buildAdditionalSolutionAttributeMap(
                calculateDistancePerDay(distanceAcceleratorCount), totalNumberofDays);
        buildMiceAndWallSoulutionMap(initialAdditionalAttributesMap, initialAdditionalAttributesMap);
        distanceAcceleratorCount++;
        refreshTotalDistance();
    }

    /**
     * Retrieve the total distance count based on MiceType
     * 
     * @param miceName
     * @return traveledDistance
     */
    private double getTotalDistanceofMice(MiceType miceName) {
        return miceAndWallSoulutionMap.get(miceName).get(AdditionalAttributes.TOTAL_DISTANCE);
    }

    /**
     * Calculate the current distance that both mice traveled.
     */
    private void refreshTotalDistance() {
        currentDistanceInMeters = getTotalDistanceofMice(MiceType.BIG_MICE)
                + getTotalDistanceofMice(MiceType.SMALL_MICE);
    }

    /**
     * Distance of each mice dig in a one day
     * 
     * @param accelerationRate
     * @return distance
     */
    private double calculateDistancePerDay(double accelerationRate) {
        return Math.pow(2, accelerationRate);
    }

    /**
     * Build a Map which has TotalDistance and NoofDays of each Mice
     * 
     * @param distance
     * @param noOfDays
     * @return aditionalAttributes
     */
    private Map<AdditionalAttributes, Double> buildAdditionalSolutionAttributeMap(double distance, double noOfDays) {
        Map<AdditionalAttributes, Double> newResultMap = new HashMap<AdditionalAttributes, Double>();
        newResultMap.put(AdditionalAttributes.TOTAL_DAYS, noOfDays);
        newResultMap.put(AdditionalAttributes.TOTAL_DISTANCE, distance);
        return newResultMap;
    }

    /**
     * Build the final result which contain the MiceType and distance, noofDays traveled by each Mice
     * 
     * @param bigMiceAdditionalAttributes
     * @param smallMiceAdditionalAttributes
     */
    private void buildMiceAndWallSoulutionMap(final Map<AdditionalAttributes, Double> bigMiceAdditionalAttributes,
            final Map<AdditionalAttributes, Double> smallMiceAdditionalAttributes) {
        miceAndWallSoulutionMap.put(MiceType.BIG_MICE, bigMiceAdditionalAttributes);
        miceAndWallSoulutionMap.put(MiceType.SMALL_MICE, smallMiceAdditionalAttributes);
    }
}
