/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IskoJHipsterTestModule } from '../../../test.module';
import { SupplierComponent } from '../../../../../../main/webapp/app/entities/supplier/supplier.component';
import { SupplierService } from '../../../../../../main/webapp/app/entities/supplier/supplier.service';
import { Supplier } from '../../../../../../main/webapp/app/entities/supplier/supplier.model';

describe('Component Tests', () => {

    describe('Supplier Management Component', () => {
        let comp: SupplierComponent;
        let fixture: ComponentFixture<SupplierComponent>;
        let service: SupplierService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IskoJHipsterTestModule],
                declarations: [SupplierComponent],
                providers: [
                    SupplierService
                ]
            })
            .overrideTemplate(SupplierComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SupplierComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplierService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Supplier(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.suppliers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
