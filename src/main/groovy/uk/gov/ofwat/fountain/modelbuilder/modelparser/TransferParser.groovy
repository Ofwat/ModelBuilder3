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
import uk.gov.ofwat.fountain.modelbuilder.domain.Transfer
import uk.gov.ofwat.fountain.modelbuilder.domain.TransferBlock
import uk.gov.ofwat.fountain.modelbuilder.domain.TransferBlockDetails
import uk.gov.ofwat.fountain.modelbuilder.domain.TransferBlockItem
import uk.gov.ofwat.fountain.modelbuilder.domain.TransferCondition
import uk.gov.ofwat.fountain.modelbuilder.domain.YearCode
import uk.gov.ofwat.fountain.modelbuilder.repository.TransferBlockDetailsRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.TransferBlockItemRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.TransferBlockRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.TransferConditionRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.TransferRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.YearCodeRepository

import javax.inject.Inject

@Component
class TransferParser {

    @Inject TransferRepository transferRepository
    @Inject TransferBlockRepository transferBlockRepository
    @Inject TransferConditionRepository transferConditionRepository
    @Inject TransferBlockItemRepository transferBlockItemRepository
    @Inject YearCodeRepository yearCodeRepository
    @Inject TransferBlockDetailsRepository transferBlockDetailsRepository

    Set<Transfer> populateTransfers(transfersNode, Model model){
        Set<Transfer> transfers = new HashSet<Transfer>()
        transfersNode.children.each{ transferNode ->
            Transfer transfer = new Transfer()
            transfer.model = model
            transfer = transferRepository.save(transfer)
            transferNode.children.each{ transferNodeChild ->
                //println transferNodeChild
                switch(transferNodeChild.name){
                    case "description":
                        transfer.description = transferNodeChild.text()
                        break
                    case "transfer-condition":
                        println "populating transfer conditions..."
                        transfer.transferCondition = populateTransferCondition(transferNodeChild)
                        break
                    case "transfer-blocks":
                        println "populating transfer blocks..."
                        transfer.transferBlocks = populateTransferBlocks(transferNodeChild, transfer)
                        break
                }
            }
            transfers.add(transfer)
        }
        transferRepository.save(transfers)
        return transfers
    }

//TODO fix this!
//
    TransferCondition populateTransferCondition(transferConditionsNode){
        //Set<TransferCondition> transferConditions = new HashSet<TransferCondition>()
        //println transferConditionsNode.children
        TransferCondition transferCondition = new TransferCondition()
        transferConditionsNode.children.each{ transferConditionNode ->
            //println transferConditionNode.name

            //transferConditionNode.children.each{ transferConditionNodeChild ->
            switch(transferConditionNode.name){
                case "item-code":
                    transferCondition.itemCode = transferConditionNode.text()
                    break
                case "year-code":
                    transferCondition.yearCode = transferConditionNode.text()
                    break
                case "value":
                    transferCondition.value = transferConditionNode.text()
                    break
                case "failure-message":
                    transferCondition.failureMessage = transferConditionNode.text()
                    break
            }
            //}

        }
        //transferConditions.add(transferCondition)
        transferCondition = transferConditionRepository.save(transferCondition)
        return transferCondition
    }

    Set<TransferBlock> populateTransferBlocks(transferBlocksNode, Transfer transfer){
        Set<TransferBlock> transferBlocks = new HashSet<TransferBlock>()
        //println transferBlocksNode
        transferBlocksNode.children.each{ transferBlockNode ->
            TransferBlock transferBlock = new TransferBlock()
            transferBlock = transferBlockRepository.save(transferBlock)
            //println transferBlockNode
            transferBlockNode.children.each{ transferBlockNodeChild ->
                switch(transferBlockNodeChild.name){
                    case "transfer-block-details":
                        transferBlock.transferBlockDetails = populateTransferBlockDetails(transferBlockNodeChild)
                        break
                    case "transfer-block-items":
                        transferBlock.transferBlockItems = populateTransferBlockItems(transferBlockNodeChild, transferBlock)
                        break
                }
            }
            transferBlock.transfer = transfer
            transferBlocks.add(transferBlock)
        }
        transferBlockRepository.save(transferBlocks)
        return transferBlocks
    }

    Set<TransferBlockItem> populateTransferBlockItems(transferBlockItemsNode, TransferBlock transferBlock){
        println "populating transfer block items"
        Set<TransferBlockItem> transferBlockItems = new HashSet<TransferBlockItem>()
        transferBlockItemsNode.children.each{ transferBlockItemNode ->
            TransferBlockItem transferBlockItem = new TransferBlockItem()
            transferBlockItem.transferBLock = transferBlock
            Set<YearCode> yearCodes = new HashSet<YearCode>()
            transferBlockItemNode.children.each{ transferBlockItemNodeChild ->
                switch(transferBlockItemNodeChild.name){
                    case "item-code":
                        transferBlockItem.itemCode = transferBlockItemNodeChild.text()
                        break
                    case "company-type":
                        transferBlockItem.companyType = transferBlockItemNodeChild.text()
                        break
                    case "year-code":
                        //transferBlockItem.yearCode = transferBlockItemNodeChild.text()
                        YearCode yearCode = new YearCode()
                        yearCode.yearCode = transferBlockItemNodeChild.text()
                        yearCode.transferBlockItem = transferBlockItem
                        yearCodes.add(yearCode)
                        break
                }
            }
            transferBlockItem = transferBlockItemRepository.save(transferBlockItem)
            yearCodes = yearCodeRepository.save(yearCodes)
            transferBlockItem.yearCodes = yearCodes
            transferBlockItems.add(transferBlockItem)
        }
        transferBlockItems = transferBlockItemRepository.save(transferBlockItems)
        return transferBlockItems
    }

    TransferBlockDetails populateTransferBlockDetails(transferBlockDetailsNode){
        println "populating transfer block details"
        TransferBlockDetails transferBlockDetails = new TransferBlockDetails()
        transferBlockDetailsNode.children.each{ transferBlockDetailsNodeChild ->
            switch(transferBlockDetailsNodeChild.name){
                case "from-model-code":
                    transferBlockDetails.fromModelCode = transferBlockDetailsNodeChild.text()
                    break
                case "from-version-code":
                    transferBlockDetails.fromVersionCode = transferBlockDetailsNodeChild.text()
                    break
                case "from-page-code":
                    transferBlockDetails.fromPageCode = transferBlockDetailsNodeChild.text()
                    break
                case "to-model-code":
                    transferBlockDetails.toModelCode = transferBlockDetailsNodeChild.text()
                    break
                case "to-version-code":
                    transferBlockDetails.toVersionCode = transferBlockDetailsNodeChild.text()
                    break
                case "to-page-code":
                    transferBlockDetails.toPageCode = transferBlockDetailsNodeChild.text()
                    break
                case "to-macro-code":
                    transferBlockDetails.toMacroCode = transferBlockDetailsNodeChild.text()
                    break
            }
        }
        transferBlockDetailsRepository.save(transferBlockDetails)
        return transferBlockDetails
    }


}
