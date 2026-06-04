# grails-data-access

Sample app for the apache/grails-static-website guide [grails-data-access/v8](https://grails.apache.org/guides/grails-data-access/8/guide/index.html).

A Book + Author + Tag REST API on Grails 8 demonstrating GORM data access: `belongsTo` / `hasMany` / many-to-many associations, query logic kept in services (criteria, `where` queries, dynamic finders), JSON views, and Spock unit + Testcontainers integration tests.

`initial/` is a vanilla Grails 8 `rest-api` starter. `complete/` adds the domain model, services, controllers, views, and tests described in the guide.

```bash
git clone -b grails8 https://github.com/grails-guides/grails-data-access.git
cd grails-data-access/complete
./gradlew bootRun
curl http://localhost:8080/api/books
```
