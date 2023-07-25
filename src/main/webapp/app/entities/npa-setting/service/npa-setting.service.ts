import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INpaSetting, NewNpaSetting } from '../npa-setting.model';

export type PartialUpdateNpaSetting = Partial<INpaSetting> & Pick<INpaSetting, 'id'>;

type RestOf<T extends INpaSetting | NewNpaSetting> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestNpaSetting = RestOf<INpaSetting>;

export type NewRestNpaSetting = RestOf<NewNpaSetting>;

export type PartialUpdateRestNpaSetting = RestOf<PartialUpdateNpaSetting>;

export type EntityResponseType = HttpResponse<INpaSetting>;
export type EntityArrayResponseType = HttpResponse<INpaSetting[]>;

@Injectable({ providedIn: 'root' })
export class NpaSettingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/npa-settings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(npaSetting: NewNpaSetting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(npaSetting);
    return this.http
      .post<RestNpaSetting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(npaSetting: INpaSetting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(npaSetting);
    return this.http
      .put<RestNpaSetting>(`${this.resourceUrl}/${this.getNpaSettingIdentifier(npaSetting)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(npaSetting: PartialUpdateNpaSetting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(npaSetting);
    return this.http
      .patch<RestNpaSetting>(`${this.resourceUrl}/${this.getNpaSettingIdentifier(npaSetting)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestNpaSetting>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestNpaSetting[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNpaSettingIdentifier(npaSetting: Pick<INpaSetting, 'id'>): number {
    return npaSetting.id;
  }

  compareNpaSetting(o1: Pick<INpaSetting, 'id'> | null, o2: Pick<INpaSetting, 'id'> | null): boolean {
    return o1 && o2 ? this.getNpaSettingIdentifier(o1) === this.getNpaSettingIdentifier(o2) : o1 === o2;
  }

  addNpaSettingToCollectionIfMissing<Type extends Pick<INpaSetting, 'id'>>(
    npaSettingCollection: Type[],
    ...npaSettingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const npaSettings: Type[] = npaSettingsToCheck.filter(isPresent);
    if (npaSettings.length > 0) {
      const npaSettingCollectionIdentifiers = npaSettingCollection.map(npaSettingItem => this.getNpaSettingIdentifier(npaSettingItem)!);
      const npaSettingsToAdd = npaSettings.filter(npaSettingItem => {
        const npaSettingIdentifier = this.getNpaSettingIdentifier(npaSettingItem);
        if (npaSettingCollectionIdentifiers.includes(npaSettingIdentifier)) {
          return false;
        }
        npaSettingCollectionIdentifiers.push(npaSettingIdentifier);
        return true;
      });
      return [...npaSettingsToAdd, ...npaSettingCollection];
    }
    return npaSettingCollection;
  }

  protected convertDateFromClient<T extends INpaSetting | NewNpaSetting | PartialUpdateNpaSetting>(npaSetting: T): RestOf<T> {
    return {
      ...npaSetting,
      lastModified: npaSetting.lastModified?.toJSON() ?? null,
      createdOn: npaSetting.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restNpaSetting: RestNpaSetting): INpaSetting {
    return {
      ...restNpaSetting,
      lastModified: restNpaSetting.lastModified ? dayjs(restNpaSetting.lastModified) : undefined,
      createdOn: restNpaSetting.createdOn ? dayjs(restNpaSetting.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestNpaSetting>): HttpResponse<INpaSetting> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestNpaSetting[]>): HttpResponse<INpaSetting[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
