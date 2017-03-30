import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CellComponent } from './cell.component';
import { CellDetailComponent } from './cell-detail.component';
import { CellPopupComponent } from './cell-dialog.component';
import { CellDeletePopupComponent } from './cell-delete-dialog.component';

import { Principal } from '../../shared';


export const cellRoute: Routes = [
  {
    path: 'cell',
    component: CellComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Cells'
    }
  }, {
    path: 'cell/:id',
    component: CellDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Cells'
    }
  }
];

export const cellPopupRoute: Routes = [
  {
    path: 'cell-new',
    component: CellPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Cells'
    },
    outlet: 'popup'
  },
  {
    path: 'cell/:id/edit',
    component: CellPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Cells'
    },
    outlet: 'popup'
  },
  {
    path: 'cell/:id/delete',
    component: CellDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Cells'
    },
    outlet: 'popup'
  }
];
