import { ValidationRuleDetails } from '../validation-rule-details';
import { ValidationRuleItem } from '../validation-rule-item';
import { Model } from '../model';
export class ValidationRule {
    constructor(
        public id?: number,
        public validationRuleDetail?: ValidationRuleDetails,
        public validationRuleItems?: ValidationRuleItem,
        public model?: Model,
    ) { }
}
