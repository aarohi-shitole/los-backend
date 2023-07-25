import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDeclearation, NewDeclearation } from '../declearation.model';

export type PartialUpdateDeclearation = Partial<IDeclearation> & Pick<IDeclearation, 'id'>;

type RestOf<T extends IDeclearation | NewDeclearation> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestDeclearation = RestOf<IDeclearation>;

export type NewRestDeclearation = RestOf<NewDeclearation>;

export type PartialUpdateRestDeclearation = RestOf<PartialUpdateDeclearation>;

export type EntityResponseType = HttpResponse<IDeclearation>;
export type EntityArrayResponseType = HttpResponse<IDeclearation[]>;

@Injectable({ providedIn: 'root' })
export class DeclearationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/declearations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(declearation: NewDeclearation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(declearation);
    return this.http
      .post<RestDeclearation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(declearation: IDeclearation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(declearation);
    return this.http
      .put<RestDeclearation>(`${this.resourceUrl}/${this.getDeclearationIdentifier(declearation)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(declearation: PartialUpdateDeclearation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(declearation);
    return this.http
      .patch<RestDeclearation>(`${this.resourceUrl}/${this.getDeclearationIdentifier(declearation)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDeclearation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDeclearation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDeclearationIdentifier(declearation: Pick<IDeclearation, 'id'>): number {
    return declearation.id;
  }

  compareDeclearation(o1: Pick<IDeclearation, 'id'> | null, o2: Pick<IDeclearation, 'id'> | null): boolean {
    return o1 && o2 ? this.getDeclearationIdentifier(o1) === this.getDeclearationIdentifier(o2) : o1 === o2;
  }

  addDeclearationToCollectionIfMissing<Type extends Pick<IDeclearation, 'id'>>(
    declearationCollection: Type[],
    ...declearationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const declearations: Type[] = declearationsToCheck.filter(isPresent);
    if (declearations.length > 0) {
      const declearationCollectionIdentifiers = declearationCollection.map(
        declearationItem => this.getDeclearationIdentifier(declearationItem)!
      );
      const declearationsToAdd = declearations.filter(declearationItem => {
        const declearationIdentifier = this.getDeclearationIdentifier(declearationItem);
        if (declearationCollectionIdentifiers.includes(declearationIdentifier)) {
          return false;
        }
        declearationCollectionIdentifiers.push(declearationIdentifier);
        return true;
      });
      return [...declearationsToAdd, ...declearationCollection];
    }
    return declearationCollection;
  }

  protected convertDateFromClient<T extends IDeclearation | NewDeclearation | PartialUpdateDeclearation>(declearation: T): RestOf<T> {
    return {
      ...declearation,
      lastModified: declearation.lastModified?.toJSON() ?? null,
      createdOn: declearation.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDeclearation: RestDeclearation): IDeclearation {
    return {
      ...restDeclearation,
      lastModified: restDeclearation.lastModified ? dayjs(restDeclearation.lastModified) : undefined,
      createdOn: restDeclearation.createdOn ? dayjs(restDeclearation.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDeclearation>): HttpResponse<IDeclearation> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDeclearation[]>): HttpResponse<IDeclearation[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
