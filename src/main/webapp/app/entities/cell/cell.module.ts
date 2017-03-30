import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    CellService,
    CellPopupService,
    CellComponent,
    CellDetailComponent,
    CellDialogComponent,
    CellPopupComponent,
    CellDeletePopupComponent,
    CellDeleteDialogComponent,
    cellRoute,
    cellPopupRoute,
} from './';

let ENTITY_STATES = [
    ...cellRoute,
    ...cellPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CellComponent,
        CellDetailComponent,
        CellDialogComponent,
        CellDeleteDialogComponent,
        CellPopupComponent,
        CellDeletePopupComponent,
    ],
    entryComponents: [
        CellComponent,
        CellDialogComponent,
        CellPopupComponent,
        CellDeleteDialogComponent,
        CellDeletePopupComponent,
    ],
    providers: [
        CellService,
        CellPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderCellModule {}
