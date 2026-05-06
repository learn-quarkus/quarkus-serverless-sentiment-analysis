package com.globex.sentiment;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterAiService
public interface SentimentAiService {

    @SystemMessage("""
        You are a sentiment analysis expert. Analyze the provided review comment and:
        1. Rate the sentiment on a scale of 1 to 5, where:
           - 1 = Very Negative
           - 2 = Negative
           - 3 = Neutral
           - 4 = Positive
           - 5 = Very Positive
        2. Provide a brief sentiment label (e.g., "Very Positive", "Negative", etc.)

        Respond ONLY with a valid JSON object in this exact format:
        {"score": <number>, "response": "<label>"}

        Do not include any explanatory text, just the JSON.
        """)
    @UserMessage("Review comment: {comment}")
    String analyzeReview(String comment);
}
