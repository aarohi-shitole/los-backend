import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IOrganisation, NewOrganisation } from '../organisation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrganisation for edit and NewOrganisationFormGroupInput for create.
 */
type OrganisationFormGroupInput = IOrganisation | PartialWithRequiredKeyOf<NewOrganisation>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IOrganisation | NewOrganisation> = Omit<T, 'createdOn' | 'lastModified'> & {
  createdOn?: string | null;
  lastModified?: string | null;
};

type OrganisationFormRawValue = FormValueOf<IOrganisation>;

type NewOrganisationFormRawValue = FormValueOf<NewOrganisation>;

type OrganisationFormDefaults = Pick<NewOrganisation, 'id' | 'isActivate' | 'createdOn' | 'lastModified'>;

type OrganisationFormGroupContent = {
  id: FormControl<OrganisationFormRawValue['id'] | NewOrganisation['id']>;
  orgName: FormControl<OrganisationFormRawValue['orgName']>;
  description: FormControl<OrganisationFormRawValue['description']>;
  regNumber: FormControl<OrganisationFormRawValue['regNumber']>;
  gstin: FormControl<OrganisationFormRawValue['gstin']>;
  pan: FormControl<OrganisationFormRawValue['pan']>;
  tan: FormControl<OrganisationFormRawValue['tan']>;
  phone: FormControl<OrganisationFormRawValue['phone']>;
  email: FormControl<OrganisationFormRawValue['email']>;
  webSite: FormControl<OrganisationFormRawValue['webSite']>;
  fax: FormControl<OrganisationFormRawValue['fax']>;
  isActivate: FormControl<OrganisationFormRawValue['isActivate']>;
  orgType: FormControl<OrganisationFormRawValue['orgType']>;
  createdOn: FormControl<OrganisationFormRawValue['createdOn']>;
  createdBy: FormControl<OrganisationFormRawValue['createdBy']>;
  isDeleted: FormControl<OrganisationFormRawValue['isDeleted']>;
  lastModified: FormControl<OrganisationFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<OrganisationFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<OrganisationFormRawValue['freeField1']>;
  freeField2: FormControl<OrganisationFormRawValue['freeField2']>;
  freeField3: FormControl<OrganisationFormRawValue['freeField3']>;
  freeField4: FormControl<OrganisationFormRawValue['freeField4']>;
  freeField5: FormControl<OrganisationFormRawValue['freeField5']>;
  address: FormControl<OrganisationFormRawValue['address']>;
};

export type OrganisationFormGroup = FormGroup<OrganisationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrganisationFormService {
  createOrganisationFormGroup(organisation: OrganisationFormGroupInput = { id: null }): OrganisationFormGroup {
    const organisationRawValue = this.convertOrganisationToOrganisationRawValue({
      ...this.getFormDefaults(),
      ...organisation,
    });
    return new FormGroup<OrganisationFormGroupContent>({
      id: new FormControl(
        { value: organisationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      orgName: new FormControl(organisationRawValue.orgName, {
        validators: [Validators.required],
      }),
      description: new FormControl(organisationRawValue.description),
      regNumber: new FormControl(organisationRawValue.regNumber, {
        validators: [Validators.required],
      }),
      gstin: new FormControl(organisationRawValue.gstin, {
        validators: [Validators.required],
      }),
      pan: new FormControl(organisationRawValue.pan),
      tan: new FormControl(organisationRawValue.tan),
      phone: new FormControl(organisationRawValue.phone),
      email: new FormControl(organisationRawValue.email),
      webSite: new FormControl(organisationRawValue.webSite),
      fax: new FormControl(organisationRawValue.fax),
      isActivate: new FormControl(organisationRawValue.isActivate),
      orgType: new FormControl(organisationRawValue.orgType),
      createdOn: new FormControl(organisationRawValue.createdOn),
      createdBy: new FormControl(organisationRawValue.createdBy),
      isDeleted: new FormControl(organisationRawValue.isDeleted),
      lastModified: new FormControl(organisationRawValue.lastModified),
      lastModifiedBy: new FormControl(organisationRawValue.lastModifiedBy),
      freeField1: new FormControl(organisationRawValue.freeField1),
      freeField2: new FormControl(organisationRawValue.freeField2),
      freeField3: new FormControl(organisationRawValue.freeField3),
      freeField4: new FormControl(organisationRawValue.freeField4),
      freeField5: new FormControl(organisationRawValue.freeField5),
      address: new FormControl(organisationRawValue.address),
    });
  }

  getOrganisation(form: OrganisationFormGroup): IOrganisation | NewOrganisation {
    return this.convertOrganisationRawValueToOrganisation(form.getRawValue() as OrganisationFormRawValue | NewOrganisationFormRawValue);
  }

  resetForm(form: OrganisationFormGroup, organisation: OrganisationFormGroupInput): void {
    const organisationRawValue = this.convertOrganisationToOrganisationRawValue({ ...this.getFormDefaults(), ...organisation });
    form.reset(
      {
        ...organisationRawValue,
        id: { value: organisationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OrganisationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isActivate: false,
      createdOn: currentTime,
      lastModified: currentTime,
    };
  }

  private convertOrganisationRawValueToOrganisation(
    rawOrganisation: OrganisationFormRawValue | NewOrganisationFormRawValue
  ): IOrganisation | NewOrganisation {
    return {
      ...rawOrganisation,
      createdOn: dayjs(rawOrganisation.createdOn, DATE_TIME_FORMAT),
      lastModified: dayjs(rawOrganisation.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertOrganisationToOrganisationRawValue(
    organisation: IOrganisation | (Partial<NewOrganisation> & OrganisationFormDefaults)
  ): OrganisationFormRawValue | PartialWithRequiredKeyOf<NewOrganisationFormRawValue> {
    return {
      ...organisation,
      createdOn: organisation.createdOn ? organisation.createdOn.format(DATE_TIME_FORMAT) : undefined,
      lastModified: organisation.lastModified ? organisation.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
