import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EnquiryDetailsFormService, EnquiryDetailsFormGroup } from './enquiry-details-form.service';
import { IEnquiryDetails } from '../enquiry-details.model';
import { EnquiryDetailsService } from '../service/enquiry-details.service';
import { IState } from 'app/entities/state/state.model';
import { StateService } from 'app/entities/state/service/state.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { TalukaService } from 'app/entities/taluka/service/taluka.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';

@Component({
  selector: 'jhi-enquiry-details-update',
  templateUrl: './enquiry-details-update.component.html',
})
export class EnquiryDetailsUpdateComponent implements OnInit {
  isSaving = false;
  enquiryDetails: IEnquiryDetails | null = null;

  statesSharedCollection: IState[] = [];
  districtsSharedCollection: IDistrict[] = [];
  talukasSharedCollection: ITaluka[] = [];
  citiesSharedCollection: ICity[] = [];
  productsSharedCollection: IProduct[] = [];

  editForm: EnquiryDetailsFormGroup = this.enquiryDetailsFormService.createEnquiryDetailsFormGroup();

  constructor(
    protected enquiryDetailsService: EnquiryDetailsService,
    protected enquiryDetailsFormService: EnquiryDetailsFormService,
    protected stateService: StateService,
    protected districtService: DistrictService,
    protected talukaService: TalukaService,
    protected cityService: CityService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareState = (o1: IState | null, o2: IState | null): boolean => this.stateService.compareState(o1, o2);

  compareDistrict = (o1: IDistrict | null, o2: IDistrict | null): boolean => this.districtService.compareDistrict(o1, o2);

  compareTaluka = (o1: ITaluka | null, o2: ITaluka | null): boolean => this.talukaService.compareTaluka(o1, o2);

  compareCity = (o1: ICity | null, o2: ICity | null): boolean => this.cityService.compareCity(o1, o2);

  compareProduct = (o1: IProduct | null, o2: IProduct | null): boolean => this.productService.compareProduct(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enquiryDetails }) => {
      this.enquiryDetails = enquiryDetails;
      if (enquiryDetails) {
        this.updateForm(enquiryDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enquiryDetails = this.enquiryDetailsFormService.getEnquiryDetails(this.editForm);
    if (enquiryDetails.id !== null) {
      this.subscribeToSaveResponse(this.enquiryDetailsService.update(enquiryDetails));
    } else {
      this.subscribeToSaveResponse(this.enquiryDetailsService.create(enquiryDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnquiryDetails>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(enquiryDetails: IEnquiryDetails): void {
    this.enquiryDetails = enquiryDetails;
    this.enquiryDetailsFormService.resetForm(this.editForm, enquiryDetails);

    this.statesSharedCollection = this.stateService.addStateToCollectionIfMissing<IState>(
      this.statesSharedCollection,
      enquiryDetails.state
    );
    this.districtsSharedCollection = this.districtService.addDistrictToCollectionIfMissing<IDistrict>(
      this.districtsSharedCollection,
      enquiryDetails.district
    );
    this.talukasSharedCollection = this.talukaService.addTalukaToCollectionIfMissing<ITaluka>(
      this.talukasSharedCollection,
      enquiryDetails.taluka
    );
    this.citiesSharedCollection = this.cityService.addCityToCollectionIfMissing<ICity>(this.citiesSharedCollection, enquiryDetails.city);
    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing<IProduct>(
      this.productsSharedCollection,
      enquiryDetails.product
    );
  }

  protected loadRelationshipsOptions(): void {
    this.stateService
      .query()
      .pipe(map((res: HttpResponse<IState[]>) => res.body ?? []))
      .pipe(map((states: IState[]) => this.stateService.addStateToCollectionIfMissing<IState>(states, this.enquiryDetails?.state)))
      .subscribe((states: IState[]) => (this.statesSharedCollection = states));

    this.districtService
      .query()
      .pipe(map((res: HttpResponse<IDistrict[]>) => res.body ?? []))
      .pipe(
        map((districts: IDistrict[]) =>
          this.districtService.addDistrictToCollectionIfMissing<IDistrict>(districts, this.enquiryDetails?.district)
        )
      )
      .subscribe((districts: IDistrict[]) => (this.districtsSharedCollection = districts));

    this.talukaService
      .query()
      .pipe(map((res: HttpResponse<ITaluka[]>) => res.body ?? []))
      .pipe(map((talukas: ITaluka[]) => this.talukaService.addTalukaToCollectionIfMissing<ITaluka>(talukas, this.enquiryDetails?.taluka)))
      .subscribe((talukas: ITaluka[]) => (this.talukasSharedCollection = talukas));

    this.cityService
      .query()
      .pipe(map((res: HttpResponse<ICity[]>) => res.body ?? []))
      .pipe(map((cities: ICity[]) => this.cityService.addCityToCollectionIfMissing<ICity>(cities, this.enquiryDetails?.city)))
      .subscribe((cities: ICity[]) => (this.citiesSharedCollection = cities));

    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) => this.productService.addProductToCollectionIfMissing<IProduct>(products, this.enquiryDetails?.product))
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));
  }
}
