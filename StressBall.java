package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.robotcontroller.external.samples.BasicOpMode_Iterative;
import org.firstinspires.ftc.robotcontroller.external.samples.BasicOpMode_Linear;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static com.qualcomm.robotcore.util.Range.scale;

/**
 * Created by Jessica on 4/10/2018.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "StressBall" , group = "TOComp")

public class StressBall extends OpMode {

    private final int PULSE_PER_REVOLUTION_NEVEREST40 = 1120;

    private final int PULSE_PER_REVOLUTION_NEVEREST40_OVER_40 = 280;

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
//
    private DcMotor FeedMotor;

    private CRServo ContinuousShooter;

    private CRServo SmallContinuous = null;

//    static final double Increment = 0.001;
//    static final int CYCLE_MS = 500;
//    static final double MAX_F = 1.0;
//    static final double MAX_R = -1.0;
//    static final double STOP = 0;
//
//    double power = 0;
//    boolean rampUp = true;
//    boolean rampDown = true;

    public float SERVO_LATCH_UP = (float) 1.0;
    public float SERVO_STOP = (float) 0;

    private float FeedSpeed = (float) 0.05;

    final double    CLAW_SPEED      = 0.01 ;                            // sets rate to move servo
    final double    ARM_SPEED       = 0.01 ;                            // sets rate to move servo


    @Override
    public void init() {

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
//
        FeedMotor = hardwareMap.dcMotor.get("FeedMotor");

        ContinuousShooter = hardwareMap.crservo.get("ContinuousShooter");
        SmallContinuous = hardwareMap.crservo.get("SmallContinuous");

        frontLeft.setMode(RUN_WITHOUT_ENCODER);
        frontRight.setMode(RUN_WITHOUT_ENCODER);
        backLeft.setMode(RUN_WITHOUT_ENCODER);
        backRight.setMode(RUN_WITHOUT_ENCODER);

        FeedMotor.setMode(RUN_WITHOUT_ENCODER);

        backLeft.setDirection(REVERSE);
        frontLeft.setDirection(REVERSE);
        frontRight.setDirection(FORWARD);
        backRight.setDirection(FORWARD);

        FeedMotor.setDirection(FORWARD);


//

        //Use Interface Module to find scale
     //   ContinuousShooter.scaleRange(0, 1);
      //  SmallContinuous.scaleRange(0, 1);


       ContinuousShooter.setPower(0);

        SmallContinuous.setPower(0);

        telemetry.update();
    }

    @Override
    public void init_loop(){
        super.init_loop();
    }


    @Override
    public void loop() {

        float speed = -gamepad1.right_stick_y;
        float direction = gamepad1.right_stick_x;
        float strafe = gamepad1.left_stick_x;

        float Magnitude = Math.abs(speed) + Math.abs(direction) + Math.abs(strafe);
        if (Magnitude < 1) {
            Magnitude = 1;
        }
        frontLeft.setPower(scale(speed + direction - strafe, -Magnitude, Magnitude, -1, 1));
        frontRight.setPower(scale(speed - direction + strafe, -Magnitude, Magnitude, -1, 1));
        backLeft.setPower(scale(speed + direction + strafe, -Magnitude, Magnitude, -1, 1));
        backRight.setPower(scale(speed - direction - strafe, -Magnitude, Magnitude, -1, 1));


        //soft start
//        if (speed > 0 || direction > 0 || strafe > 0){
//            frontLeft.setPower(power += Increment);
//            frontRight.setPower(power += Increment);
//            backLeft.setPower(power += Increment);
//            backRight.setPower(power += Increment);
//
////        } else if (speed == 0 || direction == 0 || strafe == 0){
//            frontLeft.setPower(0);
//            frontRight.setPower(0);
//            backLeft.setPower(0);
//            backRight.setPower(0);
//        }
//
//        if (speed < 0 || direction < 0 || strafe < 0){
//            frontLeft.setPower(power -= Increment);
//            frontRight.setPower(power -= Increment);
//            backLeft.setPower(power -= Increment);
//            backRight.setPower(power -= Increment);
//        }
//
//        if (power >= MAX_F){
//            power = MAX_F;
//            rampUp = !rampUp;
//        } else if (power < MAX_F || power > MAX_R){
//            power = STOP;
//            rampDown = !rampDown;
//            rampUp = !rampUp;
//        }
//
//        if (power <= MAX_R){
//            power = MAX_R;
//            rampDown = !rampDown;
//        }

        //FeedMotor
        telemetry.addData("Continuous Power", ContinuousShooter.getPower());
        if (gamepad1.right_bumper && ContinuousShooter.getPower()==1) {
            //OneFlickRotation(1); //Change MAX

            FeedMotor.setPower(-1);
            //FeedMotor.setTargetPosition(FeedMotor.getCurrentPosition() + PULSE_PER_REVOLUTION_NEVEREST40_OVER_40); //Uses 70 percent of power
            //telemetry.addData("FlickerAfter", /*"%10d",*/ Flicker.getCurrentPosition());

        }
        else {
            FeedMotor.setPower(0);
        }

        //ContinuosBigOl'Servo

        if (gamepad1.a) {
            ContinuousShooter.setPower(1);
        }
        else if (gamepad1.b){
            ContinuousShooter.setPower(0);
        }
//        if (gamepad1.y) {
//            ContinuousShooter.setPosition(0); //Max Speed up and down
//        }
//        else if (gamepad1.a){
//            ContinuousShooter.setPosition(1);
//        }
//        else {
//            ContinuousShooter.setPosition(0.5);
//        }

        //ContinuosBabyDuboisServo
        if (gamepad1.dpad_up) {
            SmallContinuous.setPower(-1);
        }
        else if (gamepad1.dpad_down) {
            SmallContinuous.setPower(1);
        } else {
            SmallContinuous.setPower(0);
        }



    }

    @Override
    public void stop() {
        super.stop();
        ContinuousShooter.setPower(0);
        SmallContinuous.setPower(0);
    }
}
