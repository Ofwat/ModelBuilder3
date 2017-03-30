import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ValidationRuleDetailsComponent } from './validation-rule-details.component';
import { ValidationRuleDetailsDetailComponent } from './validation-rule-details-detail.component';
import { ValidationRuleDetailsPopupComponent } from './validation-rule-details-dialog.component';
import { ValidationRuleDetailsDeletePopupComponent } from './validation-rule-details-delete-dialog.component';

import { Principal } from '../../shared';


export const validationRuleDetailsRoute: Routes = [
  {
    path: 'validation-rule-details',
    component: ValidationRuleDetailsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRuleDetails'
    }
  }, {
    path: 'validation-rule-details/:id',
    component: ValidationRuleDetailsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRuleDetails'
    }
  }
];

export const validationRuleDetailsPopupRoute: Routes = [
  {
    path: 'validation-rule-details-new',
    component: ValidationRuleDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRuleDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'validation-rule-details/:id/edit',
    component: ValidationRuleDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRuleDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'validation-rule-details/:id/delete',
    component: ValidationRuleDetailsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRuleDetails'
    },
    outlet: 'popup'
  }
];
