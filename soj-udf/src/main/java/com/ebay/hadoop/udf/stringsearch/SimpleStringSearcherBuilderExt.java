package com.ebay.hadoop.udf.stringsearch;

import org.neosearch.stringsearcher.SimpleStringSearcherBuilder;
import org.neosearch.stringsearcher.StringSearcher;
import org.neosearch.stringsearcher.StringSearcherBuilder;

import java.util.Collection;

public class SimpleStringSearcherBuilderExt {
    private final StringSearcherBuilderExt<String> stringSearcherBuilder;

    public SimpleStringSearcherBuilderExt() {
        this.stringSearcherBuilder = new StringSearcherBuilderExt<String>();
    }

    /**
     * Configure the Trie to ignore case when searching for keywords in the text.
     * This must be called before calling addSearchString because the algorithm
     * converts keywords to lowercase as they are added, depending on this case
     * sensitivity setting.
     *
     * @return This builder.
     */
    public SimpleStringSearcherBuilderExt ignoreCase() {
        this.stringSearcherBuilder.ignoreCase();
        return this;
    }

    /**
     * Configure the Trie to ignore overlapping keywords.
     *
     * @return This builder.
     */
    public SimpleStringSearcherBuilderExt ignoreOverlaps() {
        this.stringSearcherBuilder.ignoreOverlaps();
        return this;
    }

    /**
     * Adds a keyword to the Trie's list of text search keywords. No Payload is
     * supplied.
     *
     * @param keyword The keyword to add to the list.
     * @return This builder.
     * @throws NullPointerException if the keyword is null.
     */
    public SimpleStringSearcherBuilderExt addSearchString(final String keyword) {
        this.stringSearcherBuilder.addSearchString(keyword);
        return this;
    }

    /**
     * Adds keywords list of text search keywords. No Payload is supplied.
     *
     * @param keywords Keywords to add to the list.
     * @return This builder.
     * @throws NullPointerException if the keyword is null.
     */
    public SimpleStringSearcherBuilderExt addSearchStrings(final String... keywords) {
        this.stringSearcherBuilder.addSearchStrings(keywords);
        return this;
    }

    /**
     * Adds keywords list of text search keywords. No Payload is supplied.
     *
     * @param keywords Keywords to add to the list.
     * @return This builder.
     * @throws NullPointerException if the keyword is null.
     */
    public SimpleStringSearcherBuilderExt addSearchStrings(final Collection<String> keywords) {
        this.stringSearcherBuilder.addSearchStrings(keywords);
        return this;
    }

    /**
     * Configure the Trie to match whole keywords in the text.
     *
     * @return This builder.
     */
    public SimpleStringSearcherBuilderExt onlyWholeWords() {
        this.stringSearcherBuilder.onlyWholeWords();
        return this;
    }

    /**
     * Configure the Trie to match whole keywords that are separated by whitespace
     * in the text. For example, "this keyword thatkeyword" would only match the
     * first occurrence of "keyword".
     *
     * @return This builder.
     */
    public SimpleStringSearcherBuilderExt onlyWholeWordsWhiteSpaceSeparated() {
        this.stringSearcherBuilder.onlyWholeWordsWhiteSpaceSeparated();
        return this;
    }

    /**
     * Configure the Trie to stop after the first keyword is found in the text.
     *
     * @return This builder.
     */
    public SimpleStringSearcherBuilderExt stopOnHit() {
        this.stringSearcherBuilder.stopOnHit();
        return this;
    }

    /**
     * Configure the PayloadTrie based on the builder settings.
     *
     * @return The configured PayloadTrie.
     */
    public StringSearcherExt<String> build() {
        return this.stringSearcherBuilder.build();
    }
}