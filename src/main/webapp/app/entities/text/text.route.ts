import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TextComponent } from './text.component';
import { TextDetailComponent } from './text-detail.component';
import { TextPopupComponent } from './text-dialog.component';
import { TextDeletePopupComponent } from './text-delete-dialog.component';

import { Principal } from '../../shared';


export const textRoute: Routes = [
  {
    path: 'text',
    component: TextComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Texts'
    }
  }, {
    path: 'text/:id',
    component: TextDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Texts'
    }
  }
];

export const textPopupRoute: Routes = [
  {
    path: 'text-new',
    component: TextPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Texts'
    },
    outlet: 'popup'
  },
  {
    path: 'text/:id/edit',
    component: TextPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Texts'
    },
    outlet: 'popup'
  },
  {
    path: 'text/:id/delete',
    component: TextDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Texts'
    },
    outlet: 'popup'
  }
];
