import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TransferComponent } from './transfer.component';
import { TransferDetailComponent } from './transfer-detail.component';
import { TransferPopupComponent } from './transfer-dialog.component';
import { TransferDeletePopupComponent } from './transfer-delete-dialog.component';

import { Principal } from '../../shared';


export const transferRoute: Routes = [
  {
    path: 'transfer',
    component: TransferComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Transfers'
    }
  }, {
    path: 'transfer/:id',
    component: TransferDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Transfers'
    }
  }
];

export const transferPopupRoute: Routes = [
  {
    path: 'transfer-new',
    component: TransferPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Transfers'
    },
    outlet: 'popup'
  },
  {
    path: 'transfer/:id/edit',
    component: TransferPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Transfers'
    },
    outlet: 'popup'
  },
  {
    path: 'transfer/:id/delete',
    component: TransferDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Transfers'
    },
    outlet: 'popup'
  }
];
