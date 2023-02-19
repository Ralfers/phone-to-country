# Phone-2-country application

The backend is a Spring Boot application which exposes the following API for fetching country codes based on the given phone number:

- `GET /country-codes?phoneNumber=<phoneNumber>` - determines the country/region codes for the given phone number based on calling codes fetched
  from [the calling code Wiki page](https://en.wikipedia.org/wiki/List_of_country_calling_codes).

An insomnia import script is provided in `Insomnia_2023-02-19.json`, which can be used to test the API.

The graphical user interface is a React application located under the `ui` directory.

## Building from the command line

### Building the GUI

First, the GUI React application must be compiled by following these steps:

- install npm
- under the `ui` directory run:
    - `npm i` to install the required npm dependencies;
    - `npm run build` to compile the React code and copy the required static files under the Spring Boot backend application;
    - alternatively, can run `npm run start` to deploy the GUI on a Node server for development purposes with auto-reload enabled (works with a running Spring Boot backend).

### Building the backend

After building the GUI, the backend must be packaged using Maven by following these steps:

- install maven
- under the root directory run:
    - `mvn clean package` to package the application as an executable jar file under the `target` directory.
      Will additionally run unit tests, which can be skipped by passing the `-Dmaven.test.skip=true` flag.

## Running the application

After the application has been built, the packaged jar file can be run using the command `java -jar phone-to-country-<version>.jar`.
Upon startup, by default, the APIs will be hosted on port `8080` and the GUI can be accessed at the root URL.

## Testing the application

Unit tests can be manually executed using the command `mvn test`, which will also log a report on all successful and failed tests.
