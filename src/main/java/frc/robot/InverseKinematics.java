package frc.robot;
import java.lang.Math;

public class InverseKinematics {
	// r1 is the bottom arm's length
	// r2 is the upper arm's length
	// (x, y) is the coordinate of the target location
	public static double[] calculateTheta(double r1, double r2, double x, double y) {
		if (y < 0) {
			System.out.println("Given y is negative, this code does not support points in quadrants 3 & 4");
			return new double[] {Double.NaN, Double.NaN};
		}
		if (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) > r1 + r2) {
			System.out.println("Given (x,y) unrechable by r1 & r2 lengths");
			return new double[] {Double.NaN, Double.NaN};
		}
		
		boolean isSecondQuadrant = (x < 0);
		if (isSecondQuadrant) {
			x = -x;
		}
		double firstTheta2 = Math.acos((Math.pow(x, 2) + Math.pow(y, 2) - Math.pow(r1, 2) - Math.pow(r2, 2)) / (2 * r1 * r2));
		double firstTheta1 = Math.atan(y / x) - Math.atan((r2 * Math.sin(firstTheta2)) / (r1 + r2 * Math.cos(firstTheta2)));
		double secondTheta2 = -Math.acos((Math.pow(x, 2) + Math.pow(y, 2) - Math.pow(r1, 2) - Math.pow(r2, 2)) / (2 * r1 * r2));
		double secondTheta1 = Math.atan(y / x) + Math.atan((r2 * Math.sin(secondTheta2)) / (r1 + r2 * Math.cos(secondTheta2)));
		
		double[] thetaSet;
		if (firstTheta1 > 0) {
			if (isSecondQuadrant) {
				thetaSet = new double[]{Math.PI - firstTheta1, Math.PI - firstTheta2 - firstTheta1};
			} else {
			thetaSet = new double[]{firstTheta1, firstTheta2 + firstTheta1};
			}
		} else if (secondTheta1 > 0) {
			if (isSecondQuadrant) {
				thetaSet = new double[]{Math.PI - secondTheta1, Math.PI - secondTheta2 - secondTheta1};
			} else {
			thetaSet = new double[]{secondTheta1, secondTheta2 + secondTheta1};
			}
		} else {
			if (isSecondQuadrant) {
				thetaSet = new double[]{Math.PI  - firstTheta2 - firstTheta1, Math.PI - firstTheta1};
			} else {
			thetaSet = new double[]{firstTheta2 + firstTheta1, firstTheta1};
			}
		}
		
		// This should theoretically never happen with the used logic, but you can never be too cautious
		assert (thetaSet[0] > 0 && thetaSet[0] < Math.PI) : "Error has Occured, theta1 value invalid";
		assert (thetaSet[1] - thetaSet[0] > Math.PI/2 && thetaSet[1] - thetaSet[0] < Math.PI): "Error has Occured, wrong solution has been selected";

		return thetaSet;
	}
	
	// calculate the proportional speed needed to smoothly go from one angle set to another
	public static double[] calculateThetaSpeed(double currTheta1, double currTheta2, double destTheta1, double destTheta2) {
		double deltaTheta1 = destTheta1 - currTheta1;
		double deltaTheta2 = destTheta2 - currTheta2;
		double maxDelta = Math.max(Math.abs(deltaTheta1), Math.abs(deltaTheta2));
		return new double[]{deltaTheta1/maxDelta, deltaTheta2/maxDelta};
	}
}