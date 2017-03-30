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
import uk.gov.ofwat.fountain.modelbuilder.domain.Heading
import uk.gov.ofwat.fountain.modelbuilder.domain.Model
import uk.gov.ofwat.fountain.modelbuilder.repository.HeadingRepository

import javax.inject.Inject

@Component
class HeadingParser {

    @Inject HeadingRepository headingRepository

    Set<Heading> populateHeadings(headingsNode, Model model){
        Set<Heading> headings = new HashSet<Heading>()
        headingsNode.children.each{ headingsNodeChild ->
            headingsNodeChild.each{ headingNode ->
                Heading heading = new Heading()
                headingNode.children.each{ headingNodeChild ->
                    switch(headingNodeChild.name){
                        case "code":
                            heading.code = headingNodeChild.text()
                            break
                        case "description":
                            heading.description = headingNodeChild.text()
                            break
                        case "parent":
                            heading.parent = headingNodeChild.text()
                            break
                        case "notes":
                            heading.notes = headingNodeChild.text()
                            break
                        case "annotation":
                            heading.annotation = headingNodeChild.text()
                            break
                    }
                }
                heading.model = model
                headings.add(heading)
            }
        }
        headingRepository.save(headings)
        return headings
    }

}
