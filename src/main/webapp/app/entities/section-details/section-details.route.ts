import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SectionDetailsComponent } from './section-details.component';
import { SectionDetailsDetailComponent } from './section-details-detail.component';
import { SectionDetailsPopupComponent } from './section-details-dialog.component';
import { SectionDetailsDeletePopupComponent } from './section-details-delete-dialog.component';

import { Principal } from '../../shared';


export const sectionDetailsRoute: Routes = [
  {
    path: 'section-details',
    component: SectionDetailsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'SectionDetails'
    }
  }, {
    path: 'section-details/:id',
    component: SectionDetailsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'SectionDetails'
    }
  }
];

export const sectionDetailsPopupRoute: Routes = [
  {
    path: 'section-details-new',
    component: SectionDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'SectionDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'section-details/:id/edit',
    component: SectionDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'SectionDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'section-details/:id/delete',
    component: SectionDetailsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'SectionDetails'
    },
    outlet: 'popup'
  }
];
