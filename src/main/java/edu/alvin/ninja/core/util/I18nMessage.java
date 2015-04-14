package edu.alvin.ninja.core.util;

import com.google.common.base.Optional;
import edu.alvin.ninja.core.filters.ContextProvider;
import ninja.i18n.Messages;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class I18nMessage {
    private Messages messages;
    private ContextProvider contextProvider;

    @Inject
    public I18nMessage(Messages messages, ContextProvider contextProvider) {
        this.messages = messages;
        this.contextProvider = contextProvider;
    }

    public String get(String key, Object...arguments) {
        return messages.get(key, contextProvider.get(), Optional.absent(), arguments).get();
    }
}
