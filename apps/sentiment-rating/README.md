# Sentiment Rating Service

A Quarkus-based microservice that analyzes sentiment in review comments using AI (Llama) and returns ratings via CloudEvents.

## Features

- **Quarkus Funqy CloudEvents**: Native Quarkus extension for CloudEvents handling
- **AI-Powered Sentiment Analysis**: Uses Quarkus LangChain4j extension with Jlama
- **Rating Scale**: Provides ratings from 1 (Very Negative) to 5 (Very Positive)
- **JSON Output**: Returns structured sentiment analysis results

## Technology Stack

- Quarkus 3.19.1 with Funqy extension
- Java 21
- Quarkus LangChain4j extension with Jlama
- CloudEvents (via Quarkus Funqy)
- Red Hat UBI 9

## Building and Running

### Local Development

```bash
mvn quarkus:dev
```

### Build JAR

```bash
mvn clean package
```

### Run JAR

```bash
java --enable-preview --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.vector -jar target/quarkus-app/quarkus-run.jar
```

### Docker

Build the image:
```bash
docker build -t sentiment-rating .
```

Run the container:
```bash
docker run -p 8080:8080 sentiment-rating
```

## API Usage

### Endpoint

`POST /` (Funqy CloudEvents endpoint)

### Request (CloudEvent via HTTP Binary Mode)

Using CloudEvents HTTP Binary Content Mode (headers):

```bash
curl -X POST http://localhost:8080/ \
  -H "Content-Type: application/json" \
  -H "ce-specversion: 1.0" \
  -H "ce-type: review.comment" \
  -H "ce-source: review-system" \
  -H "ce-id: test-123" \
  -d '{
    "comment": "This product exceeded my expectations!"
  }'
```

### Request (CloudEvent via HTTP Structured Mode)

```bash
curl -X POST http://localhost:8080/ \
  -H "Content-Type: application/cloudevents+json" \
  -d '{
    "specversion": "1.0",
    "type": "review.comment",
    "source": "review-system",
    "id": "12345",
    "datacontenttype": "application/json",
    "data": {
      "comment": "This product is absolutely amazing! Best purchase ever!"
    }
  }'
```

### Response (CloudEvent)

```json
{
  "specversion": "1.0",
  "type": "sentiment.rating",
  "source": "sentiment-rating-service",
  "id": "uuid-generated",
  "datacontenttype": "application/json",
  "data": {
    "rating": 5,
    "sentiment": "Very Positive"
  }
}
```

## Configuration

Edit `src/main/resources/application.properties` to customize:

- LLM model
- Temperature
- Max tokens
- Logging levels


Disclaimer: Built with support of Claude