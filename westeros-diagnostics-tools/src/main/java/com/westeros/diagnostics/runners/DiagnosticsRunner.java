package com.westeros.diagnostics.runners;

import com.westeros.diagnostics.services.contract.Diagnostics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DiagnosticsRunner implements IRunDiagnoses {

    private final List<IDiagnose> diagnoses;

    @Override
    public List<Diagnostics> runAll() {
        List<Diagnostics> results = new ArrayList<>();

        for (IDiagnose diagnose : diagnoses) {
            try {
                Diagnostics result = diagnose.run();
                if (result == null) {
                    result = new Diagnostics();
                }
                if (result.getName() == null) {
                    result.setName(diagnose.getName());
                }
                if (result.getDescription() == null) {
                    result.setDescription(diagnose.getDescription());
                }
                results.add(result);
            } catch (Exception ex) {
                Diagnostics failed = new Diagnostics();
                failed.setName(diagnose.getName());
                failed.setDescription(diagnose.getDescription());
                failed.setSuccess(false);
                failed.setErrorMessage(ex.getMessage());
                results.add(failed);
            }
        }

        return results;
    }
}
