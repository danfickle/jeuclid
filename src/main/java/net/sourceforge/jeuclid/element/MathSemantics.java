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

package net.sourceforge.jeuclid.element;

import net.sourceforge.jeuclid.MathBase;
import net.sourceforge.jeuclid.element.generic.AbstractRowLikeElement;

/**
 * This class represents a semantics element.
 * 
 * @author Max Berger
 * @version $Revision$ $Date$
 */
public class MathSemantics extends AbstractRowLikeElement {

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "semantics";

    /**
     * Creates a math element.
     * 
     * @param base
     *            The base for the math element tree.
     */
    public MathSemantics(final MathBase base) {
        super(base);
    }

    /** {@inheritDoc} */
    public String getTagName() {
        return ELEMENT;
    }

}