import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ModelBuilderSharedModule } from '../../shared';

import {
    HeadingService,
    HeadingPopupService,
    HeadingComponent,
    HeadingDetailComponent,
    HeadingDialogComponent,
    HeadingPopupComponent,
    HeadingDeletePopupComponent,
    HeadingDeleteDialogComponent,
    headingRoute,
    headingPopupRoute,
} from './';

let ENTITY_STATES = [
    ...headingRoute,
    ...headingPopupRoute,
];

@NgModule({
    imports: [
        ModelBuilderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HeadingComponent,
        HeadingDetailComponent,
        HeadingDialogComponent,
        HeadingDeleteDialogComponent,
        HeadingPopupComponent,
        HeadingDeletePopupComponent,
    ],
    entryComponents: [
        HeadingComponent,
        HeadingDialogComponent,
        HeadingPopupComponent,
        HeadingDeleteDialogComponent,
        HeadingDeletePopupComponent,
    ],
    providers: [
        HeadingService,
        HeadingPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderHeadingModule {}
