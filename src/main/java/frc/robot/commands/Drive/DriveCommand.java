package frc.robot.commands.Drive;

//subsystems and commands
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.wpilibj.GenericHID;
//constants
import frc.robot.Constants.JoystickConstants;

//constructor
public class DriveCommand extends CommandBase {
    //declaring subsystems
    private DriveSubsystem m_driveSubsystem;

    //declaring the joystick used
    private GenericHID joystick;

    private double leftPower;
    private double rightPower;

    //constructor
    public DriveCommand(DriveSubsystem driveSubsystem, GenericHID joystick) {
        //initializing the joystick used
        this.joystick = joystick;
        
        //initializing subsystems
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);  
    }

    @Override
    public void execute() {
        if(Math.abs(joystick.getRawAxis(JoystickConstants.kLeftYJoystickPort))  >= 0.1) {
            leftPower = -joystick.getRawAxis(JoystickConstants.kLeftYJoystickPort) * m_driveSubsystem.getDriveSpeed();
        }   
        else {
            leftPower = 0;
        }

        if(Math.abs(joystick.getRawAxis(JoystickConstants.kRightYJoystickPort)) >= 0.1) {
            rightPower = -joystick.getRawAxis(JoystickConstants.kRightYJoystickPort) * m_driveSubsystem.getDriveSpeed();
        }
        else {
            rightPower = 0;
        }

        // double leftPower = -joystick.getRawAxis(JoystickConstants.kLeftYJoystickPort) * m_driveSubsystem.getDriveSpeed();
        // double rightPower = -joystick.getRawAxis(JoystickConstants.kRightYJoystickPort) * m_driveSubsystem.getDriveSpeed();

        if(joystick.getRawAxis(JoystickConstants.kLeftTriggerPort) >= 0.5) {
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