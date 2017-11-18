package org.firstinspires.ftc.teamcode.Experimental.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Experimental.Framework.Subsystem;
import org.firstinspires.ftc.teamcode.competitionCode.Log;
import org.firstinspires.ftc.teamcode.competitionCode.MotorGroup;

/**
 * Created by Varun on 11/17/2017.
 */

public class LiftSubsystem extends Subsystem{
    Log LiftLog;
    MotorGroup liftMotors;
    Servo ll, rl;
    AnalogInput pl, pr;

    public LiftSubsystem(OpMode opmode, MotorGroup liftMotors, Servo ll, Servo rl, AnalogInput pl, AnalogInput pr){
        super(opmode);

        this.liftMotors = liftMotors;
        this.ll = ll;
        this.rl = rl;
        this.pl = pl;
        this.pr = pr;
        LiftLog = new Log(opmode);
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void stop() {

    }
}
