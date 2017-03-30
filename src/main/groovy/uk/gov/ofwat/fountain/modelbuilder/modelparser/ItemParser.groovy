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

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uk.gov.ofwat.fountain.modelbuilder.domain.Item
import uk.gov.ofwat.fountain.modelbuilder.domain.Model
import uk.gov.ofwat.fountain.modelbuilder.domain.ModelItem
import uk.gov.ofwat.fountain.modelbuilder.repository.ItemRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelItemRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelRepository

import javax.inject.Inject

@Component
class ItemParser {

    private final Logger log = LoggerFactory.getLogger(ItemParser.class)

    @Inject ItemRepository itemRepository
    @Inject ModelItemRepository modelItemRepository
    @Inject ModelRepository modelRepository

    Set<Item> populateItems(itemsNode, model){
        Set<Item> items = new HashSet<Item>()
        itemsNode.children.each{ itemNode ->


            String code
            String versionNumber
            String pricebaseCode
            String purposeCode
            String textCode

            //Item item = itemRepository.findByCode()
            //Item item = new Item()

            itemNode.children.each{ itemNodeChild ->
                //println "name:" + itemNodeChild.name
                switch(itemNodeChild.name){
                    case "code":
                        code = itemNodeChild.text()
                        break
                    case "version-number":
                        versionNumber = itemNodeChild.text()
                        break
                    case "pricebase-code":
                        pricebaseCode = itemNodeChild.text()
                        break
                    case "purpose-code":
                        purposeCode = itemNodeChild.text()
                        break
                    case "text-code":
                        textCode = itemNodeChild.text()
                        break
                }
            }

            ModelItem modelItem
            Item item = itemRepository.findByCodeAndVersionNumber(code, versionNumber)
            if(item != null){
                log.warn("Already found an item with code:${code} and version: ${versionNumber} - Not adding Item again.")
                Model existingModel = modelRepository.findById(model.id)
                modelItem = modelItemRepository.findByItemAndModel(item, existingModel)
                if(modelItem != null){
                    log.warn("Already found an ModelItem with code:${code} and version: ${versionNumber} for model ${model.modelDetails?.name} - Not adding Item again.")
                }else{
                    log.info("Creating new ModelItem for model ${model.modelDetails?.name}, item code ${code}.")
                    modelItem = new ModelItem()
                    modelItem.item = item
                    modelItem.model = model
                    modelItemRepository.save(modelItem)
                }
            }else{
                //Create a new Item and ModelItem
                log.info("Creating new Item ${code} at version: ${versionNumber} and associated ModelItem for model ${model.modelDetails?.name}.")
                item = new Item()
                item.code = code
                item.versionNumber = versionNumber
                item.pricebaseCode = pricebaseCode
                item.purposeCode = purposeCode
                item.textCode = textCode
                item = itemRepository.save(item)
                modelItem = new ModelItem()
                modelItem.item = item
                modelItem.model = model
                modelItemRepository.save(modelItem)
            }
            items.add(item)
        }
         return items
    }
}
