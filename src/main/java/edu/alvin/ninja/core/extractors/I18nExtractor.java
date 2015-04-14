package edu.alvin.ninja.core.extractors;

import com.google.inject.Inject;
import edu.alvin.ninja.core.util.I18nMessage;
import ninja.Context;
import ninja.params.ArgumentExtractor;

public class I18nExtractor implements ArgumentExtractor<I18nMessage> {
    private I18nMessage message;

    @Inject
    public I18nExtractor(I18nMessage messages) {
        this.message = messages;
    }

    @Override
    public I18nMessage extract(Context context) {
        return message;
    }

    @Override
    public Class<I18nMessage> getExtractedType() {
        return I18nMessage.class;
    }

    @Override
    public String getFieldName() {
        return null;
    }
}
