import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    ModelDetailsService,
    ModelDetailsPopupService,
    ModelDetailsComponent,
    ModelDetailsDetailComponent,
    ModelDetailsDialogComponent,
    ModelDetailsPopupComponent,
    ModelDetailsDeletePopupComponent,
    ModelDetailsDeleteDialogComponent,
    modelDetailsRoute,
    modelDetailsPopupRoute,
} from './';

let ENTITY_STATES = [
    ...modelDetailsRoute,
    ...modelDetailsPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ModelDetailsComponent,
        ModelDetailsDetailComponent,
        ModelDetailsDialogComponent,
        ModelDetailsDeleteDialogComponent,
        ModelDetailsPopupComponent,
        ModelDetailsDeletePopupComponent,
    ],
    entryComponents: [
        ModelDetailsComponent,
        ModelDetailsDialogComponent,
        ModelDetailsPopupComponent,
        ModelDetailsDeleteDialogComponent,
        ModelDetailsDeletePopupComponent,
    ],
    providers: [
        ModelDetailsService,
        ModelDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderModelDetailsModule {}
