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
package com.tealcube.minecraft.spigot.spoils.names;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.tealcube.minecraft.spigot.spoils.api.names.ResourceTable;
import com.tealcube.minecraft.spigot.spoils.api.names.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class ResourceTableImpl implements ResourceTable {

    private final Table<ResourceType, String, List<String>> nameTable;

    public ResourceTableImpl() {
        this.nameTable = HashBasedTable.create();
    }

    @Override
    public List<String> getAvailableResources(ResourceType resourceType, String fileName) {
        Preconditions.checkNotNull(resourceType);
        Preconditions.checkNotNull(fileName);
        List<String> availableNames = new ArrayList<>();
        if (nameTable.contains(resourceType, fileName)) {
            availableNames.addAll(nameTable.get(resourceType, fileName));
        }
        return availableNames;
    }

    @Override
    public List<String> getFileNames(ResourceType resourceType) {
        return new ArrayList<>(nameTable.columnMap().keySet());
    }

    @Override
    public void setAvailableResources(ResourceType resourceType, String fileName, List<String> strings) {
        Preconditions.checkNotNull(resourceType);
        Preconditions.checkNotNull(fileName);
        Preconditions.checkNotNull(strings);
        nameTable.put(resourceType, fileName, strings);
    }

    @Override
    public int getAmountOfLoadedResources(ResourceType resourceType) {
        Preconditions.checkNotNull(resourceType);
        int i = 0;
        for (String s : getFileNames(resourceType)) {
            i += getAvailableResources(resourceType, s).size();
        }
        return i;
    }

}
