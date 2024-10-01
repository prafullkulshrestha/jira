Test plugin exercising all functionality.

To run integration tests in refapp: `mvn verify`

To run integration tests in other products: `mvn -DtestGroups=jira verify`

To run manually and attach debugger: `mvn -Dproduct=refapp -Djvm.debug.suspend=true amps:debug`

(Note all the products are configured to appear at the same URL: "http://localhost:5990/product")


