package hu.nive.ujratervezes.licensemanager;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class License {
    private String name;
    private LocalDate offDate;
    private Set<String> users = new HashSet<>();

    public License(String name, LocalDate offDate, Set<String> users) {
        this.name = name;
        this.offDate = offDate;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public LocalDate getOffDate() {
        return offDate;
    }

    public Set<String> getUsers() {
        return new HashSet<>(users);
    }

    public int getTimeLeftUntilExpiration(LocalDate now) {
        return 0;
    }
}
