import { ValidationRule } from '../validation-rule';
export class ValidationRuleItem {
    constructor(
        public id?: number,
        public type?: string,
        public evaluate?: string,
        public value?: string,
        public validationRule?: ValidationRule,
    ) { }
}
