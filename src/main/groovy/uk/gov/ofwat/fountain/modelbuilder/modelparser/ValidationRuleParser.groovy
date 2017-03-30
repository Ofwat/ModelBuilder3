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
import uk.gov.ofwat.fountain.modelbuilder.domain.Model
import uk.gov.ofwat.fountain.modelbuilder.domain.ValidationRule
import uk.gov.ofwat.fountain.modelbuilder.domain.ValidationRuleDetails
import uk.gov.ofwat.fountain.modelbuilder.domain.ValidationRuleItem
import uk.gov.ofwat.fountain.modelbuilder.repository.ValidationRuleDetailsRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.ValidationRuleItemRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.ValidationRuleRepository

import javax.inject.Inject

@Component
class ValidationRuleParser {
    @Inject ValidationRuleRepository validationRuleRepository
    @Inject ValidationRuleItemRepository validationRuleItemRepository
    @Inject ValidationRuleDetailsRepository validationRuleDetailsRepository

    Set<ValidationRule> populateValidationRules(validationRulesNode, Model model){
        Set<ValidationRule> validationRules = new HashSet<ValidationRule>()
        validationRulesNode.children.each{ validationRulesChildNode ->
            ValidationRule validationRule = new ValidationRule()
            validationRule = validationRuleRepository.save(validationRule)

            ValidationRuleDetails validationRuleDetails
            Set<ValidationRuleItem> validationRuleItems
            def validationRulesDetailNode = validationRulesChildNode.children[0]
            def validationRulesItemsNode = validationRulesChildNode.children[1]
            validationRuleDetails = populateValidationRuleDetails(validationRulesDetailNode)
            validationRuleDetailsRepository.save(validationRuleDetails)

            validationRuleItems = populateValidationRuleItems(validationRulesItemsNode)
            validationRuleItems.each{
                it.validationRule = validationRule
                validationRule.validationRuleItems.add(it)
            }
            validationRuleItemRepository.save(validationRule.validationRuleItems)
            validationRule.validationRuleDetail = validationRuleDetails
            validationRule.model = model
            validationRules.add(validationRule)
        }
        println "****Size:${validationRules.size()}****"
        validationRuleRepository.save(validationRules)
        return validationRules
    }

    ValidationRuleDetails populateValidationRuleDetails(validationRuleDetailsNode){
        ValidationRuleDetails validationRuleDetails = new ValidationRuleDetails()
        validationRuleDetailsNode.children.each{ validationRuleDetailsNodeChild ->
            switch(validationRuleDetailsNodeChild.name){
                case "code":
                    validationRuleDetails.code = validationRuleDetailsNodeChild.text()
                    break
                case "description":
                    validationRuleDetails.description = validationRuleDetailsNodeChild.text()
                    break
                case "formula":
                    validationRuleDetails.formula = validationRuleDetailsNodeChild.text()
                    break
            }
        }
        return validationRuleDetails
    }

    Set<ValidationRuleItem> populateValidationRuleItems(validationRuleItemsNode){
        Set<ValidationRuleItem> validationRuleItems = new HashSet<ValidationRuleItem>()
        validationRuleItemsNode.children.each{ validationRuleItemsNodeChild ->
            ValidationRuleItem validationRuleItem = new ValidationRuleItem()
            validationRuleItemsNodeChild.children.each{ validationRuleItemNode ->
                switch(validationRuleItemNode.name){
                    case "type":
                        validationRuleItem.type = validationRuleItemNode.text()
                        break
                    case "evaluate":
                        validationRuleItem.evaluate = validationRuleItemNode.text()
                        break
                    case "value":
                        validationRuleItem.value = validationRuleItemNode.text()
                        break
                }
            }
            validationRuleItems.add(validationRuleItem)
        }
        return validationRuleItems
    }


}
