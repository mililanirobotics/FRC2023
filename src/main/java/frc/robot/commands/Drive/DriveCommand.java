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
    private GenericHID leftStick;
    private GenericHID rightStick;

    private double leftPower;
    private double rightPower;

    //constructor
    public DriveCommand(DriveSubsystem driveSubsystem, GenericHID leftStick, GenericHID rightStick) {
        //initializing the joystick used
        this.leftStick = leftStick;
        this.rightStick = rightStick;
        
        //initializing subsystems
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);  
    }

    @Override
    public void execute() {
        //powers the left side if the joystick is pushed far enough (deadzone)
        if(Math.abs(leftStick.getRawAxis(JoystickConstants.kAttackYAxisPort))  >= JoystickConstants.kDeadzone) {
            leftPower = -leftStick.getRawAxis(JoystickConstants.kAttackYAxisPort) * m_driveSubsystem.getDriveSpeed();
        }   
        else {
            leftPower = 0;
        }

        //powers the right side if the joystick is pushed far enough (deadzone)
        if(Math.abs(rightStick.getRawAxis(JoystickConstants.kAttackYAxisPort)) >= JoystickConstants.kDeadzone) {
            rightPower = -rightStick.getRawAxis(JoystickConstants.kAttackYAxisPort) * m_driveSubsystem.getDriveSpeed();
        }
        else {
            rightPower = 0;
        }

        //half speed
        if(leftStick.getRawButton(JoystickConstants.kAttackTriggerPort)) {
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