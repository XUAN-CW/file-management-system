package com.xuanchengwei.filemanagementsystem.handler;

import com.xuanchengwei.filemanagementsystem.constants.DateConstants;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 完成 LocalDateTime 到数据库的映射
 * <a href="https://www.cnblogs.com/Hakurei-Reimu-Zh/p/16534759.html">Mybatis 使用 SQLite 不支持 LocalDateTime 的解决方案</a>
 * @author 禤成伟
 * @since 2022-07-30 12:19
 */
@Component
public class LocalDateTimeHandler extends BaseTypeHandler<LocalDateTime> {

    @Override
    public void setNonNullParameter(@NonNull PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, DateTimeFormatter.ofPattern(DateConstants.LOCAL_DATE_TIME_PATTERN).format(parameter));
    }

    @Override
    public LocalDateTime getNullableResult(@NonNull ResultSet rs, String columnName) throws SQLException {
        return ts2LocalDateTime(rs.getString(columnName));
    }

    @Override
    public LocalDateTime getNullableResult(@NonNull ResultSet rs, int columnIndex) throws SQLException {
        return ts2LocalDateTime(rs.getString(columnIndex));
    }

    @Override
    public LocalDateTime getNullableResult(@NonNull CallableStatement cs, int columnIndex) throws SQLException {
        return ts2LocalDateTime(cs.getString(columnIndex));
    }

    @NonNull
    private LocalDateTime ts2LocalDateTime(@NonNull String localDateTimeString) {
        return LocalDateTime.parse(localDateTimeString,DateTimeFormatter.ofPattern(DateConstants.LOCAL_DATE_TIME_PATTERN));
    }

}

