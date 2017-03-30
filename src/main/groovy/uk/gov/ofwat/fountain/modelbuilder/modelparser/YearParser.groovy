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
import uk.gov.ofwat.fountain.modelbuilder.domain.Year
import uk.gov.ofwat.fountain.modelbuilder.repository.YearRepository

import javax.inject.Inject

@Component
class YearParser {

    @Inject private YearRepository yearRepository

    Set<Year> populateYears(yearsNode, Model model){
        Set<Year> years = new HashSet<Year>()
        yearsNode.children.each{ yearNode ->
            Year year = new Year()
            year.base = yearNode.text()
            year.model = model
            years.add(year)
        }
        years = yearRepository.save(years)
        return years
    }
}
