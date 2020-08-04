package com.basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrganizationSection extends Section {
    private static final long serialVersionUID = 1L;

    private List<Organization> organizations;

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations.equals(that.organizations);

    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toHtml(Section section) {
        StringBuilder result = null;
        for (Organization org : organizations) {
            String link = org.getHomePage().toString();
            String pos = org.getPositions().stream()
                    .map(Organization.Position::toString)
                    .collect(Collectors.joining(" <br/>", "{", "}"));
            result.append(link).append(" <br/>").append(pos).append(" <br/>");
        }
        if (section == null) {
            return "";
        } else {
            assert result != null;
            return result.toString();
        }
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}