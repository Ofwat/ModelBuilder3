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
package uk.gov.ofwat.fountain.modelbuilder.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uk.gov.ofwat.fountain.modelbuilder.domain.*
import uk.gov.ofwat.fountain.modelbuilder.modelparser.AuditParser
import uk.gov.ofwat.fountain.modelbuilder.modelparser.CompanyPageParser
import uk.gov.ofwat.fountain.modelbuilder.modelparser.HeadingParser
import uk.gov.ofwat.fountain.modelbuilder.modelparser.InputParser
import uk.gov.ofwat.fountain.modelbuilder.modelparser.ItemParser
import uk.gov.ofwat.fountain.modelbuilder.modelparser.MacroParser
import uk.gov.ofwat.fountain.modelbuilder.modelparser.ModelDetailsParser
import uk.gov.ofwat.fountain.modelbuilder.modelparser.PageParser
import uk.gov.ofwat.fountain.modelbuilder.modelparser.TextParser
import uk.gov.ofwat.fountain.modelbuilder.modelparser.TransferParser
import uk.gov.ofwat.fountain.modelbuilder.modelparser.ValidationRuleParser
import uk.gov.ofwat.fountain.modelbuilder.modelparser.YearParser
import uk.gov.ofwat.fountain.modelbuilder.repository.*


import javax.inject.Inject

@Service
@Component
@Transactional
class FountainModelParserService {

    private final Logger log = LoggerFactory.getLogger(FountainModelParserService.class)

    @Inject AuditParser auditParser
    @Inject YearParser yearParser
    @Inject ItemParser itemParser
    @Inject InputParser inputParser
    @Inject HeadingParser headingParser
    @Inject CompanyPageParser companyPageParser
    @Inject MacroParser macroParser
    @Inject TextParser textParser
    @Inject ModelDetailsParser modelDetailsParser
    @Inject ValidationRuleParser validationRuleParser
    @Inject TransferParser transferParser
    @Inject PageParser pageParser

    @Inject ModelRepository modelRepository

    public Model parseModelFromXmlFile(File modelXmlFile){
        Model model = new Model()
        model = modelRepository.save(model)

        def rootNode = new XmlSlurper().parseText(modelXmlFile.text)
        rootNode[0].children.each{
            switch(it.name) {
                case "modeldetails":
                    model.modelDetails = modelDetailsParser.populateModelDetails(it)
                    break
                case "audits":
                    println "Populating audits"
                    //model.auditss = populateAudits(it, model)
                    model.modelAudits = auditParser.populateAudits(it, model)
                    break
                case "years":
                    println "Populating years"
                    model.years = yearParser.populateYears(it, model)
                    break
                case "items":
                    println "Populating items"
                    //model.itemss = populateItems(it)
                    model.items = itemParser.populateItems(it, model)
                    break
                case "inputs":
                    println "Populating Inputs"
                    //model.inputss = populateInputs(it)
                    model.inputs = inputParser.populateInputs(it, model)
                    break
                case "headings":
                    println "populating headings"
                    model.headings = headingParser.populateHeadings(it, model)
                    break
                case "validation-rules":
                    println "populating validation rules"
                    model.validationRules = validationRuleParser.populateValidationRules(it, model)
                    break
                case "company-pages":
                    println "populating company pages"
                    model.companyPages = companyPageParser.populateCompanyPages(it, model)
                    break
                case "pages":
                    println "Populating pages..."
                    model.pages = pageParser.populatePages(it, model)
                    break
                case "transfers":
                    println "Populating transfers..."
                    model.transfers = transferParser.populateTransfers(it, model)
                    break
                case "texts":
                    println "populating texts..."
                    model.texts = textParser.populateTexts(it, model)
                    break
                case "macros":
                    println "Populating Macros"
                    model.macros = macroParser.populateMacros(it, model)
            }
        }
        modelRepository.save(model)
        return model
    }
}
