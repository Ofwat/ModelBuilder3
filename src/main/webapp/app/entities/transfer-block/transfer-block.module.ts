import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    TransferBlockService,
    TransferBlockPopupService,
    TransferBlockComponent,
    TransferBlockDetailComponent,
    TransferBlockDialogComponent,
    TransferBlockPopupComponent,
    TransferBlockDeletePopupComponent,
    TransferBlockDeleteDialogComponent,
    transferBlockRoute,
    transferBlockPopupRoute,
} from './';

let ENTITY_STATES = [
    ...transferBlockRoute,
    ...transferBlockPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TransferBlockComponent,
        TransferBlockDetailComponent,
        TransferBlockDialogComponent,
        TransferBlockDeleteDialogComponent,
        TransferBlockPopupComponent,
        TransferBlockDeletePopupComponent,
    ],
    entryComponents: [
        TransferBlockComponent,
        TransferBlockDialogComponent,
        TransferBlockPopupComponent,
        TransferBlockDeleteDialogComponent,
        TransferBlockDeletePopupComponent,
    ],
    providers: [
        TransferBlockService,
        TransferBlockPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderTransferBlockModule {}
