package com.dx.utils;

import java.sql.SQLException;

/**
 * 和事务管理相关的工具类，它包含了，开启事务、提交事务、回滚事务和释放事务
 * 线程用完之后要与连接进行解绑操作，
 * 在事务管理类中注入连接，方便对连接的管理操作
 */
public class TransactionManagerUtils {

    private ConnectionUtils connectionUtils;
    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    public void begin(){
        try {
            connectionUtils.getThreadConnection().setAutoCommit(false);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void commit(){
        try {
            connectionUtils.getThreadConnection().commit();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void rollback(){
        try {
            connectionUtils.getThreadConnection().rollback();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void release(){
        try {
            connectionUtils.getThreadConnection().close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
