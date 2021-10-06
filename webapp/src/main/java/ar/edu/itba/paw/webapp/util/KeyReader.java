package ar.edu.itba.paw.webapp.util;

import ar.edu.itba.paw.models.exception.KeyReaderException;
import ar.edu.itba.paw.webapp.config.WebAuthConfig;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class KeyReader {

    private KeyReader() {
        // Empty constructor
    }
    public static String get(String filename)
            throws KeyReaderException {
        try {
            InputStream fileStream = WebAuthConfig.class.getResourceAsStream(filename);
            return IOUtils.toString(Objects.requireNonNull(fileStream), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new KeyReaderException();
        }
    }
}
