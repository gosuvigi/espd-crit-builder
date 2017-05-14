package com.vigi.xml;

import com.vigi.criteria.Criterion;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by vigi on 5/14/2017.
 */
public interface XmlExporter {

    ByteArrayOutputStream generateEspdResponse(List<Criterion> criteria);
}
