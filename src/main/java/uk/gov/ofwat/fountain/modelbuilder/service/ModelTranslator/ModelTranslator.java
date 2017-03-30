package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.Model;

/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class ModelTranslator implements Translator {

    @Override
    public Object toJaxB(Object entity) throws TranslatorException {
        if (!(entity instanceof Model)) {
            throw new TranslatorException("This is not a model");
        }
        Model model = (Model)entity;
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Model jaxbModel = new uk.gov.ofwat.fountain.modelbuilder.model.generated.Model();

        ModelDetailsTranslator modelDetailsTranslator =  new ModelDetailsTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Modeldetails jaxbModeldetails =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Modeldetails)modelDetailsTranslator.toJaxB(model.getModelDetails());
        jaxbModel.setModeldetails(jaxbModeldetails);

        //TODO These should now be ModelItems
        ItemsTranslator itemsTranslator =  new ItemsTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Items jaxbItems =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Items)itemsTranslator.toJaxB(model.getItems());
        jaxbModel.setItems(jaxbItems);

        YearsTranslator yearsTranslator =  new YearsTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Years jaxbYears =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Years)yearsTranslator.toJaxB(model.getYears());
        jaxbModel.setYears(jaxbYears);

        InputsTranslator inputsTranslator =  new InputsTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Inputs jaxbInputs =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Inputs)inputsTranslator.toJaxB(model.getInputs());
        jaxbModel.setInputs(jaxbInputs);

        HeadingsTranslator headingsTranslator =  new HeadingsTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Headings jaxbHeadings =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Headings)headingsTranslator.toJaxB(model.getHeadings());
        jaxbModel.setHeadings(jaxbHeadings);

        // ValidationRules

        CompanyPagesTranslator companyPagesTranslator =  new CompanyPagesTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.CompanyPages jaxbCompanyPages =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.CompanyPages)companyPagesTranslator.toJaxB(model.getCompanyPages());
        jaxbModel.setCompanyPages(jaxbCompanyPages);

        // Documents

        // Pages
        PagesTranslator pagesTranslator =  new PagesTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Pages jaxbPages =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Pages)pagesTranslator.toJaxB(model.getPages());
        jaxbModel.setPages(jaxbPages);

        // Transfers

        // Marcos

        // Texts

        return jaxbModel;
    }
}
