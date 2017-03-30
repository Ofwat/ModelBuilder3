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
import uk.gov.ofwat.fountain.modelbuilder.domain.Text
import uk.gov.ofwat.fountain.modelbuilder.domain.TextBlock
import uk.gov.ofwat.fountain.modelbuilder.repository.TextBlockRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.TextRepository

import javax.inject.Inject

@Component
class TextParser {
    @Inject TextRepository textRepository
    @Inject TextBlockRepository textBlockRepository

    Set<Text> populateTexts(textsNode, Model model){
        Set<Text> texts = new HashSet<Text>()
        textsNode.children.each{
            Text text = populateText(it)
            text.model = model
            texts.add(text)
        }
        textRepository.save(texts)
        return texts
    }

    Text populateText(textNode){
        Text text = new Text()
        Set<TextBlock> textBlocks = new HashSet<TextBlock>()
        textNode.children.each{ textNodeChild ->
            switch(textNodeChild.name){
                case "code":
                    text.code = textNodeChild.text()
                    text = textRepository.save(text)
                    break
                case "text-blocks":
                    textNodeChild.children.each{ textBlockNode ->
                        TextBlock textBlock = new TextBlock()
                        textBlockNode.children.each{ textBlockNodeChild ->
                            switch(textBlockNodeChild.name){
                                case "description":
                                    textBlock.description = textBlockNodeChild.text()
                                    break
                                case "version-number":
                                    textBlock.versionNumber = textBlockNodeChild.text()
                                    break
                                case "text-format-code":
                                    textBlock.textFormatCode = textBlockNodeChild.text()
                                    //TODO Should remove this attribute as it is not required.
                                    textBlock.textFormatCode = textBlockNodeChild.text()
                                    break
                                case "text-type-code":
                                    textBlock.textTypeCode = textBlockNodeChild.text()
                                    break
                                case "retired":
                                    textBlock.retired = new Boolean(textBlockNodeChild.text())
                                    break
                                case "data":
                                    textBlock.data = textBlockNodeChild.text()
                                    break
                            }
                        }
                        textBlock.text = text
                        textBlockRepository.save(textBlock)
                        textBlocks.add(textBlock)
                    }
                    break
            }
        }
        text.textBlocks = textBlocks
        return text
    }

}
