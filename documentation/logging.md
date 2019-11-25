# Logging

Since there are multiple machines running multiple different programs, it is useful to 
implemented a centralized crashlog system that would allow the administrator to see all the crash logs.
in one place, prererably from a well-polished GUI. But the GUI would require a lot of work, so it should probably be an extension. 
(so maybe during spring term). I suggest starting with a simple file based logs. We can utilize the fact that all knot servers have access to the same disc and group the logs together.
A single directory named by the corpus with a single entry for each running service. It is simple enough to be implemented quickly, but it should provide us with what we need - a single place where all crash logs can be found.  The GUI can then simply load data from these files and present them in a more readable way.

Structure:
```
{corpusName} --| {service1} --| {all log files}
                .
                .
                .
               | {serviceN} --| {all log files}
```