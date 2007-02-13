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

import java.awt.Graphics2D;

import net.sourceforge.jeuclid.MathBase;
import net.sourceforge.jeuclid.element.generic.AbstractMathElement;
import net.sourceforge.jeuclid.element.generic.MathElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class arange a element lower, and a other elements upper to an
 * element.
 * 
 */

public class MathMultiScripts extends AbstractMathElement {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory
            .getLog(MathMultiScripts.class);

    private static final double DY = 0.43 / 2;

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "mmultiscripts";

    private int msubscriptshift = 0;

    private int msuperscriptshift = 0;

    /**
     * Default constructor.
     * 
     * @param base
     *            The base for the math element tree.
     */
    public MathMultiScripts(final MathBase base) {
        super(base);
    }

    /**
     * Sets subscriptshift property value.
     * 
     * @param subscriptshift
     *            Value of subscriptshift.
     */
    public final void setSubScriptShift(final int subscriptshift) {
        this.msubscriptshift = subscriptshift;
    }

    /**
     * Gets value of subscriptshift.
     * 
     * @return Value of subscriptshift property.
     */
    public final int getSubScriptShift() {
        return this.msubscriptshift;
    }

    /**
     * Sets superscriptshift property value.
     * 
     * @param superscriptshift
     *            Value of superscriptshift.
     */
    public final void setSuperScriptShift(final int superscriptshift) {
        this.msuperscriptshift = superscriptshift;
    }

    /**
     * Gets value of superscriptshift.
     * 
     * @return Value of superscriptshift property.
     */
    public final int getSuperScriptShift() {
        return this.msuperscriptshift;
    }

    /**
     * Paints this element.
     * 
     * @param g
     *            The graphics context to use for painting
     * @param posX
     *            The first left position for painting
     * @param posY
     *            The position of the baseline
     */
    @Override
    public final void paint(final Graphics2D g, int posX, final int posY) {
        super.paint(g, posX, posY);
        int prPos = -1;
        for (int i = 1; i < this.getMathElementCount(); i++) {
            if (this.getMathElement(i) instanceof MathPreScripts) {
                prPos = i;
                break;
            }
        }
        MathElement baseElement = null;
        baseElement = this.getMathElement(0);
        MathElement childElement = null;
        if (this.getMathElementCount() > 2) {
            for (int i = 1; i < this.getMathElementCount(); i++) {
                if (!(this.getMathElement(i) instanceof MathPreScripts)) {
                    childElement = this.getMathElement(i);
                    break;
                }
            }
        }
        int middleshift = 0;
        if (childElement != null) {
            middleshift = (int) (baseElement.getHeight(g) * MathMultiScripts.DY);
        }
        int e1DescentHeight = 0;
        if (baseElement != null) {
            e1DescentHeight = baseElement.getDescentHeight(g);
        }
        if (e1DescentHeight == 0) {
            e1DescentHeight = this.getFontMetrics(g).getDescent();
        }
        int e1AscentHeight = 0;
        if (baseElement != null) {
            e1AscentHeight = baseElement.getAscentHeight(g);
        }
        if (e1AscentHeight == 0) {
            e1AscentHeight = this.getFontMetrics(g).getAscent();
        }

        final int posY1 = posY + e1DescentHeight
                + this.calculateMaxElementAscentHeight(g) - middleshift;
        final int posY2 = posY - e1AscentHeight + middleshift
                - this.calculateMaxElementDescentHeight(g);

        int width = 0;
        if (prPos != -1) {
            int p = 0;
            if ((this.getMathElementCount() - prPos - 1) % 2 != 0) {
                p = 1;
            }
            for (int i = prPos + 1; i < this.getMathElementCount() - p; i++) {
                if ((i - (prPos + 1)) > 1) {
                    if ((i - (prPos + 1)) % 2 == 0) {
                        width += Math.max(this.getMathElement(i - 2)
                                .getWidth(g), this.getMathElement(i - 1)
                                .getWidth(g));
                    }
                }
                if ((i - (prPos + 1)) % 2 == 0) {
                    this.getMathElement(i).paint(g, posX + width, posY1);
                } else {
                    this.getMathElement(i).paint(g, posX + width, posY2);
                }
            }
            width += Math.max(this.getMathElement(
                    this.getMathElementCount() - 2 - p).getWidth(g), this
                    .getMathElement(this.getMathElementCount() - 1 - p)
                    .getWidth(g));

        }
        if (baseElement != null) {
            baseElement.paint(g, posX + width, posY);
            posX += baseElement.getWidth(g);
        }
        if (prPos == -1) {
            prPos = this.getMathElementCount();
        }
        int p = 0;
        if (prPos % 2 == 0) {
            p = 1;
        }

        for (int i = 1; i < prPos - p; i++) {
            if ((i - 1) > 1) {
                if ((i - 1) % 2 == 0) {
                    width += Math.max(this.getMathElement(i - 2).getWidth(g),
                            this.getMathElement(i - 1).getWidth(g));
                }
            }
            if ((i - 1) % 2 == 0) {
                this.getMathElement(i).paint(g, posX + width, posY1);
            } else {
                this.getMathElement(i).paint(g, posX + width, posY2);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public final int calculateWidth(final Graphics2D g) { // done
        int width = 0;
        if (this.getMathElementCount() > 0) {
            width += this.getMathElement(0).getWidth(g);
            int prPos = -1;
            for (int i = 0; i < this.getMathElementCount(); i++) {
                if (this.getMathElement(i) instanceof MathPreScripts) {
                    prPos = i;
                    break;
                }
            }
            if (prPos == -1) {
                prPos = this.getMathElementCount();
            }
            int p = 0;
            if (prPos % 2 == 0) {
                p = 1;
            }
            for (int i = 1; i < prPos - p; i = i + 2) {
                width += Math.max(this.getMathElement(i).getWidth(g), this
                        .getMathElement(i + 1).getWidth(g));
            }
            if (prPos != this.getMathElementCount()) {
                if ((this.getMathElementCount() - prPos - 1) % 2 != 0) {
                    p = 1;
                } else {
                    p = 0;
                }
                for (int i = prPos + 1; i < this.getMathElementCount() - p; i = i + 2) {
                    width += Math.max(this.getMathElement(i).getWidth(g),
                            this.getMathElement(i + 1).getWidth(g));
                }
            }
        }
        return width + 1;
    }

    /**
     * Return the current height of the upper part of child component from the
     * baseline.
     * 
     * @return Height of the upper part
     * @param g
     *            Graphics2D context to use.
     */
    public final int calculateMaxElementAscentHeight(final Graphics2D g) {

        int prPos = -1;
        int descenHeight = 0;
        for (int i = 0; i < this.getMathElementCount(); i++) {
            if (this.getMathElement(i) instanceof MathPreScripts) {
                prPos = i;
                break;
            }
        }
        if (prPos != -1) {
            int p = 0;
            if (prPos % 2 == 0) {
                p = 1;
            }
            for (int i = 1; i < prPos - p; i = i + 2) {
                descenHeight = Math.max(descenHeight, this.getMathElement(i)
                        .getAscentHeight(g));
            }
            if ((this.getMathElementCount() - prPos - 1) % 2 != 0) {
                p = 1;
            }
            for (int i = prPos + 1; i < this.getMathElementCount() - p; i = i + 2) {
                descenHeight = Math.max(descenHeight, this.getMathElement(i)
                        .getAscentHeight(g));
            }
        } else {
            for (int i = 1; i < this.getMathElementCount(); i = i + 2) {
                descenHeight = Math.max(descenHeight, this.getMathElement(i)
                        .getAscentHeight(g));
            }
        }
        return descenHeight;
    }

    /** {@inheritDoc} */
    @Override
    public final int calculateAscentHeight(final Graphics2D g) {
        int prPos = -1;
        int e2h = 0;
        for (int i = 0; i < this.getMathElementCount(); i++) {
            if (this.getMathElement(i) instanceof MathPreScripts) {
                prPos = i;
                break;
            }
        }
        if (prPos != -1) {
            int p = 0;
            if (prPos % 2 == 0) {
                p = 1;
            }
            for (int i = 2; i < prPos - p; i = i + 2) {
                e2h = Math
                        .max(
                                e2h,
                                Math
                                        .max(
                                                this.getMathElement(i)
                                                        .getHeight(g)
                                                        - (int) (this
                                                                .getMathElement(
                                                                        0)
                                                                .getHeight(g) * MathMultiScripts.DY),
                                                0));
            }
            if ((this.getMathElementCount() - prPos - 1) % 2 != 0) {
                p = 1;
            }
            for (int i = prPos + 2; i < this.getMathElementCount() - p; i = i + 2) {
                e2h = Math
                        .max(
                                e2h,
                                Math
                                        .max(
                                                this.getMathElement(i)
                                                        .getHeight(g)
                                                        - (int) (this
                                                                .getMathElement(
                                                                        0)
                                                                .getHeight(g) * MathMultiScripts.DY),
                                                0));
            }
        } else {
            for (int i = 2; i < this.getMathElementCount(); i = i + 2) {
                e2h = Math
                        .max(
                                e2h,
                                Math
                                        .max(
                                                this.getMathElement(i)
                                                        .getHeight(g)
                                                        - (int) (this
                                                                .getMathElement(
                                                                        0)
                                                                .getHeight(g) * MathMultiScripts.DY),
                                                0));
            }
        }
        return this.getMathElement(0).getAscentHeight(g) + e2h;
    }

    /**
     * Return the max height of the sub element.
     * 
     * @return max height of the sub element
     * @param g
     *            Graphics2D context to use.
     */
    public final int getMaxElementHeight(final Graphics2D g) {
        int childHeight = 0;
        int prPos = -1;
        for (int i = 0; i < this.getMathElementCount(); i++) {
            if (this.getMathElement(i) instanceof MathPreScripts) {
                prPos = i;
                break;
            }
        }
        if (prPos != -1) {
            int p = 0;
            if (prPos % 2 == 0) {
                p = 1;
            }
            for (int i = 2; i < prPos - p; i = i + 2) {
                childHeight = Math.max(childHeight, this.getMathElement(i)
                        .getHeight(g));
            }
            if ((this.getMathElementCount() - prPos - 1) % 2 != 0) {
                p = 1;
            }
            for (int i = prPos + 2; i < this.getMathElementCount() - p; i = i + 2) {
                childHeight = Math.max(childHeight, this.getMathElement(i)
                        .getHeight(g));
            }
        } else {
            for (int i = 2; i < this.getMathElementCount(); i = i + 2) {
                childHeight = Math.max(childHeight, this.getMathElement(i)
                        .getHeight(g));
            }
        }
        return childHeight;
    }

    /**
     * Return the current height of the lower part of child component from the
     * baseline.
     * 
     * @return Height of the lower part
     * @param g
     *            Graphics2D context to use.
     */

    public final int calculateMaxElementDescentHeight(final Graphics2D g) {
        int prPos = -1;
        int ascentHeight = 0;
        for (int i = 0; i < this.getMathElementCount(); i++) {
            if (this.getMathElement(i) instanceof MathPreScripts) {
                prPos = i;
                break;
            }
        }
        if (prPos != -1) {
            int p = 0;
            if (prPos % 2 == 0) {
                p = 1;
            }
            for (int i = 2; i < prPos - p; i = i + 2) {
                ascentHeight = Math.max(ascentHeight, this.getMathElement(i)
                        .getDescentHeight(g));
            }
            if ((this.getMathElementCount() - prPos - 1) % 2 != 0) {
                p = 1;
            }
            for (int i = prPos + 2; i < this.getMathElementCount() - p; i = i + 2) {
                ascentHeight = Math.max(ascentHeight, this.getMathElement(i)
                        .getDescentHeight(g));
            }
        } else {
            for (int i = 2; i < this.getMathElementCount(); i = i + 2) {
                ascentHeight = Math.max(ascentHeight, this.getMathElement(i)
                        .getDescentHeight(g));
            }
        }
        return ascentHeight;
    }

    /** {@inheritDoc} */
    @Override
    public final int calculateDescentHeight(final Graphics2D g) {

        int prPos = -1;
        int e2h = 0;
        for (int i = 0; i < this.getMathElementCount(); i++) {
            if (this.getMathElement(i) instanceof MathPreScripts) {
                prPos = i;
                break;
            }
        }
        if (prPos != -1) {
            int p = 0;
            if (prPos % 2 == 0) {
                p = 1;
            }
            for (int i = 1; i < prPos - p; i = i + 2) {
                e2h = Math
                        .max(
                                e2h,
                                Math
                                        .max(
                                                this.getMathElement(i)
                                                        .getHeight(g)
                                                        - (int) (this
                                                                .getMathElement(
                                                                        0)
                                                                .getHeight(g) * MathMultiScripts.DY),
                                                0));
            }
            if ((this.getMathElementCount() - prPos - 1) % 2 != 0) {
                p = 1;
            }
            for (int i = prPos + 1; i < this.getMathElementCount() - p; i = i + 2) {
                e2h = Math
                        .max(
                                e2h,
                                Math
                                        .max(
                                                this.getMathElement(i)
                                                        .getHeight(g)
                                                        - (int) (this
                                                                .getMathElement(
                                                                        0)
                                                                .getHeight(g) * MathMultiScripts.DY),
                                                0));
            }
        } else {
            for (int i = 1; i < this.getMathElementCount(); i = i + 2) {
                e2h = Math
                        .max(
                                e2h,
                                Math
                                        .max(
                                                this.getMathElement(i)
                                                        .getHeight(g)
                                                        - (int) (this
                                                                .getMathElement(
                                                                        0)
                                                                .getHeight(g) * MathMultiScripts.DY),
                                                0));
            }
        }
        return this.getMathElement(0).getDescentHeight(g) + e2h;
    }

    /**
     * Write errors in conditions.
     */
    @Override
    public final void eventAllElementsComplete() {
        super.eventAllElementsComplete();
        if (this.getMathElementCount() == 0) {
            MathMultiScripts.logger
                    .error("Wrong number of parametrs, must be 1 or more");
        } else if (this.getMathElement(0) instanceof MathPreScripts) {
            MathMultiScripts.logger.error("The first argument must be base.");
        }
        boolean isMultMPrescripts = false;
        if (this.getMathElementCount() > 0) {
            int prPos = -1;
            for (int i = 0; i < this.getMathElementCount(); i++) {
                if (this.getMathElement(i) instanceof MathPreScripts) {
                    if (prPos != -1) {
                        MathMultiScripts.logger
                                .error("The empty element mprescripts must be declared once.");
                        isMultMPrescripts = true;
                        break;
                    }
                    prPos = i;

                }
            }
            if (!isMultMPrescripts) {
                if (prPos == -1 && this.getMathElementCount() % 2 == 0) {
                    MathMultiScripts.logger
                            .error("The total number of the arguments must be odd.\n"
                                    + "Some elements may not be drown. ");
                } else if (prPos != -1) {
                    if (prPos % 2 == 0) {
                        MathMultiScripts.logger
                                .error("The total number of the postcripts elements must be even.\n"
                                        + "Some elements may not be drown.");
                    }
                    if ((this.getMathElementCount() - prPos - 1) % 2 != 0) {
                        MathMultiScripts.logger
                                .error("The total number of the prestcripts elements must be even.\n"
                                        + "Some elements may not be drown.");
                    }

                }
            }
        }
    };

    /** {@inheritDoc} */
    @Override
    public int getScriptlevelForChild(final MathElement child) {
        if (child.isSameNode(this.getFirstChild())) {
            return this.getAbsoluteScriptLevel();
        } else {
            return this.getAbsoluteScriptLevel() + 1;
        }
    }

    /** {@inheritDoc} */
    public String getTagName() {
        return MathMultiScripts.ELEMENT;
    }

}