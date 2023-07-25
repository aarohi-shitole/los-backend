import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRegion, NewRegion } from '../region.model';

export type PartialUpdateRegion = Partial<IRegion> & Pick<IRegion, 'id'>;

type RestOf<T extends IRegion | NewRegion> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

export type RestRegion = RestOf<IRegion>;

export type NewRestRegion = RestOf<NewRegion>;

export type PartialUpdateRestRegion = RestOf<PartialUpdateRegion>;

export type EntityResponseType = HttpResponse<IRegion>;
export type EntityArrayResponseType = HttpResponse<IRegion[]>;

@Injectable({ providedIn: 'root' })
export class RegionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/regions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(region: NewRegion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(region);
    return this.http
      .post<RestRegion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(region: IRegion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(region);
    return this.http
      .put<RestRegion>(`${this.resourceUrl}/${this.getRegionIdentifier(region)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(region: PartialUpdateRegion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(region);
    return this.http
      .patch<RestRegion>(`${this.resourceUrl}/${this.getRegionIdentifier(region)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRegion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRegion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRegionIdentifier(region: Pick<IRegion, 'id'>): number {
    return region.id;
  }

  compareRegion(o1: Pick<IRegion, 'id'> | null, o2: Pick<IRegion, 'id'> | null): boolean {
    return o1 && o2 ? this.getRegionIdentifier(o1) === this.getRegionIdentifier(o2) : o1 === o2;
  }

  addRegionToCollectionIfMissing<Type extends Pick<IRegion, 'id'>>(
    regionCollection: Type[],
    ...regionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const regions: Type[] = regionsToCheck.filter(isPresent);
    if (regions.length > 0) {
      const regionCollectionIdentifiers = regionCollection.map(regionItem => this.getRegionIdentifier(regionItem)!);
      const regionsToAdd = regions.filter(regionItem => {
        const regionIdentifier = this.getRegionIdentifier(regionItem);
        if (regionCollectionIdentifiers.includes(regionIdentifier)) {
          return false;
        }
        regionCollectionIdentifiers.push(regionIdentifier);
        return true;
      });
      return [...regionsToAdd, ...regionCollection];
    }
    return regionCollection;
  }

  protected convertDateFromClient<T extends IRegion | NewRegion | PartialUpdateRegion>(region: T): RestOf<T> {
    return {
      ...region,
      lastModified: region.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restRegion: RestRegion): IRegion {
    return {
      ...restRegion,
      lastModified: restRegion.lastModified ? dayjs(restRegion.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRegion>): HttpResponse<IRegion> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRegion[]>): HttpResponse<IRegion[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
