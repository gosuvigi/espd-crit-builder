package com.vigi.xml.v200;

import com.vigi.criteria.Criterion;
import com.vigi.xml.XmlExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import test.x.ubl.pre_award.qualificationapplicationresponse.QualificationApplicationResponseType;

import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by vigi on 5/14/2017.
 */
@Component("xmlExporter200")
@Primary
class XmlExporter200 implements XmlExporter {

    private final Jaxb2Marshaller jaxb2Marshaller;
    private final QualificationApplicationResponseTransformer responseTransformer;
    private final test.x.ubl.pre_award.qualificationapplicationresponse.ObjectFactory objectFactory;

    @Autowired
    XmlExporter200(Jaxb2Marshaller jaxb2Marshaller,
                   QualificationApplicationResponseTransformer responseTransformer) {
        this.jaxb2Marshaller = jaxb2Marshaller;
        this.responseTransformer = responseTransformer;
        this.objectFactory = new test.x.ubl.pre_award.qualificationapplicationresponse.ObjectFactory();
    }

    @Override
    public ByteArrayOutputStream generateEspdResponse(List<Criterion> criteria) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        QualificationApplicationResponseType espdResponseType = responseTransformer.buildResponseType(criteria);
        StreamResult result = new StreamResult(out);
        jaxb2Marshaller.marshal(objectFactory.createQualificationApplicationResponse(espdResponseType), result);
        return out;
    }
}
