package com.team1389.autonomous;

import com.team1389.auto.command.TurnAngleCommand;
import com.team1389.command_framework.command_base.Command;
import com.team1389.configuration.PIDConstants;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;

import com.team1389.robot.RobotSoftware;

public class BecomeParallelCommand extends Command
{

    private TurnAngleCommand<Percent> turnNinety;
    private RangeOut<Percent> turnController;
    private final double TOLERANCE_IN_DEGREES = 1;

    public BecomeParallelCommand(RobotSoftware robot)
    {
        // turns clockwise on positive
        turnController = TurnAngleCommand.createTurnController(robot.voltageDrive);
        turnNinety = new TurnAngleCommand<>(90, true, TOLERANCE_IN_DEGREES, robot.gyroInput, turnController,
                new PIDConstants(0.01, 0, 0));
    }

    @Override
    protected boolean execute()
    {
        // turnController.set(.3);
        return false;
        // return turnNinety.execute();
    }
}