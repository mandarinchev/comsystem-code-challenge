package com.comsystem.homework.rest;

import com.comsystem.homework.exception.IllegalExcavationDaysException;
import com.comsystem.homework.exception.IllegalNumberOfStonesException;
import com.comsystem.homework.model.RobotPlan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.comsystem.homework.robot.RobotOperations;

import java.util.Optional;

@RestController()
@RequestMapping("/api/v1/robot/operation")
public final class RobotRestController {

    /**
     * This method exposes the functionality of {@link RobotOperations#excavateStonesForDays(int)} via HTTP
     */
    @PostMapping("/excavation")
    public ResponseEntity<RobotPlan> excavateStones(@RequestParam Integer numberOfDays) {
        // Return an error on invalid parameter value.
        if (numberOfDays <= 0 || numberOfDays > 31) {
            String errorMessage = String.format(
                    "Cannot handle excavation for %d days. Supported duration is from 1 to 31 days.",
                    numberOfDays
            );
            throw new IllegalExcavationDaysException(errorMessage);
        }
        RobotOperations robotOperations = new RobotOperations();
        return ResponseEntity.of(Optional.of(robotOperations.excavateStonesForDays(numberOfDays)));
    }

    /**
     * This method exposes the functionality of {@link RobotOperations#daysRequiredToCollectStones(int)} via HTTP
     */
    @PostMapping("/approximation")
    public ResponseEntity<RobotPlan> approximateDays(@RequestParam Integer numberOfStones) {
        // Return an error on invalid parameter value.
        if (numberOfStones <= 0) {
            String errorMessage = String.format(
                    "Cannot collect %d stones. Number of stones must be positive.",
                    numberOfStones
            );
            throw new IllegalNumberOfStonesException(errorMessage);
        }
        RobotOperations robotOperations = new RobotOperations();
        return ResponseEntity.of(Optional.of(robotOperations.daysRequiredToCollectStones(numberOfStones)));
    }
}
