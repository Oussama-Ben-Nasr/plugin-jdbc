package io.kestra.plugin.jdbc.postgresql;

import io.kestra.core.exceptions.IllegalVariableEvaluationException;
import io.kestra.core.runners.RunContext;
import io.kestra.plugin.jdbc.AbstractJdbcConnection;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public abstract class PostgresService {
    public static void handleSsl(Properties properties, RunContext runContext, PostgresConnectionInterface conn, AbstractJdbcConnection abstractJdbcConnection) throws IllegalVariableEvaluationException, IOException {
        if (conn.getSsl() != null && conn.getSsl()) {
            properties.put("ssl", "true");
        }

        if (conn.getSslMode() != null) {
            properties.put("sslmode", conn.getSslMode().name().toUpperCase(Locale.ROOT).replace("_", "-"));
        }

        if (conn.getSslRootCert() != null) {
            properties.put("sslrootcert", abstractJdbcConnection.tempFile(runContext.render(conn.getSslRootCert())).toAbsolutePath().toString());
        }

        if (conn.getSslCert() != null) {
            properties.put("sslcert", abstractJdbcConnection.tempFile(runContext.render(conn.getSslCert())).toAbsolutePath().toString());
        }

        if (conn.getSslKey() != null) {
            properties.put("sslkey", abstractJdbcConnection.tempFile(runContext.render(conn.getSslKey())).toAbsolutePath().toString());
        }

        if (conn.getSslKeyPassword() != null) {
            properties.put("sslpassword", runContext.render(conn.getSslKeyPassword()));
        }
    }
}
