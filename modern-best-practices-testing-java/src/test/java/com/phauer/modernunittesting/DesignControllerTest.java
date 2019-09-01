package com.phauer.modernunittesting;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DesignControllerTest {
    @Nested
    class GetDesigns {
        @Test
        public void allFieldsAreIncluded() {
        }

        @Test
        public void limitParameter() {
        }

        @Test
        public void filterParameter() {
        }
    }

    @Nested
    class DeleteDesign {
        @Test
        public void designIsRemovedFromDb() {
        }

        @Test
        public void return404OnInvalidIdParameter() {
        }

        @Test
        public void return401IfNotAuthorized() {
        }
    }
}
