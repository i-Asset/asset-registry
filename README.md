# asset-registry

The asset-registry represents the main service hosting all the details of arbitrary assets

## prerequisites

A working & running instance of a PostgreSQL Database is required. The database name as well as the credentials are taken 
from the 'boostrap.yml" 

The database schema is created automatically during startup. Be sure to have appropriate permissions for creating tables.


## External build dependencies 

1) maven-build the project "solr-model"
2) maven-build the project "solr-indexing"
3) maven-build the project "basyx"
4) maven build this


## Service build and startup

for starting the service, either run

 ```
 mvn clean spring-boot:run
 ```

 or run the main class 
 
 ```
 java at.srfg.iot.aas.AssetRegistryApplication
 ```
 from the command line or from within the IDE.
 
 Info: This program needs a correctly setup MS infrastructure, otherwise you will experience "Connection refused" - Errors