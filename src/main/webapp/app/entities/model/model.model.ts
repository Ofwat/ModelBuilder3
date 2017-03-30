import { ModelDetails } from '../model-details';
import { ModelAudit } from '../model-audit';
import { Item } from '../item';
import { Year } from '../year';
import { Heading } from '../heading';
import { ValidationRule } from '../validation-rule';
import { CompanyPage } from '../company-page';
import { ModelBuilderDocument } from '../model-builder-document';
import { Page } from '../page';
import { Transfer } from '../transfer';
import { Macro } from '../macro';
import { Text } from '../text';
import { Input } from '../input';
export class Model {
    constructor(
        public id?: number,
        public modelDetails?: ModelDetails,
        public modelAudits?: ModelAudit,
        public items?: Item,
        public years?: Year,
        public headings?: Heading,
        public validationRules?: ValidationRule,
        public companyPages?: CompanyPage,
        public documents?: ModelBuilderDocument,
        public pages?: Page,
        public transfers?: Transfer,
        public macros?: Macro,
        public texts?: Text,
        public input?: Input,
    ) { }
}
