import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PageDetailsComponent } from './page-details.component';
import { PageDetailsDetailComponent } from './page-details-detail.component';
import { PageDetailsPopupComponent } from './page-details-dialog.component';
import { PageDetailsDeletePopupComponent } from './page-details-delete-dialog.component';

import { Principal } from '../../shared';


export const pageDetailsRoute: Routes = [
  {
    path: 'page-details',
    component: PageDetailsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PageDetails'
    }
  }, {
    path: 'page-details/:id',
    component: PageDetailsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PageDetails'
    }
  }
];

export const pageDetailsPopupRoute: Routes = [
  {
    path: 'page-details-new',
    component: PageDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PageDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'page-details/:id/edit',
    component: PageDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PageDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'page-details/:id/delete',
    component: PageDetailsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PageDetails'
    },
    outlet: 'popup'
  }
];
