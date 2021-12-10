/*
 * @author Zhen Guan
 * @id 202191382
 * @email zguan@mun.ca
 *
 * This file was prepared by Zhen Guan
 * It was finished by me alone
 */

package util;

public class Assert {
    public static void check(boolean exp, String why) {
        StackTraceElement[] bt = Thread.currentThread().getStackTrace();
        String detail = "(bt unavailable)";

        if (bt.length > 2) {
            StackTraceElement currentTrace = bt[2];

            // Backtrace info.
            String className = currentTrace.getClassName();
            String methodName = currentTrace.getMethodName();
            int lineNumber = currentTrace.getLineNumber();

            detail = String.format("at %s::%s, line #{%d}",
                className, methodName, lineNumber);
        }

        if (!exp) {
            throw new AssertionError(why + ": " + detail);
        }
    }

    public static void check(boolean exp) {
        check(exp, "Assertion failed");
    }
}
