import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TransferBlockDetailsComponent } from './transfer-block-details.component';
import { TransferBlockDetailsDetailComponent } from './transfer-block-details-detail.component';
import { TransferBlockDetailsPopupComponent } from './transfer-block-details-dialog.component';
import { TransferBlockDetailsDeletePopupComponent } from './transfer-block-details-delete-dialog.component';

import { Principal } from '../../shared';


export const transferBlockDetailsRoute: Routes = [
  {
    path: 'transfer-block-details',
    component: TransferBlockDetailsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlockDetails'
    }
  }, {
    path: 'transfer-block-details/:id',
    component: TransferBlockDetailsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlockDetails'
    }
  }
];

export const transferBlockDetailsPopupRoute: Routes = [
  {
    path: 'transfer-block-details-new',
    component: TransferBlockDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlockDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'transfer-block-details/:id/edit',
    component: TransferBlockDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlockDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'transfer-block-details/:id/delete',
    component: TransferBlockDetailsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlockDetails'
    },
    outlet: 'popup'
  }
];
