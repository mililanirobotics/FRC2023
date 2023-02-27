package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.PivotConstants;
import frc.robot.subsystems.ElbowPivotSubsystem;

public class AutoPivotElbowCommand extends CommandBase {
    private ElbowPivotSubsystem m_ElbowPivotSubsystem;
    private double speed;

    public AutoPivotElbowCommand(double angleRotation) {
        speed = 0;

        m_ElbowPivotSubsystem = RobotContainer.elbowPivotSubsystem;
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
        
        speed = m_ElbowPivotSubsystem.error() * PivotConstants.kPPivotAngle;
            if (Math.abs(speed) > 0.5) {
                speed = Math.copySign(0.5, speed);
            }
            else if (Math.abs(speed) < 0.35) {
                speed = Math.copySign(0.35, speed);
            }
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
