package utils;

import components.base.UserSession;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {

    private static final Configuration CFG = new Configuration(new Version(2, 3, 27));

    static {
        CFG.setClassForTemplateLoading(PageGenerator.class, "/views");
    }

    public static String getPage(UserSession userSession, Map<String, Object> data) {

        String filename;
        if (userSession == null || userSession.getName() == null) {
            filename = "authform.html";
        } else if (userSession.getUserId() != 0) {
            filename = "main.html";
        } else {
            filename = "wait_authorization.html";
        }
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

}
