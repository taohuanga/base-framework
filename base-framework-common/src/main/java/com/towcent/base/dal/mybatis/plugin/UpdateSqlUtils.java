package com.towcent.base.dal.mybatis.plugin;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import java.util.regex.Pattern;

public final class UpdateSqlUtils {
  private static final XLogger LOGGER = XLoggerFactory.getXLogger(UpdateSqlUtils.class);

  private static final Pattern updatePattern =
      Pattern.compile("update\\s+", Pattern.CASE_INSENSITIVE);
  private static final Pattern deletePattern =
      Pattern.compile("delete\\s+", Pattern.CASE_INSENSITIVE);
  private static final Pattern wherePattern =
      Pattern.compile("\\s+where\\s+", Pattern.CASE_INSENSITIVE);
  private static final Pattern oneEqualOnePattern =
      Pattern.compile("1\\s*=\\s*1", Pattern.CASE_INSENSITIVE);
  private static final Pattern andPattern =
      Pattern.compile("\\s+and\\s+", Pattern.CASE_INSENSITIVE);

  private UpdateSqlUtils() {

  }

  /**
   * Check to see whether SQL statements including update, delete are submittable to
   * prevent from updating 'all' data in case no where clause appended in where clause.
   */
  public static final boolean isSqlSubmittable(String sqlStr) {
    if (StringUtils.isEmpty(sqlStr)) {
      return true;
    }

    if (updatePattern.matcher(sqlStr).find() ||
        deletePattern.matcher(sqlStr).find()) {
      if (!wherePattern.matcher(sqlStr).find()) {
        LOGGER.error("SQL should have where clause to prevent from deleting all data:\n" + sqlStr);
        return false;
      } else if (oneEqualOnePattern.matcher(sqlStr).find() && !andPattern.matcher(sqlStr).find()) {
        LOGGER.error("SQL shouldn't have 1=1 clause to prevent from deleting all data:\n" + sqlStr);
        return false;
      } else {
        return true;
      }
    } else {
      return true;
    }
  }
}
