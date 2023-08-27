package frc.robot.subsystems;

//subsystems and commands
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//general imports
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

//constants
import frc.robot.Constants.LEDConstants;

public class LEDSubsystem extends SubsystemBase {
    //declaring LED object and the Buffer
    private AddressableLED ledLights;
    private AddressableLEDBuffer ledBuffer;

    //boolean to specify which ledState is currently being displayed
    private boolean signalLEDs;

    //HSV values to be used for the color.
    private int hue;
    private int saturation;
    private int value;

    //variables to be used in the LED animations. 
    private int tempValue;
    private int animationPixelA;
    private int animationPixelB;
    private boolean animationBounced;
    private int animationItteration;
    private int rainbowFirstPixelHue;

    //enum for animation types for easier access
    public enum AnimationTypes { 
        SetAll,
        RainblowAnimation,
        BlinkAnimation,
        StrobeAnimation,
        PulseAnimation,
        ChaseAnimation,
        BounceAnimation,
        DualBounceAnimation
    }
    private AnimationTypes currentAnimation;

    //constructor
    public LEDSubsystem() {
        //intializing LEDs and the Buffer
        ledLights = new AddressableLED(LEDConstants.kLED);
        ledBuffer = new AddressableLEDBuffer(LEDConstants.kLEDCount);

        //defining how many LEDs there are in the strip
        ledLights.setLength(ledBuffer.getLength());
        ledLights.setData(ledBuffer);
        ledLights.start();
    }

    /**
     * Toggles the state of signalLEDS, signifies if the LEDs are currently displaying
     * a signal or default colors
     */
    public void toggleSignalLED() {
        signalLEDs = !signalLEDs;
    }

    /**
     * Tells the current LED state in terms of whether or not the signal is on
     * 
     * @return The current value of the LEDs
     */
    public boolean getSignalLED() {
        return signalLEDs;
    }

    //=========================================================================== 
    // HSV value methods
    //===========================================================================

    /**
     * Sets the hue of the LEDs
     * 
     * @param hue The desired hue of the LEDs
     */
    public void setHue(int hue) {
        this.hue = hue;
    }

    /**
     * Sets the saturation of the LEDs
     * 
     * @param saturation The desired saturation of the LEDs
     */
    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    /**
     * Sets the value of the LEDs
     * 
     * @param value The desired value of the LEDs
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Sets the HSV values of the LED colors
     * 
     * @param hue The desired hue of the LEDs
     * @param saturation The desired saturation of the LEDs
     * @param value The desired value of the LEDs
     */
    public void setHSV(int hue, int saturation, int value) {
        this.hue = hue;
        this.saturation = saturation;
        this.value = value;
    }

    //=========================================================================== 
    // animation methods
    //===========================================================================

    /**
     * Sets the animation type of the LEDs
     * 
     * @param animation The desired animation of the LEDs
     */
    public void setLEDAnimation(AnimationTypes animation) {
        currentAnimation = animation;
    }
    
    /**
     * Updates the current animation of the LEDs. 
     * This method should be called preiodically
     */
    public void updateLEDState() {
        switch(currentAnimation) {
            case SetAll: solidAnimation(); break;
            case RainblowAnimation: rainbowAnimation(); break;
            case BlinkAnimation: blinkAnimation(); break;
            case StrobeAnimation: strobeAnimation(); break;
            case PulseAnimation: pulseAnimation(); break;
            case ChaseAnimation: animationPixelA = 0; chaseAnimation(); break;
            case BounceAnimation: animationPixelA = 0; bounceAnimation(); break;
            case DualBounceAnimation: animationPixelA = ledBuffer.getLength(); 
                animationPixelB = 0; dualBounceAnimation(); break;
        }
    }

    //=========================================================================== 
    // definition of all the animation types
    //===========================================================================

    /**
     * Sets all the LEDs to one solid color
     */
    public void solidAnimation() {
        //each LED has to be set to a color individually
        //For loops are utilized to optimize the process and for readability.
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setHSV(i, hue, saturation, value);
        }
        ledLights.setData(ledBuffer);
    }

    /**
     * Sets the LED color to flow in a rainbow pattern
     */
    private void rainbowAnimation() {
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            final var hue = (rainbowFirstPixelHue + (i * 180 / ledBuffer.getLength())) % 180;
            ledBuffer.setHSV(i, hue, saturation, value);
        }

        ledLights.setData(ledBuffer);
        //change the amount this variable increases by to speed up the animation
        rainbowFirstPixelHue += 5;
        rainbowFirstPixelHue %= 180;
    }

    /**
     * Sets the LEDs to turn on briefly, then off for a set amount of time. 
     * Does not change the current color
     */
    private void blinkAnimation() {
        //Animations like blink and strobe use a variable called itteration
        //to track how much time has passed

        //Using something like delay() will cause the robot to cease functioning for that 
        //amount of time
        if (animationItteration % 50 < 5) {
            //Sets the LEDs to turn on if its within the first 100ms of a cycle
            //One second cycles
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                ledBuffer.setHSV(i, hue, saturation, value);
            }
            ledLights.setData(ledBuffer);
            return;
        }
        //If the cycle is passed 100ms then turn off the LEDs
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setHSV(i, 0, 0, 0);
        }
        ledLights.setData(ledBuffer);
    }

    /**
     * Sets the LEDs to rapidly turn off and on. 
     * Does not change the current color
     */
    private void strobeAnimation() {
        //Same concept as blink animation, except the delay between LEDs turning on
        //is drastically lower
        if (animationItteration % 25 < 5) {
            for (var i = 0; i < ledBuffer.getLength(); i++) {
                ledBuffer.setHSV(i, hue, saturation, value);
            }
            ledLights.setData(ledBuffer);
            return;
        }
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setHSV(i, 0, 0, 0);
        }
        ledLights.setData(ledBuffer);
    }

    /**
     * Sets the LEDs to smoothly transition from off to the current color, then back to off.
     */
    private void pulseAnimation() {
        //If the current cycle is within the first 500ms then turn off the LEDs
        if (animationItteration % 50 < 25) {
            tempValue = 0;
        }
        else {
            tempValue = (animationItteration % 25 < 13) ?
                //If the cycle is within 500-740ms then slowly turn on the LEDs
                (animationItteration % 25) * 21 : 
                //If the cycle is within 740-1000ms then slowly turn off the LEDs
                525 - (animationItteration % 25) * 21;
        }
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setHSV(i, hue, saturation, tempValue);
        }
        ledLights.setData(ledBuffer);
    }

    /**
     * Sets the LEDs to have a single light race down the strip
     */
    private void chaseAnimation() {
        ledBuffer.setHSV(animationPixelA, 0, 0, 0);
        ledLights.setData(ledBuffer);

        animationPixelA ++;
        animationPixelA %= ledBuffer.getLength();

        ledBuffer.setHSV(animationPixelA, hue, saturation, tempValue);
        ledLights.setData(ledBuffer);
    }

    /**
     * Sets the LEDs to have a single light race down the strip and bounce back once it reaches the end 
     */ 
    private void bounceAnimation() {
        ledBuffer.setHSV(animationPixelA, 0, 0, 0);
        ledLights.setData(ledBuffer);
        
        if (animationPixelA == ledBuffer.getLength()) {
            animationBounced = true;
        }
        else if (animationPixelA == 0) {
            animationBounced = false;
        }

        if (animationBounced) {
            animationPixelA --;
        }
        else {
            animationPixelA ++;
        }

        ledBuffer.setHSV(animationPixelA, hue, saturation, tempValue);
        ledLights.setData(ledBuffer);
    }

    /**
     * Sets the LEDs to have two lights at either end of the strip to race down into eachother.
     * Once they reach eachother they will bounce back to their respective starting points
     */
    private void dualBounceAnimation() {
        ledBuffer.setHSV(animationPixelA, 0, 0, 0);
        ledBuffer.setHSV(animationPixelB, 0, 0, 0);
        ledLights.setData(ledBuffer);

        if (animationPixelA == ledBuffer.getLength() && animationPixelB == 0) {
            animationBounced = true;
        }
        else if (animationPixelA == animationPixelB) {
            animationBounced = false;
        }

        if (animationBounced) {
            animationPixelA --;
            animationPixelB ++;
        }
        else {
            animationPixelA ++;
            animationPixelB --;
        }
       
        ledBuffer.setHSV(animationPixelA, hue, saturation, tempValue);
        ledBuffer.setHSV(animationPixelB, hue, saturation, tempValue);
        ledLights.setData(ledBuffer);
    }

    
    //=========================================================================== 
    // periodic method
    //===========================================================================

    @Override
    public void periodic() {
        updateLEDState();
        animationItteration++;
    }
}