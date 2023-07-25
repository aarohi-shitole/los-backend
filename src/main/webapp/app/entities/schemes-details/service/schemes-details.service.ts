import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISchemesDetails, NewSchemesDetails } from '../schemes-details.model';

export type PartialUpdateSchemesDetails = Partial<ISchemesDetails> & Pick<ISchemesDetails, 'id'>;

type RestOf<T extends ISchemesDetails | NewSchemesDetails> = Omit<T, 'lastDayOfScheam' | 'lastModified' | 'createdOn'> & {
  lastDayOfScheam?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestSchemesDetails = RestOf<ISchemesDetails>;

export type NewRestSchemesDetails = RestOf<NewSchemesDetails>;

export type PartialUpdateRestSchemesDetails = RestOf<PartialUpdateSchemesDetails>;

export type EntityResponseType = HttpResponse<ISchemesDetails>;
export type EntityArrayResponseType = HttpResponse<ISchemesDetails[]>;

@Injectable({ providedIn: 'root' })
export class SchemesDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/schemes-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(schemesDetails: NewSchemesDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(schemesDetails);
    return this.http
      .post<RestSchemesDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(schemesDetails: ISchemesDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(schemesDetails);
    return this.http
      .put<RestSchemesDetails>(`${this.resourceUrl}/${this.getSchemesDetailsIdentifier(schemesDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(schemesDetails: PartialUpdateSchemesDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(schemesDetails);
    return this.http
      .patch<RestSchemesDetails>(`${this.resourceUrl}/${this.getSchemesDetailsIdentifier(schemesDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSchemesDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSchemesDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSchemesDetailsIdentifier(schemesDetails: Pick<ISchemesDetails, 'id'>): number {
    return schemesDetails.id;
  }

  compareSchemesDetails(o1: Pick<ISchemesDetails, 'id'> | null, o2: Pick<ISchemesDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getSchemesDetailsIdentifier(o1) === this.getSchemesDetailsIdentifier(o2) : o1 === o2;
  }

  addSchemesDetailsToCollectionIfMissing<Type extends Pick<ISchemesDetails, 'id'>>(
    schemesDetailsCollection: Type[],
    ...schemesDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const schemesDetails: Type[] = schemesDetailsToCheck.filter(isPresent);
    if (schemesDetails.length > 0) {
      const schemesDetailsCollectionIdentifiers = schemesDetailsCollection.map(
        schemesDetailsItem => this.getSchemesDetailsIdentifier(schemesDetailsItem)!
      );
      const schemesDetailsToAdd = schemesDetails.filter(schemesDetailsItem => {
        const schemesDetailsIdentifier = this.getSchemesDetailsIdentifier(schemesDetailsItem);
        if (schemesDetailsCollectionIdentifiers.includes(schemesDetailsIdentifier)) {
          return false;
        }
        schemesDetailsCollectionIdentifiers.push(schemesDetailsIdentifier);
        return true;
      });
      return [...schemesDetailsToAdd, ...schemesDetailsCollection];
    }
    return schemesDetailsCollection;
  }

  protected convertDateFromClient<T extends ISchemesDetails | NewSchemesDetails | PartialUpdateSchemesDetails>(
    schemesDetails: T
  ): RestOf<T> {
    return {
      ...schemesDetails,
      lastDayOfScheam: schemesDetails.lastDayOfScheam?.toJSON() ?? null,
      lastModified: schemesDetails.lastModified?.toJSON() ?? null,
      createdOn: schemesDetails.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSchemesDetails: RestSchemesDetails): ISchemesDetails {
    return {
      ...restSchemesDetails,
      lastDayOfScheam: restSchemesDetails.lastDayOfScheam ? dayjs(restSchemesDetails.lastDayOfScheam) : undefined,
      lastModified: restSchemesDetails.lastModified ? dayjs(restSchemesDetails.lastModified) : undefined,
      createdOn: restSchemesDetails.createdOn ? dayjs(restSchemesDetails.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSchemesDetails>): HttpResponse<ISchemesDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSchemesDetails[]>): HttpResponse<ISchemesDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
