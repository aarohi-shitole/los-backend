import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIncomeDetails, NewIncomeDetails } from '../income-details.model';

export type PartialUpdateIncomeDetails = Partial<IIncomeDetails> & Pick<IIncomeDetails, 'id'>;

type RestOf<T extends IIncomeDetails | NewIncomeDetails> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestIncomeDetails = RestOf<IIncomeDetails>;

export type NewRestIncomeDetails = RestOf<NewIncomeDetails>;

export type PartialUpdateRestIncomeDetails = RestOf<PartialUpdateIncomeDetails>;

export type EntityResponseType = HttpResponse<IIncomeDetails>;
export type EntityArrayResponseType = HttpResponse<IIncomeDetails[]>;

@Injectable({ providedIn: 'root' })
export class IncomeDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/income-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(incomeDetails: NewIncomeDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(incomeDetails);
    return this.http
      .post<RestIncomeDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(incomeDetails: IIncomeDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(incomeDetails);
    return this.http
      .put<RestIncomeDetails>(`${this.resourceUrl}/${this.getIncomeDetailsIdentifier(incomeDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(incomeDetails: PartialUpdateIncomeDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(incomeDetails);
    return this.http
      .patch<RestIncomeDetails>(`${this.resourceUrl}/${this.getIncomeDetailsIdentifier(incomeDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestIncomeDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestIncomeDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIncomeDetailsIdentifier(incomeDetails: Pick<IIncomeDetails, 'id'>): number {
    return incomeDetails.id;
  }

  compareIncomeDetails(o1: Pick<IIncomeDetails, 'id'> | null, o2: Pick<IIncomeDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getIncomeDetailsIdentifier(o1) === this.getIncomeDetailsIdentifier(o2) : o1 === o2;
  }

  addIncomeDetailsToCollectionIfMissing<Type extends Pick<IIncomeDetails, 'id'>>(
    incomeDetailsCollection: Type[],
    ...incomeDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const incomeDetails: Type[] = incomeDetailsToCheck.filter(isPresent);
    if (incomeDetails.length > 0) {
      const incomeDetailsCollectionIdentifiers = incomeDetailsCollection.map(
        incomeDetailsItem => this.getIncomeDetailsIdentifier(incomeDetailsItem)!
      );
      const incomeDetailsToAdd = incomeDetails.filter(incomeDetailsItem => {
        const incomeDetailsIdentifier = this.getIncomeDetailsIdentifier(incomeDetailsItem);
        if (incomeDetailsCollectionIdentifiers.includes(incomeDetailsIdentifier)) {
          return false;
        }
        incomeDetailsCollectionIdentifiers.push(incomeDetailsIdentifier);
        return true;
      });
      return [...incomeDetailsToAdd, ...incomeDetailsCollection];
    }
    return incomeDetailsCollection;
  }

  protected convertDateFromClient<T extends IIncomeDetails | NewIncomeDetails | PartialUpdateIncomeDetails>(incomeDetails: T): RestOf<T> {
    return {
      ...incomeDetails,
      lastModified: incomeDetails.lastModified?.toJSON() ?? null,
      createdOn: incomeDetails.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restIncomeDetails: RestIncomeDetails): IIncomeDetails {
    return {
      ...restIncomeDetails,
      lastModified: restIncomeDetails.lastModified ? dayjs(restIncomeDetails.lastModified) : undefined,
      createdOn: restIncomeDetails.createdOn ? dayjs(restIncomeDetails.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestIncomeDetails>): HttpResponse<IIncomeDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestIncomeDetails[]>): HttpResponse<IIncomeDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
