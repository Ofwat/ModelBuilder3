import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    TransferConditionService,
    TransferConditionPopupService,
    TransferConditionComponent,
    TransferConditionDetailComponent,
    TransferConditionDialogComponent,
    TransferConditionPopupComponent,
    TransferConditionDeletePopupComponent,
    TransferConditionDeleteDialogComponent,
    transferConditionRoute,
    transferConditionPopupRoute,
} from './';

let ENTITY_STATES = [
    ...transferConditionRoute,
    ...transferConditionPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TransferConditionComponent,
        TransferConditionDetailComponent,
        TransferConditionDialogComponent,
        TransferConditionDeleteDialogComponent,
        TransferConditionPopupComponent,
        TransferConditionDeletePopupComponent,
    ],
    entryComponents: [
        TransferConditionComponent,
        TransferConditionDialogComponent,
        TransferConditionPopupComponent,
        TransferConditionDeleteDialogComponent,
        TransferConditionDeletePopupComponent,
    ],
    providers: [
        TransferConditionService,
        TransferConditionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderTransferConditionModule {}
