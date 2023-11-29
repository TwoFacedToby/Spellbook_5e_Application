package com.example.spellbook5eapplication.test

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["features"],
    glue = ["com.example.spellbook5eapplication.test"]
)
class CucumberTestOptions