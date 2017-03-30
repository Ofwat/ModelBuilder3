package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

/**
 * Created by Adam Edgar on 10/05/2016.
 */
public interface Translator {

    public Object toJaxB(Object entity) throws TranslatorException;
//    public Object toDomain();
}
