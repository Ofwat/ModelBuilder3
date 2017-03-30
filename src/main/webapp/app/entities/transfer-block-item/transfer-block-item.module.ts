import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    TransferBlockItemService,
    TransferBlockItemPopupService,
    TransferBlockItemComponent,
    TransferBlockItemDetailComponent,
    TransferBlockItemDialogComponent,
    TransferBlockItemPopupComponent,
    TransferBlockItemDeletePopupComponent,
    TransferBlockItemDeleteDialogComponent,
    transferBlockItemRoute,
    transferBlockItemPopupRoute,
} from './';

let ENTITY_STATES = [
    ...transferBlockItemRoute,
    ...transferBlockItemPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TransferBlockItemComponent,
        TransferBlockItemDetailComponent,
        TransferBlockItemDialogComponent,
        TransferBlockItemDeleteDialogComponent,
        TransferBlockItemPopupComponent,
        TransferBlockItemDeletePopupComponent,
    ],
    entryComponents: [
        TransferBlockItemComponent,
        TransferBlockItemDialogComponent,
        TransferBlockItemPopupComponent,
        TransferBlockItemDeleteDialogComponent,
        TransferBlockItemDeletePopupComponent,
    ],
    providers: [
        TransferBlockItemService,
        TransferBlockItemPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderTransferBlockItemModule {}
