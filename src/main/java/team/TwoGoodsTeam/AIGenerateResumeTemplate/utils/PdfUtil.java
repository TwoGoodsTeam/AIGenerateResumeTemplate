package team.TwoGoodsTeam.AIGenerateResumeTemplate.utils;


import com.lowagie.text.pdf.BaseFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

public class PdfUtil {

    private Logger logger = LoggerFactory.getLogger(PdfUtil.class);
    private AbstractConfigurableTemplateResolver templateResolver;
    private TemplateEngine templateEngine;

    public PdfUtil(final String prefix, final String suffix) {
        this(prefix, suffix, "HTML5", "UTF-8");
    }

    public PdfUtil(final String templatePrefix, final String templateSuffix, final String templateMode, final String templateEncoding) {
        this(new ClassLoaderTemplateResolver());
        this.templateResolver.setPrefix(templatePrefix);
        this.templateResolver.setSuffix(templateSuffix);
        this.templateResolver.setTemplateMode(templateMode);
        this.templateResolver.setCharacterEncoding(templateEncoding);
    }

    public PdfUtil(AbstractConfigurableTemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
    }

    public PdfUtil(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    private TemplateEngine getTemplateEngine() {
        if (templateEngine == null) {
            templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);
        }
        return templateEngine;
    }

    public void generate(File outputPDF, String template, Map<String, Object> variables) throws Exception {
        try {
            final Context context = new Context();
            context.setVariables(variables);
            final TemplateEngine templateEngine = getTemplateEngine();
            String htmlContent = templateEngine.process(template, context);

            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();
            try {
                fontResolver.addFont(System.getProperty("user.dir")+"/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                fontResolver.addFont(System.getProperty("user.dir")+"/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new Exception(e.getMessage());
            }
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(new FileOutputStream(outputPDF));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
