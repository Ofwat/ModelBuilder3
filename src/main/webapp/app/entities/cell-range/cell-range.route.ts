import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CellRangeComponent } from './cell-range.component';
import { CellRangeDetailComponent } from './cell-range-detail.component';
import { CellRangePopupComponent } from './cell-range-dialog.component';
import { CellRangeDeletePopupComponent } from './cell-range-delete-dialog.component';

import { Principal } from '../../shared';


export const cellRangeRoute: Routes = [
  {
    path: 'cell-range',
    component: CellRangeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CellRanges'
    }
  }, {
    path: 'cell-range/:id',
    component: CellRangeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CellRanges'
    }
  }
];

export const cellRangePopupRoute: Routes = [
  {
    path: 'cell-range-new',
    component: CellRangePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CellRanges'
    },
    outlet: 'popup'
  },
  {
    path: 'cell-range/:id/edit',
    component: CellRangePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CellRanges'
    },
    outlet: 'popup'
  },
  {
    path: 'cell-range/:id/delete',
    component: CellRangeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CellRanges'
    },
    outlet: 'popup'
  }
];
