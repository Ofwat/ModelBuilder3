import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    TransferService,
    TransferPopupService,
    TransferComponent,
    TransferDetailComponent,
    TransferDialogComponent,
    TransferPopupComponent,
    TransferDeletePopupComponent,
    TransferDeleteDialogComponent,
    transferRoute,
    transferPopupRoute,
} from './';

let ENTITY_STATES = [
    ...transferRoute,
    ...transferPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TransferComponent,
        TransferDetailComponent,
        TransferDialogComponent,
        TransferDeleteDialogComponent,
        TransferPopupComponent,
        TransferDeletePopupComponent,
    ],
    entryComponents: [
        TransferComponent,
        TransferDialogComponent,
        TransferPopupComponent,
        TransferDeleteDialogComponent,
        TransferDeletePopupComponent,
    ],
    providers: [
        TransferService,
        TransferPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderTransferModule {}
