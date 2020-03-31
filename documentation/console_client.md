# Console client 
Console client is a command line utility which you can use to submit a query or 
a batch of them from the command line. One important difference between the web interface
and the ConsoleClient is that the ConsoleClient can return all results for a given query, 
not just top X snippets as in the GUI. ConsoleClient can also be used for making performance 
measurements. 

## Interface
Console client can either run a **single command** or start an **interactive shell**. 
It has a set of commands that can be used to specify what kind of task should be 
executed. This configuration can be specified in three different ways. 
1. command line flags
2. query file 
3. in the interactive shell
 
Each command has a unique name and optionally some parameters. Frequently used
commands also have one letter abbreviations. To speficy commands from as console flags,
use prefix **--** for the full name or **-** for the abbreviation.

### Supported commands
Format: **abbreviation(if any),full name** _argument [optional argument = default value]_ - description.
* **-q,--query** _path_ - specifies an input file, each nonempty line is evaluated as query
* **-f,--file** _path_ - specifies an input file, each nonempty line is evaluated as query
* **o,output** _path_ - where the results should be stored  
* ****



