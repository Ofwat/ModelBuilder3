package uk.gov.ofwat.fountain.modelbuilder.service

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uk.gov.ofwat.fountain.modelbuilder.domain.Item
import uk.gov.ofwat.fountain.modelbuilder.domain.Model
import uk.gov.ofwat.fountain.modelbuilder.domain.ModelDetails
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelDetailsRepository
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelRepository

import javax.inject.Inject

@Service
@Component
@Transactional
class ModelCopyService {

    @Inject
    ModelRepository modelRepository;

    @Inject
    ModelDetailsRepository modelDetailsRepository;

    public Model copyModel(Model model, String newModelName, String newModelCode){

        Model newModel = new Model()
        /*
        ModelDetails modelDetails = model.getModelDetails();
        entityManager.detach(modelDetails);
        modelDetails.setId(null);
        modelDetails.setFountainModelId(null);
        modelDetails.setName(newModelName);
        modelDetails.setCode(newModelCode);
        modelDetails = modelDetailsRepository.save(modelDetails);
        model.setModelDetails(modelDetails);

        //Copy the model.
        //Detach it from the hibernate session.
        entityManager.detach(model);
        model.setId(null);

        model.itemss.each {Item item ->
            item.mo
        }


        existingModel.setAuditss(null);
        existingModel.setCompanyPagess(null);
        existingModel.setDocumentss(null);
        existingModel.setHeadingss(null);
        existingModel.setInputss(null);
        existingModel.setMacross(null);

        existingModel.setTextss(null);
        existingModel.setPagess(null);
        existingModel.setAuditss(null);
        existingModel.setTransferss(null);
        existingModel.setValidationRuless(null);
        existingModel.setYearss(null);

        //The following won't work as we will get FK constraint errors.
        Model newModel = modelRepository.save(existingModel);
        */
        return newModel



    }

    private Model cloneModel(Model model){
        return model;
    }

}
