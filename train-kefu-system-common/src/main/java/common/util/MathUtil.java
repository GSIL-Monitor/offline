package common.util;

import java.text.NumberFormat;

public class MathUtil {
    /**
     *
     * 获取百分比
     * @param num1
     * @param num2
     * @return
     */
    public static String getPercentage(int num1,int num2){
        return String.format("%.2f",(double) num1 / num2 * 100);
    }
}
