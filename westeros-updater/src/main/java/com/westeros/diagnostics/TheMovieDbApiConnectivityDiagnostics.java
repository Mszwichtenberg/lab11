package com.westeros.diagnostics;

import com.westeros.diagnostics.runners.IDiagnose;
import com.westeros.diagnostics.services.contract.Diagnostics;
import com.westeros.moviesclient.IMoviesClientUriBuilderProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class TheMovieDbApiConnectivityDiagnostics implements IDiagnose {

    private final IMoviesClientUriBuilderProvider uriBuilderProvider;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getName() {
        return "TheMovieDbApiConnectivityDiagnostics";
    }

    @Override
    public String getDescription() {
        return "Checks connectivity with TheMovieDB API using configured credentials.";
    }

    @Override
    public Diagnostics run() {
        var diagnostics = new Diagnostics();
        diagnostics.setName(getName());
        diagnostics.setDescription(getDescription());

        try {
            var uri = uriBuilderProvider.builder()
                    .pathSegment("configuration")
                    .toUriString();
            restTemplate.getForEntity(uri, String.class);
            diagnostics.setSuccess(true);
        } catch (Exception ex) {
            diagnostics.setSuccess(false);
            diagnostics.setErrorMessage(ex.getMessage());
        }

        return diagnostics;
    }
}
