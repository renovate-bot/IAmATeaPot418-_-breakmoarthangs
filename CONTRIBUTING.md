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
mvn clean verify site site:stage scm-publish:publish-scm -DperformRelease
```
