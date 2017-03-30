import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    InputService,
    InputPopupService,
    InputComponent,
    InputDetailComponent,
    InputDialogComponent,
    InputPopupComponent,
    InputDeletePopupComponent,
    InputDeleteDialogComponent,
    inputRoute,
    inputPopupRoute,
} from './';

let ENTITY_STATES = [
    ...inputRoute,
    ...inputPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InputComponent,
        InputDetailComponent,
        InputDialogComponent,
        InputDeleteDialogComponent,
        InputPopupComponent,
        InputDeletePopupComponent,
    ],
    entryComponents: [
        InputComponent,
        InputDialogComponent,
        InputPopupComponent,
        InputDeleteDialogComponent,
        InputDeletePopupComponent,
    ],
    providers: [
        InputService,
        InputPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderInputModule {}
