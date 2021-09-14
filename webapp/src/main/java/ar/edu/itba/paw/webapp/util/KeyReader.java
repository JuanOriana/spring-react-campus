package ar.edu.itba.paw.webapp.util;

import ar.edu.itba.paw.webapp.config.WebAuthConfig;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class KeyReader {
    public static String get(String filename)
            throws Exception {
        InputStream cookie_stream = WebAuthConfig.class.getResourceAsStream(filename);
        return IOUtils.toString(Objects.requireNonNull(cookie_stream), StandardCharsets.UTF_8);
    }
}
