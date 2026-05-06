#!/bin/bash

echo "Testing Positive Review..."
curl -X POST http://localhost:8080/ \
  -H "Content-Type: application/json" \
  -H "ce-specversion: 1.0" \
  -H "ce-type: review.comment" \
  -H "ce-source: test-client" \
  -H "ce-id: test-$(date +%s)" \
  -d '{
    "comment": "This product is absolutely amazing! Best purchase ever!"
  }' | jq .

echo ""
