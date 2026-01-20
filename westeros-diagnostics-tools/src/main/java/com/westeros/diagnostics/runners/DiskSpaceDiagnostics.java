package com.westeros.diagnostics.runners;

import com.westeros.diagnostics.services.contract.Diagnostics;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DiskSpaceDiagnostics implements IDiagnose {

    private static final long MIN_FREE_SPACE_BYTES = 100 * 1024 * 1024; // 100 MB

    @Override
    public String getName() {
        return "DiskSpaceDiagnostics";
    }

    @Override
    public String getDescription() {
        return "Checks if there is enough free disk space on the server.";
    }

    @Override
    public Diagnostics run() {
        var diagnostics = new Diagnostics();
        diagnostics.setName(getName());
        diagnostics.setDescription(getDescription());

        try {
            File root = new File("/");
            long free = root.getFreeSpace();
            boolean hasEnoughSpace = free >= MIN_FREE_SPACE_BYTES;
            diagnostics.setSuccess(hasEnoughSpace);

            if (!hasEnoughSpace) {
                diagnostics.setErrorMessage("Low disk space: " + free + " bytes available");
            }
        } catch (Exception ex) {
            diagnostics.setSuccess(false);
            diagnostics.setErrorMessage(ex.getMessage());
        }

        return diagnostics;
    }
}
