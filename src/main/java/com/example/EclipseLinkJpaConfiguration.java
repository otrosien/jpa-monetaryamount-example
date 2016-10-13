package com.example;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.google.common.collect.ImmutableMap;

@Configuration
public class EclipseLinkJpaConfiguration extends JpaBaseConfiguration {

    private JpaProperties jpaProperties;

    public EclipseLinkJpaConfiguration(DataSource dataSource,
            JpaProperties jpaProperties,
            ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider) {
        super(dataSource, jpaProperties, jtaTransactionManagerProvider);
        this.jpaProperties = jpaProperties;
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        // http://dev.eclipse.org/mhonarc/lists/eclipselink-users/msg08511.html
        // http://adfinmunich.blogspot.de/2012/04/nosuchmethoderror-when-having-abstract.html
        // http://www.eclipse.org/eclipselink/documentation/2.5/concepts/app_dev007.htm
        final ImmutableMap<String, Object> immutableMap = ImmutableMap.<String, Object>builder() //
                .put(PersistenceUnitProperties.TABLE_CREATION_SUFFIX, ";") //
                .put(PersistenceUnitProperties.WEAVING, "false") //
                        // WARNING: incompatible with: @RepositoryEventHandler(value=XXX.class)
                .put(PersistenceUnitProperties.WEAVING_INTERNAL, "true") //
                .put(PersistenceUnitProperties.WEAVING_LAZY, "true") //
                .put(PersistenceUnitProperties.WEAVING_EAGER, "true") //
                .put(PersistenceUnitProperties.WEAVING_FETCHGROUPS, "true") //
                .put(PersistenceUnitProperties.WEAVING_CHANGE_TRACKING, "true") //
                .put(PersistenceUnitProperties.WEAVING_REST, "true") //
                .put(PersistenceUnitProperties.CACHE_SHARED_DEFAULT, "false") //
//                .put(PersistenceUnitProperties.SESSION_CUSTOMIZER, com.epages.jpa.UUIDSequence.class.getName()) //
                // project-custom properties
                .putAll(jpaProperties.getProperties())
                .build();
        // must be mutable in order to be fed into #customizeVendorProperties(Map<String, Object>)
        return newHashMap(immutableMap);
    }
}
