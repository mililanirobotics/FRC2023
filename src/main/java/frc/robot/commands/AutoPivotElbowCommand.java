package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.PivotConstants;
import frc.robot.subsystems.ElbowPivotSubsystem;

public class AutoPivotElbowCommand extends CommandBase {
    private ElbowPivotSubsystem m_ElbowPivotSubsystem;
    private double speed;

    public AutoPivotElbowCommand(double angleRotation, ElbowPivotSubsystem elbowPivotSubsystem) {
        speed = 0;

        m_ElbowPivotSubsystem = elbowPivotSubsystem;
        m_ElbowPivotSubsystem.angleRotation = angleRotation;
        
        addRequirements(m_ElbowPivotSubsystem);
    }

    @Override
    public void initialize() {
        m_ElbowPivotSubsystem.resetEncoders();
    }

    @Override
    public void execute() {
        System.out.println("Error: " + m_ElbowPivotSubsystem.error());
        System.out.println("Angle: " + m_ElbowPivotSubsystem.angleRotation);
        System.out.println("Speed: " + speed);
        
        speed = RobotContainer.limitValue(m_ElbowPivotSubsystem.error() * PivotConstants.kPPivotAngle, 0.5, 0.35);
        m_ElbowPivotSubsystem.elbowPivot.set(speed);
   
    }

    @Override
    public void end(boolean interrupted) {
        m_ElbowPivotSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(m_ElbowPivotSubsystem.error()) < 1;
    }
}
