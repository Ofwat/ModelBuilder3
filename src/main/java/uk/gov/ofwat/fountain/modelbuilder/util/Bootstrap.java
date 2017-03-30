package uk.gov.ofwat.fountain.modelbuilder.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.gov.ofwat.fountain.modelbuilder.repository.ModelDetailsRepository;
import uk.gov.ofwat.fountain.modelbuilder.service.FountainModelParserService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;

/**
 * Created by james.toddington on 27/04/2016.
 */
@Component
public class Bootstrap {

    private final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    @Inject private FountainModelParserService fountainModelParserService;
    @Inject private ModelDetailsRepository modelDetailsRepository;

    @PostConstruct
    protected void loadModel(){
        if(modelDetailsRepository.findByName("Regulatory Accounts 2014-15 test") == null) {
//            if(modelDetailsRepository.findByName("Regulatory Accounts 2014-15 V4") == null) {
            ClassLoader classLoader = getClass().getClassLoader();
            //File file = new File(classLoader.getResource("sampleDataNewModel.xml").getFile());
            File file = new File("C:/Fountain/ModelFiles/REG_ACCS_2014-15_Control_mdl.xml");
//            File file = new File("C:/Fountain/ModelFiles/Regulatory Accounts 2014-15_mdl.xml");
//          File file = new File("c:/Dev/data/CR_MDL.xml");
//          File file = new File("c:/Dev/data/Regulatory Accounts 2014-15_mdl.xml");
            log.info("We are going to load model file from: " + file.getAbsolutePath());

            try {
                fountainModelParserService.parseModelFromXmlFile(file);
            }catch(Exception  e){
                log.error("Not loading model we had an exception:" + e.toString());
            }
        }
    }
}
