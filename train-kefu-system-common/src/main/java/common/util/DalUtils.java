package common.util;

import com.ctrip.platform.dal.dao.StatementParameters;

import java.sql.Types;
import java.util.List;

/**
 * to refine the code of the original DAL framework
 * and make the code more reliable
 * simplify the parameter given
 * @author ying_zhou 2017/10/25 15:01
 * @version 2.0
 */
public class DalUtils {
    public static Builder createBuilder() {
        return new Builder();
    }

    public final static class Builder {
        private StatementParameters parameters;
        private StringBuilder sb;
        private int parameterIndex;

        private Builder() {
            parameters = new StatementParameters();
            sb = new StringBuilder();
            parameterIndex = 1;
        }

        public Builder insertFirst(String sql) {
            sb.insert(0, String.format(" %s ", sql));
            return this;
        }

        /**
         * the types of parameter can be inferred by itself with reflection
         * @param sql
         * @param parameter
         * @since 2.0
         * @return
         */
        public Builder combine(String sql, Object parameter) {
            return combine(sql,convertSqlTypes(parameter), parameter);
        }
        public Builder combine(String sql, int sqlType, Object value) {
            sb.append(String.format(" %s ", sql));
            parameters.set(parameterIndex++, sqlType, value);
            return this;
        }

        public Builder combine(String sql) {
            sb.append(String.format(" %s ", sql));
            return this;
        }

        public Builder combine(boolean condition, String sql, Object value) {
            if(condition){
                combine( sql,value);
            }
            return this;
        }
        public Builder combine(boolean condition, String sql, int sqlType, Object value) {
            if (condition) {
                return combine(sql, sqlType, value);
            }
            return this;

        }

        public Builder combine(boolean condition, String sql) {
            if (condition) {
                return combine(sql);
            }
            return this;
        }

        /**
         * @since 2.0
         * @param sql
         * @param value
         * @return
         */
        public Builder combineIn(String sql, int sqlType, List<?> value) {
            sb.append(String.format(" %s ", sql));
            parameters.setInParameter(parameterIndex++, sqlType, value);
            return this;
        }

        public Builder combineIn(boolean condition, String sql, int sqlType, List<? extends Object> value) {
           if(condition){
               combineIn(sql, sqlType, value);
           }
            return this;
        }

        /**
         * @since 2.0
         * @param conditionExpr
         * @param pageIndex if this parameter is less than 1,it will be reset to 1 inner
         * @param pageSize if this parameter is less than 1,it will be reset to 20 inner
         * @return
         */
        public Builder combinePageLimit(boolean conditionExpr, Integer pageIndex,Integer pageSize) {
            if(conditionExpr){
                return combinePageLimit(pageIndex,pageSize);
            }
            else{
                return combinePageLimit(1,20);
            }
        }
        public Builder combinePageLimit(Integer pageIndex,Integer pageSize) {
            sb.append(String.format(" limit %d,%d ",(pageIndex-1)*pageSize,pageSize));
            return this;
        }
        /**
         * get the parameters with reliable order
         *
         * @return
         */
        public StatementParameters getParameters() {
            return parameters;
        }

        /**
         * get the sql combined by  this builder;
         * @return
         */
        public String getSql() {
            return sb.toString();
        }

        /**
         * Not Intact but enough for now
         * @param value
         * @return
         */
        private int convertSqlTypes(Object value) {
            switch (value.getClass().getName()) {
                case "java.lang.Integer":
                    return Types.INTEGER;
                case "java.lang.Long":
                    return Types.BIGINT;
                case "java.lang.Double":
                    return Types.DOUBLE;
                case "java.lang.Float":
                    return Types.REAL;
                case "java.lang.Short":
                    return Types.SMALLINT;
                case "java.lang.String":
                    return Types.VARCHAR;
                case "java.lang.Boolean":
                    return Types.BIT;
                case "java.lang.Byte":
                    return Types.TINYINT;
                case "java.sql.Timestamp":
                    return Types.TIMESTAMP;
                default:
                    return Types.OTHER;
            }
        }
    }
}
