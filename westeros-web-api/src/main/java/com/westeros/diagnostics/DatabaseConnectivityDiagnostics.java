package com.westeros.diagnostics;

import com.westeros.diagnostics.runners.IDiagnose;
import com.westeros.diagnostics.services.contract.Diagnostics;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
@RequiredArgsConstructor
public class DatabaseConnectivityDiagnostics implements IDiagnose {

    private final DataSource dataSource;

    @Override
    public String getName() {
        return "DatabaseConnectivityDiagnostics";
    }

    @Override
    public String getDescription() {
        return "Checks if application can connect to the configured database.";
    }

    @Override
    public Diagnostics run() {
        var diagnostics = new Diagnostics();
        diagnostics.setName(getName());
        diagnostics.setDescription(getDescription());

        try (Connection connection = dataSource.getConnection()) {
            boolean valid = connection.isValid(2);
            diagnostics.setSuccess(valid);
            if (!valid) {
                diagnostics.setErrorMessage("Connection is not valid");
            }
        } catch (Exception ex) {
            diagnostics.setSuccess(false);
            diagnostics.setErrorMessage(ex.getMessage());
        }

        return diagnostics;
    }
}
