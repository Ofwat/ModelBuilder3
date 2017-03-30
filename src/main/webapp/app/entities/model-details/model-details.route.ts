import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ModelDetailsComponent } from './model-details.component';
import { ModelDetailsDetailComponent } from './model-details-detail.component';
import { ModelDetailsPopupComponent } from './model-details-dialog.component';
import { ModelDetailsDeletePopupComponent } from './model-details-delete-dialog.component';

import { Principal } from '../../shared';


export const modelDetailsRoute: Routes = [
  {
    path: 'model-details',
    component: ModelDetailsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelDetails'
    }
  }, {
    path: 'model-details/:id',
    component: ModelDetailsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelDetails'
    }
  }
];

export const modelDetailsPopupRoute: Routes = [
  {
    path: 'model-details-new',
    component: ModelDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'model-details/:id/edit',
    component: ModelDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'model-details/:id/delete',
    component: ModelDetailsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelDetails'
    },
    outlet: 'popup'
  }
];
