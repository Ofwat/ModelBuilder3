import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FormComponent } from './form.component';
import { FormDetailComponent } from './form-detail.component';
import { FormPopupComponent } from './form-dialog.component';
import { FormDeletePopupComponent } from './form-delete-dialog.component';

import { Principal } from '../../shared';


export const formRoute: Routes = [
  {
    path: 'form',
    component: FormComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Forms'
    }
  }, {
    path: 'form/:id',
    component: FormDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Forms'
    }
  }
];

export const formPopupRoute: Routes = [
  {
    path: 'form-new',
    component: FormPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Forms'
    },
    outlet: 'popup'
  },
  {
    path: 'form/:id/edit',
    component: FormPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Forms'
    },
    outlet: 'popup'
  },
  {
    path: 'form/:id/delete',
    component: FormDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Forms'
    },
    outlet: 'popup'
  }
];
