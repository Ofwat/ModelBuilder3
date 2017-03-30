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
import uk.gov.ofwat.fountain.modelbuilder.domain.ModelDetails
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelDetailsRepository
import javax.inject.Inject

@Component
class ModelDetailsParser {

    @Inject ModelDetailsRepository modelDetailsRepository

    ModelDetails populateModelDetails(modelDetailsNode){
        ModelDetails modelDetails = new ModelDetails()
        modelDetailsNode.children.each{ modelDetailsChild ->
            switch(modelDetailsChild.name){
                case "code":
                    modelDetails.code = modelDetailsChild.text()
                    break
                case "name":
                    modelDetails.name = modelDetailsChild.text()
                    break
                case "version":
                    modelDetails.version = modelDetailsChild.text()
                    break
                case "type":
                    modelDetails.modelType = modelDetailsChild.text()
                    break
                case "text-code":
                    modelDetails.textCode = modelDetailsChild.text()
                    break
                case "report-year-code":
                    modelDetails.reportYearCode = modelDetailsChild.text()
                    break
                case "base-year-code":
                    modelDetails.baseYearCode = modelDetailsChild.text()
                    break
                case "allow-data-changes":
                    modelDetails.allowDataChanges = new Boolean(modelDetailsChild.text())
                    break
                case "model-family-code":
                    modelDetails.modelFamilyCode = modelDetailsChild.text()
                    break
                case "model-family-parent":
                    modelDetails.modelFamilyParent = new Boolean(modelDetailsChild.text())
                    break
                case "display-order":
                    modelDetails.displayOrder = new Integer(modelDetailsChild.text())
                    break
                case "branch-tag":
                    modelDetails.branchTag = modelDetailsChild.text()
                    break
                case "run-code":
                    modelDetails.runCode = modelDetailsChild.text()
                    break
            }

        }
        modelDetails = modelDetailsRepository.save(modelDetails)
        return modelDetails
    }

}
