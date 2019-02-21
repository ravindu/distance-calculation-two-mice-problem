package com.example.distance_calculation.application;

import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.distance_calculation.common.AdditionalAttributes;
import com.example.distance_calculation.common.MiceType;
import com.example.distance_calculation.common.TestConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/example-impl-context.xml"})
public class DistanceCalculationServiceTest implements TestConstants {

    @Autowired
    private DistanceCalculationService distanceCalculationService;

    private static Map<MiceType, Map<AdditionalAttributes, Double>> miceAndWallSoulutionMap;

    @Test
    public void testSuccessDistanceCalculation() {
        miceAndWallSoulutionMap = distanceCalculationService.CalculateNoofDays(WIDTH_OF_THE_WALL);
        double bigMiceDistance = miceAndWallSoulutionMap.get(MiceType.BIG_MICE)
                .get(AdditionalAttributes.TOTAL_DISTANCE);
        double smallMiceDistance = miceAndWallSoulutionMap.get(MiceType.SMALL_MICE)
                .get(AdditionalAttributes.TOTAL_DISTANCE);
        // Assert.assertEquals((double) WIDTH_OF_THE_WALL, (bigMiceDistance + smallMiceDistance));
        Assert.assertEquals((double) WIDTH_OF_THE_WALL, (bigMiceDistance + smallMiceDistance), 0);
    }

    @Ignore
    @Test
    public void testFailureDistanceCalculation() {
    }
}
