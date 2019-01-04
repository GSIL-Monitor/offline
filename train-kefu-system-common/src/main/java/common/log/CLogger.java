package common.log;

import com.ctrip.framework.clogging.agent.log.ILog;
import com.ctrip.framework.clogging.agent.log.LogManager;
import com.ctrip.framework.clogging.agent.metrics.IMetric;
import com.ctrip.framework.clogging.agent.metrics.MetricManager;
import com.ctrip.framework.foundation.Foundation;

import java.util.Map;

/**
 * standard log tool for the Clog
 * @author ying_zhou
 */
public class CLogger {
    private static ILog instance = LogManager.getLogger("Train.Offline");

    private static IMetric metric = MetricManager.getMetricer();

    public static void fatal(String title, Exception ex) {
        instance.fatal(createStandardTitle(title), ex);
    }
    public static  void fatal(String title, String msg){
        instance.fatal(title,msg);
    }
    public static void fatal(String title, Exception ex, Map<String, String> tags) {
        instance.fatal(createStandardTitle(title), ex, tags);
    }
    public static void fatal(String title, String  msg, Map<String, String> tags) {
        instance.fatal(createStandardTitle(title), msg, tags);
    }
    public static void error(String title, String msg) {
        instance.error(createStandardTitle(title), msg);
    }
    public static void error(String title, Exception ex) {
        instance.error(createStandardTitle(title), ex);
    }
    public static void error(String title, Exception ex, Map<String, String> tags) {
        instance.error(createStandardTitle(title), ex, tags);
    }
    public static void error(String title, String msg , Map<String, String> tags) {
        instance.error(createStandardTitle(title),msg,tags);
    }
    public static void warn(String title, String msg) {
        instance.warn(createStandardTitle(title), msg);
    }
    public static void warn(String title, Exception ex) {
        instance.warn(createStandardTitle(title), ex);
    }
    public static void warn(String title, Exception ex, Map<String, String> tags) {
        instance.warn(createStandardTitle(title), ex, tags);
    }
    public static void warn(String title, String msg , Map<String, String> tags) {
        instance.warn(createStandardTitle(title),msg,tags);
    }
    public static void info(String title, String msg) {
        instance.info(createStandardTitle(title), msg);
    }
    public static void info(String title, Exception ex) {
        instance.info(createStandardTitle(title), ex);
    }
    public static void info(String title, Exception ex, Map<String, String> tags) {
        instance.info(createStandardTitle(title), ex, tags);
    }
    public static void info(String title, String msg , Map<String, String> tags) {
        instance.info(createStandardTitle(title),msg,tags);
    }
    public static void debug(String title, String msg) {
        instance.debug(createStandardTitle(title), msg);
    }
    public static void debug(String title, Exception ex) {
        instance.debug(createStandardTitle(title), ex);
    }
    public static void debug(String title, Exception ex, Map<String, String> tags) {
        instance.debug(createStandardTitle(title), ex, tags);
    }
    public static void debug(String title, String msg , Map<String, String> tags) {
        instance.debug(createStandardTitle(title),msg,tags);
    }

    /**
     * Create a title like 'env_title' according to the runtime environment
     * @param title
     * @return
     */
    private static String createStandardTitle(String title) {
        return String.format("%s_%s", Foundation.server().getEnv().getName(), title);
    }

    /**
     * Write metric log to Dashboard
     * And the time recorded is only accurate to one second
     * @see IMetric
     * @param name
     * @param value
     * @param tags not more than 4 exclude 'appid' and 'hostip'
     */
    public static void writeMetricLog(String name, float value, Map<String, String> tags) {
        try {
            metric.log(name, value, tags);
        } catch (Exception ex) {
            error("Metric Exception", ex, null);
        }
    }

}
