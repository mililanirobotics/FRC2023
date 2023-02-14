package frc.robot.commands.Drive;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

//constants
import frc.robot.Constants.JoystickConstants;
import frc.robot.RobotContainer;

//subsystems used
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
    private DriveSubsystem m_driveSubsystem;

    public DriveCommand() {
        m_driveSubsystem = RobotContainer.driveSubsystem;
        addRequirements(m_driveSubsystem);  
    }

    @Override
    public void execute() {
        double leftPower = -RobotContainer.joystick.getRawAxis(JoystickConstants.kLeftYJoystickPort) * m_driveSubsystem.driveScale;
        double rightPower = -RobotContainer.joystick.getRawAxis(JoystickConstants.kRightYJoystickPort) * m_driveSubsystem.driveScale;

        if(RobotContainer.joystick.getRawAxis(2) >= 0.5) {
            leftPower *= 0.5;
            rightPower *= 0.5;
        }

        RobotContainer.leftEncoderWidget.setDouble(m_driveSubsystem.getLeftEncoder());
        RobotContainer.rightEncoderWidget.setDouble(m_driveSubsystem.getRightEncoder());

        System.out.println(m_driveSubsystem.getRightEncoder());

        m_driveSubsystem.drive(leftPower, rightPower);
    }

    @Override
    public void end(boolean interrupted) {
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        return false; //command won't finish
    }


}
