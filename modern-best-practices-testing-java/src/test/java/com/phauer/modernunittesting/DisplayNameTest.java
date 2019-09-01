package com.phauer.modernunittesting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DisplayNameTest {
    @Test
    @DisplayName("Design is removed from database")
    void designIsRemoved() {
    }

    @Test
    @DisplayName("Return 404 in case of an invalid parameter")
    void return404() {
    }

    @Test
    @DisplayName("Return 401 if the request is not authorized")
    void return401() {
    }
}


//class DesignControllerTest {
//    @Test
//    fun `design is removed from db`() {
//    }
//    @Test
//    fun `return 404 on invalid id parameter`() {
//    }
//
//    @Test
//    fun `return 401 if not authorized`() {
//    }
//}
