package hu.nive.ujratervezes.licensemanager;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class LicenseManager {
    List<License> licenses = new ArrayList<>();

    public LicenseManager(List<License> licenses) {
        if (licenses == null) {
            throw new IllegalArgumentException();
        }
        this.licenses = licenses;
    }

    public List<License> findLicensesValidFor10MoreYears(LocalDate now) {
        List<License> find = licenses.stream()
                .filter(n->n.getOffDate().isBefore(now.plusYears(10)))
                .toList();
        return new ArrayList<>(find);
    }

    public List<License> findExpiredLicenses(LocalDate now) {
        return licenses.stream()
                .filter(n->n.getOffDate().isBefore(now))
                .toList();
    }

    public Map<LocalDate, Long> findExpiresNextYear(LocalDate now) {
        return new HashMap<>();
    }

    public List<License> findLicensesWithUsers(Set<String> users) {
        if (users == null || users.size() == 0) {
            throw new IllegalArgumentException();
        }
        return licenses.stream()
                .filter(n->n.getUsers().containsAll(users))
                .toList();

        //return new ArrayList<>();
    }

    public Map<Month, List<String>> getNamesPerMonth() {
        return new HashMap<>();

    }
}
