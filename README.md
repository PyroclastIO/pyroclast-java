## pyroclast-java

Java API client for Pyroclast.

## Installation

### Maven

```
<dependency>
    <groupId>io.pyroclast</groupId>
    <artifactId>pyroclast-java</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Gradle

```gradle
compile "io.pyroclast:pyroclast-java:1.0.1"
```

## Topic APIs

### Writing Events

Instantiate a client and producer.

```java
import io.pyroclast.pyroclastjava.v1.topic.PyroclastTopicClient;
import io.pyroclast.pyroclastjava.v1.topic.PyroclastProducer;
import java.util.List;
import java.util.Map;

PyroclastTopicClient client = new PyroclastTopicClient()
        .withWriteApiKey("xxxxxxxxxx")
        .withTopicId("yyyyyyyyyy")
        .buildClient();

PyroclastProducer producer = client.createProducer();
```

#### Send one event synchronously

```java
Map<Object, Object> event = new HashMap<>();
event.put("name", "mike");

producer.send(event);
```

#### Send a batch of events synchronously

```java
Map<Object, Object> e1 = new HashMap<>();
e1.put("name", "mike");

Map<Object, Object> e2 = new HashMap<>();
e2.put("name", "ron");

List<Map<Object, Object>> events = new ArrayList<>();
events.add(e1);
events.add(e2);

producer.send(events);
```

#### Send one event asynchronously

```java
import io.pyroclast.pyroclastjava.v1.topic.async.AsyncCancelledCallback;
import io.pyroclast.pyroclastjava.v1.topic.async.AsyncFailCallback;
import io.pyroclast.pyroclastjava.v1.topic.async.AsyncSuccessCallback;
import io.pyroclast.pyroclastjava.v1.topic.responses.ProducedEventResult;

Map<Object, Object> event = new HashMap<>();
event.put("car", "red");

AsyncSuccessCallback<ProducedEventResult> onSuccess = per -> System.out.println("Ok");
AsyncFailCallback onFail = e -> System.err.println(e);
AsyncCancelledCallback onCancel = status -> System.out.println("Request cancelled");

producer.send(event, onSuccess, onFail, onCancel);
```

#### Send a batch of events asynchronously

```java
import io.pyroclast.pyroclastjava.v1.topic.responses.ProducedEventsResult;

Map<Object, Object> e1 = new HashMap<>();
e1.put("name", "mike");

Map<Object, Object> e2 = new HashMap<>();
e2.put("name", "ron");

List<Map<Object, Object>> events = new ArrayList<>();
events.add(e1);
events.add(e2);

AsyncSuccessCallback<ProducedEventsResult> onSuccess = per -> System.out.println("Ok");
AsyncFailCallback onFail = e -> System.err.println(e);
AsyncCancelledCallback onCancel = status -> System.out.println("Request cancelled");

producer.send(events, onSuccess, onFail, onCancel);
```

### Reading events

Instantiate a client.

```java
import io.pyroclast.pyroclastjava.v1.topic.PyroclastTopicClient;
import io.pyroclast.pyroclastjava.v1.topic.PyroclastConsumer;

PyroclastTopicClient client = new PyroclastTopicClient()
        .withReadApiKey("xxxxxxxxxx")
        .withTopicId("yyyyyyyyyy")
        .buildClient();
```

#### Subscribe to a topic

```java
String subscriptionName = "example-subscription-name";
PyroclastConsumer consumer = client.createConsumer(subscriptionName);
```

#### Poll subscribed topic

```java
import io.pyroclast.pyroclastjava.v1.topic.TopicRecord;
import java.util.Iterator;
import java.util.List;

List<TopicRecord> records = consumer.pollTopic().getRecords();
Iterator<TopicRecord> it = records.iterator();

while (it.hasNext()) {
    TopicRecord rec = it.next();
    System.out.println(rec.getValue());
}
```

#### Commit read records

```java
consumer.commit();
```

## Deployed Service APIs

### Read deployed service aggregations

Instantiate a client.

```java
import io.pyroclast.pyroclastjava.v1.deployment.PyroclastDeploymentClient;

PyroclastDeploymentClient client = new PyroclastDeploymentClient()
        .withDeploymentId("xxxxxxxxxx")
        .withReadApiKey("yyyyyyyyyy")
        .buildClient();
```

#### Get all aggregates for a deployment

```java
import io.pyroclast.pyroclastjava.v1.deployment.DeploymentAggregates;

DeploymentAggregates aggregates = client.readAggregates().getAggregates();
```

#### Get an aggregate by name for a deployment

```java
import io.pyroclast.pyroclastjava.v1.deployment.DeploymentAggregate;

DeploymentAggregate aggregate = client.readAggregate("aggregate-name").getAggregate();
```

#### Get a single aggregate group by name

```java
import io.pyroclast.pyroclastjava.v1.deployment.DeploymentAggregate;

DeploymentAggregate aggregate = client.readAggregateGroup("aggregate-name", "group-name").getAggregate();
```

## License

(The MIT License)

Copyright © 2017 Distributed Masonry
