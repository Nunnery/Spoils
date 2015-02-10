/*
 * This file is part of spoils-core, licensed under the ISC License.
 *
 * Copyright (c) 2014 Richard Harrah
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted,
 * provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF
 * THIS SOFTWARE.
 */
package me.topplethenun.spoils.names;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import me.topplethenun.spoils.api.names.NameTable;
import me.topplethenun.spoils.api.names.NameType;

import java.util.ArrayList;
import java.util.List;

public class NameTableImpl implements NameTable {

    private final Table<NameType, String, List<String>> nameTable;

    public NameTableImpl() {
        this.nameTable = HashBasedTable.create();
    }

    @Override
    public List<String> getAvailableNames(NameType nameType, String fileName) {
        Preconditions.checkNotNull(nameType);
        Preconditions.checkNotNull(fileName);
        List<String> availableNames = new ArrayList<>();
        if (nameTable.contains(nameType, fileName)) {
            availableNames.addAll(nameTable.get(nameType, fileName));
        }
        return availableNames;
    }

    @Override
    public List<String> getFileNames(NameType nameType) {
        return new ArrayList<>(nameTable.columnMap().keySet());
    }

    @Override
    public void setAvailableNames(NameType nameType, String fileName, List<String> strings) {
        Preconditions.checkNotNull(nameType);
        Preconditions.checkNotNull(fileName);
        Preconditions.checkNotNull(strings);
        nameTable.put(nameType, fileName, strings);
    }

}
