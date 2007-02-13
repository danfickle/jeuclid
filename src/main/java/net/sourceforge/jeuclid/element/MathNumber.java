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

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import net.sourceforge.jeuclid.MathBase;

/**
 * This class presents numbers in a equation.
 * 
 */
public class MathNumber extends MathText {

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "mn";

    /**
     * Default constructor.
     * 
     * @param base
     *            The base for the math element tree.
     */
    public MathNumber(MathBase base) {
        super(base);
    }

    /**
     * @return Width of till point
     * @param g
     *            Graphics2D context to use.
     */
    public int getWidthTillPoint(Graphics2D g) {
        int result = 0;

        if (getText() == null && getText().length() == 0) {
            return result;
        }
        FontMetrics metrics = getFontMetrics(g);
        String integer = getText();
        if (integer.indexOf(".") > 0) {
            integer = integer.substring(0, integer.indexOf("."));
        }
        result = metrics.stringWidth(integer);
        return result;
    }

    /**
     * @return width of point
     * @param g
     *            Graphics2D context to use.
     */
    public int getPointWidth(Graphics2D g) {
        int result = 0;

        FontMetrics metrics = getFontMetrics(g);
        result = metrics.stringWidth(".");

        return result;
    }

    /** {@inheritDoc} */
    public String getTagName() {
        return ELEMENT;
    }

}