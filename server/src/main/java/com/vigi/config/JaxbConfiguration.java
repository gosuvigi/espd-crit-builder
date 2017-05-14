package com.vigi.config;

import grow.names.specification.ubl.schema.xsd.espdrequest_1.ESPDRequestType;
import grow.names.specification.ubl.schema.xsd.espdresponse_1.ESPDResponseType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import test.x.ubl.pre_award.qualificationapplicationrequest.QualificationApplicationRequestType;
import test.x.ubl.pre_award.qualificationapplicationresponse.QualificationApplicationResponseType;

import javax.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vigi on 5/13/2017.
 */
@Configuration
public class JaxbConfiguration {

    @Bean("jaxb2Marshaller200")
    @Primary
    public Jaxb2Marshaller jaxb2Marshaller200() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan(QualificationApplicationResponseType.class.getPackage().getName(),
                QualificationApplicationRequestType.class.getPackage().getName());
        Map<String, Object> map = new HashMap<>(2);
        map.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxb2Marshaller.setMarshallerProperties(map);
        return jaxb2Marshaller;
    }

    @Bean("jaxb2Marshaller102")
    public Jaxb2Marshaller jaxb2Marshaller102() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan(ESPDRequestType.class.getPackage().getName(),
                ESPDResponseType.class.getPackage().getName());
        Map<String, Object> map = new HashMap<>(2);
        map.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxb2Marshaller.setMarshallerProperties(map);
        return jaxb2Marshaller;
    }
}
