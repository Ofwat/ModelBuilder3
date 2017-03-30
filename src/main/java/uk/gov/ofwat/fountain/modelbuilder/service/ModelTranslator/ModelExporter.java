package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.gov.ofwat.fountain.modelbuilder.domain.Model;
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelRepository;
import uk.gov.ofwat.fountain.modelbuilder.service.FountainRestService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Adam Edgar on 04/05/2016.
 */
@Service
public class ModelExporter {
    private final Logger log = LoggerFactory.getLogger(ModelExporter.class);

    @Autowired
    private FountainRestService fountainRestService;

    @Inject
    private ModelRepository modelRepository;

    public ModelExporter() {
    }

    // file name - check against notes

    @Transactional
    public boolean save(Long modelId, ModelFileName modelFileName) {

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Model jaxbModel = transalateModelToJaxB(modelId);

        if (null == jaxbModel) {
            return false;
        }
        if (!writeModelToXMLFile(jaxbModel, modelFileName)) {
            return false;
        }
        return postToFountain();
    }

    private Boolean postToFountain() {
        String plainCreds = fountainRestService.getUser() + ":" + fountainRestService.getPassword();
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        String url = fountainRestService.getUrl() + "file/bulkModelUpload";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
        return (303 == response.getStatusCode().value());
    }

    private Boolean writeModelToXMLFile(uk.gov.ofwat.fountain.modelbuilder.model.generated.Model jaxbModel, ModelFileName modelFileName) {
        Boolean saved = true;

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(modelFileName.getFullPathAndName());
            marshaller.setLazyInit(true);
            marshaller.setClassesToBeBound(new Class<?>[] { uk.gov.ofwat.fountain.modelbuilder.model.generated.Model.class });
            if (marshaller.supports(uk.gov.ofwat.fountain.modelbuilder.model.generated.Model.class)) {
                marshaller.marshal(jaxbModel, new StreamResult(fos));
            }
            else {
                saved = false;
                log.error("JaxB marshalling not supported");
            }
        } catch (IOException e) {
            saved = false;
            log.error("File IO error when writeModelToXMLFile");
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e2) {
                saved = false;
                log.error("File IO error when closing file in writeModelToXMLFile");
                e2.printStackTrace();
            }
        }

        return saved;
    }


    private uk.gov.ofwat.fountain.modelbuilder.model.generated.Model transalateModelToJaxB(Long modelId) {

        Model model = modelRepository.findOne(modelId);
        ModelTranslator modelTranslator =  new ModelTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Model jaxbModel = null;
        try {
            jaxbModel = (uk.gov.ofwat.fountain.modelbuilder.model.generated.Model)modelTranslator.toJaxB(model);
        } catch (TranslatorException e) {
            log.error("Model (id:" + modelId + ") could not be translated to JaxB.");
            e.printStackTrace();
        }
        return jaxbModel;
    }

}
