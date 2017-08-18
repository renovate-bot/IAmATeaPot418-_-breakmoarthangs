# Perform full release with no stage

```
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/close-milestone.sh | bash
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/create-stage.sh | bash
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/close-and-release-stage.sh | sed 's/$1/ioultreia/' | bash
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/update-changelog.sh | bash
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/create-milestone.sh | bash
```

# Create Release stage

```
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/close-milestone.sh | bash
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/create-stage.sh | bash
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/close-stage.sh | sed 's/$1/ioultreia/' | bash
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/update-staging-changelog.sh | sed 's/$1/ioultreia/' | bash
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/create-milestone.sh | bash
```

# Release stage

``` 
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/release-stage.sh | sed 's/$1/ioultreia/' | bash
```

# Drop stage

```
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/drop-stage.sh | sed 's/$1/ioultreia/' | bash
```

# Regenerate changelog

``` 
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/update-changelog.sh | bash
```

# Regenerate staging changelog

``` 
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/update-staging-changelog.sh | sed 's/$1/ioultreia/' | bash
```

# Generate site

``` 
mvn clean verify site -DperformRelease scm-publish:publish-scm
```

# Deploy latest demo

``` 
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/execute-maven.sh | sed 's/$1/-Padd-git-commit-id-to-project-version -N/' | bash
wget -q -O - https://gitlab.com/ultreiaio/pom/raw/master/bin/execute-maven.sh | sed 's/$1/install -am -pl application-web -DskipTests -Pdeploy-demo -Dclassifier=latest/' | bash
```


# Add a referential

Example for *seine.ObjectMaterialType* on version *6.901*

* [persistence] edit persistence/src/main/models/Observe.model
* [persistence] add migration script persistence/src/main/resources/db/migration/6.901/xx_add_referenial-common.sql
* [persistence] register this script in migrationResolver fr.ird.observe.persistence.migration.DataSourceMigrationForVersion_7_0
* [persistence] register type in fr.ird.observe.persistence.Entities
* [services] edit services/src/main/models/Observe.model
* build `mvn clean install -DskipTests`

will fails in services

* [services] register in fr.ird.observe.services.dto.referential.ReferentialHelper
* [services-topia] add Binder fr.ird.observe.services.binder.referential.seine.ObjectMaterialTypeBinder
* [services-topia] implements new method in fr.ird.observe.services.binder.BinderEngineInitializer
* [services] implements new method in fr.ird.observe.services.ObserveDtoInitializer
* [services] register in fr.ird.observe.services.decoration.DecoratorService.modelInitializer class
* [services] add i18n key observe.type.objectMaterialType in application-swing-decoration i18n bundle
* [services] add i18n key observe.type.objectMaterialTypes in application-swing-decoration i18n bundle
* [validation] add swing validation in validation/src/main/resources/fr/ird/observe/services/dto/referential
* [client] add swing referential editor

* build `mvn clean install -DskipTests`

* migrate db test `mvn -Pupdate-test-dbs -pl services-topia`
* [tests] register in fr.ird.observe.test.ObserveFixtures (count depends on your migration script)
* [tests] increments lastupdatedate count in fr.ird.observe.test.ObserveFixtures 
* [validation] fix test fr.ird.observe.client.validation.BeanValidatorDetectorTest
* [services-topia] fix test fr.ird.observe.services.topia.service.actions.validate.ValidateServiceTopiaTest
* build `mvn clean install`

