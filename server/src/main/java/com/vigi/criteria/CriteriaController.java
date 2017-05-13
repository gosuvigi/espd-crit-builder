package com.vigi.criteria;

import com.vigi.xml.XmlExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * Created by vigi on 5/13/2017.
 */
@RestController
@RequestMapping("/api/criteria")
class CriteriaController {

    private final XmlExporter xmlExporter;

    @Autowired
    CriteriaController(XmlExporter xmlExporter) {
        this.xmlExporter = xmlExporter;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public String getEconomicEntity(@RequestBody Criterion criterion,
                                    HttpServletResponse response) throws IOException {
        System.out.println(criterion);
        response.setContentType(APPLICATION_XML_VALUE);
        ByteArrayOutputStream out = xmlExporter.generateEspdResponse(criterion);
        serveFileForDownload(out, response);

        return null;
    }

    private void serveFileForDownload(ByteArrayOutputStream fileStream,
                                      HttpServletResponse response) throws IOException {
        String fileName = "espd-response.xml";
        response.setContentType(APPLICATION_XML_VALUE);
        response.setContentLength(fileStream.size());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, format("attachment; filename=\"%s\"", fileName));

        // Send content to Browser
        response.getOutputStream().write(fileStream.toByteArray());
        response.getOutputStream().flush();
    }

}
