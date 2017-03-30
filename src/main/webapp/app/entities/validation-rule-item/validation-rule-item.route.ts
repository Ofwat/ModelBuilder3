import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ValidationRuleItemComponent } from './validation-rule-item.component';
import { ValidationRuleItemDetailComponent } from './validation-rule-item-detail.component';
import { ValidationRuleItemPopupComponent } from './validation-rule-item-dialog.component';
import { ValidationRuleItemDeletePopupComponent } from './validation-rule-item-delete-dialog.component';

import { Principal } from '../../shared';


export const validationRuleItemRoute: Routes = [
  {
    path: 'validation-rule-item',
    component: ValidationRuleItemComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRuleItems'
    }
  }, {
    path: 'validation-rule-item/:id',
    component: ValidationRuleItemDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRuleItems'
    }
  }
];

export const validationRuleItemPopupRoute: Routes = [
  {
    path: 'validation-rule-item-new',
    component: ValidationRuleItemPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRuleItems'
    },
    outlet: 'popup'
  },
  {
    path: 'validation-rule-item/:id/edit',
    component: ValidationRuleItemPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRuleItems'
    },
    outlet: 'popup'
  },
  {
    path: 'validation-rule-item/:id/delete',
    component: ValidationRuleItemDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ValidationRuleItems'
    },
    outlet: 'popup'
  }
];
