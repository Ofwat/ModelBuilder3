package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.Item;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class ItemTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Item)) {
            throw new TranslatorException("This is not a Item");
        }

        Item item = (Item)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Item jaxbItem =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Item();
        jaxbItem.setCode(item.getCode());
        jaxbItem.setPricebaseCode(item.getPricebaseCode());
        jaxbItem.setPurposeCode(item.getPurposeCode());
        jaxbItem.setTextCode(item.getTextCode());
        //TODO move to ModelItem
        //jaxbItem.setVersionNumber(item.getVersionNumber());

        return jaxbItem;
    }
}
