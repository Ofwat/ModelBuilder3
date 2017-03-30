import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TransferConditionComponent } from './transfer-condition.component';
import { TransferConditionDetailComponent } from './transfer-condition-detail.component';
import { TransferConditionPopupComponent } from './transfer-condition-dialog.component';
import { TransferConditionDeletePopupComponent } from './transfer-condition-delete-dialog.component';

import { Principal } from '../../shared';


export const transferConditionRoute: Routes = [
  {
    path: 'transfer-condition',
    component: TransferConditionComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferConditions'
    }
  }, {
    path: 'transfer-condition/:id',
    component: TransferConditionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferConditions'
    }
  }
];

export const transferConditionPopupRoute: Routes = [
  {
    path: 'transfer-condition-new',
    component: TransferConditionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferConditions'
    },
    outlet: 'popup'
  },
  {
    path: 'transfer-condition/:id/edit',
    component: TransferConditionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferConditions'
    },
    outlet: 'popup'
  },
  {
    path: 'transfer-condition/:id/delete',
    component: TransferConditionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferConditions'
    },
    outlet: 'popup'
  }
];
