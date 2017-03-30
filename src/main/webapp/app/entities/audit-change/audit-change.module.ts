import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    AuditChangeService,
    AuditChangePopupService,
    AuditChangeComponent,
    AuditChangeDetailComponent,
    AuditChangeDialogComponent,
    AuditChangePopupComponent,
    AuditChangeDeletePopupComponent,
    AuditChangeDeleteDialogComponent,
    auditChangeRoute,
    auditChangePopupRoute,
} from './';

let ENTITY_STATES = [
    ...auditChangeRoute,
    ...auditChangePopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuditChangeComponent,
        AuditChangeDetailComponent,
        AuditChangeDialogComponent,
        AuditChangeDeleteDialogComponent,
        AuditChangePopupComponent,
        AuditChangeDeletePopupComponent,
    ],
    entryComponents: [
        AuditChangeComponent,
        AuditChangeDialogComponent,
        AuditChangePopupComponent,
        AuditChangeDeleteDialogComponent,
        AuditChangeDeletePopupComponent,
    ],
    providers: [
        AuditChangeService,
        AuditChangePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderAuditChangeModule {}
