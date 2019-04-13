# Vaadin 10: SASS Integration and CSS-Refresh Development Workflow

```bash
# start the app (execute main() in Vaadin10SassCssrefreshApplication)

mvn sass:watch

# open http://localhost:8080/

# change sass

# profit (= css will be updated in the browser without a page reload)
```

If you are not using `sass:watch`, mind to execute at least `mvn sass:update-stylesheets` once up front before starting the app. Otherwise there will be no CSS at all.