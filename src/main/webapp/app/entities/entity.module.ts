import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { IskoJHipsterProductModule } from './product/product.module';
import { IskoJHipsterSupplierModule } from './supplier/supplier.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        IskoJHipsterProductModule,
        IskoJHipsterSupplierModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IskoJHipsterEntityModule {}
