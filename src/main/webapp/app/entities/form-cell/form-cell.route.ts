import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FormCellComponent } from './form-cell.component';
import { FormCellDetailComponent } from './form-cell-detail.component';
import { FormCellPopupComponent } from './form-cell-dialog.component';
import { FormCellDeletePopupComponent } from './form-cell-delete-dialog.component';

import { Principal } from '../../shared';


export const formCellRoute: Routes = [
  {
    path: 'form-cell',
    component: FormCellComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormCells'
    }
  }, {
    path: 'form-cell/:id',
    component: FormCellDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormCells'
    }
  }
];

export const formCellPopupRoute: Routes = [
  {
    path: 'form-cell-new',
    component: FormCellPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormCells'
    },
    outlet: 'popup'
  },
  {
    path: 'form-cell/:id/edit',
    component: FormCellPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormCells'
    },
    outlet: 'popup'
  },
  {
    path: 'form-cell/:id/delete',
    component: FormCellDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormCells'
    },
    outlet: 'popup'
  }
];
