package frc.robot.commands.Alignment;

//subsystems and commands imports
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.LimelightSubsystem.Pipeline;
//general imports
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.RobotContainer;
//constants
import frc.robot.Constants.LimelightConstants;
import frc.robot.Constants.DriveConstants;

public class AlignmentCommand extends CommandBase {
    //subsystems
    private LimelightSubsystem m_limelightSubsystem;
    private DriveSubsystem m_driveSubsystem;
    //values being measured in multiple parts of the program
    private double offsetAngle;
    //timer
    private int iteration;
    //enum that designates which pipeline the limelight will use
    private LimelightSubsystem.Pipeline pipeline;
    //PID loop used
    private PIDController alignmentPID;
    //widgets
    private static GenericEntry offsetWidget;
    
    //constructor 
    public AlignmentCommand(LimelightSubsystem.Pipeline pipeline, DriveSubsystem driveSubsystem, LimelightSubsystem limelightSubsystem, ShuffleboardTab limelightTab) {
        //initializing subsystems
        m_limelightSubsystem = limelightSubsystem;
        m_driveSubsystem = driveSubsystem;

        //initializing PID controller and the pipeline that'll be used
        alignmentPID = new PIDController(DriveConstants.kAlignmentP, DriveConstants.kAlignmentI, DriveConstants.kAlignmentD);
        this.pipeline = pipeline;

        addRequirements(m_driveSubsystem, m_limelightSubsystem);

        if(offsetWidget == null) {
            //initializing the offset widget
            offsetWidget = limelightTab.add("Tx Offset", 0).withSize(2, 1).getEntry();
        }
    }

    @Override
    public void initialize() {
        //setting the pipeline
        m_limelightSubsystem.setPipeline(pipeline);

        iteration = 0;
    }

    @Override
    public void execute() {
        if(iteration >= LimelightConstants.kSwitchDelay) {
            //gets the current horizontal offset and uses the PID controller to calculate a speed (setpoint is 0 degrees)
            offsetAngle = m_limelightSubsystem.getHorizontalOffset();
            double speed = alignmentPID.calculate(offsetAngle, 0);

            //minimum speed is 0.3 and the maximum is 0.45
            // speed = RobotContainer.limitSpeed(speed, 0.2, 0.45);
            speed = Math.copySign(0.2, speed);
            //setting the power
            m_driveSubsystem.drive(-speed, speed);  

            //displaying the current horizontal offset and speed of the motors
            offsetWidget.setDouble(offsetAngle);

            iteration++;
        }
        else {
            iteration++;
        }
    }    
    
    @Override
    public void end(boolean interrupted) {
        System.out.println("Ending angle: "+offsetAngle);
        System.out.println("Alignment command has finished");
        m_driveSubsystem.shutdown();
        m_limelightSubsystem.setPipeline(Pipeline.DRIVER_VIEW);
    }

    @Override
    public boolean isFinished() {
        //ends the command if the offset angle is within the allowed interval [-2, 2]
        return Math.abs(offsetAngle) < DriveConstants.kAlignmentSlack && iteration >= LimelightConstants.kMinimumTime;
    }
}
