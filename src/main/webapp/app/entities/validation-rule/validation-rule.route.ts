import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ValidationRuleComponent } from './validation-rule.component';
import { ValidationRuleDetailComponent } from './validation-rule-detail.component';
import { ValidationRulePopupComponent } from './validation-rule-dialog.component';
import { ValidationRuleDeletePopupComponent } from './validation-rule-delete-dialog.component';

import { Principal } from '../../shared';


export const validationRuleRoute: Routes = [
  {
    path: 'validation-rule',
    component: ValidationRuleComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRules'
    }
  }, {
    path: 'validation-rule/:id',
    component: ValidationRuleDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRules'
    }
  }
];

export const validationRulePopupRoute: Routes = [
  {
    path: 'validation-rule-new',
    component: ValidationRulePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRules'
    },
    outlet: 'popup'
  },
  {
    path: 'validation-rule/:id/edit',
    component: ValidationRulePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRules'
    },
    outlet: 'popup'
  },
  {
    path: 'validation-rule/:id/delete',
    component: ValidationRuleDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRules'
    },
    outlet: 'popup'
  }
];
