package com.dx.utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getThreadConnection(){
        try {
            Connection conn = threadLocal.get();
            if (conn == null){
                conn = dataSource.getConnection();
                threadLocal.set(conn);
            }
            return  conn;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void removeConnection(){
        threadLocal.remove();
    }
}
