import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    TextBlockService,
    TextBlockPopupService,
    TextBlockComponent,
    TextBlockDetailComponent,
    TextBlockDialogComponent,
    TextBlockPopupComponent,
    TextBlockDeletePopupComponent,
    TextBlockDeleteDialogComponent,
    textBlockRoute,
    textBlockPopupRoute,
} from './';

let ENTITY_STATES = [
    ...textBlockRoute,
    ...textBlockPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TextBlockComponent,
        TextBlockDetailComponent,
        TextBlockDialogComponent,
        TextBlockDeleteDialogComponent,
        TextBlockPopupComponent,
        TextBlockDeletePopupComponent,
    ],
    entryComponents: [
        TextBlockComponent,
        TextBlockDialogComponent,
        TextBlockPopupComponent,
        TextBlockDeleteDialogComponent,
        TextBlockDeletePopupComponent,
    ],
    providers: [
        TextBlockService,
        TextBlockPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderTextBlockModule {}
