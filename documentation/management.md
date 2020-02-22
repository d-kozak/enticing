# Management system for Enticing
In the beginning, all components from enticing were started using their own specialized script. And on top of that, for each deployment configuration,
new scripts were created. This of course did not scale very well, therefore a management module based on scripts in Python was created. This module absorbed some of the functionality, but it internally still relies on some of the old scripts.
Over the time of the development of the platform, it became clear that a better management system would be beneficial. This module could be
written as another kotlin module instead of a bunch of python scripts.

## Motivation
* reuse existing dto classes and business logic in the management service whenever necessary
* robust system with automated testing from the CI
* it's commands can be used in integration and performance tests for starting and killing tested components
* "One configuration rules them all" - there could be only one configuration DSL used by all components
* The scripts are not that big, so the reimplementation should be reasonably fast
* Possibility to run this app as a deamon in the backgroud which can perform heartbeat and other monitoring thigs
* This component could also aggregate most important logs(failures and serious problems) from all components and present it to the user in the 
most compact way

## Design

### Requirements
* easily start or kill any component of the system
* monitor long running tasks - in this case especially file distribution and creating indexes
* healthcheck the components

The tasks may be long running and their monitoring should survive restarting the management service.
This suggests using a database will be beneficial.

The workflow for each monitored command can be the following. 
1) the tasks is persisted in the database, will get a unique ID
2) the asynchronous processes are started
3) they send notifications to the management service about their progress, using the ID to identify which 
task they belong to

Similarly, each started component will get a unique internal ID which can be used to gather it's logs.




