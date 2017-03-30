import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { LineDetailsComponent } from './line-details.component';
import { LineDetailsDetailComponent } from './line-details-detail.component';
import { LineDetailsPopupComponent } from './line-details-dialog.component';
import { LineDetailsDeletePopupComponent } from './line-details-delete-dialog.component';

import { Principal } from '../../shared';


export const lineDetailsRoute: Routes = [
  {
    path: 'line-details',
    component: LineDetailsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'LineDetails'
    }
  }, {
    path: 'line-details/:id',
    component: LineDetailsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'LineDetails'
    }
  }
];

export const lineDetailsPopupRoute: Routes = [
  {
    path: 'line-details-new',
    component: LineDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'LineDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'line-details/:id/edit',
    component: LineDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'LineDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'line-details/:id/delete',
    component: LineDetailsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'LineDetails'
    },
    outlet: 'popup'
  }
];
