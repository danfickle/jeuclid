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

package net.sourceforge.jeuclid.dom;

import java.util.List;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

/**
 * Partial implementation of org.w3c.dom.Node
 * <p>
 * This implements only the functions necesessay for MathElements. Feel free
 * to implement whatever functions you need.
 * 
 * @author Max Berger
 */
public abstract class AbstractPartialNodeImpl implements Node {

    private final List<Node> children = new Vector<Node>();

    private String textContent = "";

    private Node parent;

    /**
     * @see org.w3c.dom.NodeList
     */
    public class NodeList implements org.w3c.dom.NodeList {
        private final List children;

        /**
         * default constructor.
         * 
         * @param childs
         *            list of children.
         */
        protected NodeList(final List childs) {
            this.children = childs;
        }

        /** {@inheritDoc} */
        public final int getLength() {
            return this.children.size();
        }

        /** {@inheritDoc} */
        public final Node item(final int index) {
            return (Node) this.children.get(index);
        }
    }

    /** {@inheritDoc} */
    public final String lookupNamespaceURI(final String prefix) {
        throw new UnsupportedOperationException("lookupNamespaceURI");
    }

    /** {@inheritDoc} */
    public final void normalize() {
        throw new UnsupportedOperationException("normalize");
    }

    /** {@inheritDoc} */
    public final org.w3c.dom.NodeList getChildNodes() {
        return new NodeList(this.children);
    }

    /** {@inheritDoc} */
    public final Node getFirstChild() {
        try {
            return this.children.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /** {@inheritDoc} */
    public final boolean isSameNode(final Node other) {
        return this.equals(other);
    }

    /** {@inheritDoc} */
    public Node appendChild(final Node newChild) {
        if (newChild instanceof AbstractPartialElementImpl) {
            final AbstractPartialNodeImpl pelement = (AbstractPartialNodeImpl) newChild;
            this.children.add(pelement);
            pelement.parent = this;
            return pelement;
        } else {
            throw new IllegalArgumentException(
                    "Can only add children of type "
                            + this.getClass().getName() + " to "
                            + this.getClass().getName());
        }
    }

    /** {@inheritDoc} */
    public final String getTextContent() {
        return this.textContent;
    }

    /** {@inheritDoc} */
    public void setTextContent(final String newTextContent) {
        this.textContent = newTextContent;
    }

    /** {@inheritDoc} */
    public final Node cloneNode(final boolean deep) {
        throw new UnsupportedOperationException("cloneNode");
    }

    /** {@inheritDoc} */
    public final short getNodeType() {
        return Node.ELEMENT_NODE;
    }

    /** {@inheritDoc} */
    public final String getNodeValue() {
        throw new UnsupportedOperationException("getNodeValue");
    }

    /** {@inheritDoc} */
    public final String getNamespaceURI() {
        throw new UnsupportedOperationException("getNamespaceURI");
    }

    /** {@inheritDoc} */
    public final Node getParentNode() {
        return this.parent;
    }

    /** {@inheritDoc} */
    public final String getBaseURI() {
        throw new UnsupportedOperationException("getBaseURI");
    }

    /** {@inheritDoc} */
    public final String getPrefix() {
        throw new UnsupportedOperationException("getPrefix");
    }

    /** {@inheritDoc} */
    public final NamedNodeMap getAttributes() {
        throw new UnsupportedOperationException("getAttributes");
    }

    /** {@inheritDoc} */
    public final boolean hasChildNodes() {
        throw new UnsupportedOperationException("hasChildNodes");
    }

    /** {@inheritDoc} */
    public final boolean isDefaultNamespace(final String namespaceURI) {
        throw new UnsupportedOperationException("isDefaultNamespace");
    }

    /** {@inheritDoc} */
    public final Node replaceChild(final Node newChild, final Node oldChild) {
        throw new UnsupportedOperationException("replaceChild");
    }

    /** {@inheritDoc} */
    public final Node insertBefore(final Node newChild, final Node refChild) {
        throw new UnsupportedOperationException("insertBefore");
    }

    /** {@inheritDoc} */
    public final boolean isEqualNode(final Node arg) {
        throw new UnsupportedOperationException("isEqualNode");
    }

    /** {@inheritDoc} */
    public final Node getNextSibling() {
        throw new UnsupportedOperationException("getNextSibling");
    }

    /** {@inheritDoc} */
    public final boolean hasAttributes() {
        throw new UnsupportedOperationException("hasAttributes");
    }

    /** {@inheritDoc} */
    public final short compareDocumentPosition(final Node other) {
        throw new UnsupportedOperationException("compareDocumentPosition");
    }

    /** {@inheritDoc} */
    public final Node removeChild(final Node oldChild) {
        throw new UnsupportedOperationException("removeChild");
    }

    /** {@inheritDoc} */
    public final Object getFeature(final String feature, final String version) {
        throw new UnsupportedOperationException("getFeature");
    }

    /** {@inheritDoc} */
    public final Node getLastChild() {
        final int size = this.children.size();
        if (size == 0) {
            return null;
        } else {
            return this.children.get(size - 1);
        }
    }

    /** {@inheritDoc} */
    public final String getLocalName() {
        throw new UnsupportedOperationException("getLocalName");
    }

    /** {@inheritDoc} */
    public final Document getOwnerDocument() {
        throw new UnsupportedOperationException("getOwnerDocument");
    }

    /** {@inheritDoc} */
    public final Node getPreviousSibling() {
        throw new UnsupportedOperationException("getPreviousSibling");
    }

    /** {@inheritDoc} */
    public final boolean isSupported(final String feature,
            final String version) {
        throw new UnsupportedOperationException("isSupported");
    }

    /** {@inheritDoc} */
    public final String lookupPrefix(final String namespaceURI) {
        throw new UnsupportedOperationException("lookupPrefix");
    }

    /** {@inheritDoc} */
    public final void setNodeValue(final String nodeValue) {
        throw new UnsupportedOperationException("setNodeValue");
    }

    /** {@inheritDoc} */
    public final void setPrefix(final String prefix) {
        throw new UnsupportedOperationException("setPrefix");
    }

    /** {@inheritDoc} */
    public final Object getUserData(final String key) {
        throw new UnsupportedOperationException("getUserData");
    }

    /** {@inheritDoc} */
    public final Object setUserData(final String key, final Object data,
            final UserDataHandler handler) {
        throw new UnsupportedOperationException("setUserData");
    }

}