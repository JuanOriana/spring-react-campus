package ar.edu.itba.paw.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceptionMessageUtil {
    private ExceptionMessageUtil() {

    }
    public static String translate(String errorMessage) {
        String res = "";
        Pattern p = Pattern.compile( "Detail:(.*)$" );
        Matcher m = p.matcher( errorMessage );
        if ( m.find() ) {
            res = m.group(1);
        }
        return res;
    }
}
