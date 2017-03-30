import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FormDetailsComponent } from './form-details.component';
import { FormDetailsDetailComponent } from './form-details-detail.component';
import { FormDetailsPopupComponent } from './form-details-dialog.component';
import { FormDetailsDeletePopupComponent } from './form-details-delete-dialog.component';

import { Principal } from '../../shared';


export const formDetailsRoute: Routes = [
  {
    path: 'form-details',
    component: FormDetailsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormDetails'
    }
  }, {
    path: 'form-details/:id',
    component: FormDetailsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormDetails'
    }
  }
];

export const formDetailsPopupRoute: Routes = [
  {
    path: 'form-details-new',
    component: FormDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'form-details/:id/edit',
    component: FormDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'form-details/:id/delete',
    component: FormDetailsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormDetails'
    },
    outlet: 'popup'
  }
];
