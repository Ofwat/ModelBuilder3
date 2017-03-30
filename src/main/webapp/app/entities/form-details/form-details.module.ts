import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    FormDetailsService,
    FormDetailsPopupService,
    FormDetailsComponent,
    FormDetailsDetailComponent,
    FormDetailsDialogComponent,
    FormDetailsPopupComponent,
    FormDetailsDeletePopupComponent,
    FormDetailsDeleteDialogComponent,
    formDetailsRoute,
    formDetailsPopupRoute,
} from './';

let ENTITY_STATES = [
    ...formDetailsRoute,
    ...formDetailsPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FormDetailsComponent,
        FormDetailsDetailComponent,
        FormDetailsDialogComponent,
        FormDetailsDeleteDialogComponent,
        FormDetailsPopupComponent,
        FormDetailsDeletePopupComponent,
    ],
    entryComponents: [
        FormDetailsComponent,
        FormDetailsDialogComponent,
        FormDetailsPopupComponent,
        FormDetailsDeleteDialogComponent,
        FormDetailsDeletePopupComponent,
    ],
    providers: [
        FormDetailsService,
        FormDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderFormDetailsModule {}
