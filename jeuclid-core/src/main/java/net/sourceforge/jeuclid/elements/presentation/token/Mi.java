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

/* $Id$ */

package net.sourceforge.jeuclid.elements.presentation.token;

import net.sourceforge.jeuclid.elements.AbstractJEuclidElement;
import net.sourceforge.jeuclid.elements.support.text.StringUtil;

import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.Node;


/**
 * This class presents a mathematical identifier, like "x".
 *
 * @version $Revision$
 */
public final class Mi extends AbstractTokenWithTextLayout {
    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "mi";

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor. Sets MathML Namespace.
     *
     * @param qname
     *            Qualified name.
     * @param odoc
     *            Owner Document.
     */
    public Mi(final String qname, final AbstractDocument odoc) {
        super(qname, odoc);
    }

    /** {@inheritDoc} */
    @Override
    protected Node newNode() {
        return new Mi(this.nodeName, this.ownerDocument);
    }

    /** {@inheritDoc} */
    @Override
    public void changeHook() {
        super.changeHook();
        if (StringUtil.countDisplayableCharacters(this.getText()) == 1) {
            this.setDefaultMathAttribute(
                    AbstractJEuclidElement.ATTR_MATHVARIANT, "italic");
        } else {
            this.setDefaultMathAttribute(
                    AbstractJEuclidElement.ATTR_MATHVARIANT, "normal");
        }
    }

}
