package com.example.artmind;

public class FindHelpCard {
    private String helpCentreName;
    private String helpCentreTitle;
    private String helpCentreWebsite;
    private String helpCentreContact;


    public FindHelpCard(String name, String title, String website, String contact) {
        helpCentreName = name;
        helpCentreTitle = title;
        helpCentreWebsite = website;
        helpCentreContact = contact;
    }

    public String getHelpCentreName() {
        return helpCentreName;
    }

    public String getHelpCentreTitle() {
        return helpCentreTitle;
    }

    public String getHelpCentreContact() {
        return helpCentreContact;
    }
    public String getHelpCentreWebsite() {
        return helpCentreWebsite;
    }
}
