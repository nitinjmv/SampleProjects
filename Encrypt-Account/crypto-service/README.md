Just make copy of this project.
- change the name of directory
- change the base package name
- update the pom.xml with project name

http://localhost:8080/actuator/health
http://localhost:8080/v3/api-docs

### What is included in this sample project

- Actuator
- Lombok
- Logging
- Sample Endpoint `/products`
  - Standard APIResponse
    - error
    - data
    - count
    - http status
- Test & Dev profile 
  - `application-test.yml` 
  - `application-dev.yml`
- H2 for test
- Postgres for Dev