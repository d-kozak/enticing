for server in $(cat ./servers.txt); do
  output=$(ssh xkozak15@$server jps)
  ids=$(echo $output | grep MainKt | cut -d' ' -f1)
  for id in $ids; do
    ssh xkozak15@$server kill $id
  done
done
