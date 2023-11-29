package com.dtu.uemad.cucumbertest.test

import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.junit.Assert


class SimpleTestSteps {
    @Given("I have a simple test environment")
    fun i_have_a_simple_test_environment() {
        // Set up your test environment here
        println("Given step executed")
    }

    @When("I perform a simple action")
    fun i_perform_a_simple_action() {
        // Perform your action here
        println("When step executed")
    }

    @Then("I should get a simple result")
    fun i_should_get_a_simple_result() {
        // Assert the expected outcome here
        println("Then step executed")
        Assert.assertTrue(true) // Simple assertion for demonstration
    }
}