package com.epam.payments.model.database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {
    private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();
    private static final DataSource dataSource;

    private ConnectionProvider() {
    }

    static {
        try {
            Context initialContext = new InitialContext();
            Context environmentContext = (Context) initialContext
                    .lookup("java:comp/env");
            String dataResourceName = "jdbc/payments";
            dataSource = (DataSource) environmentContext
                    .lookup(dataResourceName);
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        if (threadLocalConnection.get() != null) {
            return threadLocalConnection.get();
        } else {
            throw new RuntimeException("No connection is binded to the current environment context");
        }
    }

    public static void bindConnection() {
        try {
            if (threadLocalConnection.get() == null) {
                threadLocalConnection.set(dataSource.getConnection());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void unbindConnection() {
        try {
            if (threadLocalConnection.get() != null) {
                threadLocalConnection.get().close();
                threadLocalConnection.remove();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
