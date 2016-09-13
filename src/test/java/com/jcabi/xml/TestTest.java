package com.jcabi.xml;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.w3c.dom.Node;

/**
 * Created by smv on 13/09/16.
 */
public class TestTest {

    @Test
    public void firstTry() {
        System.out.println(StringUtils.join(
                "sdf+",
                "asdf+",
                "adf"));

        System.setProperty("javax.xml.transform.TransformerFactory", "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");

        XML xml = new XMLDocument("<xml><level1><level2><level3></level3><level3></level3></level2></level1></xml>");

        XML node = xml.nodes("//level1").get(0);

        System.out.println(xml.toString());
        System.out.println(xml.toCompactString());
        System.out.println(xml.toPrettyString(4));

        System.out.println(System.getProperty("javax.xml.transform.TransformerFactory"));

    }

    @Test
    public void testStrip() {
        XML xml = new XMLDocument("<xml><level1><level2><level3></level3><level3></level3></level2></level1></xml>");

        XSL xsl = XSLDocument.STRIP;
        System.out.println(xsl.applyTo(xml));


    }

}
