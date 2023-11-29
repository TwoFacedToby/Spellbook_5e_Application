package com.example.spellbook5eapplication.test

import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.junit.Assert


class PrintToConsoleTest {
    @Given("I want to print something to console")
    fun i_want_to_print_something_to_console() {
        // Set up your test environment here
        println("Given step executed")
    }

    @When("I run the println method")
    fun i_run_the_println_method() {
        // Perform your action here
        println("When step executed")
    }

    @Then("I should see a print in the console")
    fun i_should_see_a_print_in_the_console() {
        // Assert the expected outcome here
        println("Then step executed")
        Assert.assertTrue(true) // Simple assertion for demonstration
    }
}