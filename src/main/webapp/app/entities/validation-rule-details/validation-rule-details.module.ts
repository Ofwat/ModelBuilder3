import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    ValidationRuleDetailsService,
    ValidationRuleDetailsPopupService,
    ValidationRuleDetailsComponent,
    ValidationRuleDetailsDetailComponent,
    ValidationRuleDetailsDialogComponent,
    ValidationRuleDetailsPopupComponent,
    ValidationRuleDetailsDeletePopupComponent,
    ValidationRuleDetailsDeleteDialogComponent,
    validationRuleDetailsRoute,
    validationRuleDetailsPopupRoute,
} from './';

let ENTITY_STATES = [
    ...validationRuleDetailsRoute,
    ...validationRuleDetailsPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ValidationRuleDetailsComponent,
        ValidationRuleDetailsDetailComponent,
        ValidationRuleDetailsDialogComponent,
        ValidationRuleDetailsDeleteDialogComponent,
        ValidationRuleDetailsPopupComponent,
        ValidationRuleDetailsDeletePopupComponent,
    ],
    entryComponents: [
        ValidationRuleDetailsComponent,
        ValidationRuleDetailsDialogComponent,
        ValidationRuleDetailsPopupComponent,
        ValidationRuleDetailsDeleteDialogComponent,
        ValidationRuleDetailsDeletePopupComponent,
    ],
    providers: [
        ValidationRuleDetailsService,
        ValidationRuleDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderValidationRuleDetailsModule {}
