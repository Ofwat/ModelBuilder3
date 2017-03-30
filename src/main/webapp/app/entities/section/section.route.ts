import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SectionComponent } from './section.component';
import { SectionDetailComponent } from './section-detail.component';
import { SectionPopupComponent } from './section-dialog.component';
import { SectionDeletePopupComponent } from './section-delete-dialog.component';

import { Principal } from '../../shared';


export const sectionRoute: Routes = [
  {
    path: 'section',
    component: SectionComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Sections'
    }
  }, {
    path: 'section/:id',
    component: SectionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Sections'
    }
  }
];

export const sectionPopupRoute: Routes = [
  {
    path: 'section-new',
    component: SectionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Sections'
    },
    outlet: 'popup'
  },
  {
    path: 'section/:id/edit',
    component: SectionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Sections'
    },
    outlet: 'popup'
  },
  {
    path: 'section/:id/delete',
    component: SectionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Sections'
    },
    outlet: 'popup'
  }
];
