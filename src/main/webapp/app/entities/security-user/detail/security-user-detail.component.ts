import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISecurityUser } from '../security-user.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-security-user-detail',
  templateUrl: './security-user-detail.component.html',
})
export class SecurityUserDetailComponent implements OnInit {
  securityUser: ISecurityUser | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityUser }) => {
      this.securityUser = securityUser;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
