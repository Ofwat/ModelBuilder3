import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    PageDetailsService,
    PageDetailsPopupService,
    PageDetailsComponent,
    PageDetailsDetailComponent,
    PageDetailsDialogComponent,
    PageDetailsPopupComponent,
    PageDetailsDeletePopupComponent,
    PageDetailsDeleteDialogComponent,
    pageDetailsRoute,
    pageDetailsPopupRoute,
} from './';

let ENTITY_STATES = [
    ...pageDetailsRoute,
    ...pageDetailsPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PageDetailsComponent,
        PageDetailsDetailComponent,
        PageDetailsDialogComponent,
        PageDetailsDeleteDialogComponent,
        PageDetailsPopupComponent,
        PageDetailsDeletePopupComponent,
    ],
    entryComponents: [
        PageDetailsComponent,
        PageDetailsDialogComponent,
        PageDetailsPopupComponent,
        PageDetailsDeleteDialogComponent,
        PageDetailsDeletePopupComponent,
    ],
    providers: [
        PageDetailsService,
        PageDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderPageDetailsModule {}
