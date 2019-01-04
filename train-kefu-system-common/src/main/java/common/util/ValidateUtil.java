package common.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xzding
 */
public class ValidateUtil {
    /**
     * 判断日期是否符合某种格式
     * @param objective
     * @param pattern
     * @return
     */
    public static boolean isDate(String objective, String pattern){
        if(objective == null || pattern == null){
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try{
            dateFormat.parse(objective);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 验证email是否合法
     * copy from com.ctriposs.baiji.rpc.server.validation.validators.EmailValidator
     * @param objective
     * @return
     */
    public static boolean isEmail(String objective){
        if(objective == null){
            return false;
        }
        String expression = "^(?:[\\w\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\`\\{\\|\\}\\~]+\\.)*[\\w\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\`\\{\\|\\}\\~]+@(?:(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9\\-](?!\\.)){0,61}[a-zA-Z0-9]?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9\\-](?!$)){0,61}[a-zA-Z0-9]?)|(?:\\[(?:(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.){3}(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\]))$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(objective);
        return matcher.matches();
    }

    public static boolean isPhoneNumber(String objective){
        if(objective == null){
            return false;
        }
        String expression = "^[0-9]*$";//由于不清楚国际手机号码格式，所以判定是数字的都是手机号
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(objective);
        return matcher.matches();
    }

    /**
     * 判断字符串是否是成长整型
     * @param objective
     * @return
     */
    public static boolean isLong(String objective){
        try{
            Long.valueOf(objective);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
}
