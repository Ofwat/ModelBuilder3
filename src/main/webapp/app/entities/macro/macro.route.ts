import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MacroComponent } from './macro.component';
import { MacroDetailComponent } from './macro-detail.component';
import { MacroPopupComponent } from './macro-dialog.component';
import { MacroDeletePopupComponent } from './macro-delete-dialog.component';

import { Principal } from '../../shared';


export const macroRoute: Routes = [
  {
    path: 'macro',
    component: MacroComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Macros'
    }
  }, {
    path: 'macro/:id',
    component: MacroDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Macros'
    }
  }
];

export const macroPopupRoute: Routes = [
  {
    path: 'macro-new',
    component: MacroPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Macros'
    },
    outlet: 'popup'
  },
  {
    path: 'macro/:id/edit',
    component: MacroPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Macros'
    },
    outlet: 'popup'
  },
  {
    path: 'macro/:id/delete',
    component: MacroDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Macros'
    },
    outlet: 'popup'
  }
];
