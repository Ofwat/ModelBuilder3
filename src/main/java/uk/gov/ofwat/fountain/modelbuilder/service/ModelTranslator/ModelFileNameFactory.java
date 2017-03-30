package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * Created by Adam Edgar on 24/05/2016.
 */
@Service
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "modelBuilder.modelFileNameFactory")
public class ModelFileNameFactory {

    private final Logger log = LoggerFactory.getLogger(ModelFileNameFactory.class);

    private String path;
    private String ext;

    public ModelFileName create(String modelCode) {
        return create(modelCode, "");
    }

    public ModelFileName create(String modelCode, String suffix) {
        ModelFileName modelFileName = new ModelFileName();
        modelFileName.setModelCode(modelCode);
        modelFileName.setSuffix(suffix);
        modelFileName.setPath(getPath());
        modelFileName.setExtension(getExt());
        return modelFileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
