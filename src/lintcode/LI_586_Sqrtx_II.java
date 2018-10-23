package lintcode;

/**
 * Created by yuank on 10/19/18.
 */
public class LI_586_Sqrtx_II {
    /**
         Implement double sqrt(double x) and x >= 0.

         Compute and return the square root of x.

         Example
         Given n = 2 return 1.41421356

         Notice
         You do not care about the accuracy of the result, we will help you to output results.

         Medium
     */

    /**
     * Newton
     */
    public double sqrt(double number) {
        double epsilon = 1e-15;    // relative error tolerance
        double c = number;
        double t = number;              // estimate of the square root of c

        // repeatedly apply Newton update step until desired precision is achieved
        while (Math.abs(t - c / t) > epsilon * t) {
            t = (t + c / t) / 2.0;
        }

        return t;
    }
}
