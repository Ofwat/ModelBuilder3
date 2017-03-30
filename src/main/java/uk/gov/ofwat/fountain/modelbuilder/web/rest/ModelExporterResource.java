package uk.gov.ofwat.fountain.modelbuilder.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ofwat.fountain.modelbuilder.domain.Model;
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelRepository;
import uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator.ModelExporter;
import uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator.ModelFileName;
import uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator.ModelFileNameFactory;
import uk.gov.ofwat.fountain.modelbuilder.web.rest.util.HeaderUtil;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing ModelExporter.
 */
@RestController
@RequestMapping("/api")
public class ModelExporterResource {

    private final Logger log = LoggerFactory.getLogger(ModelExporterResource.class);

    @Inject
    private ModelRepository modelRepository;

    @Inject
    private ModelExporter modelExporter;

    @Inject
    private ModelFileNameFactory modelFileNameFactory;

    /**
     * POST  /modelExport -> Create a new exported model.
     */
    @RequestMapping(value = "/modelExport/{modelId}", method = RequestMethod.POST)
    @Timed
    public ResponseEntity<Long> exportModel(@PathVariable Long modelId, @RequestBody String form) throws URISyntaxException {
        log.debug("REST request to export model : {}", modelId);
        if (modelId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("modelExport", "", "Must have a modelId to export.")).body(null);
        }
        Model model = modelRepository.findOne(modelId);
        if (model == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("modelExport", "", "Cannot find model with modelId " + modelId)).body(null);
        }
        ModelFileName modelFileName = modelFileNameFactory.create(model.getModelDetails().getCode());
        Boolean saved = modelExporter.save(modelId, modelFileName);
        if (!saved) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(HeaderUtil.createFailureAlert("modelExport", "", "Export failed withmodelId " + modelId)).body(null);
        }

        return ResponseEntity.created(new URI("/api/modelDetailss/" + modelId))
            .headers(HeaderUtil.createEntityCreationAlert("modelDetails", "" + modelId))
            .body(modelId);
   }

}
