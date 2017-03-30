import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    FormService,
    FormPopupService,
    FormComponent,
    FormDetailComponent,
    FormDialogComponent,
    FormPopupComponent,
    FormDeletePopupComponent,
    FormDeleteDialogComponent,
    formRoute,
    formPopupRoute,
} from './';

let ENTITY_STATES = [
    ...formRoute,
    ...formPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FormComponent,
        FormDetailComponent,
        FormDialogComponent,
        FormDeleteDialogComponent,
        FormPopupComponent,
        FormDeletePopupComponent,
    ],
    entryComponents: [
        FormComponent,
        FormDialogComponent,
        FormPopupComponent,
        FormDeleteDialogComponent,
        FormDeletePopupComponent,
    ],
    providers: [
        FormService,
        FormPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderFormModule {}
