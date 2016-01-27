/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.model;

/** Generated. **/
public class Language {
    /* Language */
    private String language = null;
    /* ISO tag */
    private String iso_tag = null;
    /* Get Wiki URL */
    private String wiki_url = null;
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getIso_tag() {
        return iso_tag;
    }
    
    public void setIso_tag(String iso_tag) {
        this.iso_tag = iso_tag;
    }
    
    public String getWiki_url() {
        return wiki_url;
    }
    
    public void setWiki_url(String wiki_url) {
        this.wiki_url = wiki_url;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LanguageRest {\n");
        sb.append("    language: ").append(language).append("\n");
        sb.append("    iso_tag: ").append(iso_tag).append("\n");
        sb.append("    wiki_url: ").append(wiki_url).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
