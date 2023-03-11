package frc.robot.commands.Alignment;

//subsystems and commands imports
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
//general imports
import edu.wpi.first.math.controller.PIDController;
import frc.robot.RobotContainer;
//constants
import frc.robot.Constants.GameConstants;
import frc.robot.Constants.RobotConstants;


public class AlignmentCommand extends CommandBase {
    //subsystems
    private LimelightSubsystem m_limelightSubsystem;
    private DriveSubsystem m_driveSubsystem;
    //values being measured in multiple parts of the program
    private double offsetAngle;
    //enum that designates which pipeline the limelight will use
    private LimelightSubsystem.Pipeline pipeline;
    //PID loop used
    private PIDController alignmentPID;
    
    //constructor 
    public AlignmentCommand(LimelightSubsystem.Pipeline pipeline, DriveSubsystem driveSubsystem, LimelightSubsystem limelightSubsystem) {
        m_limelightSubsystem = limelightSubsystem;
        m_driveSubsystem = driveSubsystem;

        alignmentPID = new PIDController(RobotConstants.kTurnP, RobotConstants.kTurnI, RobotConstants.kTurnD);
        this.pipeline = pipeline;

        addRequirements(m_driveSubsystem, m_limelightSubsystem);
    }

    @Override
    public void initialize() {
        //setting the pipeline
        m_limelightSubsystem.setPipeline(pipeline);
    }

    @Override
    public void execute() {
        //gets the current horizontal offset and uses the PID controller to calculate a speed (setpoint is 0 degrees)
        offsetAngle = m_limelightSubsystem.getHorizontalOffset();
        double speed = alignmentPID.calculate(offsetAngle, 0);
        
        //minimum speed is 0.3 and the maximum is 0.45
        speed = RobotContainer.limitSpeed(speed, 0.3, 0.45);
        
        //setting the power
        m_driveSubsystem.drive(speed, -speed);  
    }    
    
    @Override
    public void end(boolean interrupted) {
        System.out.println("Ending angle: "+offsetAngle);
        System.out.println("Alignment command has finished");
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        //ends the command if the offset angle is within the allowed interval [-2, 2]
        return Math.abs(offsetAngle) < GameConstants.kAlignmentSlack;
    }
}
