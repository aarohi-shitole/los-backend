package com.techvg.los.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.techvg.los.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.techvg.los.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.techvg.los.domain.User.class.getName());
            createCache(cm, com.techvg.los.domain.Authority.class.getName());
            createCache(cm, com.techvg.los.domain.User.class.getName() + ".authorities");
            createCache(cm, com.techvg.los.domain.SecurityUser.class.getName());
            createCache(cm, com.techvg.los.domain.SecurityUser.class.getName() + ".securityPermissions");
            createCache(cm, com.techvg.los.domain.SecurityUser.class.getName() + ".securityRoles");
            createCache(cm, com.techvg.los.domain.SecurityRole.class.getName());
            createCache(cm, com.techvg.los.domain.SecurityRole.class.getName() + ".securityPermissions");
            createCache(cm, com.techvg.los.domain.SecurityRole.class.getName() + ".securityUsers");
            createCache(cm, com.techvg.los.domain.SecurityPermission.class.getName());
            createCache(cm, com.techvg.los.domain.SecurityPermission.class.getName() + ".securityRoles");
            createCache(cm, com.techvg.los.domain.SecurityPermission.class.getName() + ".securityUsers");
            createCache(cm, com.techvg.los.domain.Organisation.class.getName());
            createCache(cm, com.techvg.los.domain.Branch.class.getName());
            createCache(cm, com.techvg.los.domain.Address.class.getName());
            createCache(cm, com.techvg.los.domain.Region.class.getName());
            createCache(cm, com.techvg.los.domain.State.class.getName());
            createCache(cm, com.techvg.los.domain.District.class.getName());
            createCache(cm, com.techvg.los.domain.Taluka.class.getName());
            createCache(cm, com.techvg.los.domain.City.class.getName());
            createCache(cm, com.techvg.los.domain.ParameterLookup.class.getName());
            createCache(cm, com.techvg.los.domain.Declearation.class.getName());
            createCache(cm, com.techvg.los.domain.OrgPrerequisiteDoc.class.getName());
            createCache(cm, com.techvg.los.domain.SequenceGenerator.class.getName());
            createCache(cm, com.techvg.los.domain.Product.class.getName());
            createCache(cm, com.techvg.los.domain.LoanCatagory.class.getName());
            createCache(cm, com.techvg.los.domain.Member.class.getName());
            createCache(cm, com.techvg.los.domain.Guarantor.class.getName());
            createCache(cm, com.techvg.los.domain.Documents.class.getName());
            createCache(cm, com.techvg.los.domain.MemberBank.class.getName());
            createCache(cm, com.techvg.los.domain.MemberAssets.class.getName());
            createCache(cm, com.techvg.los.domain.EmployementDetails.class.getName());
            createCache(cm, com.techvg.los.domain.IncomeDetails.class.getName());
            createCache(cm, com.techvg.los.domain.MemberExistingfacility.class.getName());
            createCache(cm, com.techvg.los.domain.Nominee.class.getName());
            createCache(cm, com.techvg.los.domain.LoanApplications.class.getName());
            createCache(cm, com.techvg.los.domain.LoanAccount.class.getName());
            createCache(cm, com.techvg.los.domain.RemarkHistory.class.getName());
            createCache(cm, com.techvg.los.domain.LoanDisbursement.class.getName());
            createCache(cm, com.techvg.los.domain.LedgerAccounts.class.getName());
            createCache(cm, com.techvg.los.domain.AccountHeadCode.class.getName());
            createCache(cm, com.techvg.los.domain.NpaSetting.class.getName());
            createCache(cm, com.techvg.los.domain.InterestConfig.class.getName());
            createCache(cm, com.techvg.los.domain.SchemesDetails.class.getName());
            createCache(cm, com.techvg.los.domain.AmortizationDetails.class.getName());
            createCache(cm, com.techvg.los.domain.LoanRepaymentDetails.class.getName());
            createCache(cm, com.techvg.los.domain.Vouchers.class.getName());
            createCache(cm, com.techvg.los.domain.VoucherDetails.class.getName());
            createCache(cm, com.techvg.los.domain.VouchersHistory.class.getName());
            createCache(cm, com.techvg.los.domain.MasterAgreement.class.getName());
            createCache(cm, com.techvg.los.domain.MemberLimit.class.getName());
            createCache(cm, com.techvg.los.domain.Epay.class.getName());
            createCache(cm, com.techvg.los.domain.EnquiryDetails.class.getName());
            createCache(cm, com.techvg.los.domain.Notification.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
