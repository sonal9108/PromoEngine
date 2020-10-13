package com.example.PromotionEngine.PromotionEngine;

import com.example.PromotionEngine.PromotionEngine.Model.PromoDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class PromoResponseConverter implements AttributeConverter<PromoDetails, String> {

    private static final Logger logger = LoggerFactory.getLogger(PromoResponseConverter.class);

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    @Override
    public String convertToDatabaseColumn(PromoDetails promoDetails) {
        String promoRequestJson = null;
        try{
            promoRequestJson = objectMapper.writeValueAsString(promoDetails);
        }
        catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }
        return promoRequestJson;
    }

    @Override
    public PromoDetails convertToEntityAttribute(String promoRequestJson) {
        PromoDetails promoDetails = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            promoDetails = objectMapper.readValue(promoRequestJson, PromoDetails.class);
        } catch (final IOException e) {
            logger.error("Unable to convert to entity", e);
        }
        return promoDetails;
    }
}
