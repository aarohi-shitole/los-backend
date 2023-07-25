import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'security-user',
        data: { pageTitle: 'loanOrignationSystemApp.securityUser.home.title' },
        loadChildren: () => import('./security-user/security-user.module').then(m => m.SecurityUserModule),
      },
      {
        path: 'security-role',
        data: { pageTitle: 'loanOrignationSystemApp.securityRole.home.title' },
        loadChildren: () => import('./security-role/security-role.module').then(m => m.SecurityRoleModule),
      },
      {
        path: 'security-permission',
        data: { pageTitle: 'loanOrignationSystemApp.securityPermission.home.title' },
        loadChildren: () => import('./security-permission/security-permission.module').then(m => m.SecurityPermissionModule),
      },
      {
        path: 'organisation',
        data: { pageTitle: 'loanOrignationSystemApp.organisation.home.title' },
        loadChildren: () => import('./organisation/organisation.module').then(m => m.OrganisationModule),
      },
      {
        path: 'branch',
        data: { pageTitle: 'loanOrignationSystemApp.branch.home.title' },
        loadChildren: () => import('./branch/branch.module').then(m => m.BranchModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'loanOrignationSystemApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'region',
        data: { pageTitle: 'loanOrignationSystemApp.region.home.title' },
        loadChildren: () => import('./region/region.module').then(m => m.RegionModule),
      },
      {
        path: 'state',
        data: { pageTitle: 'loanOrignationSystemApp.state.home.title' },
        loadChildren: () => import('./state/state.module').then(m => m.StateModule),
      },
      {
        path: 'district',
        data: { pageTitle: 'loanOrignationSystemApp.district.home.title' },
        loadChildren: () => import('./district/district.module').then(m => m.DistrictModule),
      },
      {
        path: 'taluka',
        data: { pageTitle: 'loanOrignationSystemApp.taluka.home.title' },
        loadChildren: () => import('./taluka/taluka.module').then(m => m.TalukaModule),
      },
      {
        path: 'city',
        data: { pageTitle: 'loanOrignationSystemApp.city.home.title' },
        loadChildren: () => import('./city/city.module').then(m => m.CityModule),
      },
      {
        path: 'parameter-lookup',
        data: { pageTitle: 'loanOrignationSystemApp.parameterLookup.home.title' },
        loadChildren: () => import('./parameter-lookup/parameter-lookup.module').then(m => m.ParameterLookupModule),
      },
      {
        path: 'declearation',
        data: { pageTitle: 'loanOrignationSystemApp.declearation.home.title' },
        loadChildren: () => import('./declearation/declearation.module').then(m => m.DeclearationModule),
      },
      {
        path: 'org-prerequisite-doc',
        data: { pageTitle: 'loanOrignationSystemApp.orgPrerequisiteDoc.home.title' },
        loadChildren: () => import('./org-prerequisite-doc/org-prerequisite-doc.module').then(m => m.OrgPrerequisiteDocModule),
      },
      {
        path: 'sequence-generator',
        data: { pageTitle: 'loanOrignationSystemApp.sequenceGenerator.home.title' },
        loadChildren: () => import('./sequence-generator/sequence-generator.module').then(m => m.SequenceGeneratorModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'loanOrignationSystemApp.product.home.title' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'loan-catagory',
        data: { pageTitle: 'loanOrignationSystemApp.loanCatagory.home.title' },
        loadChildren: () => import('./loan-catagory/loan-catagory.module').then(m => m.LoanCatagoryModule),
      },
      {
        path: 'member',
        data: { pageTitle: 'loanOrignationSystemApp.member.home.title' },
        loadChildren: () => import('./member/member.module').then(m => m.MemberModule),
      },
      {
        path: 'guarantor',
        data: { pageTitle: 'loanOrignationSystemApp.guarantor.home.title' },
        loadChildren: () => import('./guarantor/guarantor.module').then(m => m.GuarantorModule),
      },
      {
        path: 'documents',
        data: { pageTitle: 'loanOrignationSystemApp.documents.home.title' },
        loadChildren: () => import('./documents/documents.module').then(m => m.DocumentsModule),
      },
      {
        path: 'member-bank',
        data: { pageTitle: 'loanOrignationSystemApp.memberBank.home.title' },
        loadChildren: () => import('./member-bank/member-bank.module').then(m => m.MemberBankModule),
      },
      {
        path: 'member-assets',
        data: { pageTitle: 'loanOrignationSystemApp.memberAssets.home.title' },
        loadChildren: () => import('./member-assets/member-assets.module').then(m => m.MemberAssetsModule),
      },
      {
        path: 'employement-details',
        data: { pageTitle: 'loanOrignationSystemApp.employementDetails.home.title' },
        loadChildren: () => import('./employement-details/employement-details.module').then(m => m.EmployementDetailsModule),
      },
      {
        path: 'income-details',
        data: { pageTitle: 'loanOrignationSystemApp.incomeDetails.home.title' },
        loadChildren: () => import('./income-details/income-details.module').then(m => m.IncomeDetailsModule),
      },
      {
        path: 'member-existingfacility',
        data: { pageTitle: 'loanOrignationSystemApp.memberExistingfacility.home.title' },
        loadChildren: () => import('./member-existingfacility/member-existingfacility.module').then(m => m.MemberExistingfacilityModule),
      },
      {
        path: 'nominee',
        data: { pageTitle: 'loanOrignationSystemApp.nominee.home.title' },
        loadChildren: () => import('./nominee/nominee.module').then(m => m.NomineeModule),
      },
      {
        path: 'loan-applications',
        data: { pageTitle: 'loanOrignationSystemApp.loanApplications.home.title' },
        loadChildren: () => import('./loan-applications/loan-applications.module').then(m => m.LoanApplicationsModule),
      },
      {
        path: 'loan-account',
        data: { pageTitle: 'loanOrignationSystemApp.loanAccount.home.title' },
        loadChildren: () => import('./loan-account/loan-account.module').then(m => m.LoanAccountModule),
      },
      {
        path: 'remark-history',
        data: { pageTitle: 'loanOrignationSystemApp.remarkHistory.home.title' },
        loadChildren: () => import('./remark-history/remark-history.module').then(m => m.RemarkHistoryModule),
      },
      {
        path: 'loan-disbursement',
        data: { pageTitle: 'loanOrignationSystemApp.loanDisbursement.home.title' },
        loadChildren: () => import('./loan-disbursement/loan-disbursement.module').then(m => m.LoanDisbursementModule),
      },
      {
        path: 'ledger-accounts',
        data: { pageTitle: 'loanOrignationSystemApp.ledgerAccounts.home.title' },
        loadChildren: () => import('./ledger-accounts/ledger-accounts.module').then(m => m.LedgerAccountsModule),
      },
      {
        path: 'account-head-code',
        data: { pageTitle: 'loanOrignationSystemApp.accountHeadCode.home.title' },
        loadChildren: () => import('./account-head-code/account-head-code.module').then(m => m.AccountHeadCodeModule),
      },
      {
        path: 'npa-setting',
        data: { pageTitle: 'loanOrignationSystemApp.npaSetting.home.title' },
        loadChildren: () => import('./npa-setting/npa-setting.module').then(m => m.NpaSettingModule),
      },
      {
        path: 'interest-config',
        data: { pageTitle: 'loanOrignationSystemApp.interestConfig.home.title' },
        loadChildren: () => import('./interest-config/interest-config.module').then(m => m.InterestConfigModule),
      },
      {
        path: 'schemes-details',
        data: { pageTitle: 'loanOrignationSystemApp.schemesDetails.home.title' },
        loadChildren: () => import('./schemes-details/schemes-details.module').then(m => m.SchemesDetailsModule),
      },
      {
        path: 'amortization-details',
        data: { pageTitle: 'loanOrignationSystemApp.amortizationDetails.home.title' },
        loadChildren: () => import('./amortization-details/amortization-details.module').then(m => m.AmortizationDetailsModule),
      },
      {
        path: 'loan-repayment-details',
        data: { pageTitle: 'loanOrignationSystemApp.loanRepaymentDetails.home.title' },
        loadChildren: () => import('./loan-repayment-details/loan-repayment-details.module').then(m => m.LoanRepaymentDetailsModule),
      },
      {
        path: 'vouchers',
        data: { pageTitle: 'loanOrignationSystemApp.vouchers.home.title' },
        loadChildren: () => import('./vouchers/vouchers.module').then(m => m.VouchersModule),
      },
      {
        path: 'voucher-details',
        data: { pageTitle: 'loanOrignationSystemApp.voucherDetails.home.title' },
        loadChildren: () => import('./voucher-details/voucher-details.module').then(m => m.VoucherDetailsModule),
      },
      {
        path: 'vouchers-history',
        data: { pageTitle: 'loanOrignationSystemApp.vouchersHistory.home.title' },
        loadChildren: () => import('./vouchers-history/vouchers-history.module').then(m => m.VouchersHistoryModule),
      },
      {
        path: 'master-agreement',
        data: { pageTitle: 'loanOrignationSystemApp.masterAgreement.home.title' },
        loadChildren: () => import('./master-agreement/master-agreement.module').then(m => m.MasterAgreementModule),
      },
      {
        path: 'member-limit',
        data: { pageTitle: 'loanOrignationSystemApp.memberLimit.home.title' },
        loadChildren: () => import('./member-limit/member-limit.module').then(m => m.MemberLimitModule),
      },
      {
        path: 'epay',
        data: { pageTitle: 'loanOrignationSystemApp.epay.home.title' },
        loadChildren: () => import('./epay/epay.module').then(m => m.EpayModule),
      },
      {
        path: 'enquiry-details',
        data: { pageTitle: 'loanOrignationSystemApp.enquiryDetails.home.title' },
        loadChildren: () => import('./enquiry-details/enquiry-details.module').then(m => m.EnquiryDetailsModule),
      },
      {
        path: 'notification',
        data: { pageTitle: 'loanOrignationSystemApp.notification.home.title' },
        loadChildren: () => import('./notification/notification.module').then(m => m.NotificationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
