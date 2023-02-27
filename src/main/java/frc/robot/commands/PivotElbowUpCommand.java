package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElbowPivotSubsystem;

import frc.robot.RobotContainer;


// Command will be changed to fit joystick axis later
public class PivotElbowUpCommand extends CommandBase{
    ElbowPivotSubsystem m_ElbowPivotSubsystem;
    private double speed; 

    public PivotElbowUpCommand(double speed) {
        m_ElbowPivotSubsystem = RobotContainer.elbowPivotSubsystem;
        this.speed = speed;
    }

    public void initialize() {
        m_ElbowPivotSubsystem.elbowPivot.set(0);
    }

    public void execute() {
        m_ElbowPivotSubsystem.elbowPivot.set(speed);
    }

    public void end(boolean interrupted) {
        m_ElbowPivotSubsystem.shutdown();
    }

    public boolean isFinished() {
        return RobotContainer.joystick.getRawButton(7) == false;
    }
}

