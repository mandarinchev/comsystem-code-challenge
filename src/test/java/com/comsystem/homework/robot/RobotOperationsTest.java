package com.comsystem.homework.robot;

import com.comsystem.homework.model.RobotPlan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RobotOperationsTest {

    /**
     * See explanation for value range in {@link RobotOperations#excavateStonesForDays(int)}
     */
    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
            17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31})
    void excavate_ShouldReturnMaximumStones(int numberOfDays) {
        // Given
        RobotOperations robotOperations = new RobotOperations();
        int expectedStones = (int) Math.pow(2, numberOfDays-1);
        int actualStones;
        RobotPlan robotPlan;

        //When
        robotPlan = robotOperations.excavateStonesForDays(numberOfDays);
        actualStones = robotPlan.numberOfStones();

        //Then
        assertEquals(expectedStones, actualStones);
    }

    @Test
    void daysToCollect_OneStone_ShouldBeOne() {
        // Given
        RobotOperations robotOperations = new RobotOperations();
        int numberOfStones = 1;
        int expectedDays = 1;
        int actualDays;
        RobotPlan robotPlan;

        //When
        robotPlan = robotOperations.daysRequiredToCollectStones(numberOfStones);
        actualDays = robotPlan.numberOfDays();

        //Then
        assertEquals(expectedDays, actualDays);
    }

    @Test
    void daysToCollect_MaxStones_ShouldBeThirtyTwo() {
        // Given
        RobotOperations robotOperations = new RobotOperations();
        int numberOfStones = Integer.MAX_VALUE;
        int expectedDays = 32;
        int actualDays;
        RobotPlan robotPlan;

        //When
        robotPlan = robotOperations.daysRequiredToCollectStones(numberOfStones);
        actualDays = robotPlan.numberOfDays();

        //Then
        assertEquals(expectedDays, actualDays);
    }
}
