import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    ValidationRuleItemService,
    ValidationRuleItemPopupService,
    ValidationRuleItemComponent,
    ValidationRuleItemDetailComponent,
    ValidationRuleItemDialogComponent,
    ValidationRuleItemPopupComponent,
    ValidationRuleItemDeletePopupComponent,
    ValidationRuleItemDeleteDialogComponent,
    validationRuleItemRoute,
    validationRuleItemPopupRoute,
} from './';

let ENTITY_STATES = [
    ...validationRuleItemRoute,
    ...validationRuleItemPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ValidationRuleItemComponent,
        ValidationRuleItemDetailComponent,
        ValidationRuleItemDialogComponent,
        ValidationRuleItemDeleteDialogComponent,
        ValidationRuleItemPopupComponent,
        ValidationRuleItemDeletePopupComponent,
    ],
    entryComponents: [
        ValidationRuleItemComponent,
        ValidationRuleItemDialogComponent,
        ValidationRuleItemPopupComponent,
        ValidationRuleItemDeleteDialogComponent,
        ValidationRuleItemDeletePopupComponent,
    ],
    providers: [
        ValidationRuleItemService,
        ValidationRuleItemPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderValidationRuleItemModule {}
