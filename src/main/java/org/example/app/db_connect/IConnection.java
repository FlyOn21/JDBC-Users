package org.example.app.db_connect;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public interface IConnection {
    Configuration initEnvironment();
    boolean isConnected();
    List<String> getConnectErrors();
    SessionFactory getSessionFactory();
}
