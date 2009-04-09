/*
 * Copyright 2002 - 2007 JEuclid, http://jeuclid.sf.net
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: DocumentElement.java 835 2008-09-23 09:48:54Z maxberger $ */

package euclid.elements.generic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import euclid.LayoutContext;

import org.apache.batik.dom.GenericDocument;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.mathml.MathMLDocument;
import org.w3c.dom.views.DocumentView;

import euclid.context.LayoutContextImpl;
import euclid.context.Parameter;
import euclid.elements.JEuclidElementFactory;
import euclid.elements.JEuclidNode;
import euclid.elements.support.ElementListSupport;
import euclid.layout.JEuclidView;
import euclid.layout.LayoutInfo;
import euclid.layout.LayoutStage;
import euclid.layout.LayoutView;
import euclid.layout.LayoutableDocument;
import euclid.layout.LayoutableNode;

/**
 * Class for MathML Document Nodes.
 * 
 * @version $Revision: 835 $
 */
public final class DocumentElement extends GenericDocument implements
        MathMLDocument, JEuclidNode, DocumentView, LayoutableDocument {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a math element.
     * 
     */
    public DocumentElement() {
        this(null);
    }

    /**
     * Creates a MathML compatible document with the given DocumentType.
     * 
     * @param doctype
     *            DocumentType to use. This is currently ignored.
     */
    public DocumentElement(final DocumentType doctype) {
        super(doctype, JEuclidDOMImplementation.getInstance());
        super.setEventsEnabled(true);
        this.ownerDocument = this;
    }

    /** {@inheritDoc} */
    public String getDomain() {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    public String getReferrer() {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    public String getURI() {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    public LayoutContext getChildLayoutContext(final int childNum,
            final LayoutContext context) {
        return context;
    }

    /** {@inheritDoc} */
    // CHECKSTYLE:OFF
    public JEuclidView getDefaultView() {
        // CHECKSTYLE:ON
        final Image tempimage = new BufferedImage(1, 1,
                BufferedImage.TYPE_INT_ARGB);
        final Graphics2D tempg = (Graphics2D) tempimage.getGraphics();
        // original ohne erstes This
        return new JEuclidView(this, this, LayoutContextImpl
                .getDefaultLayoutContext(), tempg);
    }

    /** {@inheritDoc} */
    public List<LayoutableNode> getChildrenToLayout() {
        return ElementListSupport.createListOfLayoutChildren(this);
    }

    /** {@inheritDoc} */
    public List<LayoutableNode> getChildrenToDraw() {
        return ElementListSupport.createListOfLayoutChildren(this);
    }

    /** {@inheritDoc} */
    public void layoutStage1(final LayoutView view, final LayoutInfo info,
            final LayoutStage childMinStage, final LayoutContext context) {
        ElementListSupport.layoutSequential(view, info, this
                .getChildrenToLayout(), LayoutStage.STAGE1);
        info.setLayoutStage(childMinStage);
        // TODO: This should be done in a better way.
        if (context.getParameter(Parameter.MATHBACKGROUND) == null) {
            info.setLayoutStage(childMinStage);
        } else {
            info.setLayoutStage(LayoutStage.STAGE1);
        }
    }

    /** {@inheritDoc} */
    public void layoutStage2(final LayoutView view, final LayoutInfo info,
            final LayoutContext context) {
        ElementListSupport.layoutSequential(view, info, this
                .getChildrenToLayout(), LayoutStage.STAGE2);
        ElementListSupport.addBackground((Color) context
                .getParameter(Parameter.MATHBACKGROUND), info, true);
        info.setLayoutStage(LayoutStage.STAGE2);
    }

    /** {@inheritDoc} */
    @Override
    protected Node newNode() {
        return new DocumentElement();
    }

    /** {@inheritDoc} */
    @Override
    public Element createElement(final String tagName) {
        // TODO: This should be refactored.
        // Note: removeNSPrefix is needed because Saxon calls this function
        // with a NS Prefix.
        return JEuclidElementFactory.elementFromName(this
                .removeNSPrefix(tagName), this);
    }

    /** {@inheritDoc} */
    @Override
    public Element createElementNS(final String namespaceURI,
            final String qualifiedName) {
        final String ns;
        if (namespaceURI != null && namespaceURI.length() == 0) {
            ns = null;
        } else {
            ns = namespaceURI;
        }
        if (ns == null) {
            return this.createElement(qualifiedName.intern());
        } else {
            final String tagname = this.removeNSPrefix(qualifiedName);
            final Element e = this.createElement(tagname);
            // TODO: E should contain ns
            return e;
        }
    }

    private String removeNSPrefix(final String qualifiedName) {
        final int posSeparator = qualifiedName.indexOf(':');
        if (posSeparator >= 0) {
            return qualifiedName.substring(posSeparator + 1);
        }
        return qualifiedName;
    }

}