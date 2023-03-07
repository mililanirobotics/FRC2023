package frc.robot.commands.Drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

//constants
import frc.robot.Constants.JoystickConstants;
import frc.robot.Constants.RobotConstants;
//subsystems used
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
    private DriveSubsystem m_driveSubsystem;
    private GenericHID joystick;

    public DriveCommand(DriveSubsystem driveSubsystem, GenericHID joystick) {
        this.joystick = joystick;
        
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);  
    }

    @Override
    public void execute() {
        double leftPower = -joystick.getRawAxis(JoystickConstants.kLeftYJoystickPort) * m_driveSubsystem.getDriveSpeed();
        double rightPower = -joystick.getRawAxis(JoystickConstants.kRightYJoystickPort) * m_driveSubsystem.getDriveSpeed();

        if(joystick.getRawAxis(2) >= 0.5) {
            leftPower *= 0.5;
            rightPower *= 0.5;
        }

        //sending the current encoder readings to shuffleboard
        m_driveSubsystem.printLeftEncoder(m_driveSubsystem.getLeftEncoder());
        m_driveSubsystem.printRightEncoder(m_driveSubsystem.getRightEncoder());

        //setting the current power of the drive
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
