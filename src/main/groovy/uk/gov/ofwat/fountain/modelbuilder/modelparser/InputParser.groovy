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
import uk.gov.ofwat.fountain.modelbuilder.domain.Input
import uk.gov.ofwat.fountain.modelbuilder.domain.Model
import uk.gov.ofwat.fountain.modelbuilder.repository.InputRepository

import javax.inject.Inject

@Component
class InputParser {

    @Inject InputRepository inputRepository

    Set<Input> populateInputs(inputsNode, Model model){
        Set<Input> inputs = new HashSet<Input>()
        inputsNode.children.each{ inputNode ->
            Input input = new Input()
            input.model = model

            inputNode.children.each{ inputNodeChild ->
                switch(inputNodeChild.name){
                    case "code":
                        input.code = inputNodeChild.text()
                        break
                    case "tag":
                        input.tag = inputNodeChild.text()
                        break
                    case "version":
                        input.version = inputNodeChild.text()
                        break
                    case "company":
                        input.company = inputNodeChild.text()
                        break
                    case "default":
                        input.defaultInput = new Boolean(inputNodeChild.text())
                        break
                    case "model":
                        input.modelReference = inputNodeChild.text()
                        break
                }
            }
            inputs.add(input)
        }
        inputRepository.save(inputs)
        return inputs
    }

}
