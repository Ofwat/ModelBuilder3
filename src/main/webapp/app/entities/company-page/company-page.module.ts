import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    CompanyPageService,
    CompanyPagePopupService,
    CompanyPageComponent,
    CompanyPageDetailComponent,
    CompanyPageDialogComponent,
    CompanyPagePopupComponent,
    CompanyPageDeletePopupComponent,
    CompanyPageDeleteDialogComponent,
    companyPageRoute,
    companyPagePopupRoute,
} from './';

let ENTITY_STATES = [
    ...companyPageRoute,
    ...companyPagePopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompanyPageComponent,
        CompanyPageDetailComponent,
        CompanyPageDialogComponent,
        CompanyPageDeleteDialogComponent,
        CompanyPagePopupComponent,
        CompanyPageDeletePopupComponent,
    ],
    entryComponents: [
        CompanyPageComponent,
        CompanyPageDialogComponent,
        CompanyPagePopupComponent,
        CompanyPageDeleteDialogComponent,
        CompanyPageDeletePopupComponent,
    ],
    providers: [
        CompanyPageService,
        CompanyPagePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderCompanyPageModule {}
