import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { InputComponent } from './input.component';
import { InputDetailComponent } from './input-detail.component';
import { InputPopupComponent } from './input-dialog.component';
import { InputDeletePopupComponent } from './input-delete-dialog.component';

import { Principal } from '../../shared';


export const inputRoute: Routes = [
  {
    path: 'input',
    component: InputComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Inputs'
    }
  }, {
    path: 'input/:id',
    component: InputDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Inputs'
    }
  }
];

export const inputPopupRoute: Routes = [
  {
    path: 'input-new',
    component: InputPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Inputs'
    },
    outlet: 'popup'
  },
  {
    path: 'input/:id/edit',
    component: InputPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Inputs'
    },
    outlet: 'popup'
  },
  {
    path: 'input/:id/delete',
    component: InputDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Inputs'
    },
    outlet: 'popup'
  }
];
