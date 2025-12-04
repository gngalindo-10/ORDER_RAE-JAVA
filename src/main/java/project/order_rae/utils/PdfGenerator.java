package project.order_rae.utils;

import freemarker.template.Template;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import jakarta.servlet.http.HttpServletResponse;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.OutputStream;
import java.util.*;

@Component
public class PdfGenerator {

    private final FreeMarkerConfigurer configurer;

    public PdfGenerator(FreeMarkerConfigurer configurer) {
        this.configurer = configurer;
    }

        public void generarPdf(String templateName, List<?> datos, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("datos", datos);
        model.put("fechaActual", new Date()); 

        Template template = configurer.getConfiguration().getTemplate(templateName + ".ftl", "UTF-8");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + templateName + ".pdf");

        try (OutputStream out = response.getOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(out);
        }
    }

}