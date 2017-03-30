import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TransferBlockComponent } from './transfer-block.component';
import { TransferBlockDetailComponent } from './transfer-block-detail.component';
import { TransferBlockPopupComponent } from './transfer-block-dialog.component';
import { TransferBlockDeletePopupComponent } from './transfer-block-delete-dialog.component';

import { Principal } from '../../shared';


export const transferBlockRoute: Routes = [
  {
    path: 'transfer-block',
    component: TransferBlockComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlocks'
    }
  }, {
    path: 'transfer-block/:id',
    component: TransferBlockDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlocks'
    }
  }
];

export const transferBlockPopupRoute: Routes = [
  {
    path: 'transfer-block-new',
    component: TransferBlockPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlocks'
    },
    outlet: 'popup'
  },
  {
    path: 'transfer-block/:id/edit',
    component: TransferBlockPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlocks'
    },
    outlet: 'popup'
  },
  {
    path: 'transfer-block/:id/delete',
    component: TransferBlockDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TransferBlocks'
    },
    outlet: 'popup'
  }
];
