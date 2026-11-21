package Sessions;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Named("rapportBean")
@RequestScoped
public class RapportBean {
    private static final Logger LOGGER = Logger.getLogger(RapportBean.class.getName());
    private static final String DATA_SOURCE = "java:jboss/datasources/gestion_conditionnement_db";

    public String exporterPdfStocks() {
        return exporter("/reports/etat_stocks.jrxml", "etat-stocks.pdf", "SELECT COUNT(*) FROM stocks s JOIN articles a ON a.id = s.article_id");
    }

    public String exporterPdfOperations() {
        return exporter("/reports/recapitulatif_operations.jrxml", "recapitulatif-operations.pdf", "SELECT COUNT(*) FROM operations_conditionnement");
    }

    private String exporter(String template, String filename, String countSql) {
        FacesContext faces = FacesContext.getCurrentInstance();
        if (faces == null) return null;
        try (InputStream jrxml = getClass().getResourceAsStream(template)) {
            if (jrxml == null) throw new IllegalStateException("Report template missing: " + template);
            DataSource dataSource = (DataSource) new InitialContext().lookup(DATA_SOURCE);
            try (Connection connection = dataSource.getConnection()) {
                if (!hasRows(connection, countSql)) {
                    faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Aucune donnée n’est disponible pour ce rapport.", ""));
                    return null;
                }
                JasperReport report = JasperCompileManager.compileReport(jrxml);
                JasperPrint print = JasperFillManager.fillReport(report, Collections.emptyMap(), connection);
                byte[] pdf = JasperExportManager.exportReportToPdf(print);

                HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
                response.resetBuffer();
                response.setContentType("application/pdf");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
                response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
                response.setHeader("Pragma", "no-cache");
                response.setContentLengthLong(pdf.length);
                try (OutputStream output = response.getOutputStream()) {
                    output.write(pdf);
                    output.flush();
                }
                faces.responseComplete();
                return null;
            }
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Échec de génération du rapport " + template, exception);
            faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Le rapport est temporairement indisponible. Réessayez plus tard.", ""));
            return null;
        }
    }

    private boolean hasRows(Connection connection, String query) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            return result.next() && result.getLong(1) > 0;
        }
    }
}
