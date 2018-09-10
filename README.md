# amazon_ranking_api_batch

```
mvn package
```

```
java -jar amazon_rank_api_batch-v1.0.jar "samaple_param1.json" "sample_param2.json" "save_dir" "save_file_name.json"
```

## param1 

```
{"accessKeyId":"0123456789",
  "secretKey":"0123456789",
  "endpoint":"webservices.amazon.co.jp"}
```

## param2 

```
{"service":"AWSECommerceService",
  "operation":"BrowseNodeLookup",
  "awsAccessKeyId":"aaaaaaaaaaaaaaaa",
  "associateTag":"aaaaaaaaaaaaaaaa",
  "browseNodeId":"2275256051",
  "responseGroup":"TopSellers"}
```

## param3

the directory

## param4

the file name