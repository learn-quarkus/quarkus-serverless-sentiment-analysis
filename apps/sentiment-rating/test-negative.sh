#!/bin/bash

echo "Testing Negative Review..."
curl -X POST http://localhost:8080/ \
  -H "Content-Type: application/json" \
  -H "ce-specversion: 1.0" \
  -H "ce-type: review.comment" \
  -H "ce-source: test-client" \
  -H "ce-id: test-$(date +%s)" \
  -d '{
    "comment": "Terrible product. Complete waste of money. Very disappointed."
  }'

echo ""
