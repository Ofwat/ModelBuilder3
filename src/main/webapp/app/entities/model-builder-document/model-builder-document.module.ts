import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    ModelBuilderDocumentService,
    ModelBuilderDocumentPopupService,
    ModelBuilderDocumentComponent,
    ModelBuilderDocumentDetailComponent,
    ModelBuilderDocumentDialogComponent,
    ModelBuilderDocumentPopupComponent,
    ModelBuilderDocumentDeletePopupComponent,
    ModelBuilderDocumentDeleteDialogComponent,
    modelBuilderDocumentRoute,
    modelBuilderDocumentPopupRoute,
} from './';

let ENTITY_STATES = [
    ...modelBuilderDocumentRoute,
    ...modelBuilderDocumentPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ModelBuilderDocumentComponent,
        ModelBuilderDocumentDetailComponent,
        ModelBuilderDocumentDialogComponent,
        ModelBuilderDocumentDeleteDialogComponent,
        ModelBuilderDocumentPopupComponent,
        ModelBuilderDocumentDeletePopupComponent,
    ],
    entryComponents: [
        ModelBuilderDocumentComponent,
        ModelBuilderDocumentDialogComponent,
        ModelBuilderDocumentPopupComponent,
        ModelBuilderDocumentDeleteDialogComponent,
        ModelBuilderDocumentDeletePopupComponent,
    ],
    providers: [
        ModelBuilderDocumentService,
        ModelBuilderDocumentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderModelBuilderDocumentModule {}
