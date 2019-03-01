package com.team1389.systems;

import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.system.Subsystem;

import com.team1389.auto.command.TurnAngleCommand;
import com.team1389.command_framework.CommandUtil;
import com.team1389.command_framework.command_base.Command;
import com.team1389.configuration.PIDConstants;
import com.team1389.controllers.SynchronousPIDController;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Value;
import com.team1389.system.Subsystem;
import com.team1389.system.drive.DriveOut;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class TeleopAlignmentSystem extends Subsystem
{
    DigitalIn alignBtn;
    DigitalIn centerBtn;
    AlignmentSystem align;

    /*
     * public AlignmentSystem(DriveOut<Percent> drive, RangeIn<Position>
     * leftDistance, RangeIn<Position> rightDistance, RangeIn<Position>
     * robotAngle)
     */
    public TeleopAlignmentSystem(DriveOut<Percent> drive, RangeIn<Position> leftDistance,
            RangeIn<Position> rightDistance, RangeIn<Position> robotAngle, DigitalIn alignBtn, DigitalIn centerBtn)
    {
        this.alignBtn = alignBtn;
        this.centerBtn = centerBtn;
        align = new AlignmentSystem(drive, leftDistance, rightDistance, robotAngle);
        align.init();
    }

    @Override
    public void init()
    {

    }

    @Override
    public void update()
    {
        if (alignBtn.get())
        {
            align.alignAngle();
        }
        else if (centerBtn.get())
        {
            align.centerOnTarget();
        }
        align.update();
    }

    @Override
    public AddList<Watchable> getSubWatchables(AddList<Watchable> arg0)
    {
        return arg0;
    }

    @Override
    public String getName()
    {
        return "temp";
    }
}