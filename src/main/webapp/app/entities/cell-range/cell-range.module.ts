import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    CellRangeService,
    CellRangePopupService,
    CellRangeComponent,
    CellRangeDetailComponent,
    CellRangeDialogComponent,
    CellRangePopupComponent,
    CellRangeDeletePopupComponent,
    CellRangeDeleteDialogComponent,
    cellRangeRoute,
    cellRangePopupRoute,
} from './';

let ENTITY_STATES = [
    ...cellRangeRoute,
    ...cellRangePopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CellRangeComponent,
        CellRangeDetailComponent,
        CellRangeDialogComponent,
        CellRangeDeleteDialogComponent,
        CellRangePopupComponent,
        CellRangeDeletePopupComponent,
    ],
    entryComponents: [
        CellRangeComponent,
        CellRangeDialogComponent,
        CellRangePopupComponent,
        CellRangeDeleteDialogComponent,
        CellRangeDeletePopupComponent,
    ],
    providers: [
        CellRangeService,
        CellRangePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderCellRangeModule {}
