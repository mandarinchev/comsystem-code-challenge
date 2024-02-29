package com.comsystem.homework.robot;

import com.comsystem.homework.model.RobotAction;
import com.comsystem.homework.model.RobotPlan;

import java.util.ArrayList;
import java.util.List;

public class RobotOperations {

    /**
     * An algorithm that converts a number of days into an action plan.
     * @param days the number of days that the robot can work
     * @return The action plan <em>must maximize</em> the number of stones that the robot will dig. In other words, this
     *         algorithm must try to achieve the highest value of {@link RobotPlan#numberOfStones} possible for the
     *         number of provided days. The value of {@link RobotPlan#numberOfDays} is equal to the input
     *         days parameter
     * @see RobotPlan
     */
    public RobotPlan excavateStonesForDays(int days) {
        /*
        This algorithm assumes:
            1. "Cloning" means creating an exact copy of an existing robot that will continue to exist in future days.
            2. Each robot must follow the exact same action plan starting from the day it is created onwards
                (not that the resulting algorithm would change otherwise, but still).
            3. Excavating for 0 days doesn't make sense (and excavating for a negative amount of days clearly doesn't
                make sense). Thus, the API will not handle non-positive values for its days parameter.
            4. Excavating for over 31 days cannot be handled by this method. Given the current project structure,
                the number of stones needs to fit within a signed integer in order to be stored in a RobotPlan instance.
                Therefore, the maximum value that can be stored is Integer.MAX_VALUE, or 2^(31)-1, which is 1 under
                the maximum number of stones that can be excavated in 32 days.
                Thus, the API will not handle values for its days parameter greater than 31.
        The logic goes as follows:
            - Given N days for the robots to operate, where N > 0;
            - On the very last day, cloning is pointless, therefore every existing robot will dig;
            - On any other given day, cloning means a lost stone for that specific day. However, cloning
                results in an extra robot which, at the very least, can make up for the lost stone by digging on the
                final day. This means that on day N-1 cloning and digging both produce equivalent results. However, on
                any day N-K, where 1 < K < N, cloning is always better than digging, as on day N-K+1 there will be
                twice the amount of robots able to clone themselves, such that on day N-K+2 they are able to dig up
                4*{number of robots on day N-K} stones, as opposed to 3*{number of robots on day N-K} stones if they
                had simply dug three days in a row.
        To maximize the amount of stones collected in N days, every existing robot will clone itself up until day N-1,
        inclusive, then dig on the final day.
        As such, the maximum number of stones able to be excavated in N days is 2^(N-1) stones.
         */
        List<RobotAction> actions = new ArrayList<>();
        int robots = 1;

        for (int i = 0; i < days-1; i++) {
            actions.add(RobotAction.CLONE);
            robots *= 2;
        }
        actions.add(RobotAction.DIG);
//        Each robot digs once on the final day, so the number of stones is equal to the number of robots.
        return new RobotPlan(days, robots, actions);
    }

    /**
     * An algorithm that converts a number of stones into an action plan. Essentially this algorithm is the inverse of
     * {@link #excavateStonesForDays(int)}.
     * @param numberOfStones the number of stones the robot has to collect
     * @return The action plan <em>must minimize</em> the number of days necessary for the robot to dig the
     *         provided number of stones. In other words, this algorithm must try to achieve the lowest value of
     *         {@link RobotPlan#numberOfDays} possible for the number of provided stones. The value of
     *         {@link RobotPlan#numberOfStones} is equal to the numberOfStones parameter
     * @see RobotPlan
     */
    public RobotPlan daysRequiredToCollectStones(int numberOfStones) {
        /*
        As per the reasoning provided in excavateStonesForDays(), the maximum number of stones in N days is achieved
        by cloning for N-1 days and digging on the last. It stands to reason that the same strategy will produce the
        minimum number of days to dig up a given number of stones, as any number of stones X,
        where 2^(N-2) < X <= 2^(N-1), will require the same number of days to be collected, namely N.
        In this algorithm the robots will clone themselves until they reach or exceed the targeted number of stones,
        then dig once on the final day, collecting the necessary amount.
        As such, the minimum number of days required to collect X stones is ceil(log2(X)) + 1 days.
         */
        List<RobotAction> actions = new ArrayList<>();
        int daysToCollect = (int) Math.ceil(Math.log(numberOfStones)/Math.log(2)) + 1;

        for (int i = 0; i < daysToCollect - 1; i++) {
            actions.add(RobotAction.CLONE);
        }
        actions.add(RobotAction.DIG);
        return new RobotPlan(daysToCollect, numberOfStones, actions);
    }

}
