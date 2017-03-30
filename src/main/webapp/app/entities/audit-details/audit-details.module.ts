import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    AuditDetailsService,
    AuditDetailsPopupService,
    AuditDetailsComponent,
    AuditDetailsDetailComponent,
    AuditDetailsDialogComponent,
    AuditDetailsPopupComponent,
    AuditDetailsDeletePopupComponent,
    AuditDetailsDeleteDialogComponent,
    auditDetailsRoute,
    auditDetailsPopupRoute,
} from './';

let ENTITY_STATES = [
    ...auditDetailsRoute,
    ...auditDetailsPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuditDetailsComponent,
        AuditDetailsDetailComponent,
        AuditDetailsDialogComponent,
        AuditDetailsDeleteDialogComponent,
        AuditDetailsPopupComponent,
        AuditDetailsDeletePopupComponent,
    ],
    entryComponents: [
        AuditDetailsComponent,
        AuditDetailsDialogComponent,
        AuditDetailsPopupComponent,
        AuditDetailsDeleteDialogComponent,
        AuditDetailsDeletePopupComponent,
    ],
    providers: [
        AuditDetailsService,
        AuditDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderAuditDetailsModule {}
