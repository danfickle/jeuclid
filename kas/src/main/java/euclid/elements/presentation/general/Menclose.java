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

/* $Id: Menclose.java 795 2008-06-21 10:53:35Z maxberger $ */

package euclid.elements.presentation.general;

import java.awt.Color;
import java.awt.geom.Dimension2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

import euclid.LayoutContext;

import org.apache.batik.dom.AbstractNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;
import org.w3c.dom.mathml.MathMLEncloseElement;

import euclid.context.Parameter;
import euclid.elements.AbstractElementWithDelegates;
import euclid.elements.JEuclidElement;
import euclid.elements.presentation.AbstractContainer;
import euclid.elements.support.Dimension2DImpl;
import euclid.elements.support.ElementListSupport;
import euclid.elements.support.GraphicsSupport;
import euclid.elements.support.attributes.AttributesHelper;
import euclid.layout.GraphicsObject;
import euclid.layout.LayoutInfo;
import euclid.layout.LayoutStage;
import euclid.layout.LayoutView;
import euclid.layout.LayoutableNode;
import euclid.layout.LineObject;

/**
 * Class for supporting "menclose" elements.
 * 
 * @version $Revision: 795 $
 */
public final class Menclose extends AbstractElementWithDelegates implements
        MathMLEncloseElement {

    /**
     * base class for all row-like notations.
     * 
     */
    private abstract static class AbstractRowLikeNotation extends
            AbstractContainer {

        /**
         * Default constructor.
         */
        public AbstractRowLikeNotation() {
            super();
        }

        /** {@inheritDoc} */
        @Override
        protected void layoutStageInvariant(final LayoutView view,
                final LayoutInfo info, final LayoutStage stage,
                final LayoutContext context) {
            final LayoutContext now = this
                    .applyLocalAttributesToContext(context);
            final Dimension2D borderLeftTop = this.getBorderLeftTop(now);
            final float borderLeft = (float) borderLeftTop.getWidth();
            if (borderLeft > 0.0f) {
                view.getInfo((LayoutableNode) this.getFirstChild()).moveTo(
                        borderLeft, 0, stage);
            }
            ElementListSupport.fillInfoFromChildren(view, info, this, stage,
                    borderLeftTop, this.getBorderRightBottom(now));
            this.enclHook(info, stage, this.applyLocalAttributesToContext(now));
        }

        /**
         * Add left / top border to enclosed object.
         * 
         * @param now
         *            current Layout Context
         * @return Left and Top border
         */
        protected Dimension2D getBorderLeftTop(final LayoutContext now) {
            return new Dimension2DImpl(0.0f, 0.0f);
        }

        /**
         * Add right / bottom border to enclosed object.
         * 
         * @param now
         *            current Layout Context
         * @return Right and Bottom border
         */
        protected Dimension2D getBorderRightBottom(final LayoutContext now) {
            return new Dimension2DImpl(0.0f, 0.0f);
        }

        /**
         * Add Graphic objects to enclosed object.
         * 
         * @param info
         *            Layout Info
         * @param stage
         *            current Layout Stage
         * @param now
         *            current Layout Context
         */
        protected abstract void enclHook(final LayoutInfo info,
                final LayoutStage stage, final LayoutContext now);
    }

    /**
     * Represents the US long-division notation, to support the notation
     * "longdiv".
     * 
     */
    private static final class Longdiv extends Menclose.AbstractRowLikeNotation {
        private static final long serialVersionUID = 1L;

        /**
         * Default constructor.
         */
        public Longdiv() {
            super();
        }

        @Override
        protected Node newNode() {
            return new Longdiv();
        }

        /** {@inheritDoc} */
        @Override
        protected Dimension2D getBorderLeftTop(final LayoutContext now) {
            final float lineSpace = GraphicsSupport.lineWidth(now) * 2;
            return new Dimension2DImpl(AttributesHelper.convertSizeToPt(
                    "0.25em", now, null)
                    + lineSpace, lineSpace);
        }

        /** {@inheritDoc} */
        @Override
        protected void enclHook(final LayoutInfo info, final LayoutStage stage,
                final LayoutContext now) {
            final List<GraphicsObject> graphicObjects = info
                    .getGraphicObjects();
            graphicObjects.clear();
            final float lineWidth = GraphicsSupport.lineWidth(now);
            final float top = info.getAscentHeight(stage) + lineWidth;
            final Color color = (Color) now.getParameter(Parameter.MATHCOLOR);
            graphicObjects.add(new LineObject(lineWidth, -top, lineWidth, info
                    .getDescentHeight(stage), lineWidth, color));
            graphicObjects.add(new LineObject(lineWidth, -top, info
                    .getWidth(stage), -top, lineWidth, color));
        }
    }

    /**
     * Up-Diagonal Strike.
     * 
     */
    private static final class Updiagonalstrike extends
            Menclose.AbstractRowLikeNotation {

        private static final long serialVersionUID = 1L;

        /**
         * Default constructor.
         */
        public Updiagonalstrike() {
            super();
        }

        @Override
        protected Node newNode() {
            return new Updiagonalstrike();
        }

        /** {@inheritDoc} */
        @Override
        protected void enclHook(final LayoutInfo info, final LayoutStage stage,
                final LayoutContext now) {

            final Color color = (Color) now.getParameter(Parameter.MATHCOLOR);
            final float lineWidth = GraphicsSupport.lineWidth(now);
            info.setGraphicsObject(new LineObject(0, info
                    .getDescentHeight(stage), info.getWidth(stage), -info
                    .getAscentHeight(stage), lineWidth, color));
        }

    }

    /**
     * Down-Diagonal Strike.
     * 
     */
    private static final class Downdiagonalstrike extends
            Menclose.AbstractRowLikeNotation {

        private static final long serialVersionUID = 1L;

        /**
         * Default constructor.
         */
        public Downdiagonalstrike() {
            super();
        }

        @Override
        protected Node newNode() {
            return new Downdiagonalstrike();
        }

        /** {@inheritDoc} */
        @Override
        protected void enclHook(final LayoutInfo info, final LayoutStage stage,
                final LayoutContext now) {

            final Color color = (Color) now.getParameter(Parameter.MATHCOLOR);
            final float lineWidth = GraphicsSupport.lineWidth(now);
            info.setGraphicsObject(new LineObject(0, -info
                    .getAscentHeight(stage), info.getWidth(stage), info
                    .getDescentHeight(stage), lineWidth, color));
        }
    }

    private static final class Actuarial extends
            Menclose.AbstractRowLikeNotation {
        private static final long serialVersionUID = 1L;

        public Actuarial() {
            super();
        }

        @Override
        protected Node newNode() {
            return new Actuarial();
        }

        /** {@inheritDoc} */
        @Override
        protected Dimension2D getBorderLeftTop(final LayoutContext now) {
            final float lineSpace = GraphicsSupport.lineWidth(now) * 2;
            return new Dimension2DImpl(0.0f, lineSpace);
        }

        /** {@inheritDoc} */
        @Override
        protected Dimension2D getBorderRightBottom(final LayoutContext now) {
            final float lineSpace = GraphicsSupport.lineWidth(now) * 2;
            return new Dimension2DImpl(lineSpace, 0.0f);
        }

        /** {@inheritDoc} */
        @Override
        protected void enclHook(final LayoutInfo info, final LayoutStage stage,
                final LayoutContext now) {
            final List<GraphicsObject> graphicObjects = info
                    .getGraphicObjects();
            graphicObjects.clear();
            final float lineWidth = GraphicsSupport.lineWidth(now);
            final float top = info.getAscentHeight(stage) + lineWidth;
            final Color color = (Color) now.getParameter(Parameter.MATHCOLOR);
            graphicObjects.add(new LineObject(0, -top, info.getWidth(stage)
                    - lineWidth, -top, lineWidth, color));
            graphicObjects.add(new LineObject(info.getWidth(stage) - lineWidth,
                    -top, info.getWidth(stage) - lineWidth, info
                            .getDescentHeight(stage), lineWidth, color));
        }

    }

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "menclose";

    /** The notation attribute. */
    public static final String ATTR_NOTATION = "notation";

    /**
     * Logger for this class
     */
    private static final Log LOGGER = LogFactory.getLog(Menclose.class);

    private static final Map<String, Constructor<?>> IMPL_CLASSES = new HashMap<String, Constructor<?>>();;

    private static final long serialVersionUID = 1L;

    /**
     * Creates a math element.
     */
    public Menclose() {
        super();
        this.setDefaultMathAttribute(Menclose.ATTR_NOTATION, "");
    }

    /** {@inheritDoc} */
    @Override
    protected Node newNode() {
        return new Menclose();
    }

    /**
     * @return notation of menclose element
     */
    public String getNotation() {
        return this.getMathAttribute(Menclose.ATTR_NOTATION);
    }

    /**
     * Sets notation for menclose element.
     * 
     * @param notation
     *            Notation
     */
    public void setNotation(final String notation) {
        this.setAttribute(Menclose.ATTR_NOTATION, notation);
    }

    /** {@inheritDoc} */
    @Override
    protected List<LayoutableNode> createDelegates() {
        final Stack<Constructor<?>> notationImpls = this.parseNotations();
        JEuclidElement lastChild = this.ensureSingleChild();
        lastChild = this.createStackOfDelegates(notationImpls, lastChild);
        return Collections.singletonList((LayoutableNode) lastChild);
    }

    private JEuclidElement createStackOfDelegates(
            final Stack<Constructor<?>> notationImpls,
            final JEuclidElement oldChild) {
        JEuclidElement lastChild = oldChild;
        while (!notationImpls.isEmpty()) {
            final Constructor<?> con = notationImpls.pop();
            try {
                final JEuclidElement element = (JEuclidElement) con
                        .newInstance();
                ((AbstractNode) element).setOwnerDocument(this.ownerDocument);
                element.appendChild(lastChild);
                lastChild = element;
            } catch (final InstantiationException e) {
                Menclose.LOGGER.warn(e);
            } catch (final IllegalAccessException e) {
                Menclose.LOGGER.warn(e);
            } catch (final InvocationTargetException e) {
                Menclose.LOGGER.warn(e);
            }
        }
        return lastChild;
    }

    /**
     * This is just to make sure that there is at least one delegate, and that
     * each of the standard delegates has exactly one child.
     * 
     * @return a single JEuclidElement.
     */
    private JEuclidElement ensureSingleChild() {
        final JEuclidElement lastChild;
        if (this.getMathElementCount() == 1) {
            lastChild = this.getMathElement(0);
        } else {
            lastChild = (JEuclidElement) this.ownerDocument
                    .createElement(Mrow.ELEMENT);
            for (final Node child : ElementListSupport
                    .createListOfChildren(this)) {
                lastChild.appendChild(child);
            }
        }
        return lastChild;
    }

    private Stack<Constructor<?>> parseNotations() {
        final String[] notations = this.getNotation().split(" ");
        final Stack<Constructor<?>> notationImpls = new Stack<Constructor<?>>();
        for (final String curNotation : notations) {
            final Constructor<?> con = Menclose.IMPL_CLASSES.get(curNotation
                    .toLowerCase(Locale.ENGLISH));
            if (con != null) {
                notationImpls.push(con);
            } else if (curNotation.length() > 0) {
                Menclose.LOGGER.info("Unsupported notation for menclose: "
                        + curNotation);
            }
        }
        return notationImpls;
    }

    static {
        try {
            Menclose.IMPL_CLASSES.put("radical", Msqrt.class.getConstructor());
            Menclose.IMPL_CLASSES.put("longdiv", Menclose.Longdiv.class
                    .getConstructor());
            Menclose.IMPL_CLASSES.put("updiagonalstrike",
                    Menclose.Updiagonalstrike.class.getConstructor());
            Menclose.IMPL_CLASSES.put("downdiagonalstrike",
                    Menclose.Downdiagonalstrike.class.getConstructor());
            Menclose.IMPL_CLASSES.put("actuarial", Menclose.Actuarial.class
                    .getConstructor());
        } catch (final NoSuchMethodException e) {
            Menclose.LOGGER.fatal(e);
        }
    }
}