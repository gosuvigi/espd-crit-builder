//package com.vigi.xml.v102;
//
//import com.vigi.criteria.Criterion;
//import com.vigi.xml.XmlExporter;
//import grow.names.specification.ubl.schema.xsd.espdresponse_1.ESPDResponseType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//import org.springframework.stereotype.Component;
//
//import javax.xml.transform.stream.StreamResult;
//import java.io.ByteArrayOutputStream;
//import java.util.List;
//
///**
// * Created by vigi on 5/13/2017.
// */
//@Component("xmlExporter102")
//class XmlExporter102 implements XmlExporter {
//
//    private final Jaxb2Marshaller jaxb2Marshaller;
//    private final ESPDResponseTypeTransformer responseTypeTransformer;
//    private final grow.names.specification.ubl.schema.xsd.espdresponse_1.ObjectFactory espdResponseObjectFactory;
//
//    @Autowired
//    XmlExporter102(Jaxb2Marshaller jaxb2Marshaller,
//                   ESPDResponseTypeTransformer responseTypeTransformer) {
//        this.jaxb2Marshaller = jaxb2Marshaller;
//        this.responseTypeTransformer = responseTypeTransformer;
//        this.espdResponseObjectFactory = new grow.names.specification.ubl.schema.xsd.espdresponse_1.ObjectFactory();
//    }
//
//    @Override
//    public ByteArrayOutputStream generateEspdResponse(List<Criterion> criteria) {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ESPDResponseType espdResponseType = responseTypeTransformer.buildResponseType(criteria.get(0));
//        StreamResult result = new StreamResult(out);
//        jaxb2Marshaller.marshal(espdResponseObjectFactory.createESPDResponse(espdResponseType), result);
//        return out;
//    }
//}
