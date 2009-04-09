/*
 * Copyright 2009 Erhard Kuenzel
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

package cTree.cCombine;

import java.util.ArrayList;
import cTree.*;


public class CC_PunktFencesFences extends CC_ {
	
	// a*(b+c+d) -> (a*b+a*c+a*d)
	// geht nicht bei : vor a oder () oder wenn in der Klammer keine Summe steht
	
	protected CElement createCombination(CElement parent, CElement cE1, CElement cE2){
		System.out.println("Multipliziere Klammer mit Klammer, die Summe enth�lt");
		ArrayList<CElement> oldAddendList = ((CPlusRow) cE2.getFirstChild()).getMemberList();
		ArrayList<CElement> newAddendList = CTimesRow.map(cE1, oldAddendList);
		CElement newChild = CFences.createFenced(CPlusRow.createRow(newAddendList));
		return newChild;
	}
	
	protected boolean canCombine(CElement cE1, CElement cE2){
		System.out.println("Repell fenced mult fenced"); 
		if (cE1.hasExtDiv() || cE2.hasExtDiv()) {return false;}
		if (!cE2.hasChildC() || !(cE2.getFirstChild() instanceof CPlusRow)) {return false;} 
		return true;
	}
}