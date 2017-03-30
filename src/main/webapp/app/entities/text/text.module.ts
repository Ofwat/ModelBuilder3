import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    TextService,
    TextPopupService,
    TextComponent,
    TextDetailComponent,
    TextDialogComponent,
    TextPopupComponent,
    TextDeletePopupComponent,
    TextDeleteDialogComponent,
    textRoute,
    textPopupRoute,
} from './';

let ENTITY_STATES = [
    ...textRoute,
    ...textPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TextComponent,
        TextDetailComponent,
        TextDialogComponent,
        TextDeleteDialogComponent,
        TextPopupComponent,
        TextDeletePopupComponent,
    ],
    entryComponents: [
        TextComponent,
        TextDialogComponent,
        TextPopupComponent,
        TextDeleteDialogComponent,
        TextDeletePopupComponent,
    ],
    providers: [
        TextService,
        TextPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderTextModule {}
