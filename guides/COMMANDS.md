


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