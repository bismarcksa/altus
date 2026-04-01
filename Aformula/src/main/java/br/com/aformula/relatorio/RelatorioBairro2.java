package br.com.aformula.relatorio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;

import br.com.aformula.domain.Bairro;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioBairro2 {

    private HttpServletResponse response;
    private FacesContext context;
    private ByteArrayOutputStream baos;
    private InputStream stream;

    private static final Logger logger = Logger.getLogger(RelatorioBairro2.class);

    public RelatorioBairro2() {
        this.context = FacesContext.getCurrentInstance();
        this.response = (HttpServletResponse) context.getExternalContext().getResponse();
    }

    public void getRelatorioBairro2(Bairro bairro, Long codigoBairro) {
        stream = this.getClass().getResourceAsStream("/Relatorio/Bairro2.jasper");

        if (stream == null) {
            logger.error("Relatório não encontrado no caminho especificado.");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("codigoBairro", codigoBairro); // Passando o parâmetro exigido pelo relatório
        
        baos = new ByteArrayOutputStream();

        try {
            logger.info("Carregando relatório...");
            JasperReport report = (JasperReport) JRLoader.loadObject(stream);
            logger.info("Relatório carregado com sucesso.");

            // Passando o bairro como item único na coleção
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(bairro));
            
            JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
            logger.info("Relatório preenchido com sucesso.");

            JasperExportManager.exportReportToPdfStream(print, baos);
            logger.info("PDF gerado com sucesso.");

            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "inline; filename=Bairro2.pdf");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();

            context.responseComplete();
        } catch (JRException e) {
            logger.error("Erro ao gerar o relatório.", e);
        } catch (IOException e) {
            logger.error("Erro ao enviar o relatório para o cliente.", e);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                logger.warn("Erro ao fechar streams.", e);
            }
        }
    }
}
