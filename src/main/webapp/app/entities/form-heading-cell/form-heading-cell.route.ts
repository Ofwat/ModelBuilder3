import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FormHeadingCellComponent } from './form-heading-cell.component';
import { FormHeadingCellDetailComponent } from './form-heading-cell-detail.component';
import { FormHeadingCellPopupComponent } from './form-heading-cell-dialog.component';
import { FormHeadingCellDeletePopupComponent } from './form-heading-cell-delete-dialog.component';

import { Principal } from '../../shared';


export const formHeadingCellRoute: Routes = [
  {
    path: 'form-heading-cell',
    component: FormHeadingCellComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormHeadingCells'
    }
  }, {
    path: 'form-heading-cell/:id',
    component: FormHeadingCellDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormHeadingCells'
    }
  }
];

export const formHeadingCellPopupRoute: Routes = [
  {
    path: 'form-heading-cell-new',
    component: FormHeadingCellPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormHeadingCells'
    },
    outlet: 'popup'
  },
  {
    path: 'form-heading-cell/:id/edit',
    component: FormHeadingCellPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormHeadingCells'
    },
    outlet: 'popup'
  },
  {
    path: 'form-heading-cell/:id/delete',
    component: FormHeadingCellDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FormHeadingCells'
    },
    outlet: 'popup'
  }
];
