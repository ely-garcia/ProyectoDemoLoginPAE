package ni.edu.uam.demologin.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQLConnection implements IConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/estudiante";

    private static final String usuario = "postgres";
    private static final String password = "postgre123";

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, usuario, password);
    }
}
