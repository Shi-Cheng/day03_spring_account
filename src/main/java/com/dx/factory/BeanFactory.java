package com.dx.factory;

import com.dx.service.IAccountService;
import com.dx.utils.TransactionManagerUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BeanFactory {
    private IAccountService accountService;
    private TransactionManagerUtils transactionManagerUtils;

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    public void setTransactionManagerUtils(TransactionManagerUtils transactionManagerUtils) {
        this.transactionManagerUtils = transactionManagerUtils;
    }

    public IAccountService getAccountService() {
        Proxy.newProxyInstance(accountService.getClass().getClassLoader(), accountService.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object objValue = null;
                try{
                    transactionManagerUtils.begin();
                    System.out.println("proxyAccountService 增强");
                    objValue  = method.invoke(accountService,args);
                    transactionManagerUtils.commit();
                    return objValue;
                }catch(Throwable t ){
                    transactionManagerUtils.rollback();
                    t.printStackTrace();
                }finally {
                    transactionManagerUtils.release();
                }
                return objValue;
            }
        });
        return accountService;
    }
}
