import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmployementDetails, NewEmployementDetails } from '../employement-details.model';

export type PartialUpdateEmployementDetails = Partial<IEmployementDetails> & Pick<IEmployementDetails, 'id'>;

type RestOf<T extends IEmployementDetails | NewEmployementDetails> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestEmployementDetails = RestOf<IEmployementDetails>;

export type NewRestEmployementDetails = RestOf<NewEmployementDetails>;

export type PartialUpdateRestEmployementDetails = RestOf<PartialUpdateEmployementDetails>;

export type EntityResponseType = HttpResponse<IEmployementDetails>;
export type EntityArrayResponseType = HttpResponse<IEmployementDetails[]>;

@Injectable({ providedIn: 'root' })
export class EmployementDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/employement-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(employementDetails: NewEmployementDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employementDetails);
    return this.http
      .post<RestEmployementDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(employementDetails: IEmployementDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employementDetails);
    return this.http
      .put<RestEmployementDetails>(`${this.resourceUrl}/${this.getEmployementDetailsIdentifier(employementDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(employementDetails: PartialUpdateEmployementDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employementDetails);
    return this.http
      .patch<RestEmployementDetails>(`${this.resourceUrl}/${this.getEmployementDetailsIdentifier(employementDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEmployementDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEmployementDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmployementDetailsIdentifier(employementDetails: Pick<IEmployementDetails, 'id'>): number {
    return employementDetails.id;
  }

  compareEmployementDetails(o1: Pick<IEmployementDetails, 'id'> | null, o2: Pick<IEmployementDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmployementDetailsIdentifier(o1) === this.getEmployementDetailsIdentifier(o2) : o1 === o2;
  }

  addEmployementDetailsToCollectionIfMissing<Type extends Pick<IEmployementDetails, 'id'>>(
    employementDetailsCollection: Type[],
    ...employementDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const employementDetails: Type[] = employementDetailsToCheck.filter(isPresent);
    if (employementDetails.length > 0) {
      const employementDetailsCollectionIdentifiers = employementDetailsCollection.map(
        employementDetailsItem => this.getEmployementDetailsIdentifier(employementDetailsItem)!
      );
      const employementDetailsToAdd = employementDetails.filter(employementDetailsItem => {
        const employementDetailsIdentifier = this.getEmployementDetailsIdentifier(employementDetailsItem);
        if (employementDetailsCollectionIdentifiers.includes(employementDetailsIdentifier)) {
          return false;
        }
        employementDetailsCollectionIdentifiers.push(employementDetailsIdentifier);
        return true;
      });
      return [...employementDetailsToAdd, ...employementDetailsCollection];
    }
    return employementDetailsCollection;
  }

  protected convertDateFromClient<T extends IEmployementDetails | NewEmployementDetails | PartialUpdateEmployementDetails>(
    employementDetails: T
  ): RestOf<T> {
    return {
      ...employementDetails,
      lastModified: employementDetails.lastModified?.toJSON() ?? null,
      createdOn: employementDetails.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEmployementDetails: RestEmployementDetails): IEmployementDetails {
    return {
      ...restEmployementDetails,
      lastModified: restEmployementDetails.lastModified ? dayjs(restEmployementDetails.lastModified) : undefined,
      createdOn: restEmployementDetails.createdOn ? dayjs(restEmployementDetails.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEmployementDetails>): HttpResponse<IEmployementDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEmployementDetails[]>): HttpResponse<IEmployementDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
