/*
 *  Copyright (C) 2016 Water Services Regulation Authority (Ofwat)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.gov.ofwat.fountain.modelbuilder.modelparser

import org.springframework.stereotype.Component
import uk.gov.ofwat.fountain.modelbuilder.domain.Macro
import uk.gov.ofwat.fountain.modelbuilder.domain.Model
import uk.gov.ofwat.fountain.modelbuilder.repository.MacroRepository

import javax.inject.Inject

@Component
class MacroParser {

    @Inject MacroRepository macroRepository

    Set<Macro> populateMacros(macrosNode, Model model){
        Set<Macro> macros = new HashSet<Macro>()
        macrosNode.children.each{ macroNode ->
            Macro macro = new Macro()
            macroNode.children.each { macroNodeChild ->
                switch (macroNodeChild.name) {
                    case "name":
                        macro.name = macroNodeChild.text()
                        break
                    case "description":
                        macro.description = macroNodeChild.text()
                        break
                    case "block":
                        macro.block = macroNodeChild.text()
                        break
                    case "conditional-item-code":
                        macro.conditionalItemCode = macroNodeChild.text()
                        break
                    case "page-code":
                        macro.pageCode = macroNodeChild.text()
                        break
                }
            }
            macro.model = model
            macros.add(macro)
        }
        macroRepository.save(macros)
        return macros
    }



}
