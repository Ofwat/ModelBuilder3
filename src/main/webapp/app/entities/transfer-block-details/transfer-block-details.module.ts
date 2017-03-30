import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    TransferBlockDetailsService,
    TransferBlockDetailsPopupService,
    TransferBlockDetailsComponent,
    TransferBlockDetailsDetailComponent,
    TransferBlockDetailsDialogComponent,
    TransferBlockDetailsPopupComponent,
    TransferBlockDetailsDeletePopupComponent,
    TransferBlockDetailsDeleteDialogComponent,
    transferBlockDetailsRoute,
    transferBlockDetailsPopupRoute,
} from './';

let ENTITY_STATES = [
    ...transferBlockDetailsRoute,
    ...transferBlockDetailsPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TransferBlockDetailsComponent,
        TransferBlockDetailsDetailComponent,
        TransferBlockDetailsDialogComponent,
        TransferBlockDetailsDeleteDialogComponent,
        TransferBlockDetailsPopupComponent,
        TransferBlockDetailsDeletePopupComponent,
    ],
    entryComponents: [
        TransferBlockDetailsComponent,
        TransferBlockDetailsDialogComponent,
        TransferBlockDetailsPopupComponent,
        TransferBlockDetailsDeleteDialogComponent,
        TransferBlockDetailsDeletePopupComponent,
    ],
    providers: [
        TransferBlockDetailsService,
        TransferBlockDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderTransferBlockDetailsModule {}
