import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CompanyPageComponent } from './company-page.component';
import { CompanyPageDetailComponent } from './company-page-detail.component';
import { CompanyPagePopupComponent } from './company-page-dialog.component';
import { CompanyPageDeletePopupComponent } from './company-page-delete-dialog.component';

import { Principal } from '../../shared';


export const companyPageRoute: Routes = [
  {
    path: 'company-page',
    component: CompanyPageComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CompanyPages'
    }
  }, {
    path: 'company-page/:id',
    component: CompanyPageDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CompanyPages'
    }
  }
];

export const companyPagePopupRoute: Routes = [
  {
    path: 'company-page-new',
    component: CompanyPagePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CompanyPages'
    },
    outlet: 'popup'
  },
  {
    path: 'company-page/:id/edit',
    component: CompanyPagePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CompanyPages'
    },
    outlet: 'popup'
  },
  {
    path: 'company-page/:id/delete',
    component: CompanyPageDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CompanyPages'
    },
    outlet: 'popup'
  }
];
