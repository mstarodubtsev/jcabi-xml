/**
 * Copyright (c) 2012-2013, JCabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.xml;

import com.jcabi.aspects.Loggable;
import com.jcabi.log.Logger;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Convenient parser of XML to DOM.
 *
 * <p>Objects of this class are immutable and thread-safe.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@ToString
@EqualsAndHashCode(of = "xml")
@Loggable(Loggable.DEBUG)
final class DomParser {

    /**
     * Pattern to detect if passed txt looks like xml.
     */
    private static final Pattern PATTERN = Pattern.compile(Patterns.XML);

    /**
     * The XML as a text.
     */
    private final transient String xml;

    /**
     * Document builder factory to use for parsing.
     */
    private final transient DocumentBuilderFactory factory;

    /**
     * Public ctor.
     *
     * <p>An {@link IllegalArgumentException} may be thrown if the parameter
     * passed is not in XML format. It doesn't perform a strict validation
     * and is not guaranteed that an exception will be thrown whenever
     * the parameter is not XML.
     *
     * @param fct Document builder factory to use
     * @param txt The XML in text
     */
    DomParser(final DocumentBuilderFactory fct, final String txt) {
        if (txt.isEmpty()) {
            throw new IllegalArgumentException("Empty document, not an XML");
        }
        if (!DomParser.PATTERN.matcher(txt.replaceAll("\\s", "")).matches()) {
            throw new IllegalArgumentException(
                String.format("Doesn't look like XML: '%s'", txt)
            );
        }
        this.xml = txt;
        this.factory = fct;
    }

    /**
     * Get document of body.
     * @return The document
     */
    @NotNull
    public Document document() {
        final Document doc;
        try {
            doc = this.factory.newDocumentBuilder().parse(
                new ByteArrayInputStream(
                    this.xml.getBytes(TextResource.ENCODING)
                )
            );
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        } catch (final ParserConfigurationException ex) {
            throw new IllegalStateException(ex);
        } catch (final SAXException ex) {
            throw new IllegalArgumentException(
                Logger.format("Invalid XML: \"%s\"", this.xml), ex
            );
        }
        return doc;
    }

}
