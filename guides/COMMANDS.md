


Start one index deamon locally
```
java -jar /mnt/minerva1/nlp/projects/corpproc/corpora_processing_sw/processing_steps/7/corpproc/target/corpproc-1.0-SNAPSHOT-jar-with-dependencies.jar -c ../myconfig serve /mnt/data/indexes/CC-2015-27/final/
```

Send post request using curl
```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"query":"person"}' \
  http://localhost:12000
```

Execute query locally, WIP just a proof of concept, not that useful currently
```
java -cp "../../../../mg4j-bin/*" it.unimi.di.big.mg4j.query.Query -h -i FileSystemItem CC-token CC-nertag
```
-cp "../../../../mg4j-bin/*" is for specifying all jars in the directory, not that '~' symbol does not work
Since specifying all indexes and the collection in the terminal will be complicated, a small wrapper should be written to handle it


CC input for indexing, about 300GiB of text
```
/mnt/data/commoncrawl/CC-2018-47/
```