import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ModelAuditComponent } from './model-audit.component';
import { ModelAuditDetailComponent } from './model-audit-detail.component';
import { ModelAuditPopupComponent } from './model-audit-dialog.component';
import { ModelAuditDeletePopupComponent } from './model-audit-delete-dialog.component';

import { Principal } from '../../shared';


export const modelAuditRoute: Routes = [
  {
    path: 'model-audit',
    component: ModelAuditComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelAudits'
    }
  }, {
    path: 'model-audit/:id',
    component: ModelAuditDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelAudits'
    }
  }
];

export const modelAuditPopupRoute: Routes = [
  {
    path: 'model-audit-new',
    component: ModelAuditPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelAudits'
    },
    outlet: 'popup'
  },
  {
    path: 'model-audit/:id/edit',
    component: ModelAuditPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelAudits'
    },
    outlet: 'popup'
  },
  {
    path: 'model-audit/:id/delete',
    component: ModelAuditDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelAudits'
    },
    outlet: 'popup'
  }
];
