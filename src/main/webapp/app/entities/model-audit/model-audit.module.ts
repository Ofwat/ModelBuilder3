import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    ModelAuditService,
    ModelAuditPopupService,
    ModelAuditComponent,
    ModelAuditDetailComponent,
    ModelAuditDialogComponent,
    ModelAuditPopupComponent,
    ModelAuditDeletePopupComponent,
    ModelAuditDeleteDialogComponent,
    modelAuditRoute,
    modelAuditPopupRoute,
} from './';

let ENTITY_STATES = [
    ...modelAuditRoute,
    ...modelAuditPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ModelAuditComponent,
        ModelAuditDetailComponent,
        ModelAuditDialogComponent,
        ModelAuditDeleteDialogComponent,
        ModelAuditPopupComponent,
        ModelAuditDeletePopupComponent,
    ],
    entryComponents: [
        ModelAuditComponent,
        ModelAuditDialogComponent,
        ModelAuditPopupComponent,
        ModelAuditDeleteDialogComponent,
        ModelAuditDeletePopupComponent,
    ],
    providers: [
        ModelAuditService,
        ModelAuditPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderModelAuditModule {}
