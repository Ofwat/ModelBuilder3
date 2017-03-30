import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { YearCodeComponent } from './year-code.component';
import { YearCodeDetailComponent } from './year-code-detail.component';
import { YearCodePopupComponent } from './year-code-dialog.component';
import { YearCodeDeletePopupComponent } from './year-code-delete-dialog.component';

import { Principal } from '../../shared';


export const yearCodeRoute: Routes = [
  {
    path: 'year-code',
    component: YearCodeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'YearCodes'
    }
  }, {
    path: 'year-code/:id',
    component: YearCodeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'YearCodes'
    }
  }
];

export const yearCodePopupRoute: Routes = [
  {
    path: 'year-code-new',
    component: YearCodePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'YearCodes'
    },
    outlet: 'popup'
  },
  {
    path: 'year-code/:id/edit',
    component: YearCodePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'YearCodes'
    },
    outlet: 'popup'
  },
  {
    path: 'year-code/:id/delete',
    component: YearCodeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'YearCodes'
    },
    outlet: 'popup'
  }
];
