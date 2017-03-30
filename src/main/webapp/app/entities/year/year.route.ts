import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { YearComponent } from './year.component';
import { YearDetailComponent } from './year-detail.component';
import { YearPopupComponent } from './year-dialog.component';
import { YearDeletePopupComponent } from './year-delete-dialog.component';

import { Principal } from '../../shared';


export const yearRoute: Routes = [
  {
    path: 'year',
    component: YearComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Years'
    }
  }, {
    path: 'year/:id',
    component: YearDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Years'
    }
  }
];

export const yearPopupRoute: Routes = [
  {
    path: 'year-new',
    component: YearPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Years'
    },
    outlet: 'popup'
  },
  {
    path: 'year/:id/edit',
    component: YearPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Years'
    },
    outlet: 'popup'
  },
  {
    path: 'year/:id/delete',
    component: YearDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Years'
    },
    outlet: 'popup'
  }
];
