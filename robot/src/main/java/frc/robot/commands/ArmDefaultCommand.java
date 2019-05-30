/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Arm;

public class ArmDefaultCommand extends Command {
    private Arm arm;

    public ArmDefaultCommand() {
        arm = Arm.getInstance();
        requires(arm);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        SmartDashboard.putBoolean("Set Raw Motion", false);
        SmartDashboard.putNumber("Set Raw Motion", arm.getGoalPosition());
        System.out.println("initializing claw state machine");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Constants.Arm.kOpenLoopOnly) {
            double speed = Robot.m_oi.getManualArmSpeed();
            arm.setOpenLoop(speed, speed > 0);
        } else {
            // Check if they want to set from SmartDashboard
            if (Robot.m_oi.useSmartDashboardMM()) {
                double position = Robot.m_oi.getSDDesiredMotionMagicPosition(arm.getGoalPosition());
                arm.setMotionMagicPosition(position);
            }

            arm.setMotorsToCurrentPosition();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
