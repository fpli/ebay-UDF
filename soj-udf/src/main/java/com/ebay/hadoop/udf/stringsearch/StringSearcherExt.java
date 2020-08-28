package com.ebay.hadoop.udf.stringsearch;

import org.neosearch.stringsearcher.Emit;
import org.neosearch.stringsearcher.StringSearcher;

public interface StringSearcherExt<T> extends StringSearcher<T> {
    Emit<T> firstMatch(CharSequence var1, int startPos);

    static SimpleStringSearcherBuilderExt builder() {
        return new SimpleStringSearcherBuilderExt();
    }
}