package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Adam Edgar on 24/05/2016.
 */
@Service
public class ModelFileName {

    private final Logger log = LoggerFactory.getLogger(ModelFileName.class);

    private String extension = "_mdl.xml";

    private String modelCode = null;
    private String suffix = null;
    private String path = null;
    private Boolean useTimeStamp = true;
//    private fountainInstance
//    private version

    public String getFileName() {
        String formatedDate = "";
        if (isUseTimeStamp()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            formatedDate = dateFormat.format(new Date());
        }
        return modelCode + "_" + (suffix.isEmpty() ? "" : suffix + "_") + (!isUseTimeStamp() ? "" : formatedDate + "_")  + extension;
    }

    public String getFullPathAndName() {
        String fileName = path + "/" + getFileName();
        log.info(fileName);
        return fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean isUseTimeStamp() {
        return useTimeStamp;
    }

    public void setUseTimeStamp(Boolean useTimeStamp) {
        this.useTimeStamp = useTimeStamp;
    }
}
