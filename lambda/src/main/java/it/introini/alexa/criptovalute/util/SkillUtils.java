package it.introini.alexa.criptovalute.util;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;

import java.util.Locale;
import java.util.ResourceBundle;

public class SkillUtils {

    public static String m(HandlerInput input, String key, Object... params) {
        ResourceBundle resources = getResourceBundle(input, "Messages");
        return String.format(resources.getString(key), params);
    }

    public static ResourceBundle getResourceBundle(HandlerInput handlerInput, String bundleName) {
        var locale = new Locale(handlerInput.getRequestEnvelope().getRequest().getLocale());
        return ResourceBundle.getBundle(bundleName, locale);
    }
}
