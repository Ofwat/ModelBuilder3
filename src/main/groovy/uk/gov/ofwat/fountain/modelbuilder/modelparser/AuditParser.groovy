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
import uk.gov.ofwat.fountain.modelbuilder.domain.ModelAudit
import uk.gov.ofwat.fountain.modelbuilder.domain.AuditChange
import uk.gov.ofwat.fountain.modelbuilder.domain.AuditDetails
import uk.gov.ofwat.fountain.modelbuilder.domain.Model
import uk.gov.ofwat.fountain.modelbuilder.domain.ModelAudit
import uk.gov.ofwat.fountain.modelbuilder.repository.AuditChangeRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.AuditDetailsRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelAuditRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelAuditRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelRepository

import javax.inject.Inject

@Component
class AuditParser {

    @Inject ModelAuditRepository auditRepository
    @Inject AuditDetailsRepository auditDetailsRepository
    @Inject AuditChangeRepository auditChangeRepository
    @Inject ModelRepository modelRepository

    Set<ModelAudit> populateAudits(auditsNode, Model model){
        Set<ModelAudit> audits = new HashSet<ModelAudit>()
        auditsNode.children.each{ auditNode ->
            ModelAudit audit = new ModelAudit()
            AuditDetails auditDetails = new AuditDetails()
            def auditDetailsNode = auditNode.children[0]
            auditDetailsNode.children.each{ auditDetailsChild ->
                //TODO add audit details data
                switch(auditDetailsChild.name){
                    case "username":
                        auditDetails.username = auditDetailsChild.text()
                        break
                    case "timestamp":
                        auditDetails.timestamp = auditDetailsChild.text()
                        break
                    case "reason":
                        auditDetails.reason = auditDetailsChild.text()
                        break
                }
            }
            auditRepository.save(audit)
            Set<AuditChange> auditChanges = new HashSet<AuditChange>()
            auditNode.children[1].children().each{ changeNode ->
                AuditChange change = new AuditChange()
                change.changeText = changeNode.children[0]
                change.modelAudit = audit
                auditChanges.add(change);
            }
            auditDetails = auditDetailsRepository.save(auditDetails)
            audit.modelAuditDetail = auditDetails
            auditChanges = auditChangeRepository.save(auditChanges)
            auditChangeRepository.flush()
            audit.changes = auditChanges
            //TODO What about assigning the model?
            audit.model = model
            audits.add(audit)
        }
        audits = auditRepository.save(audits)
        auditRepository.flush()
        model.modelAudits = audits
        modelRepository.save(model)
        return audits
    }
}
