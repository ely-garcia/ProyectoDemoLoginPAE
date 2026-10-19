package ni.edu.uam.demologin.config;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnection {
    Connection getConnection() throws SQLException;
}