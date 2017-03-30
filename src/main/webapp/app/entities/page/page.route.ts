import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PageComponent } from './page.component';
import { PageDetailComponent } from './page-detail.component';
import { PagePopupComponent } from './page-dialog.component';
import { PageDeletePopupComponent } from './page-delete-dialog.component';

import { Principal } from '../../shared';


export const pageRoute: Routes = [
  {
    path: 'page',
    component: PageComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pages'
    }
  }, {
    path: 'page/:id',
    component: PageDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pages'
    }
  }
];

export const pagePopupRoute: Routes = [
  {
    path: 'page-new',
    component: PagePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pages'
    },
    outlet: 'popup'
  },
  {
    path: 'page/:id/edit',
    component: PagePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pages'
    },
    outlet: 'popup'
  },
  {
    path: 'page/:id/delete',
    component: PageDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pages'
    },
    outlet: 'popup'
  }
];
