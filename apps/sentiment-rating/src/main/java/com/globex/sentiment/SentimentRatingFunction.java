package com.globex.sentiment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.funqy.Funq;
import io.quarkus.funqy.knative.events.CloudEventMapping;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

public class SentimentRatingFunction {

    private static final Logger LOG = Logger.getLogger(SentimentRatingFunction.class);

    @Inject
    SentimentAiService sentimentAiService;

    @Inject
    ObjectMapper objectMapper;

    @Funq
    @CloudEventMapping(trigger = "review.comment", responseType = "sentiment.rating", responseSource = "sentiment-rating-service")
    public SentimentRating processReview(ReviewComment reviewComment) {
        try {
            LOG.infof("Processing review comment: %s", reviewComment.comment());

            String aiResponse = sentimentAiService.analyzeReview(reviewComment.comment());
            LOG.infof("AI Response: %s", aiResponse);

            // Clean up the AI response to extract JSON
            String cleanedResponse = cleanJsonResponse(aiResponse);

            SentimentRating rating = objectMapper.readValue(cleanedResponse, SentimentRating.class);

            LOG.infof("Sentiment Rating: %d - %s", rating.score(), rating.response());

            return rating;

        } catch (JsonProcessingException e) {
            LOG.error("Error parsing AI response", e);
            // Return a neutral rating on error
            return new SentimentRating(3, "Neutral");
        } catch (Exception e) {
            LOG.error("Error processing review event", e);
            throw new RuntimeException("Failed to process review event", e);
        }
    }

    private String cleanJsonResponse(String response) {
        String cleaned = response.trim();

        // Remove markdown code blocks
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        }
        if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3);
        }
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }

        return cleaned.trim();
    }
}
