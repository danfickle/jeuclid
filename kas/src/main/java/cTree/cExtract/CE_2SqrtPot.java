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

package cTree.cExtract;

import cTree.*;
import cTree.cDefence.DefenceHandler;

import java.util.*;


public class CE_2SqrtPot extends CE_1{
	
	public CElement extract(CElement parent, ArrayList<CElement>selection, CElement cE2){
		// selection.get(0) ist das Produkt, Parent die Wurzel
		if (!canExtract(selection)) {return selection.get(0);}
		System.out.println("SqrtPot - Can extract");
		  selection.get(0).removeCActiveProperty();
		  // Praefix sichern
		  CRolle rolle = parent.getCRolle();
		  CElement newArg = createExtraction(parent, selection, cE2);
		  CFences newChild = CFences.createFenced(newArg);
		  newArg.setCRolle(CRolle.GEKLAMMERT);
		  ExtractHandler.getInstance().insertOrReplace(parent, newChild, selection, true);
		  newChild.setCRolle(rolle);  
		  newChild.getFirstChild().setCActiveProperty();
		  return DefenceHandler.getInstance().defence(newChild.getParent(), newChild, newChild.getFirstChild());
	}
	
	protected CElement createExtraction(CElement parent, ArrayList<CElement> selection, CElement defElement){
		CPot oldPot = (CPot) selection.get(0);
		int oldExp = ((CNum) oldPot.getExponent()).getValue();
		CElement newChild = null;
		if (oldExp == 2) {
			newChild = oldPot.getBasis().cloneCElement(false);
		} else {
			newChild = oldPot.cloneCElement(false);
			((CNum) ((CPot) newChild).getExponent()).setText(""+(oldExp/2));
		}
		return newChild;
	}
	
	protected boolean canExtract(ArrayList<CElement> selection){
		// Man kann nur die ganz linken Elemente extrahieren
		if (selection.size()!=1){
			System.out.println("Wir extrahieren nur bei einer Potenz");
			return false;
		} else {
			int exp = ((CNum) ((CPot) selection.get(0)).getExponent()).getValue();
			if (exp==0 || exp%2!=0){
				System.out.println("Wir extrahieren nur gerade Potenzen");
				return false;
			}
		}
		return true;
	}
}