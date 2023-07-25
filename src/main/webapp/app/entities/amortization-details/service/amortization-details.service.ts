import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAmortizationDetails, NewAmortizationDetails } from '../amortization-details.model';

export type PartialUpdateAmortizationDetails = Partial<IAmortizationDetails> & Pick<IAmortizationDetails, 'id'>;

type RestOf<T extends IAmortizationDetails | NewAmortizationDetails> = Omit<T, 'installmentDate' | 'lastModified'> & {
  installmentDate?: string | null;
  lastModified?: string | null;
};

export type RestAmortizationDetails = RestOf<IAmortizationDetails>;

export type NewRestAmortizationDetails = RestOf<NewAmortizationDetails>;

export type PartialUpdateRestAmortizationDetails = RestOf<PartialUpdateAmortizationDetails>;

export type EntityResponseType = HttpResponse<IAmortizationDetails>;
export type EntityArrayResponseType = HttpResponse<IAmortizationDetails[]>;

@Injectable({ providedIn: 'root' })
export class AmortizationDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/amortization-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(amortizationDetails: NewAmortizationDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationDetails);
    return this.http
      .post<RestAmortizationDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(amortizationDetails: IAmortizationDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationDetails);
    return this.http
      .put<RestAmortizationDetails>(`${this.resourceUrl}/${this.getAmortizationDetailsIdentifier(amortizationDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(amortizationDetails: PartialUpdateAmortizationDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationDetails);
    return this.http
      .patch<RestAmortizationDetails>(`${this.resourceUrl}/${this.getAmortizationDetailsIdentifier(amortizationDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAmortizationDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAmortizationDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAmortizationDetailsIdentifier(amortizationDetails: Pick<IAmortizationDetails, 'id'>): number {
    return amortizationDetails.id;
  }

  compareAmortizationDetails(o1: Pick<IAmortizationDetails, 'id'> | null, o2: Pick<IAmortizationDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getAmortizationDetailsIdentifier(o1) === this.getAmortizationDetailsIdentifier(o2) : o1 === o2;
  }

  addAmortizationDetailsToCollectionIfMissing<Type extends Pick<IAmortizationDetails, 'id'>>(
    amortizationDetailsCollection: Type[],
    ...amortizationDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const amortizationDetails: Type[] = amortizationDetailsToCheck.filter(isPresent);
    if (amortizationDetails.length > 0) {
      const amortizationDetailsCollectionIdentifiers = amortizationDetailsCollection.map(
        amortizationDetailsItem => this.getAmortizationDetailsIdentifier(amortizationDetailsItem)!
      );
      const amortizationDetailsToAdd = amortizationDetails.filter(amortizationDetailsItem => {
        const amortizationDetailsIdentifier = this.getAmortizationDetailsIdentifier(amortizationDetailsItem);
        if (amortizationDetailsCollectionIdentifiers.includes(amortizationDetailsIdentifier)) {
          return false;
        }
        amortizationDetailsCollectionIdentifiers.push(amortizationDetailsIdentifier);
        return true;
      });
      return [...amortizationDetailsToAdd, ...amortizationDetailsCollection];
    }
    return amortizationDetailsCollection;
  }

  protected convertDateFromClient<T extends IAmortizationDetails | NewAmortizationDetails | PartialUpdateAmortizationDetails>(
    amortizationDetails: T
  ): RestOf<T> {
    return {
      ...amortizationDetails,
      installmentDate: amortizationDetails.installmentDate?.toJSON() ?? null,
      lastModified: amortizationDetails.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAmortizationDetails: RestAmortizationDetails): IAmortizationDetails {
    return {
      ...restAmortizationDetails,
      installmentDate: restAmortizationDetails.installmentDate ? dayjs(restAmortizationDetails.installmentDate) : undefined,
      lastModified: restAmortizationDetails.lastModified ? dayjs(restAmortizationDetails.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAmortizationDetails>): HttpResponse<IAmortizationDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAmortizationDetails[]>): HttpResponse<IAmortizationDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
