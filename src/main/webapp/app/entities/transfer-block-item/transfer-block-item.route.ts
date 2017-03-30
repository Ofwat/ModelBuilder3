import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TransferBlockItemComponent } from './transfer-block-item.component';
import { TransferBlockItemDetailComponent } from './transfer-block-item-detail.component';
import { TransferBlockItemPopupComponent } from './transfer-block-item-dialog.component';
import { TransferBlockItemDeletePopupComponent } from './transfer-block-item-delete-dialog.component';

import { Principal } from '../../shared';


export const transferBlockItemRoute: Routes = [
  {
    path: 'transfer-block-item',
    component: TransferBlockItemComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlockItems'
    }
  }, {
    path: 'transfer-block-item/:id',
    component: TransferBlockItemDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlockItems'
    }
  }
];

export const transferBlockItemPopupRoute: Routes = [
  {
    path: 'transfer-block-item-new',
    component: TransferBlockItemPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlockItems'
    },
    outlet: 'popup'
  },
  {
    path: 'transfer-block-item/:id/edit',
    component: TransferBlockItemPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlockItems'
    },
    outlet: 'popup'
  },
  {
    path: 'transfer-block-item/:id/delete',
    component: TransferBlockItemDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlockItems'
    },
    outlet: 'popup'
  }
];
