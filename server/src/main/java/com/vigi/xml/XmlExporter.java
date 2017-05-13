package com.vigi.xml;

import com.vigi.criteria.Criterion;
import grow.names.specification.ubl.schema.xsd.espdresponse_1.ESPDResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;

/**
 * Created by vigi on 5/13/2017.
 */
@Component
public class XmlExporter {

    private final Jaxb2Marshaller jaxb2Marshaller;
    private final ResponseTypeTransformer responseTypeTransformer;
    private final grow.names.specification.ubl.schema.xsd.espdresponse_1.ObjectFactory espdResponseObjectFactory;

    @Autowired
    XmlExporter(Jaxb2Marshaller jaxb2Marshaller,
                ResponseTypeTransformer responseTypeTransformer) {
        this.jaxb2Marshaller = jaxb2Marshaller;
        this.responseTypeTransformer = responseTypeTransformer;
        this.espdResponseObjectFactory = new grow.names.specification.ubl.schema.xsd.espdresponse_1.ObjectFactory();;
    }

    public ByteArrayOutputStream generateEspdResponse(Criterion criterion) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ESPDResponseType espdResponseType = responseTypeTransformer.buildResponseType(criterion);
        StreamResult result = new StreamResult(out);
        jaxb2Marshaller.marshal(espdResponseObjectFactory.createESPDResponse(espdResponseType), result);
        return out;
    }
}
