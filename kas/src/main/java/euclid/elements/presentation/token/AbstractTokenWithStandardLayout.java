/*
 * Copyright 2007 - 2007 JEuclid, http://jeuclid.sf.net
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

/* $Id: AbstractTokenWithStandardLayout.java 518 2007-09-14 08:29:58Z maxberger $ */

package euclid.elements.presentation.token;

import java.text.AttributedString;

import euclid.elements.support.GraphicsSupport;
import euclid.elements.support.text.StringUtil;

import euclid.LayoutContext;

/**
 * Common functionality for all tokens where the text layout is based on the
 * text content.
 * 
 * @version $Revision: 518 $
 */
public abstract class AbstractTokenWithStandardLayout extends
        AbstractTokenWithTextLayout {

    /**
     * Default constructor.
     * 
     */
    public AbstractTokenWithStandardLayout() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    protected AttributedString textContentAsAttributedString(
            final LayoutContext now) {
        return StringUtil.convertStringtoAttributedString(this.getText(),
                this.getMathvariantAsVariant(), GraphicsSupport
                        .getFontsizeInPoint(now), now);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean isEmpty() {
        return this.getText().length() == 0;
    }

}