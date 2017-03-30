import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    PageService,
    PagePopupService,
    PageComponent,
    PageDetailComponent,
    PageDialogComponent,
    PagePopupComponent,
    PageDeletePopupComponent,
    PageDeleteDialogComponent,
    pageRoute,
    pagePopupRoute,
} from './';

let ENTITY_STATES = [
    ...pageRoute,
    ...pagePopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PageComponent,
        PageDetailComponent,
        PageDialogComponent,
        PageDeleteDialogComponent,
        PagePopupComponent,
        PageDeletePopupComponent,
    ],
    entryComponents: [
        PageComponent,
        PageDialogComponent,
        PagePopupComponent,
        PageDeleteDialogComponent,
        PageDeletePopupComponent,
    ],
    providers: [
        PageService,
        PagePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderPageModule {}
