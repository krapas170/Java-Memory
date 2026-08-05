package de.krapas170.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ZeitFormatTest {

    @Test
    void formatiertMinutenUndSekunden() {
        assertEquals("00:00", ZeitFormat.formatiere(0));
        assertEquals("00:09", ZeitFormat.formatiere(9));
        assertEquals("01:35", ZeitFormat.formatiere(95));
        assertEquals("59:59", ZeitFormat.formatiere(3599));
    }

    @Test
    @DisplayName("Ueber einer Stunde: frueher kam hier '61:-3560' heraus")
    void formatiertStunden() {
        assertEquals("1:00:00", ZeitFormat.formatiere(3600));
        assertEquals("1:01:40", ZeitFormat.formatiere(3700));
        assertEquals("2:30:00", ZeitFormat.formatiere(9000));
    }

    @Test
    void negativeWerteWerdenZuNull() {
        assertEquals("00:00", ZeitFormat.formatiere(-5));
    }
}
