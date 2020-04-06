# Management system for Enticing
In the beginning, all components from enticing were started using their own specialized script. And on top of that,
for each deployment configuration, new scripts were created. This of course did not scale very well, therefore 
a management module based on scripts in Python was created. This module absorbed some of the functionality, 
but it internally still relies on some of the old scripts. Over the time of the development of the platform, it 
became clear that a better management system would be beneficial. This module could be
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

### Informal specification
Enticing management service (further referred to just as service) manages other components of the platform and tasks necessary for its operation such as file distribution 
and indexing. This is an internal system that should not be accessible to normal users, only to administrators. 

Since its commands directly affect the health of other components, user authentication is required. No data should be accessible without 
logging in. Basic users should be able to view the state of the system, but only admin users should be able to execute any command. To detect malicious 
behaviour, all executed commands will be persisted to a database with the login of the user who submitted it. The output of these commands will also be persisted, so that
their execution can be traced if necessary.   

The service handles a set of servers and allows users to manage components running on them. When new server is being added, the system should check that the given server is 
really accessible using ssh. Similar semantic validation should be performed whenever possible to avoid mistakes early on. To ease the task of administration,
components should register themselves on startup. Also, they should periodically send heartbeats. These heartbeats will include the current state of the server, 
which can be used to measure the performance and detect any bottlenecks.

Upon login, the user should see a list of servers along with their components. Each item should somehow visualize its state (green okay, orange for danger, 
red for serious problems). Each item should be 'clickable' and cause a redirection to the page with more details, including last logs and heartbeats.
Since the amount of logs is huge, there should be some filtering options available. 

The user should be able to manage these components easily. There should be an option to restart each individual component or the whole system.
Also, each server should have a button for adding new components.

Apart from monitoring, the server should also support the CI and CD. There should be an option to pull the newest version of the code, 
build it, execute tests and if everything works, redeploy the platform. Internally, these can be represented just like any other command, but in the GUI,
there should be a separate section for them, since they are independent of the other tasks.

Another section will be related to long-running tasks such as file distribution and indexing.  Again, their internal representation is identical to normal commands.

Commands from the last two sections should also be cancellable.

![domain_model](../img/management.png)

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


 