package hu.nive.ujratervezes.licensemanager;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LicenseClassifierTest {
    private final List<License> licenses = new ArrayList<>();
    private LocalDate now;
    private LicenseManager licenseManager;

    @BeforeEach
    void setUp() {
        licenses.add(new License("Microsoft Office 2000", LocalDate.of(1960, Month.APRIL, 2), Set.of("peter87@hotmail.com", "kitkatfan404@live.com", "kitkatfan404@citromail.hu", "tinaturner@microsoft.org", "happybaby@hotmail.com")));
        licenses.add(new License("IntelliJ Idea Ultimate", LocalDate.of(2092, Month.JUNE, 22), Set.of("fradi@hotmail.com", "jamieoliver@mail.com", "bigwaves@mail.com")));
        licenses.add(new License("Adobe Photoshop", LocalDate.of(1960, Month.MARCH, 2), Set.of("butterfly@yahoomail.com", "beachwhale@yahoo.es", "sprinty@mail.com")));
        licenses.add(new License("Dreamweaver MX", LocalDate.of(2109, Month.FEBRUARY, 8), Set.of("kitkatfan404@citromail.hu", "highspeed567@mail.com")));
        licenses.add(new License("WinZip", LocalDate.of(1960, Month.MARCH, 12), Set.of("peter87@hotmail.com", "kitkatfan404@live.com", "kitkatfan404@citromail.hu")));
        licenses.add(new License("MS Paint", LocalDate.of(1960, Month.SEPTEMBER, 7), Set.of("sprinty@mail.com", "fradi@hotmail.com", "kitkatfan404@citromail.hu")));
        licenses.add(new License("Visual Studio Code", LocalDate.of(2111, Month.FEBRUARY, 8), Set.of("peter87@hotmail.com", "aladinn@mail.com", "SAIL@gmail.com")));
        licenses.add(new License("Visual Studio Code", LocalDate.of(2103, Month.JUNE, 8), Set.of("evasmith@hotmail.com")));
        licenses.add(new License("IntelliJ Idea Ultimate", LocalDate.of(2084, Month.FEBRUARY, 8), Set.of("peter87@hotmail.com", "kitkatfan404@live.com")));
        licenses.add(new License("Unity 3D", LocalDate.of(2111, Month.JUNE, 3), Set.of("peter87@hotmail.com", "tinaturner@microsoft.org", "kitkatfan404@citromail.hu")));
        licenses.add(new License("EA Sims 4 PC", LocalDate.of(2022, Month.APRIL, 8), Set.of("peter87@hotmail.com", "federerisuppose@live.it")));

        now = LocalDate.of(2022, Month.JANUARY, 10);
        licenseManager = new LicenseManager(licenses);
    }

    @Order(1)
    @Test
    void testIfLicenseManagerThrowsException() {
        assertThatThrownBy(() -> new LicenseManager(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Order(2)
    @Test
    void testLicenseCanTellExpiryDate() {
        License t = new License("A", now.plus(10, ChronoUnit.YEARS), null);
        assertThat(t.getTimeLeftUntilExpiration(now)).isEqualTo(10);
        License t2 = new License("A", now.plus(20, ChronoUnit.YEARS), null);
        assertThat(t2.getTimeLeftUntilExpiration(now)).isEqualTo(20);
        License t3 = new License("A", now.plus(42, ChronoUnit.YEARS), null);
        assertThat(t3.getTimeLeftUntilExpiration(now)).isEqualTo(42);
    }

    @Order(3)
    @Test
    void test_findLicensesValidFor10MoreYears() {
        licenses.add(new License("EA Sims 4 PC", LocalDate.of(2022, Month.APRIL, 8), Set.of("peter87@hotmail.com", "federerisuppose@live.it")));
        licenses.add(new License("EA Sims 5 PC", LocalDate.of(2026, Month.APRIL, 18), Set.of("peter87@hotmail.com", "federerisuppose@live.it")));
        licenses.add(new License("EA Sims 6 PC", LocalDate.of(2031, Month.APRIL, 28), Set.of("peter87@hotmail.com", "federerisuppose@live.it")));
        assertThat(licenseManager.findLicensesValidFor10MoreYears(now))
                .isNotEmpty()
                .hasSize(4)
                .allMatch(t -> t.getTimeLeftUntilExpiration(now) <= 10);
    }

    @Order(4)
    @Test
    void test_findExpiredLicenses() {
        LocalDate past = LocalDate.of(1522, Month.JANUARY, 10);
        LocalDate middle = LocalDate.of(2000, Month.JANUARY, 10);
        LocalDate farFuture = LocalDate.of(3000, Month.JANUARY, 10);
        assertThat(licenseManager.findExpiredLicenses(past)).isEmpty();
        assertThat(licenseManager.findExpiredLicenses(farFuture)).hasSameSizeAs(licenses);
        assertThat(licenseManager.findExpiredLicenses(middle)).hasSize(4);
    }

    @Order(5)
    @Test
    void test_findLicensesWithUsersIsCalledWithEnoughUsers() {
        assertThatIllegalArgumentException().isThrownBy(() -> licenseManager.findLicensesWithUsers(null));
        assertThatIllegalArgumentException().isThrownBy(() -> licenseManager.findLicensesWithUsers(Collections.emptySet()));
        assertThat(licenseManager.findLicensesWithUsers(Set.of("peter87@hotmail.com"))).isNotNull();
    }

    @Order(6)
    @Test
    void test_findLicensesWithUsersReturnsTheProperAnswer() {
        Set<String> users1 = Set.of("peter87@hotmail.com", "kitkatfan404@citromail.hu");
        List<License> g1 = licenseManager.findLicensesWithUsers(users1);
        assertThat(g1)
                .isNotEmpty()
                .hasSize(8)
                .allMatch(p -> p.getUsers().stream().anyMatch(users1::contains));

        Set<String> users2 = Set.of("sprinty@mail.com", "kitkatfan404@citromail.hu");
        List<License> g2 = licenseManager.findLicensesWithUsers(users2);
        assertThat(g2)
                .isNotEmpty()
                .hasSize(6)
                .allMatch(p -> p.getUsers().stream().anyMatch(users2::contains));

        Set<String> users3 = Set.of("peter87@hotmail.com", "kitkatfan404@live.com");
        List<License> g3 = licenseManager.findLicensesWithUsers(users3);
        assertThat(g3)
                .isNotEmpty()
                .hasSize(6)
                .allMatch(p -> p.getUsers().stream().anyMatch(users3::contains));
    }

    @Order(7)
    @Test
    void testGetNamesPerMonth() {
        Map<Month, List<String>> licensesPerMonth = licenseManager.getNamesPerMonth();
        assertThat(licensesPerMonth).hasSize(5);
        assertThat(licensesPerMonth.get(Month.FEBRUARY)).containsExactlyInAnyOrder("Dreamweaver MX", "Visual Studio Code", "IntelliJ Idea Ultimate");
        assertThat(licensesPerMonth.get(Month.JUNE)).containsExactlyInAnyOrder("IntelliJ Idea Ultimate", "Visual Studio Code", "Unity 3D");
    }

    @Order(8)
    @Test
    void testFindExpirationDateInNextYear() {
        Map<LocalDate, Long> expirationDates = licenseManager.findExpiresNextYear(now);
        assertThat(expirationDates)
                .hasSize(1)
                .containsEntry(LocalDate.of(2022, 4, 8), 1L);
    }
}