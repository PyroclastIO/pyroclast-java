## pyroclast-java

Java API client for Pyroclast.

## Installation

Coming soon.

## Usage

Coming soon.

### Send one event synchronously

```java
PyroclastTopicClient client = new PyroclastTopicClient()
    .withWriteApiKey("xxxxxxxxx")
    .withTopicId("yyyyyyyyy")
    .buildClient();

Map<Object, Object> e1 = new HashMap<>();
e1.put("name", "mike");

Map<Object, Object> e2 = new HashMap<>();
e2.put("name", "ron");

List<Map<Object, Object>> events = new ArrayList<>();
events.add(e1);
events.add(e2);
client.produceEvents(events);
```

### Send a batch of events synchronously

Coming soon.

### Send one event asynchronously

Coming soon.

### Send a batch of events asynchronously

Coming soon.

## License

(The MIT License)

Copyright © 2017 Distibuted Masonry
