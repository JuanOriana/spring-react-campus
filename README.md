# spring-react-campus

This is a campus for universities that consist on a multi-module maven project that bundles both frontend (full-fledged SPA client) and backend (API REST) into a single WAR ready to be deployed and run under the same context.

## Backend
### Structure

The overall project structure follows a model-view-controller (mvc) design pattern:

<p align="center">
  <img src="https://i.imgur.com/yGWGA6Q.png" />
</p>

- interfaces: Declares the contracts for both the `/services` and `/persistence` implementations
- models: Declares the domain (ORM-based entities for JPA)
- persistence: Implements the contracts declared over `/interfaces`, it's the link between the database and the application
- services: Implements the contracts declared over `/interfaces`, it's the link between the controllers and the persistence layer
- webapp: Implements the API REST endpoints, uses the service layer.

### Dependencies
The backend utilizes the following plugins/dependencies:
- Jersey
- Jax-RS
- JUnit4 + Mockito
- Spring 4 + Spring Security
- Spring HATEOAS + MapStruct
- Hibernate JPA + PSQL

### Functionality
The backend is an API REST that consists on three main filters that execute in order:
- Authorization filter (spring-security)
- Jersey filter (servlet-container)
- Default spring filter

Every authorized request over `/api/**` passes through the security filter which checks for either the `Basic` or `Bearer` authorization header. In the case the provided header is `Basic` and the credentials are correct, a JWT token is generated and appended in the response as `Authorization: Bearer <generated-token>`

After this, the jersey filter takes over and tries to find the endpoint corresponding to the request, in case it's not found, the request is forwarded to the spring default filter which allows serving static content on which we serve the react `/index.html` as we can see over the [web.xml](https://github.com/Reversive/spring-react-campus/blob/master/webapp/src/main/webapp/WEB-INF/web.xml)

### Build

To build the project just run (you can opt to skip tests):
```
mvn clean install --DskipTests
```

## Frontend

The frontend uses the following libraries/modules/frameworks:
- react-typescript
- react-router
- i18next
- jest

For standalone dev deployment you can navigate to the `/spa-client` folder and run:
```
npm start
```
Note: make sure to update the `/src/common/constants/index.js` API base url if you are doing this

To execute the tests run:
```
npm test
```

For a production build run:
```
npm run build
```

### Authors
- Matias Enrique Pavan [@Reversive](https://github.com/Reversive)
- Juan Oriana [@JuanOriana](https://github.com/JuanOriana)
- Tomas Cerdeira [@tomcerdeira](https://github.com/tomcerdeira)
- Santiago Garcia M. [@santigarciam](https://github.com/santigarciam)

### Thanks to
- Juan Sotuyo [@jsotuyod](https://github.com/jsotuyod)
