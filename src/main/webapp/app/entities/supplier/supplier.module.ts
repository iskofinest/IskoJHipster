import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IskoJHipsterSharedModule } from '../../shared';
import {
    SupplierService,
    SupplierPopupService,
    SupplierComponent,
    SupplierDetailComponent,
    SupplierDialogComponent,
    SupplierPopupComponent,
    SupplierDeletePopupComponent,
    SupplierDeleteDialogComponent,
    supplierRoute,
    supplierPopupRoute,
    SupplierResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...supplierRoute,
    ...supplierPopupRoute,
];

@NgModule({
    imports: [
        IskoJHipsterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SupplierComponent,
        SupplierDetailComponent,
        SupplierDialogComponent,
        SupplierDeleteDialogComponent,
        SupplierPopupComponent,
        SupplierDeletePopupComponent,
    ],
    entryComponents: [
        SupplierComponent,
        SupplierDialogComponent,
        SupplierPopupComponent,
        SupplierDeleteDialogComponent,
        SupplierDeletePopupComponent,
    ],
    providers: [
        SupplierService,
        SupplierPopupService,
        SupplierResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IskoJHipsterSupplierModule {}
