import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    MacroService,
    MacroPopupService,
    MacroComponent,
    MacroDetailComponent,
    MacroDialogComponent,
    MacroPopupComponent,
    MacroDeletePopupComponent,
    MacroDeleteDialogComponent,
    macroRoute,
    macroPopupRoute,
} from './';

let ENTITY_STATES = [
    ...macroRoute,
    ...macroPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MacroComponent,
        MacroDetailComponent,
        MacroDialogComponent,
        MacroDeleteDialogComponent,
        MacroPopupComponent,
        MacroDeletePopupComponent,
    ],
    entryComponents: [
        MacroComponent,
        MacroDialogComponent,
        MacroPopupComponent,
        MacroDeleteDialogComponent,
        MacroDeletePopupComponent,
    ],
    providers: [
        MacroService,
        MacroPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderMacroModule {}
