package com.epam.test_generator.controllers.CaseVersion.response;

import java.util.List;

/**
 * This DTO is used for organise history of test cases. Case version has information about all changes of the current
 * case. It includes list of {@Link PropertyDifferenceGetDTO} objects, which contains information about properties that have
 * been changed.
 */
public class CaseVersionGetDTO {

    private String commitId;

    private String updatedDate;

    private String author;

    private List<PropertyDifferenceGetDTO> propertyDifferences;

    public CaseVersionGetDTO() {

    }

    public CaseVersionGetDTO(String commitId, String updatedDate, String author,
                             List<PropertyDifferenceGetDTO> propertyDifferences) {
        this.commitId = commitId;
        this.updatedDate = updatedDate;
        this.author = author;
        this.propertyDifferences = propertyDifferences;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<PropertyDifferenceGetDTO> getPropertyDifferences() {
        return propertyDifferences;
    }

    public void setPropertyDifferences(
        List<PropertyDifferenceGetDTO> propertyDifferences) {
        this.propertyDifferences = propertyDifferences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CaseVersionGetDTO that = (CaseVersionGetDTO) o;

        return (commitId != null ? commitId.equals(that.commitId) : that.commitId == null)
            && (updatedDate != null ? updatedDate.equals(that.updatedDate)
            : that.updatedDate == null)
            && (author != null ? author.equals(that.author) : that.author == null)
            && (propertyDifferences != null ? propertyDifferences
            .equals(that.propertyDifferences)
            : that.propertyDifferences == null);
    }

    @Override
    public int hashCode() {
        int result = commitId != null ? commitId.hashCode() : 0;
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (propertyDifferences != null ? propertyDifferences.hashCode() : 0);
        return result;
    }
}
