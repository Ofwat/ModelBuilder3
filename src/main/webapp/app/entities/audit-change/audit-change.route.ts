import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AuditChangeComponent } from './audit-change.component';
import { AuditChangeDetailComponent } from './audit-change-detail.component';
import { AuditChangePopupComponent } from './audit-change-dialog.component';
import { AuditChangeDeletePopupComponent } from './audit-change-delete-dialog.component';

import { Principal } from '../../shared';


export const auditChangeRoute: Routes = [
  {
    path: 'audit-change',
    component: AuditChangeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AuditChanges'
    }
  }, {
    path: 'audit-change/:id',
    component: AuditChangeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AuditChanges'
    }
  }
];

export const auditChangePopupRoute: Routes = [
  {
    path: 'audit-change-new',
    component: AuditChangePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AuditChanges'
    },
    outlet: 'popup'
  },
  {
    path: 'audit-change/:id/edit',
    component: AuditChangePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AuditChanges'
    },
    outlet: 'popup'
  },
  {
    path: 'audit-change/:id/delete',
    component: AuditChangeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AuditChanges'
    },
    outlet: 'popup'
  }
];
