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
import uk.gov.ofwat.fountain.modelbuilder.domain.Cell
import uk.gov.ofwat.fountain.modelbuilder.domain.CellRange
import uk.gov.ofwat.fountain.modelbuilder.domain.Form
import uk.gov.ofwat.fountain.modelbuilder.domain.FormCell
import uk.gov.ofwat.fountain.modelbuilder.domain.FormDetails
import uk.gov.ofwat.fountain.modelbuilder.domain.FormHeadingCell
import uk.gov.ofwat.fountain.modelbuilder.domain.Line
import uk.gov.ofwat.fountain.modelbuilder.domain.LineDetails
import uk.gov.ofwat.fountain.modelbuilder.domain.Model
import uk.gov.ofwat.fountain.modelbuilder.domain.ModelBuilderDocument
import uk.gov.ofwat.fountain.modelbuilder.domain.Page
import uk.gov.ofwat.fountain.modelbuilder.domain.PageDetails
import uk.gov.ofwat.fountain.modelbuilder.domain.Section
import uk.gov.ofwat.fountain.modelbuilder.domain.SectionDetails
import uk.gov.ofwat.fountain.modelbuilder.repository.CellRangeRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.CellRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.FormCellRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.FormDetailsRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.FormHeadingCellRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.FormRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.LineDetailsRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.LineRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelBuilderDocumentRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.PageDetailsRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.PageRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.SectionDetailsRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.SectionRepository

import javax.inject.Inject

@Component
class PageParser {

    @Inject PageRepository pageRepository
    @Inject PageDetailsRepository pageDetailsRepository
    @Inject SectionRepository sectionRepository
    @Inject SectionDetailsRepository sectionDetailsRepository
    @Inject LineRepository lineRepository
    @Inject CellRepository cellRepository
    @Inject ModelBuilderDocumentRepository modelBuilderDocumentRepository
    @Inject LineDetailsRepository lineDetailsRepository
    @Inject CellRangeRepository cellRangeRepository
    @Inject FormRepository formRepository
    @Inject FormDetailsRepository formDetailsRepository
    @Inject FormHeadingCellRepository formHeadingCellRepository
    @Inject FormCellRepository formCellRepository


    Set<Page> populatePages(pagesNode, Model model){
        Set<Page> pages = new HashSet<Page>()
        pagesNode.children.each{ pageNode ->
            //println pageNode.name
            Page page = new Page()
            page.model = model
            page = pageRepository.save(page)
            pageNode.children.each{ pageNodeChild ->
                //println pageNodeChild.name
                switch(pageNodeChild.name){
                    case "pagedetails":
                        PageDetails pageDetails = populatePageDetails(pageNodeChild)
                        page.pageDetail = pageDetails
                        break
                    case "documents":
                        Set<ModelBuilderDocument> documents = populateDocuments(pageNodeChild)
                        documents.each{
                            it.page = page
                        }
                        modelBuilderDocumentRepository.save(documents)
                        page.documents = documents
                        break
                    case "sections":
                        println("Populating sections")
                        Set<Section> sections = populateSections(pageNodeChild, page)
                        page.sections = sections //TODO fix pluralisation
                        break
                }
            }
            page = pageRepository.save(page)
            pages.add(page)
        }
        return pages
    }

    Set<Section> populateSections(sectionsNode, Page page){
        Set<Section> sections = new HashSet<Section>()
        sectionsNode.children.each{ sectionNode ->
            Section section = new Section()
            section.page = page
            section = sectionRepository.save(section)
            sectionNode.children.each{ sectionNodeChild ->
                switch(sectionNodeChild.name){
                    case "sectiondetails":
                        println("Populating section details")
                        SectionDetails sectionDetails = populateSectionDetails(sectionNodeChild)
                        section.sectionDetail = sectionDetails
                        break
                    case "forms":
                        println("Populating forms")
                        Set<Form> forms = populateForms(sectionNodeChild, section)
                        section.forms = forms
                        break
                    case "documents":
                        println("Populating documents")
                        Set<ModelBuilderDocument> documents = populateDocuments(sectionNodeChild)
                        documents.each{
                            it.section = section
                        }
                        modelBuilderDocumentRepository.save(documents)
                        section.documents = documents
                        break
                    case "lines":
                        println("Populating lines")
                        Set<Line> lines = populateLines(sectionNodeChild, section)
                        section.lines = lines
                        break
                }
            }
            section = sectionRepository.save(section)
            sections.add(section)
        }
        return sections
    }

    Set<Line> populateLines(linesNode, Section section){
        Set<Line> lines = new HashSet<Line>()
        linesNode.children.each { lineNode ->
            Line line = new Line()
            line.section = section
            line = lineRepository.save(line)
            lineNode.children.each { lineNodeChild ->
                switch (lineNodeChild.name) {
                    case "linedetails":
                        LineDetails lineDetails = new LineDetails()
                        lineNodeChild.children.each { lineDetailsChildNode ->
                            switch (lineDetailsChildNode.name) {
                                case "heading":
                                    lineDetails.heading = new Boolean(lineDetailsChildNode.text())
                                    break
                                case "code":
                                    lineDetails.code = lineDetailsChildNode.text()
                                    break
                                case "description":
                                    lineDetails.description = lineDetailsChildNode.text()
                                    break
                                case "equation":
                                    lineDetails.equation = lineDetailsChildNode.text()
                                    break
                                case "linenumber":
                                    lineDetails.lineNumber = lineDetailsChildNode.text()
                                    break
                                case "ruletext":
                                    lineDetails.ruleText = lineDetailsChildNode.text()
                                    break
                                case "type":
                                    lineDetails.lineType = lineDetailsChildNode.text()
                                    break
                                case "company-type":
                                    lineDetails.companyType = lineDetailsChildNode.text()
                                    break
                                case "use-confidence-grade":
                                    lineDetails.useConfidenceGrade = new Boolean(lineDetailsChildNode.text())
                                    break
                                case "validation-rule-code":
                                    lineDetails.validationRuleCode = lineDetailsChildNode.text()
                                    break
                                case "text-code":
                                    lineDetails.textCode = lineDetailsChildNode.text()
                                    break
                                case "unit":
                                    lineDetails.unit = lineDetailsChildNode.text()
                                    break
                                case "decimal-places":
                                    lineDetails.decimalPlaces = new Integer(lineDetailsChildNode.text())
                                    break
                            }
                        }
                        lineDetailsRepository.save(lineDetails)
                        line.lineDetail = lineDetails
                        break
                    case "documents":
                        Set<ModelBuilderDocument> docs = populateDocuments(lineNodeChild)
                        docs.each{
                            it.line = line
                        }
                        modelBuilderDocumentRepository.save(docs)
                        line.documents = docs
                        break
                    case "cellrange":
                        CellRange cellRange = new CellRange()
                        lineNodeChild.children.each { cellRangeNodeChild ->
                            switch (cellRangeNodeChild.name) {
                                case "startyear":
                                    cellRange.startYear = cellRangeNodeChild.text()
                                    break
                                case "endyear":
                                    cellRange.endYear = cellRangeNodeChild.text()
                                    break
                            }
                        }
                        if (null != cellRange) {
                            cellRange = cellRangeRepository.save(cellRange)
                            line.cellRange = cellRange
                        }
                        break
                    case "cells":
                        Set<Cell> cells = new HashSet<Cell>()
                        lineNodeChild.children.each { cellNode ->
                            Cell cell = new Cell()
                            cell.line = line
                            cellNode.children.each { cellNodeChild ->
                                switch (cellNodeChild.name) {
                                    case "code":
                                        cell.code = cellNodeChild.text()
                                        break
                                    case "year":
                                        cell.year = cellNodeChild.text()
                                        break
                                    case "equation":
                                        cell.equation = cellNodeChild.text()
                                        break
                                    case "type":
                                        cell.type = cellNodeChild.text()
                                        break
                                    case "cgtype":
                                        cell.cgType = cellNodeChild.text()
                                        break
                                }
                            }
                            cell = cellRepository.save(cell)
                            cells.add(cell)
                        }
                        line.cells = cells
                        break
                }
            }
            lineRepository.save(line)
            lines.add(line)
        }
        return lines
    }

    Set<Form> populateForms(formsNode, Section section){
        Set<Form> forms = new HashSet<Form>()
        formsNode.children.each{ formNodesChild ->
            Form form = new Form()
            form.section = section
            form = formRepository.save(form)
            FormDetails formDetails
            Set<FormHeadingCell> formHeadingsTop
            Set<FormHeadingCell> formHeadingsLeft
            Set<FormCell> formCells
            formNodesChild.children.each{ formNodeChildNode ->
                switch(formNodeChildNode.name){
                    case "form-details":
                        println("Populating Form Details")
                        formDetails = populateFormDetails(formNodeChildNode)
                        break
                    case "form-headings-top":
                        println("Populating Form headings top")
                        formHeadingsTop = populateFormHeadings(formNodeChildNode, form)
                        formHeadingsTop.each{
                            it.formTop = form
                        }
                        formHeadingCellRepository.save(formHeadingsTop)
                        break
                    case "form-headings-left":
                        println("Populating Form headings left")
                        formHeadingsLeft = populateFormHeadings(formNodeChildNode, form)
                        formHeadingsLeft.each{
                            it.formLeft = form
                        }
                        formHeadingCellRepository.save(formHeadingsLeft)
                        break
                    case "form-cells":
                        println("Populating Form cells")
                        formCells = populateFormCells(formNodeChildNode, form)
                        break
                }
            }
            form.formDetail = formDetails
            form.formHeadingsLefts = formHeadingsLeft
            form.formHeadingsTops = formHeadingsTop
            form.formCells = formCells
            form = formRepository.save(form)
            forms.add(form)
        }
        return forms
    }

    Set<FormCell> populateFormCells(formCellsNode, Form form){
        Set<FormCell> cells = new HashSet<FormCell>()
        formCellsNode.children.each{ formCellNode ->
            FormCell cell = new FormCell()
            cell.form = form
            formCellNode.children.each{
                switch(it.name){
                    case "cell-code":
                        cell.cellCode = it.text()
                        break
                    case "use-confidence-grade":
                        cell.useConfidenceGrade = it.text()
                        break
                    case "input-confidence-grade":
                        cell.inputConfidenceGrade = it.text()
                        break
                    case "confidence-grade-input-code":
                        cell.confidenceGradeInputCode = it.text()
                        break
                    case "row-heading-source":
                        cell.rowHeadingSource = it.text()
                        break
                    case "column-heading-source":
                        cell.columnHeadingSource = it.text()
                        break
                    case "group-description-code":
                        cell.groupDescriptionCode = it.text()
                        break
                    case "row":
                        cell.row = it.text()
                        break
                    case "column":
                        cell.formColumn = it.text()
                        break
                    case "row-span":
                        cell.rowSpan = it.text()
                        break
                    case "column-span":
                        cell.formColumnSpan = it.text()
                        break
                    case "width":
                        cell.width = it.text()
                        break
                }
            }
            cells.add(cell)
        }
        formCellRepository.save(cells)
        return cells
    }

    Set<FormHeadingCell> populateFormHeadings(formHeadingsNode, Form form){
        Set<FormHeadingCell> formHeadingCells = new HashSet<FormHeadingCell>()
        formHeadingsNode.children.each{ formHeadingsNodeChild ->
            //println formHeadingsNodeChild.children
            FormHeadingCell formHeadingCell = new FormHeadingCell()
            formHeadingsNodeChild.children.each{ formHeadingCellNode ->
                //println formHeadingCellNode.name
                switch(formHeadingCellNode.name){
                    case "text":
                        formHeadingCell.text = formHeadingCellNode.text()
                        break
                    case "use-line-number":
                        formHeadingCell.useLineNumber = new Boolean(formHeadingCellNode.text())
                        break
                    case "use-item-code":
                        formHeadingCell.useItemCode = new Boolean(formHeadingCellNode.text())
                        break
                    case "use-item-description":
                        formHeadingCell.useItemDescription = new Boolean(formHeadingCellNode.text())
                        break
                    case "use-unit":
                        formHeadingCell.useUnit = new Boolean(formHeadingCellNode.text())
                        break
                    case "use-year-code":
                        formHeadingCell.useYearCode = new Boolean(formHeadingCellNode.text())
                        break
                    case "use-confidence-grades":
                        formHeadingCell.useConfidenceGrades = new Boolean(formHeadingCellNode.text())
                        break
                    case "row":
                        formHeadingCell.row = formHeadingCellNode.text()
                        break
                    case "column":
                        formHeadingCell.formHeadingColumn = formHeadingCellNode.text()
                        break
                    case "row-span":
                        formHeadingCell.rowSpan = formHeadingCellNode.text()
                        break
                    case "column-span":
                        formHeadingCell.formHeadingColumnSpan = formHeadingCellNode.text()
                        break
                    case "item-code":
                        formHeadingCell.itemCode = formHeadingCellNode.text()
                        break
                    case "year-code":
                        formHeadingCell.yearCode = formHeadingCellNode.text()
                        break
                    case "width":
                        formHeadingCell.width = formHeadingCellNode.text()
                        break
                    case "cell-code":
                        formHeadingCell.cellCode = formHeadingCellNode.text()
                        break
                    case "group-description-code":
                        formHeadingCell.groupDescriptionCode = formHeadingCellNode.text()
                        break
                }
            }
            formHeadingCell = formHeadingCellRepository.save(formHeadingCell)
            formHeadingCells.add(formHeadingCell)
        }
        return formHeadingCells
    }

    FormDetails populateFormDetails(formDetailsNode){
        FormDetails formDetails = new FormDetails()
        formDetailsNode.children.each{
            switch(it.name){
                case "company-type":
                    formDetails.companyType = it.text()
                    break
            }
        }
        formDetails = formDetailsRepository.save(formDetails)
        return formDetails
    }

    SectionDetails populateSectionDetails(sectionDetailsNode){
        SectionDetails sectionDetails = new SectionDetails()
        println("Populating Section Details")
        //println sectionDetailsNode.children
        sectionDetailsNode.children.each{ sectionDetailsNodeChild->
            //println sectionDetailsNodeChild.name
            switch(sectionDetailsNodeChild.name){
                case "display":
                    sectionDetails.display = sectionDetailsNodeChild.text()
                    break
                case "code":
                    sectionDetails.code= sectionDetailsNodeChild.text()
                    break
                case "grouptype":
                    sectionDetails.groupType = sectionDetailsNodeChild.text()
                    break
                case "use-line-number":
                    sectionDetails.useLineNumber = new Boolean(sectionDetailsNodeChild.text())
                    break
                case "use-item-code":
                    sectionDetails.useItemCode = new Boolean(sectionDetailsNodeChild.text())
                    break
                case "use-item-description":
                    sectionDetails.useItemDescription = new Boolean(sectionDetailsNodeChild.text())
                    break
                case "use-unit":
                    sectionDetails.useUnit = new Boolean(sectionDetailsNodeChild.text())
                    break
                case "use-year-code":
                    sectionDetails.useYearCode = new Boolean(sectionDetailsNodeChild.text())
                    break
                case "use-confidence-grades":
                    sectionDetails.useConfidenceGrades = new Boolean(sectionDetailsNodeChild.text())
                    break
                case "allow-group-selection":
                    sectionDetails.allowGroupSelection = new Boolean(sectionDetailsNodeChild.text())
                    break
                case "allow-data-changes":
                    sectionDetails.allowDataChanges = new Boolean(sectionDetailsNodeChild.text())
                    break
                case "section-type":
                    sectionDetails.sectionType = sectionDetailsNodeChild.text()
                    break
                case "item-code-column":
                    sectionDetails.itemCodeColumn = new Integer(sectionDetailsNodeChild.text())
                    break
            }
        }
        sectionDetails = sectionDetailsRepository.save(sectionDetails)
        return sectionDetails
    }

    Set<ModelBuilderDocument> populateDocuments(documentsNode){
        Set<ModelBuilderDocument> documents = new HashSet<ModelBuilderDocument>()
        documentsNode.children.each{ documentNode ->
            ModelBuilderDocument document = new ModelBuilderDocument()
            documentNode.children.each{ documentNodeChild ->
                switch(documentNodeChild.name){
                    case "reporter":
                        document.reporter = documentNodeChild.text()
                        break;
                    case "auditor":
                        document.auditor = documentNodeChild.text()
                        break;
                }
            }
            documents.add(document)
        }
        documents = modelBuilderDocumentRepository.save(documents)
        return documents
    }

    PageDetails populatePageDetails(pageDetailsNode){
        PageDetails pageDetails = new PageDetails()
        pageDetailsNode.children.each{ pageDetailsNodeChild ->
            switch(pageDetailsNodeChild.name){
                case "code":
                    pageDetails.code = pageDetailsNodeChild.text()
                    break
                case "description":
                    pageDetails.description = pageDetailsNodeChild.text()
                    break
                case "text":
                    pageDetails.pageText = pageDetailsNodeChild.text()
                    break
                case "company-type":
                    pageDetails.companyType = pageDetailsNodeChild.text()
                    break
                case "heading":
                    pageDetails.heading = pageDetailsNodeChild.text()
                    break
                case "commercial-in-confidence":
                    pageDetails.commercialInConfidence = new Boolean(pageDetailsNodeChild.text())
                    break
                case "hidden":
                    pageDetails.hidden = new Boolean(pageDetailsNodeChild.text())
                    break
                case "text-code":
                    pageDetails.textCode = pageDetailsNodeChild.text()
                    break
            }
        }
        pageDetails = pageDetailsRepository.save(pageDetails)
        return pageDetails
    }
}
