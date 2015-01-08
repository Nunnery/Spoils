/*
 * This file is part of Spoils, licensed under the ISC License.
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
package me.topplethenun.spoils.hilt;

import org.bukkit.Material;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class HiltBook extends HiltItemStack {

    public HiltBook(TomeType tomeType) {
        super(tomeType.material);
    }

    public String getTitle() {
        createItemMeta();
        if (getItemMeta() instanceof BookMeta && ((BookMeta) getItemMeta()).hasTitle()) {
            return ((BookMeta) getItemMeta()).getTitle();
        }
        return "";
    }

    public HiltBook setTitle(String title) {
        createItemMeta();
        if (getItemMeta() instanceof BookMeta) {
            ((BookMeta) getItemMeta()).setTitle(title);
        }
        return this;
    }

    public List<String> getPages() {
        createItemMeta();
        if (getItemMeta() instanceof BookMeta && ((BookMeta) getItemMeta()).hasPages()) {
            return new ArrayList<>(((BookMeta) getItemMeta()).getPages());
        }
        return new ArrayList<>();
    }

    public HiltBook setPages(List<String> pages) {
        createItemMeta();
        if (getItemMeta() instanceof BookMeta) {
            ((BookMeta) getItemMeta()).setPages(pages);
        }
        return this;
    }

    public enum TomeType {
        BOOK_AND_QUILL(Material.BOOK_AND_QUILL),
        WRITTEN_BOOK(Material.WRITTEN_BOOK);

        private final Material material;

        private TomeType(Material mat) {
            this.material = mat;
        }
    }

}
