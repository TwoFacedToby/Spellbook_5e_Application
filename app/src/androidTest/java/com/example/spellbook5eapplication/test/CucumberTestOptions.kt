package com.dtu.uemad.cucumbertest.test

import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith
import io.cucumber.junit.Cucumber

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["features"],
    glue = ["com.dtu.uemad.cucumbertest"]
)
class CucumberTestOptions
