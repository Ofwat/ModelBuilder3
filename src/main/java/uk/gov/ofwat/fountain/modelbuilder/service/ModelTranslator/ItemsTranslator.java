package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.Item;

import java.util.Set;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class ItemsTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Set)) {
            throw new TranslatorException("This is not a Items");
        }
        for (Object o : (Set)entity) {
            if (!(o instanceof Item)) {
                throw new TranslatorException("This is not a Item");
            }
        }

        Set<Item> items = (Set<Item>)entity;


        uk.gov.ofwat.fountain.modelbuilder.model.generated.Items jaxbItems =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Items();
        //TODO We need to change how we translate items after entity relationship changes.
        for (Item item: items) {
            ItemTranslator itemTranslator =  new ItemTranslator();
            uk.gov.ofwat.fountain.modelbuilder.model.generated.Item jaxbItem =
                (uk.gov.ofwat.fountain.modelbuilder.model.generated.Item)itemTranslator.toJaxB(item);
            jaxbItems.getItem().add(jaxbItem);
        }
        return jaxbItems;
    }
}
