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
import uk.gov.ofwat.fountain.modelbuilder.domain.CompanyPage
import uk.gov.ofwat.fountain.modelbuilder.domain.Model
import uk.gov.ofwat.fountain.modelbuilder.repository.CompanyPageRepository

import javax.inject.Inject

@Component
class CompanyPageParser {

    @Inject CompanyPageRepository companyPageRepository

    Set<CompanyPage> populateCompanyPages(companyPagesNode, Model model){
        Set<CompanyPage> companyPages = new HashSet<CompanyPage>()
        companyPagesNode.children.each{companyPagesNodeChild ->
            CompanyPage companyPage = new CompanyPage()
            companyPagesNodeChild.each{ companyPageNode ->
                companyPageNode.children.each{ companyPageNodeChild ->
                    switch(companyPageNodeChild.name){
                        case "company-code":
                            companyPage.companyCode = companyPageNodeChild.text()
                            break
                        case "page-code":
                            companyPage.pageCode = companyPageNodeChild.text()
                            break
                    }
                }
            }
            companyPage.model = model
            companyPages.add(companyPage)
        }
        companyPageRepository.save(companyPages)
        return companyPages
    }
}
