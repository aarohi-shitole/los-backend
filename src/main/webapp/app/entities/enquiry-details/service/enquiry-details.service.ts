import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEnquiryDetails, NewEnquiryDetails } from '../enquiry-details.model';

export type PartialUpdateEnquiryDetails = Partial<IEnquiryDetails> & Pick<IEnquiryDetails, 'id'>;

type RestOf<T extends IEnquiryDetails | NewEnquiryDetails> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

export type RestEnquiryDetails = RestOf<IEnquiryDetails>;

export type NewRestEnquiryDetails = RestOf<NewEnquiryDetails>;

export type PartialUpdateRestEnquiryDetails = RestOf<PartialUpdateEnquiryDetails>;

export type EntityResponseType = HttpResponse<IEnquiryDetails>;
export type EntityArrayResponseType = HttpResponse<IEnquiryDetails[]>;

@Injectable({ providedIn: 'root' })
export class EnquiryDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/enquiry-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(enquiryDetails: NewEnquiryDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enquiryDetails);
    return this.http
      .post<RestEnquiryDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(enquiryDetails: IEnquiryDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enquiryDetails);
    return this.http
      .put<RestEnquiryDetails>(`${this.resourceUrl}/${this.getEnquiryDetailsIdentifier(enquiryDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(enquiryDetails: PartialUpdateEnquiryDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enquiryDetails);
    return this.http
      .patch<RestEnquiryDetails>(`${this.resourceUrl}/${this.getEnquiryDetailsIdentifier(enquiryDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEnquiryDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEnquiryDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEnquiryDetailsIdentifier(enquiryDetails: Pick<IEnquiryDetails, 'id'>): number {
    return enquiryDetails.id;
  }

  compareEnquiryDetails(o1: Pick<IEnquiryDetails, 'id'> | null, o2: Pick<IEnquiryDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getEnquiryDetailsIdentifier(o1) === this.getEnquiryDetailsIdentifier(o2) : o1 === o2;
  }

  addEnquiryDetailsToCollectionIfMissing<Type extends Pick<IEnquiryDetails, 'id'>>(
    enquiryDetailsCollection: Type[],
    ...enquiryDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const enquiryDetails: Type[] = enquiryDetailsToCheck.filter(isPresent);
    if (enquiryDetails.length > 0) {
      const enquiryDetailsCollectionIdentifiers = enquiryDetailsCollection.map(
        enquiryDetailsItem => this.getEnquiryDetailsIdentifier(enquiryDetailsItem)!
      );
      const enquiryDetailsToAdd = enquiryDetails.filter(enquiryDetailsItem => {
        const enquiryDetailsIdentifier = this.getEnquiryDetailsIdentifier(enquiryDetailsItem);
        if (enquiryDetailsCollectionIdentifiers.includes(enquiryDetailsIdentifier)) {
          return false;
        }
        enquiryDetailsCollectionIdentifiers.push(enquiryDetailsIdentifier);
        return true;
      });
      return [...enquiryDetailsToAdd, ...enquiryDetailsCollection];
    }
    return enquiryDetailsCollection;
  }

  protected convertDateFromClient<T extends IEnquiryDetails | NewEnquiryDetails | PartialUpdateEnquiryDetails>(
    enquiryDetails: T
  ): RestOf<T> {
    return {
      ...enquiryDetails,
      lastModified: enquiryDetails.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEnquiryDetails: RestEnquiryDetails): IEnquiryDetails {
    return {
      ...restEnquiryDetails,
      lastModified: restEnquiryDetails.lastModified ? dayjs(restEnquiryDetails.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEnquiryDetails>): HttpResponse<IEnquiryDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEnquiryDetails[]>): HttpResponse<IEnquiryDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
