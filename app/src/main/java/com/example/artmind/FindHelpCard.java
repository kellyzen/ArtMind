package com.example.artmind;

/**
 * Find Help page (card)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class FindHelpCard {
    private String helpCentreName;
    private String helpCentreTitle;
    private String helpCentreWebsite;
    private String helpCentreContact;


    /**
     * Constructor method for Find Help Card
     *
     * @param name    help centre name
     * @param title   help centre title
     * @param website help centre website link
     * @param contact help centre contact numbers
     */
    public FindHelpCard(String name, String title, String website, String contact) {
        helpCentreName = name;
        helpCentreTitle = title;
        helpCentreWebsite = website;
        helpCentreContact = contact;
    }

    /**
     * Getter method for help centre name
     */
    public String getHelpCentreName() {
        return helpCentreName;
    }

    /**
     * Getter method for help centre title
     */
    public String getHelpCentreTitle() {
        return helpCentreTitle;
    }

    /**
     * Getter method for help centre contact
     */
    public String getHelpCentreContact() {
        return helpCentreContact;
    }

    /**
     * Getter method for help centre website
     */
    public String getHelpCentreWebsite() {
        return helpCentreWebsite;
    }
}
