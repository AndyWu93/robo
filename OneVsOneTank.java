import robocode.*;
import java.awt.Color;

public class OneVsOneTank extends AdvancedRobot {
    private double previousEnergy = 100;
    private int movementDirection = 1;
    private int gunDirection = 1;

    public void run() {
        setColors(Color.black, Color.orange, Color.red);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);
        turnRadarRightRadians(Double.POSITIVE_INFINITY);
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
        double bearingFromGun = Utils.normalRelativeAngle(absoluteBearing - getGunHeadingRadians());
        double bearingFromRadar = Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians());
        setTurnGunRadians(bearingFromGun);
        setTurnRadarRadians(bearingFromRadar);
        if (Math.abs(bearingFromGun) < Math.PI / 4) {
            setFire(3);
        }
        if (e.getEnergy() < previousEnergy) {
            movementDirection = -movementDirection;
            setAhead((e.getDistance() / 4 + 25) * movementDirection);
        }
        previousEnergy = e.getEnergy();
    }

    public void onHitByBullet(HitByBulletEvent e) {
        movementDirection = -movementDirection;
        setAhead(50 * movementDirection);
    }

    public void onHitWall(HitWallEvent e) {
        movementDirection = -movementDirection;
        setAhead(100 * movementDirection);
    }

    public void onHitRobot(HitRobotEvent e) {
        double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
        double bearingFromGun = Utils.normalRelativeAngle(absoluteBearing - getGunHeadingRadians());
        double bearingFromRadar = Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians());
        setTurnGunRadians(bearingFromGun);
        setTurnRadarRadians(bearingFromRadar);
        if (e.getEnergy() < 16) {
            setFire(3);
        } else if (e.getEnergy() < 10) {
            setFire(2);
        } else if (e.getEnergy() < 4) {
            setFire(1);
        } else if (e.getEnergy() < 2) {
            setFire(.5);
        } else if (e.getEnergy() < .4) {
            setFire(.1);
        }
        setBack(100);
    }
}
