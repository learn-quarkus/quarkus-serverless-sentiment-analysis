#!/bin/bash

echo "Testing Neutral Review..."
curl -X POST http://localhost:8080/ \
  -H "Content-Type: application/json" \
  -H "ce-specversion: 1.0" \
  -H "ce-type: review.comment" \
  -H "ce-source: test-client" \
  -H "ce-id: test-$(date +%s)" \
  -d '{
    "comment": "The product is acceptable. It does what it says."
  }' | jq .

echo ""
