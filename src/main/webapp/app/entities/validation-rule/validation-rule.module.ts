import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    ValidationRuleService,
    ValidationRulePopupService,
    ValidationRuleComponent,
    ValidationRuleDetailComponent,
    ValidationRuleDialogComponent,
    ValidationRulePopupComponent,
    ValidationRuleDeletePopupComponent,
    ValidationRuleDeleteDialogComponent,
    validationRuleRoute,
    validationRulePopupRoute,
} from './';

let ENTITY_STATES = [
    ...validationRuleRoute,
    ...validationRulePopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ValidationRuleComponent,
        ValidationRuleDetailComponent,
        ValidationRuleDialogComponent,
        ValidationRuleDeleteDialogComponent,
        ValidationRulePopupComponent,
        ValidationRuleDeletePopupComponent,
    ],
    entryComponents: [
        ValidationRuleComponent,
        ValidationRuleDialogComponent,
        ValidationRulePopupComponent,
        ValidationRuleDeleteDialogComponent,
        ValidationRuleDeletePopupComponent,
    ],
    providers: [
        ValidationRuleService,
        ValidationRulePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderValidationRuleModule {}
